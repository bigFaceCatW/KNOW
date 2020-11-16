//package com.know.es.util;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.entity.ContentType;
//import org.apache.http.nio.entity.NStringEntity;
//import org.apache.http.util.EntityUtils;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.client.RestClient;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//
//@Component
//public class RestLowLevelClientUtils {
//
//    @Resource(name = "restClient")
//    private RestClient restLowLevelClient;
//
//    public String post(String index, String type, String sdl) throws Exception {
//        HttpEntity entity = new NStringEntity(sdl, ContentType.APPLICATION_JSON);
//        Response response = restLowLevelClient.performRequest("POST", "/" + index + "/" + type + "/_search", new HashMap<>(), entity);
//        return EntityUtils.toString(response.getEntity());
//    }
//
//}
