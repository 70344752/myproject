package com.eo.dig.controller;

import com.alibaba.fastjson.JSON;
import com.eo.dig.dao.EoUserRFMDao;
import com.eo.dig.entity.*;
import com.eo.dig.service.EoOrderDetailService;
import com.eo.dig.util.DESDZFP;
import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.BasicDBObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * @author ：zw
 * @description TODO
 * @date ：Created in 2019/5/17 16:50
 * @version: ：1.0
 */

@RestController
public class EoOrderDetailController {

    @Autowired
    private EoOrderDetailService eoOrderDetailService;

    @Autowired
    private EoUserRFMDao eoUserRFMDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 3000;

    // 读数据超时时间
    private static final int READ_DATA_TIMEOUT = 3000;

    private static final int successCode = 200;

    @GetMapping(value = "/test1")
    public void saveTest() throws Exception {
//        EoOrderDetail mgtest = new EoOrderDetail();
//        mgtest.setMobile("13950238841");
//        mgtest.setCity_code("33");
//        mgtest.setOrder_no("00007");
//        mgtest.setPay_price(new Double(100));
//        mgtest.setShop_id("00001");
//        mgtest.setPay_time(new Date());
//        eoOrderDetailDao.saveEoOrderDetail(mgtest);
    }


    @PostMapping(value = "/saveEoOrder")
    public List<String> saveTest(@RequestBody List<EoOrderDetail> list) {

        return eoOrderDetailService.save(list);
    }

    @GetMapping(value = "/test2")
    public void saveTest2() throws Exception {
//        EoUserRFM eoUserRFM = new EoUserRFM();
//        eoUserRFM.setCity_code("0591");
//        eoUserRFM.setMobile("18650320099");
//        EoUserRFM eoUserRFMq=eoUserRFMDao.findUserRFMByMobleAndCityNo(eoUserRFM);
//        System.out.println(eoUserRFMq);

//        Calendar calendar = Calendar.getInstance();
//        //设置可用开始时间
//        calendar.roll(Calendar.YEAR, -1);
//        Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("mobile").is("18650320099"), Criteria.where("p_t").gte(calendar.getTime()));
//        //criteria.and("p_t").gte(calendar.getTime());
//        Query query = new Query(criteria);
//        query.with(new Sort(Sort.Direction.DESC, "p_t"));
//        List<EoOrderDetail> list = mongoTemplate.find(query, EoOrderDetail.class);
//
//        System.out.println(new Date().getTime()-list.get(0).getPay_time().getTime());
//=================================================================================================


//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("*")
//                .avg("r").as("r")
//                .avg("f").as("f")
//                .avg("m").as("m")
//                ,Aggregation.match(Criteria.where("c_no").is("")));
//        AggregationResults<EoRFMResutl> dbObjects = mongoTemplate.aggregate(aggregation, "eo_user_rfm", EoRFMResutl.class);
//        EoRFMResutl eoRFMResutl = dbObjects.getUniqueMappedResult();

//        Aggregation aggregation1 = Aggregation.newAggregation(Aggregation.match(Criteria.where("c_no").is("0591")),Aggregation.group("*")
//                .avg("r").as("recency")
//                .avg("f").as("frequency")
//                .avg("m").as("monetary"));
//        AggregationResults<BasicDBObject> dbObjects1 = mongoTemplate.aggregate(aggregation1, "eo_user_rfm", BasicDBObject.class);
//
//
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("*")
//                        .avg("r").as("recency")
//                        .avg("f").as("frequency")
//                        .avg("m").as("monetary")
//                , Aggregation.match(Criteria.where("c_no").is("0591")));
//        AggregationResults<EoRFMResutl> dbObjects = mongoTemplate.aggregate(aggregation, "eo_user_rfm", EoRFMResutl.class);
//        EoRFMResutl eoRFMResutl = dbObjects.getUniqueMappedResult();

//        EoBasicCity city=new EoBasicCity();
//        city.setCity_code("0591");
//        city.setName("福州");
//        mongoTemplate.save(city);
//        city.setCity_code("0000");
//        city.setName("全国");
//        mongoTemplate.save(city);
//        city.setCity_code("0592");
//        city.setName("厦门");
//        mongoTemplate.save(city);


//        Query query = new Query();
//        Update update = new Update();
//        update.set("type", 8);
//        query.addCriteria(Criteria.where("c_no").is("0591")
//                .and("r").gte(2.1)
//                .and("f").gte(2.2)
//                .and("m").gte(3)
//        );
//        mongoTemplate.updateMulti(query, update, EoUserRFM.class);
//        query = new Query();
//
//        update.set("type", 7);
//        query.addCriteria(Criteria.where("c_no").is("0591")
//                .and("r").gte(2.1)
//                .and("f").gte(2.2)
//                .and("m").gte(3)
//        );
//        mongoTemplate.updateMulti(query, update, EoUserRFM.class);

        Query query = new Query();
        query.addCriteria(Criteria.where("c_no").is("0591"));
        query.with(new Sort(Sort.Direction.DESC, "c_time"));
        List<EoCityRFM> list = mongoTemplate.find(query, EoCityRFM.class);

        System.out.println(list);

    }

