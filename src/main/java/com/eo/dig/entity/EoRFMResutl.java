package com.eo.dig.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class EoRFMResutl {

    private double recency;

    private double frequency;

    private double monetary;

}
