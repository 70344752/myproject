package com.eo.dig.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "eo_user_rfm")
public class EoUserRFM {

    @Id
    private String id;

    @Field("mobile")
    private String mobile;

    @Field("c_no")
    private String city_code;

    @Field("r")
    private Integer recency;

    @Field("f")
    private Integer frequency;

    @Field("m")
    private Integer monetary;

    @Field("type")
    private Integer type;

    @Field("ss")
    private Integer stauts;

}
