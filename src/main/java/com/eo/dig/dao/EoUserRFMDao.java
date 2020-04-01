package com.eo.dig.dao;

import com.eo.dig.entity.EoUserRFM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：zw
 * @description TODO
 * @date ：Created in 2019/5/17 16:30
 * @version: ：1.0
 */
@Component
public class EoUserRFMDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public EoUserRFM saveEoUserRFM(EoUserRFM eoUserRFM) {
        return mongoTemplate.save(eoUserRFM);
    }

    /**
     * 根据用户名查询对象
     *
     * @return
     */
    public EoUserRFM findTestByName(String name) {
        Query query = new Query(Criteria.where("mobile").is(name));
        EoUserRFM mgt = mongoTemplate.findOne(query, EoUserRFM.class);
        return mgt;
    }

    /**
     * 根据用户名查询对象
     *
     * @return
     */
    public EoUserRFM findUserRFMByMobleAndCityNo(EoUserRFM eoUserRFM) {
        Query query = new Query(Criteria.where("mobile").is(eoUserRFM.getMobile()).and("c_no").is(eoUserRFM.getCity_code()));
        EoUserRFM mgt = mongoTemplate.findOne(query, EoUserRFM.class);
        return mgt;
    }


    /**
     * 更新RFM用户总数
     *
     * @return
     */
    public int findUserRFMListSize() {
        Query query = new Query(Criteria.where("ss").is(0));
        return mongoTemplate.find(query, EoUserRFM.class).size();
    }

    /**
     * 批量获取用户
     *
     * @return
     */
    public List<EoUserRFM> findUserRFMList() {
        Query query = new Query(Criteria.where("ss").is(0));
        query.limit(1000);
        List<EoUserRFM> list = mongoTemplate.find(query, EoUserRFM.class);
        return list;
    }


    /**
     * 清空用户RFM
     *
     * @return
     */
    public void upUserRFMAll() {
        Update update = new Update();
        update.set("ss", 0);
        update.set("r", 0);
        update.set("f", 0);
        update.set("m", 0);
        mongoTemplate.updateMulti(new Query(), update, EoUserRFM.class);
    }


    /**
     * 更新用户RFM
     *
     * @return
     */
    public void upUserRFM(EoUserRFM eoUserRFM) {
        Update update = new Update();
        update.set("ss", eoUserRFM.getStauts());
        update.set("r", eoUserRFM.getRecency());
        update.set("f", eoUserRFM.getFrequency());
        update.set("m", eoUserRFM.getMonetary());
        Query query = new Query(Criteria.where("_id").is(eoUserRFM.getId()));
        mongoTemplate.updateMulti(query, update, EoUserRFM.class);
    }

    /**
     * 更新对象
     */
    public void updateTest(EoUserRFM eoOrderDetail) {
//        Query query = new Query(Criteria.where("order_no").is(eoOrderDetail.getOrder_no()));
//        Update update = new Update().set("shop_id", eoOrderDetail.getShop_id()).set("city_code", eoOrderDetail.getCity_code());
//        //更新查询返回结果集的第一条
//        mongoTemplate.updateFirst(query, update, EoOrderDetail.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
    }

    /**
     * 删除对象
     *
     * @param order_no
     */
    public void deleteTestById(String order_no) {
//        Query query = new Query(Criteria.where("order_no").is(order_no));
//        mongoTemplate.remove(query, EoOrderDetail.class);
    }

}
