package com.eo.dig.service;

import com.eo.dig.entity.EoOrderDetail;

import java.util.List;

public interface EoOrderDetailService {
    List<String> save(List<EoOrderDetail> list);
}
