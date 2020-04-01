package com.eo.dig.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "eo_order_detail")
public class EoOrderDetail {
    @Id
    private String order_no;

    @Field("mobile")
    private String mobile;

    @Field("s_id")
    private String shop_id;

    @Field("p_t")
    private Date pay_time;

    @Field("c_no")
    private String city_code;

    @Field("price")
    private Double pay_price;

}
