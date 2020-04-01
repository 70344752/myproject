package com.eo.dig.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "eo_city_rfm")
public class EoCityRFM {

    @Id
    private String id;

    @Field("c_no")
    private String city_code;

    @Field("r")
    private double recency;

    @Field("f")
    private double frequency;

    @Field("m")
    private double monetary;

    @Field("type")
    private Integer type;

    @Field("ss")
    private Integer stauts;

    @Field("c_t")
    private Date c_time;

    @Field("day")
    private Integer day;

    @Field("month")
    private Integer month;

    @Field("year")
    private Integer year;

    @Field("c_list")
    private List countList;

}
