package com.eo.dig.dao;

import com.eo.dig.entity.EoOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author ：zw
 * @description TODO
 * @date ：Created in 2019/5/17 16:30
 * @version: ：1.0
 */
@Component
public class EoOrderDetailDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     */
    public EoOrderDetail saveEoOrderDetail(EoOrderDetail eoOrderDetail) {
        return mongoTemplate.save(eoOrderDetail);
    }

    /**
     * 根据用户名查询对象
     *
     * @return
     */
    public EoOrderDetail findTestByName(String name) {
        Query query = new Query(Criteria.where("mobile").is(name));
        EoOrderDetail mgt = mongoTemplate.findOne(query, EoOrderDetail.class);
        return mgt;
    }

    /**
     * 更新对象
     */
    public void updateTest(EoOrderDetail eoOrderDetail) {
        Query query = new Query(Criteria.where("order_no").is(eoOrderDetail.getOrder_no()));
        Update update = new Update().set("shop_id", eoOrderDetail.getShop_id()).set("city_code", eoOrderDetail.getCity_code());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, EoOrderDetail.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
    }

    /**
     * 删除对象
     *
     * @param order_no
     */
    public void deleteTestById(String order_no) {
        Query query = new Query(Criteria.where("order_no").is(order_no));
        mongoTemplate.remove(query, EoOrderDetail.class);
    }

}
