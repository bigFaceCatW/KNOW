package com.know.es;

import com.know.es.config.RestHighLevelClientService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESRestHighClient {


    @Autowired
    RestHighLevelClientService service;

//    @Before
//    public void testRestHighClinet() {
//
//        RestClientBuilder restClientBuilder = RestClient.builder(
//                new HttpHost("localhost", 9200, "http")
//        );
//
//        Header[] defaultHeaders = new Header[]{
//                new BasicHeader("Accept", "*/*"),
//                new BasicHeader("Charset", "UTF-8"),
//                new BasicHeader("E_TOKEN", "esestokentoken")
//        };
//        restClientBuilder.setDefaultHeaders(defaultHeaders);
//
//        restClientBuilder.setFailureListener(new RestClient.FailureListener(){
//            @Override
//            public void onFailure(Node node) {
//                System.out.println("监听失败");
//            }
//        });
//
//        restClientBuilder.setRequestConfigCallback(builder ->
//                builder.setConnectTimeout(5000).setSocketTimeout(15000));
//
//        RestHighLevelClient highClient = new RestHighLevelClient(restClientBuilder);
//
//        restHighLevelClient = highClient;
//        service = new RestHighLevelClientService();
//    }

//https://www.cnblogs.com/chenmc/p/9516100.html 各参数解释



    @Test
    public void testAddIndex() {
        String settings = "" +
                "  {" +
                "      \"number_of_shards\" : \"2\"," +
                "      \"number_of_replicas\" : \"0\"" +
                "   }";

        String mappings = "" +
                "{" +
                "    \"properties\": {" +
                "      \"proId\" : {" +
                "        \"type\": \"keyword\"," +
                "        \"ignore_above\": 64" +
                "      }," +
                "      \"name\" : {" +
                "        \"type\": \"text\"," +
                "        \"analyzer\": \"ik_max_word\", " +
                "        \"search_analyzer\": \"ik_smart\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"mytimestamp\" : {" +
                "        \"type\": \"date\"," +
                "        \"format\": \"epoch_millis\"" +
                "      }," +
                "      \"createTime\" : {" +
                "        \"type\": \"date\"," +
                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"" +
                "      }" +
                "    }" +
                "}";

        try {
            service.createIndex("id_pro", settings, mappings);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建索引失败");
        }

    }





    @Test
    public void deleteIndex() throws IOException {
        service.deleteIndex("idx_pro");
    }

    @Test
    public void addDoc() throws IOException {

        String source = "{" +
                "  \"proId\" : \"2\"," +
                "  \"name\" : \"测试去掉/n\"," +
                "  \"timestamp\" : 1576312053946," +
                "  \"createTime\" : \"2019-12-12 12:56:56\"" +
                "  }";

        service.addDoc("client", "2", source);

    }

    @Test
    public void updateDoc() throws IOException {

        String source = " {" +
                "  \"proId\":\"3\"," +
                "  \"name\" : \"2020年2月8日阴天\"" +
                "  }";

        service.updateDoc("client", "1", source);

    }
    @Test
    public  void getDoc() throws IOException {
       GetResponse getResponse= service.getDoc("client","1");
        Map<String,Object> map=getResponse.getSource();
       System.out.println(map.toString());
    }


    @Test
    public void matchSearch() throws IOException {
        SearchResponse response =service.matchSearch("name","阴天","client");
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.get("name"));
            System.out.println(sourceAsMap.get("createTime"));
        }

    }

    @Test
    public void teamsSearch() throws IOException {
        SearchResponse response =service.termSearch("name","阴天","client");
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.get("name"));
            System.out.println(sourceAsMap.get("createTime"));
        }

    }

    @Test
    public void bulk() throws IOException {
        //不需要[]，奇不奇怪
        String bulkVal = "[" +
                "" +
                "  {" +
                "  \"proId\" : \"1\"," +
                "  \"name\" : \"冬日工装裤\"," +
                "  \"timestamp\" : 1576312053946," +
                "  \"createTime\" : \"2019-12-12 12:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"2\"," +
                "  \"name\" : \"冬日羽绒服\"," +
                "  \"timestamp\" : 1576313210024," +
                "  \"createTime\" : \"2019-12-10 10:50:50\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"3\"," +
                "  \"name\" : \"花花公子外套\"," +
                "  \"timestamp\" : 1576313239816," +
                "  \"createTime\" : \"2019-12-19 12:50:50\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"4\"," +
                "  \"name\" : \"花花公子羽绒服\"," +
                "  \"timestamp\" : 1576313264391," +
                "  \"createTime\" : \"2019-12-12 11:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"5\"," +
                "  \"name\" : \"花花公子暖心羽绒服\"," +
                "  \"timestamp\" : 1576313264491," +
                "  \"createTime\" : \"2019-12-19 11:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"6\"," +
                "  \"name\" : \"花花公子帅气外套\"," +
                "  \"timestamp\" : 1576313264691," +
                "  \"createTime\" : \"2019-12-19 15:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"7\"," +
                "  \"name\" : \"冬天暖心羽绒服\"," +
                "  \"timestamp\" : 1576313265491," +
                "  \"createTime\" : \"2019-12-19 17:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"8\"," +
                "  \"name\" : \"冬天超级暖心羽绒服\"," +
                "  \"timestamp\" : 1576313275491," +
                "  \"createTime\" : \"2019-12-20 17:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"9\"," +
                "  \"name\" : \"\"," +
                "  \"timestamp\" : 1576313275491," +
                "  \"createTime\" : \"2019-12-20 17:56:56\"" +
                "  }," +
                "  {" +
                "  \"proId\" : \"9\"," +
                "  \"name\" : []," +
                "  \"timestamp\" : 1576313275491," +
                "  \"createTime\" : \"2019-12-20 17:56:56\"" +
                "  }" +
                "]";
        service.importAll("idx_pro", true, bulkVal);
    }



}
