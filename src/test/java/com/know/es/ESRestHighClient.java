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
                "      \"number_of_shards\" : \"3\"," +
                "      \"number_of_replicas\" : \"0\"" +
                "   }";

        String mappings = "" +
                "{" +
                "    \"properties\": {" +
                "      \"pro\" : {" +
                "        \"type\": \"keyword\"," +
                "        \"ignore_above\": 64" +
                "      }," +
                "      \"english\" : {" +
                "        \"type\": \"text\"" +
                "      }," +
                "      \"name\" : {" +
                "        \"type\": \"text\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"address\" : {" +
                "        \"type\": \"text\"," +
                "        \"fields\": {" +
                "          \"keyword\" : {\"ignore_above\" : 256, \"type\" : \"keyword\"}" +
                "        }" +
                "      }," +
                "      \"age\" : {" +
                "        \"type\": \"long\"" +
                "      }," +
                "      \"createTime\" : {" +
                "        \"type\": \"date\"," +
                "        \"format\": \"yyyy-MM-dd HH:mm:ss\"" +
                "      }" +
                "    }" +
                "}";

        try {
            service.createIndex("face", settings, mappings);
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
                "  \"pro\" : \"ake\"," +
                "  \"english\" : \"siwang shizui meihaode diaoling\"," +
                "  \"name\" : \"阿轲\"," +
                "  \"adress\" : \"韩国\"," +
                "  \"age\" : 24," +
                "  \"create\" : \"2020-02-23\"" +
                "  }";

        service.addDoc("cat", "2", source);

    }

    @Test
    public void updateDoc() throws IOException {

        String source = " {" +
                "  \"pro\" : \"juzi\"," +
                "  \"name\" : \"橘右京\"," +
                "  \"address\" : \"吴国\"," +
                "  \"age\" : 2," +
                "  \"create\" : \"2020-02-19\"" +
                "  }";

        service.updateDoc("cat", "2", source);

    }
    @Test
    public  void getDoc() throws IOException {
       GetResponse getResponse= service.getDoc("client","1");
        Map<String,Object> map=getResponse.getSource();
       System.out.println(map.toString());
    }


    @Test
    public void matchSearch() throws IOException {
        SearchResponse response =service.matchSearch("name","赵","cat");
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.toString());
        }
    }

    @Test
    public void matchPhrase() throws IOException {
        service.matchPhrase("name","赵","cat");
    }


    @Test
    public void searchMultiMatch() throws IOException {
        SearchResponse response =service.searchMultiMatch("魏国","cat",0,10,"adress","name");
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.toString());
        }

    }

    @Test
    public void SearchLike() throws IOException {
        SearchResponse response = service.SearchLike("蜀", "adress", "cat");
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.toString());
        }

    }





    @Test
    public void teamsSearch() throws IOException {
        SearchResponse response =service.termSearch("age","25","cat");
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.toString());
        }

    }
    @Test
    public void boolSearch() throws IOException {
        service.boolSearch("name","赵子龙","cat");
    }

    @Test
    public void searchScroll() throws IOException {
        service.searchScroll("name","赵子龙","cat");
    }

    @Test
    public void multiSearch() throws IOException {
        service.multiSearch("name","赵子龙","cat");
    }





    @Test
    public void bulk() throws IOException {
        //不需要[]，奇不奇怪
        String bulkVal = "[" +
                "" +
                "  {" +
                "  \"pro\" : \"zhouyu\"," +
                "  \"english\" : \"nimen doushi tantu wode meimao\"," +
                "  \"name\" : \"周瑜\"," +
                "  \"adress\" : \"吴国\"," +
                "  \"age\" : 11," +
                "  \"create\" : \"2020-02-20\"" +
                "  }," +
                "  {" +
                "  \"pro\" : \"daqiao\"," +
                "  \"english\" : \"handon lingzhi\"," +
                "  \"name\" : \"大乔\"," +
                "  \"adress\" : \"吴国\"," +
                "  \"age\" : 12," +
                "  \"create\" : \"2020-02-21\"" +
                "  }" +
                "]";
        service.importAll("cat", false, bulkVal);
    }



}
