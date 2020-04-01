package com.eo.dig.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "eo_basic_city")
public class EoBasicCity {

    @Id
    private String city_code;

    @Field("name")
    private String name;

    @Field("type")
    private Integer type;

    @Field("ss")
    private Integer stauts;

}
