package com.eo.dig.job;

import com.eo.dig.dao.EoUserRFMDao;
import com.eo.dig.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ：zw
 * @description TODO
 * @date ：Created in 2019/5/22 10:19
 * @version: ：1.0
 */

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class EoUserRfmTask {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EoUserRFMDao eoUserRFMDao;


    @Scheduled(cron = "0 26 14 * * ?")
    //@Scheduled(fixedRate=5000)
    private synchronized void task() {
        //清空用户RFM
        eoUserRFMDao.upUserRFMAll();

        //计算用户RFM
        long start = System.currentTimeMillis();
        System.out.println("----------------计算RFM开始：" + start);
        int count = eoUserRFMDao.findUserRFMListSize() / 1000 + 10;
        System.out.println(count);
        while (count >= 0) {
            System.out.println("count" + count);
            computeValue();
            count -= 1;
        }
        long end = System.currentTimeMillis();
        System.out.println("----------------计算RFM结束：" + end);
        System.out.println("----------------计算RFM计算时间：" + (start - end) / 1000);

        //==============================================================

        //计算type
        computeType();
    }

    private void computeType() {
        //---获取城市
        List<EoBasicCity> list = mongoTemplate.find(new Query(), EoBasicCity.class);
        for (EoBasicCity city : list) {
            //获取RFM平均值
            Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("c_no").is(city.getCity_code())), Aggregation.group("*")
                    .avg("r").as("recency")
                    .avg("f").as("frequency")
                    .avg("m").as("monetary")
            );
            AggregationResults<EoRFMResutl> dbObjects = mongoTemplate.aggregate(aggregation, "eo_user_rfm", EoRFMResutl.class);
            EoRFMResutl eoRFMResutl = dbObjects.getUniqueMappedResult();
            EoCityRFM eoCityRFM = new EoCityRFM();
            eoCityRFM.setCity_code(city.getCity_code());
            BigDecimal bg = new BigDecimal(eoRFMResutl.getRecency());
            //保留两位小数
            double r = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            bg = new BigDecimal(eoRFMResutl.getFrequency());
            double f = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            bg = new BigDecimal(eoRFMResutl.getMonetary());
            double m = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            eoCityRFM.setRecency(r);
            eoCityRFM.setFrequency(f);
            eoCityRFM.setMonetary(m);
            //计算类型
            upDateUserRFMType(eoCityRFM);


            eoCityRFM.setC_time(new Date());
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            int year = calendar.get(Calendar.YEAR);
            eoCityRFM.setDay(day);
            eoCityRFM.setMonth(month);
            eoCityRFM.setYear(year);

            //统计每个类型用户人数
            List<Integer> countList = new LinkedList<>();
            countList.add(mongoTemplate.find(new Query(Criteria.where("c_no").is(city.getCity_code())), EoUserRFM.class).size());
            for (int i = 1; i < 9; i++) {
                int size = mongoTemplate.find(new Query(Criteria.where("type").is(i)
                        .and("c_no").is(city.getCity_code())), EoUserRFM.class).size();
                countList.add(size);
            }
            eoCityRFM.setCountList(countList);
            mongoTemplate.save(eoCityRFM);
        }


    }

    /**
     * 计算用户类型
     *
     * @param eoCityRFM
     */
    private void upDateUserRFMType(EoCityRFM eoCityRFM) {
        Query query = new Query();
        Update update = new Update();
        update.set("type", 8);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").gte(eoCityRFM.getRecency())
                .and("f").gte(eoCityRFM.getFrequency())
                .and("m").gte(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 7);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").lt(eoCityRFM.getRecency())
                .and("f").gte(eoCityRFM.getFrequency())
                .and("m").gte(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 6);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").gte(eoCityRFM.getRecency())
                .and("f").lt(eoCityRFM.getFrequency())
                .and("m").gte(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 5);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").lt(eoCityRFM.getRecency())
                .and("f").lt(eoCityRFM.getFrequency())
                .and("m").gte(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 4);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").gte(eoCityRFM.getRecency())
                .and("f").gte(eoCityRFM.getFrequency())
                .and("m").lt(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 3);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").gte(eoCityRFM.getRecency())
                .and("f").lt(eoCityRFM.getFrequency())
                .and("m").lt(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 2);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").lt(eoCityRFM.getRecency())
                .and("f").gte(eoCityRFM.getFrequency())
                .and("m").lt(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        query = new Query();
        update.set("type", 1);
        query.addCriteria(Criteria.where("c_no").is(eoCityRFM.getCity_code())
                .and("r").lt(eoCityRFM.getRecency())
                .and("f").lt(eoCityRFM.getFrequency())
                .and("m").lt(eoCityRFM.getMonetary())
        );
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);
    }

    //计算用户RFM值
    private void computeValue() {
        List<EoUserRFM> list = eoUserRFMDao.findUserRFMList();
        for (EoUserRFM eoUserRFM : list) {

            //获取用户改区域一年内订单统计（次数、总金额、距最近交易天数）
            List<Criteria> criteriaList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            //设置可用开始时间
            calendar.roll(Calendar.YEAR, -1);
            criteriaList.add(Criteria.where("mobile").is(eoUserRFM.getMobile()));
            criteriaList.add(Criteria.where("p_t").gte(calendar.getTime()));
            if (!eoUserRFM.getCity_code().equals("0000")) {
                criteriaList.add(Criteria.where("c_no").is(eoUserRFM.getCity_code()));
            }

            Criteria[] arr = new Criteria[criteriaList.size()];
            criteriaList.toArray(arr);
            Criteria criteria = new Criteria();
            criteria.andOperator(arr);
            Query query = new Query(criteria);
            query.with(new Sort(Sort.Direction.DESC, "p_t"));
            List<EoOrderDetail> eoOrderDetailList = mongoTemplate.find(query, EoOrderDetail.class);
            int count = eoOrderDetailList.size();
            double price = 0;
            long day = 0;
            for (EoOrderDetail eoOrderDetail : eoOrderDetailList) {
                price += eoOrderDetail.getPay_price();
            }
            if (eoOrderDetailList != null && eoOrderDetailList.size() != 0) {
                day = (new Date().getTime() - eoOrderDetailList.get(0).getPay_time().getTime()) / (1000 * 3600 * 24);
            }
            computeUserRFM(eoUserRFM, count, price, day);
        }
    }

    /**
     * 根据 交易次数、总结、最近交易天数计算RFM
     *
     * @param eoUserRFM
     * @param count
     * @param price
     * @param day
     */
    private void computeUserRFM(EoUserRFM eoUserRFM, int count, double price, long day) {
        eoUserRFM.setRecency(computeR(day));
        eoUserRFM.setFrequency(computeF(count));
        eoUserRFM.setMonetary(computeM(price));
        eoUserRFM.setStauts(1);
        eoUserRFMDao.upUserRFM(eoUserRFM);
    }


    //计算R
    private int computeR(long day) {
        if (day <= 30) {
            return 7;
        } else if (day <= 60) {
            return 6;
        } else if (day <= 120) {
            return 5;
        } else if (day <= 180) {
            return 4;
        } else if (day <= 240) {
            return 3;
        } else if (day <= 300) {
            return 2;
        } else if (day <= 360) {
            return 1;
        }
        return 0;
    }


    //计算F
    private int computeF(int count) {
        if (count == 0) {
            return 0;
        } else if (count <= 1) {
            return 1;
        } else if (count <= 3) {
            return 2;
        } else if (count <= 5) {
            return 3;
        } else if (count <= 7) {
            return 4;
        } else if (count <= 9) {
            return 5;
        } else if (count <= 11) {
            return 6;
        }
        return 7;
    }


    //计算M
    private int computeM(double price) {
        if (price <= 100) {
            return 0;
        } else if (price <= 200) {
            return 1;
        } else if (price <= 300) {
            return 2;
        } else if (price <= 400) {
            return 3;
        } else if (price <= 500) {
            return 4;
        } else if (price <= 600) {
            return 5;
        } else if (price <= 700) {
            return 6;
        }
        return 7;
    }

}
