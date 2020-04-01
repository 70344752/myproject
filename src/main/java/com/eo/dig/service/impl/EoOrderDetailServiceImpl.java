package com.eo.dig.service.impl;

import com.eo.dig.dao.EoOrderDetailDao;
import com.eo.dig.dao.EoUserRFMDao;
import com.eo.dig.entity.EoOrderDetail;
import com.eo.dig.entity.EoUserRFM;
import com.eo.dig.service.EoOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ：zw
 * @description TODO
 * @date ：Created in 2019/5/21 16:35
 * @version: ：1.0
 */
@Service
public class EoOrderDetailServiceImpl implements EoOrderDetailService {

    @Autowired
    private EoOrderDetailDao eoOrderDetailDao;

    @Autowired
    private EoUserRFMDao eoUserRFMDao;

    @Override
    public List<String> save(List<EoOrderDetail> list) {
        List<String> relist = new LinkedList<>();
        if (list != null) {
            for (EoOrderDetail detail : list) {
                saveRFM(detail.getMobile(), detail.getCity_code());
                EoOrderDetail returnDeatil = eoOrderDetailDao.saveEoOrderDetail(detail);
                if (returnDeatil != null) {
                    relist.add(returnDeatil.getOrder_no());
                }
            }
        }

        return relist;
    }


    public void saveRFM(String mobile, String city_code) {
        EoUserRFM eoUserRFM = new EoUserRFM();
        eoUserRFM.setCity_code(city_code);
        eoUserRFM.setMobile(mobile);
        if (eoUserRFMDao.findUserRFMByMobleAndCityNo(eoUserRFM) == null) {
            eoUserRFMDao.saveEoUserRFM(eoUserRFM);
        }

        eoUserRFM.setCity_code("0000");  //代表不分地区
        if (eoUserRFMDao.findUserRFMByMobleAndCityNo(eoUserRFM) == null) {
            eoUserRFMDao.saveEoUserRFM(eoUserRFM);
        }

    }

}
