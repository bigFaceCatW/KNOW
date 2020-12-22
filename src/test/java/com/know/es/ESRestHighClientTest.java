package com.know.es;

import com.know.es.config.RestHighLevelClientService;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESRestHighClientTest {


    @Autowired
    RestHighLevelClientService service;

    @Autowired
    private RestHighLevelClient client;


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
        SearchResponse response = service.SearchLike("西域", "address", "cat");
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
        service.boolSearch("name","王文婷","cat");
    }

    @Test
    public void searchScroll() throws IOException {
        service.searchScroll("name","赵子龙","cat");
    }

    @Test
    public void multiSearch() throws IOException {
        service.multiSearch("name","赵子龙","cat");
    }


     @Test //用于组合查询
     public  void boolQuery() throws IOException {
         SearchRequest request = new SearchRequest("cat1");
         SearchSourceBuilder builder = new SearchSourceBuilder();
         BoolQueryBuilder bool = QueryBuilders.boolQuery();
//         builder.fetchSource(new String[]{"english","name","age","createTime"}, new String[]{});
// 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
//1.查询所有数据
//         builder.query(QueryBuilders.matchAllQuery());
//2.精确查询 可传入数组(对多结构好像不行)
//       List<String> str = Arrays.asList("王文婷", "韩风");
//         bool.filter(QueryBuilders.termsQuery("name.keyword",str)); //如果类型不是keyword，则需要写成name.keyword
//         builder.query(bool);
//3. 全文检索
//         (1):模糊查询：matchPhrase(like模糊查询)
//         ①.fuzzyQuery
//         builder.query(QueryBuilders.fuzzyQuery("name", "三").fuzziness(Fuzziness.AUTO));
//         ②.wildcardQuery（通配符查询）should满足一个就行
//         bool.should(QueryBuilders.wildcardQuery("address", "*国"));
//         builder.query(bool);

//         (2):分词查询
//         builder.query(QueryBuilders.multiMatchQuery("国信","name","adress","pro"));
//         (3)范围查询-嵌入布尔
//         ①
//         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//         QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("age").gte(27).lte(30);
//         boolQueryBuilder.filter(queryBuilder1);
//         builder.query(boolQueryBuilder);
//         ②时间区间、数字查询
//         BoolQueryBuilder rangeBuilder = QueryBuilders.boolQuery();
//         boolQueryBuilder.filter(QueryBuilders.rangeQuery("age")
//                 .format("yyyy-MM-dd HH:mm:ss") //数字可以去掉这个格式
//                 .from("2020-12-12:09:00:00")
//                 .to("2020-12-12:09:00:00")
//                 .includeLower(true)//true包含下界<=，false 不包含下界<
//                 .includeUpper(true)
//         );
//         builder.query(rangeBuilder);
//         (4)精确关键词匹配：matchPhrase
//         BoolQueryBuilder phraseBool = QueryBuilders.boolQuery();
//         phraseBool.must(QueryBuilders.matchPhraseQuery("name", "妲己"));
//         bool.filter(phraseBool);
//         builder.query(bool);


//4.布尔查询：可以包含term、match、filter
//         (1)must、should、
//         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//         boolQueryBuilder.must( QueryBuilders.multiMatchQuery("韩妲","name","englist"));
//         boolQueryBuilder.must(QueryBuilders.matchQuery("address", "韩"));
//         bool.filter(boolQueryBuilder);
//         builder.query(bool);
//         (2)mustNot
//         BoolQueryBuilder notInFilter = QueryBuilders.boolQuery();
//         notInFilter.mustNot(QueryBuilders.termQuery("name","韩信"));
//         bool.filter(notInFilter);
//         builder.query(bool);



//多的数据进行滚动查询：单位为秒
//         final Scroll scroll = new Scroll(TimeValue.timeValueSeconds(6000));
//         request.scroll(scroll);

//===========================================================嵌套查询====================================================
//         1.分组后求和
//         TermsAggregationBuilder aggregationBuilderParam = AggregationBuilders.terms("count_age")
//                 .field("age")
//                 .order(BucketOrder.count(true));//按doc_count升序
//         aggregationBuilderParam.subAggregation(AggregationBuilders.sum("age_sum").field("age"));
//         2.进行倒序前2名
//         AggregationBuilder aggregationBuilderParam = AggregationBuilders.topHits("top_age").sort("age", SortOrder.fromString("desc")).size(2);
//         3.统计age在10到29的文本数量如果时间加上format()
//         AggregationBuilder aggregationBuilderParam = AggregationBuilders.range("range_age").field("age").addRange(10, 29);
//         4.进行统计所有文本的平均值
//         AggregationBuilder aggregationBuilderParam = AggregationBuilders.avg("avg_age").field("age");
//         5.直方图聚合(按时间间隔interval，列出属于哪个桶中)
//         AggregationBuilder aggregationBuilderParam = AggregationBuilders.histogram("time_histogram").field("time").interval(10000).format("yyyy-MM-dd HH:mm:ss");
//         6.日期直方图聚合
//         AggregationBuilder aggregation =
//                 AggregationBuilders
//                         .dateHistogram("agg")
//                         .field("dateOfBirth")
//                         .dateHistogramInterval(DateHistogramInterval.days(10)); //十天的间隔
//         7.日期范围聚合
//         AggregationBuilder aggregationBuilderParam =
//                 AggregationBuilders
//                         .dateRange("agg")
//                         .field("dateOfBirth")
//                         .format("yyyy")
//                         .addUnboundedTo("1950")    // from -infinity to 1950 (excluded)不包含
//                         .addRange("1950", "1960")  // from 1950 to 1960 (excluded) 范围
//                         .addUnboundedFrom("1960"); // from 1960 to +infinity不包含
//           8.过滤聚合，先查后聚合
//         AggregationBuilder aggregationBuilderParam = AggregationBuilders
//                 .filter("agg", QueryBuilders.termQuery("gender", "male"));
//           9.多重过滤
//         AggregationBuilder aggregationBuilderParam =AggregationBuilders
//                         .filters("agg",
//                 new FiltersAggregator.KeyedFilter("men", QueryBuilders.termQuery("gender", "male")),
//                 new FiltersAggregator.KeyedFilter("women", QueryBuilders.termQuery("gender", "female")));
//         builder.aggregation(aggregationBuilderParam);
// =========================================================builder参数============================================


         builder.from(0);
         builder.size(30);
//         builder.sort("age", SortOrder.DESC); //ase
//         builder.trackTotalHits(true);//查询总量超过10000条就会报错，true返回命中的真实条数没有限制
         request.source(builder);
//         返回数据
         SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
         SearchHits searchHits = searchResponse.getHits();
//         获取总记录：默认返回10条
         TotalHits total = searchHits.getTotalHits(); //
         System.out.println("cat返回条数>>>" + total.value);
//         获取数据:
         SearchHit[] hits = searchHits.getHits();
         for (SearchHit hit : hits) {
             String id = hit.getId();
             Map<String, Object> sourceAsMap = hit.getSourceAsMap();
             System.out.println("第一次查询>>>" + sourceAsMap.toString());
         }


//        scroll第二次调用
//         String scrollId = searchResponse.getScrollId();//下一次，scroll查询的id
//         long queryTotal =searchHits.getTotalHits().value; //查询的总数
//         long showTotal=searchHits.getHits().length; //显示的总数
//         SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//         scrollRequest.scroll(TimeValue.timeValueSeconds(6000));
//         SearchResponse searchResponse1=client.scroll(scrollRequest, RequestOptions.DEFAULT); //第二次查询
//         SearchHit[] searchHit1=searchResponse1.getHits().getHits();
//         for(SearchHit hit1:searchHit1){
//            Map<String,Object> sourceMap1= hit1.getSourceAsMap();
//             System.out.println("第二次查询>>>"+sourceMap1.toString());
//         }

//===================================================聚合查询返回结果===================================================
         //获取分组
         Aggregations aggregations = searchResponse.getAggregations();
//        aggs.put("group_time", aggregations.get("group_time")); //获取度量值
         Terms terms = aggregations.get("count_age");
//循环获取值使用了分组才需要：
         for(Terms.Bucket bucket:terms.getBuckets()){
            System.out.println("key: " + bucket.getKeyAsNumber());
            System.out.println("docCount: " + bucket.getDocCount());
             //取子聚合,针对于不同的
             Sum Sum = bucket.getAggregations().get("age_sum");
             double value = Sum.getValue();
           System.out.println("value: "+value);
         }
//2.通过key获取值：key为以什么分组，不通分组的内容：（以地址分组：分为2组，2组不通的内容就为key）
//         Terms.Bucket bucket = terms.getBucketByKey("27");
//         Avg averageAge = bucket.getAggregations().get("age_sum");
//         Double value = averageAge.getValue();
//         System.out.println(value);
// 3.通过集合的形式取值
         List<Aggregation> aggregationsList = aggregations.asList();
         for (Aggregation agg : aggregationsList) {
             String name = agg.getName();
             String type = agg.getType();

         }
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