    /**
     * 根据城市code 获取（30条）RFM
     *
     * @param city_no
     * @return
     */
    @GetMapping(value = "/getRfmByCity")
    public List<EoCityRFM> getRfmByCity(String city_no) {
        Query query = new Query();
        query.addCriteria(Criteria.where("c_no").is(city_no));
        query.limit(30);
        query.with(new Sort(Sort.Direction.ASC, "c_t"));
        List<EoCityRFM> list = mongoTemplate.find(query, EoCityRFM.class);
        return list;
    }

    /**
     * 根据城市code 获取最近RFM
     *
     * @param city_no
     * @return
     */
    @GetMapping(value = "/getEoCityRFM")
    public EoCityRFM getEoCityRFM(String city_no) {
        Query query = new Query();
        query.addCriteria(Criteria.where("c_no").is(city_no));
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "c_t"));
        EoCityRFM eoCityRFM = mongoTemplate.findOne(query, EoCityRFM.class);
        return eoCityRFM;
    }

    /**
     * 获取城市codeList
     *
     * @return
     */
    @GetMapping(value = "/getCity")
    public List<EoBasicCity> getCity() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "_id"));
        List<EoBasicCity> list = mongoTemplate.find(query, EoBasicCity.class);
        return list;
    }

    @GetMapping(value = "/getXXX")
    public String getXXX() {
        Map<String, Object> map_s = new HashMap<String, Object>();
        map_s.put("identity", "93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE");

        List<Map<String, Object>> list_invoiceDetail = new ArrayList<Map<String, Object>>();
        Map<String, Object> map_order = new HashMap<String, Object>();

        map_order.put("buyername", "个人");
        map_order.put("taxnum", "");
        map_order.put("phone", "13950238841");
        map_order.put("address", "");
        map_order.put("account", "");
        map_order.put("telephone", "");
        map_order.put("orderno", System.currentTimeMillis());
        map_order.put("invoicedate", "2016-01-13 12:30:00");
        map_order.put("clerk", "张三");
        map_order.put("saleaccount", "东亚银行杭州分行 131001088303400");
        map_order.put("salephone", "0571-87022168");
        map_order.put("saleaddress", "&*杭州市中河中路 222 号平海国际商务大厦 5 楼");
        map_order.put("saletaxnum", "339901999999142");
        map_order.put("kptype", "1");
        map_order.put("message", "");
        map_order.put("payee", "李四");
        map_order.put("checker", "王五");
        map_order.put("fpdm", "");
        map_order.put("fphm", "");
        map_order.put("tsfs", "1");
        map_order.put("email", "");
        map_order.put("qdbz", "0");
        map_order.put("qdxmmc", "");
        map_order.put("dkbz", "0");
//        map_order.put("deptid",);
//        map_order.put("clerkid",);
//        map_order.put("invoiceLine",);
//        map_order.put("cpybz",);

        Map<String, Object> map_invoiceDetails = new HashMap<String, Object>();
        map_invoiceDetails.put("goodsname", "水果沙拉");
        map_invoiceDetails.put("num", 2);
        map_invoiceDetails.put("price", 200);
        map_invoiceDetails.put("hsbz", 1);
        map_invoiceDetails.put("taxrate", "0.04");
//        map_invoiceDetails.put("spec",);
//        map_invoiceDetails.put("unit",);
        map_invoiceDetails.put("spbm", "1090511030000000000");
//        map_invoiceDetails.put("zsbm",);
        map_invoiceDetails.put("fphxz", "0");
        map_invoiceDetails.put("yhzcbs", "0");
//        map_invoiceDetails.put("zzstsgl",);

        list_invoiceDetail.add(map_invoiceDetails);


        map_order.put("detail", list_invoiceDetail);
        map_s.put("order", map_order);

        String SendUrl = "https://nnfpdev.jss.com.cn/shop/buyer/allow/cxfKp/cxfServerKpOrderSync.action";
//        String order = "{\"identity\":\"93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE\","
//                +
//                "\"order\":{\"orderno\":\"201905115411100007\",\"saletaxnum\":\"339901999999142\","
//                + "\"saleaddress\":\"&*杭州市中河中路 222 号平海国际商务大厦 5 楼\",\"salephone\":\"0571-87022168\","
//                + "\"saleaccount\":\"东亚银行杭州分行 131001088303400\",\"clerk\":\"袁 牧庆\",\"payee\":\"络克\","
//                + "\"checker\":\"朱燕\",\"invoicedate\":\"2016-06-15 01:51:41\","
//                + "\"kptype\":\"1\",\"address\":\"\","
//                +
//                "\"phone\":\"13185029520\",\"taxnum\":\"110101TRDX8RQU1\",\"buyername\":\" 个 人\",\"\":\"\","
//                + "\"fpdm\":\"\",\"fphm\":\"\",\"message\":\"开票机号为02 请前往诺诺网 (www.jss.com.cn) 查询发票详情\","
//                + "\"qdbz\":\"1\",\"qdxmmc\":\"1111\","
//                +
//                "\"detail\":[{\"goodsname\":\"1\",\"spec\":\"1\",\"unit\":\"1\",\"hsbz\":\"1\",\"num\":\"1\","
//                + "\"price\":\"19.99\","
//                +
//                "\"spbm\":\"1090511030000000000\",\"fphxz\":\"0\",\"yhzcbs\":\"0\",\"zzstsgl\":\"222222\",\"l slbs\":\"\","
//                + "\"taxrate\":\"0.04\"},"
//                + "{\"goodsname\":\"2\",\"spec\":\"2\","
//                + "\"unit\":\"2\",\"hsbz\":\"1\",\"num\":\"1\",\"price\":\"20\","
//                +
//                "\"spbm\":\"1090511030000000000\",\"fphxz\":\"0\",\"yhzcbs\":\"0\",\"zzstsgl\":\"222222\",\"l slbs\":\"\","
//                + "\"taxrate\":\"0.04\"}]}}";

        String order = JSON.toJSONString(map_s);
//        String SendUrl="https://nnfpdev.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";
//        String order="{\"identity\":\"93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE\",\"orderno\":[\"201701041603288333\"]}";
//
//
//

        SendUrl = "https://nnfpdev.jss.com.cn/shop/buyer/allow/cxfKp/invalidInvoice.action";
        order = "{\"identity\":\"93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE\",\"order\":{\"fpdm\":\"131880930142\",\"fphm\":\"18823020\",\"fpqqlsh\":\"19052411493901008896\"}}";

//
//        SendUrl = "https://nnfpdev.jss.com.cn/shop/buyer/allow/ecOd/queryElectricKp.action";
//        order = "{\"identity\":\"93363DCC6064869708F1F3C72A0CE72A713A9D425CD50CDE\",\"fpqqlsh\":[\"19052411493901008896\"]}";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig defaultRequestConfig = RequestConfig.custom().build();
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(READ_DATA_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT).build();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        CloseableHttpResponse response = null;

        try {
            HttpPost hpost = new HttpPost(SendUrl);
//            order = java.net.URLEncoder.encode(order, "UTF-8");
////            order = compress(order);
            order = DESDZFP.encrypt(order);
            nvps.add(new BasicNameValuePair("order", order));
            UrlEncodedFormEntity utlencode = new UrlEncodedFormEntity(nvps);// ,HTTP.UTF_8
            hpost.setEntity(utlencode);
            hpost.setConfig(requestConfig);
            response = httpclient.execute(hpost);

            Header[] heads = hpost.getAllHeaders();
            for (int i = 0; i < heads.length; i++) {
                System.out.println(heads[i].getName() + "   " + heads[i].getValue());
            }
            return printResult(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    private String printResult(CloseableHttpResponse response) {
        String str = "";
        InputStream input = null;
        ;
        if (response.getStatusLine().getStatusCode() == successCode) {
            try {
                input = response.getEntity().getContent();
                str = java.net.URLDecoder.decode(inputStreamToString(input), HTTP.UTF_8);
                Header[] heahers = response.getAllHeaders();
                System.out.println("返回消息头：");
                for (Header header : heahers) {
                    System.out.println(header.getName() + ":" + header.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
        return str;
    }


    /**
     * 获取流
     *
     * @return
     * @throws IOException
     */
    public String inputStreamToString(InputStream input) throws IOException {
        BufferedReader br = null;
        String data = "";
        StringBuffer sb = new StringBuffer();
        br = new BufferedReader(new InputStreamReader(input));
        while ((data = br.readLine()) != null) {
            sb.append(data);
        }
        return sb.toString();
    }


    public static String compress(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return "";
        }
        byte[] tArray;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        try {
            gzip.write(str.getBytes("UTF-8"));
            gzip.flush();
        } finally {
            gzip.close();
        }

        tArray = out.toByteArray();
        out.close();
        BASE64Encoder tBase64Encoder = new BASE64Encoder();
        return tBase64Encoder.encode(tArray);
    }
}
