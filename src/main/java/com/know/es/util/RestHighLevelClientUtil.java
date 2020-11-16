package com.know.es.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//DeleteRequest GetRequest UpdateRequest 都是根据 id 操作文档

/**
 * @author anqi
 */
@Service
public class RestHighLevelClientUtil {

    @Autowired
    private RestHighLevelClient client;


    /**
     * 创建索引
     * @param indexName
     * @param settings
     * @param mapping
     * @return
     * @throws IOException
     */
    public CreateIndexResponse createIndex(String indexName, String settings, String mapping) throws IOException{
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (null != settings && !"".equals(settings)) {
            request.settings(settings, XContentType.JSON);
        }
        if (null != mapping && !"".equals(mapping)) {
            request.mapping(mapping, XContentType.JSON);
        }
        return client.indices().create(request, RequestOptions.DEFAULT);
    }



    /**
     * 删除索引
     * @param indexNames 索引名称
     * @return
     * @throws IOException
     */
    public AcknowledgedResponse deleteIndex(String ... indexNames) throws IOException{
        DeleteIndexRequest request = new DeleteIndexRequest(indexNames);
        return client.indices().delete(request, RequestOptions.DEFAULT);
    }


    /**
     * 判断 index 是否存在
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean indexExists(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据 id 删除指定索引中的文档
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    public DeleteResponse deleteDoc(String indexName, String id) throws IOException{
        DeleteRequest request = new DeleteRequest(indexName, id);
        return client.delete(request, RequestOptions.DEFAULT);
    }


    /**
     * 添加文档 手动指定id
     * @param indexName
     * @param id
     * @param source
     * @return
     * @throws IOException
     */
    public IndexResponse addDoc(String indexName, String id, String source) throws IOException{
        IndexRequest request = new IndexRequest(indexName);
        if (null != id) {
            request.id(id);
        }
        request.source(source, XContentType.JSON);
        return client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 添加文档 使用自动id
     * @param indexName
     * @param source
     * @return
     * @throws IOException
     */
    public IndexResponse autoAddDoc(String indexName, String source) throws IOException{
        IndexRequest request = new IndexRequest(indexName);
        request.source(source, XContentType.JSON);
        return client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据 id 更新指定索引中的文档
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    public UpdateResponse updateDoc(String indexName, String id, String updateJson) throws IOException{
        UpdateRequest request = new UpdateRequest(indexName, id);
        request.doc(updateJson,XContentType.JSON);
        return client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 根据 id 更新指定索引中的文档,入参为map可以自动转为json可修改多个字段
     * @param indexName
     * @param id
     * @return
     * @throws IOException
     */
    public UpdateResponse updateDoc(String indexName, String id, Map<String,Object> updateMap) throws IOException{
        UpdateRequest request = new UpdateRequest(indexName, id);
        request.doc(updateMap);
        return client.update(request, RequestOptions.DEFAULT);
    }

    /**一般查询文档
     *
     *
     */

    public GetResponse getDoc(String name,String id) throws IOException {
        GetRequest getRequest = new GetRequest(name,id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        return  getResponse;
    }

    /**
     * 根据某字段的 k-v查询出文档，再将文档通过脚本修改
     * @param fieldName
     * @param value
     * @param indexName
     * @throws IOException
     */
    public BulkByScrollResponse updateByQuery(String fieldName, String value, String indexName) throws IOException {
        UpdateByQueryRequest request = new UpdateByQueryRequest(indexName);
        //单次处理文档数量
        request.setSize(10)
                .setBatchSize(10)
                .setQuery(new TermQueryBuilder(fieldName, value))
                .setScript(new Script(ScriptType.INLINE, "painless",
                        "ctx._source['name'] = '新的值'", Collections.emptyMap()))
                .setTimeout(TimeValue.timeValueMinutes(2))
                .setConflicts("proceed");
        return client.updateByQuery(request, RequestOptions.DEFAULT);
    }

//==================================================查询===========================================



    /**多匹配查询
     * @param fields
     * @param indexName
     * @param page
     * @param size
     * @param fields
     */
    public  SearchResponse searchMultiMatch(String key, String indexName, int page, int size, String ... fields) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.multiMatchQuery(key, fields))
                .sort(new FieldSortBuilder("_id").order(SortOrder.ASC))
                .from(page)
                .size(size);
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);

    }

    /**
     * match 对数字和字符连在一起的不分词,按照id排序
     *
     */
    public SearchResponse matchSearch(String field ,String key ,String ... indexNames) throws IOException {
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery(field,key))
                .sort(new FieldSortBuilder("_id").order(SortOrder.ASC));
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }

    /**
     * term 查询 精准匹配
     * @param field
     * @param key
     * @param indexNames
     * @return
     * @throws IOException
     */
    public SearchResponse termSearch(String field, String key, String ... indexNames) throws IOException{
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery(field, key))
                .from(0)
                .size(10);
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }

    public void matchPhrase(String field ,String key,String ...indexNames) throws IOException {
        List<Object> list = new ArrayList<>();
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchPhraseQuery("name", "赵子龙"));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
       SearchHit[] searchHits =  response.getHits().getHits();
       for(SearchHit  hit:searchHits){
           Map<String, Object> map = hit.getSourceAsMap();
           list.add(map);
       }
        System.out.println(JSON.toJSONString(list));
    }



    public void boolSearch(String field ,String name ,String ... indexNames) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();

        String[] fields = { "name", "create", "adress", "age" };// 指定返回的字段
        builder.fetchSource(fields, null);
        builder.from(0);   //从0开始
        builder.size(15);  //查询15行
        builder.sort("age", SortOrder.DESC); //倒叙排列
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();//创建bool
        QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("age").gte(27).lte(30);
        boolQueryBuilder.filter(queryBuilder1);
//                QueryBuilders.queryStringQuery("fieldValue").field("fieldName")
//                .must(QueryBuilders.termsQuery("name", "张飞", "韩信"))//精确查询，不分词
//               .must(QueryBuilders.matchQuery("name", ""))   //必须满足
//                .must(QueryBuilders.rangeQuery("age").gte(27).lte(30))//或者范围查询
//                .should(QueryBuilders.wildcardQuery("name", "*" + "周" + "*")) //或者通配符查询
        builder.query(boolQueryBuilder);  //封装bool类

//============================嵌套查询==============================================
        TermsAggregationBuilder aggregation= AggregationBuilders.terms("group_age").field("age");//求和
        aggregation.subAggregation(AggregationBuilders.avg("age_avg")
                .field("age"));
        builder.aggregation(aggregation);
        request.source(builder);
//        聚合
//        AggregationBuilder aggregation = AggregationBuilders.count("count_uid").field("uid");//统计字段数量
//        AggregationBuilders.cardinality("age");
//        builder.aggregation(AggregationBuilders.dateHistogram("create_sort").field("createTime")); //以时间间隔分组
//        builder.aggregation(AggregationBuilders.sum("age_sum").field("age"));//求和//时间分组
//        builder.aggregation(AggregationBuilders.terms("group_name").field("name"));//分组
//        builder.aggregation(AggregationBuilders.avg("age_avg").field("age"))
        SearchResponse searchResponse= client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }
        System.out.println(JSON.toJSONString(list));

//获取分组
        Aggregations aggregations = searchResponse.getAggregations();
//        aggs.put("group_time", aggregations.get("group_time")); //获取度量值
        Terms terms =aggregations.get("group_age");
       Terms.Bucket bucket= terms.getBucketByKey("27"); //key值，时间为keyasString值

        Avg averageAge = bucket.getAggregations().get("age_avg");
        Double value = averageAge.getValue();

     System.out.println(JSON.toJSONString(bucket));
       List<Aggregation> aggregationsList = aggregations.asList();
//			for(Aggregation agg : aggregationsList){
//				String name = agg.getName();
//				String type = agg.getType();
//        ParsedValueCount valueCount =  (ParsedValueCount) agg;
//        result = valueCount.getValueAsString();

    }


//scroll查询
    public void  searchScroll(String field ,String name ,String ... indexNames) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> list1 = new ArrayList<>();
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();

        String[] fields = { "name", "create", "adress", "age" };
        builder.fetchSource(fields, null);
        builder.from(0);   //从0开始
        builder.size(15);  //查询15行
        builder.sort("age", SortOrder.DESC); //倒叙排列
        builder.query(QueryBuilders.matchAllQuery());
        request.source(builder);
        request.scroll(TimeValue.timeValueMinutes(1L));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        String str = response.getScrollId();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }
        System.out.println(str);
        System.out.println(JSON.toJSONString(list));

//        查询到scroll后根据id来查，性能更高
        SearchScrollRequest scrollRequest = new SearchScrollRequest(str);
        scrollRequest.scroll(TimeValue.timeValueSeconds(30));
        SearchResponse scrollResponse =  client.scroll(scrollRequest, RequestOptions.DEFAULT);
        System.out.println("第二次scrollId:"+scrollResponse.getScrollId());
        SearchHit[] hitTow = scrollResponse.getHits().getHits();
        for (SearchHit hit : hitTow) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list1.add(sourceAsMap);
        }
        System.out.println(JSON.toJSONString(list1));
    }


// multiSearch
public void multiSearch(String field ,String name ,String ... indexNames) throws IOException {

        SearchRequest request = new SearchRequest(indexNames);
    //构建批量查询
    MultiSearchRequest multiRequest = new MultiSearchRequest();

    //创建查询条件

    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.termQuery("name", "橘右京"));
    request.source(builder);
    multiRequest.add(request);

    SearchRequest request1 = new SearchRequest(indexNames);
    SearchSourceBuilder builder1 = new SearchSourceBuilder();
    builder1.query(QueryBuilders.termQuery("name", "赵子龙"));
    request1.source(builder1);
    multiRequest.add(request1);

    //请求
    MultiSearchResponse response = client.msearch(multiRequest, RequestOptions.DEFAULT);
    MultiSearchResponse.Item[] items = response.getResponses();

    for (MultiSearchResponse.Item item : items) {
        SearchHits hits = item.getResponse().getHits();
        //无查询结果
        if (hits.getTotalHits().value > 0) {
            SearchHit[] hitList = hits.getHits();
            for (SearchHit searchHit : hitList) {
                System.out.println(searchHit.getSourceAsString());
            }
        }
    }
}





    public SearchResponse SearchLike(String field ,String key ,String ... indexNames) throws IOException {
        SearchRequest request = new SearchRequest(indexNames);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.queryStringQuery(field).field(key));
        request.source(builder);
        return client.search(request, RequestOptions.DEFAULT);
    }



    /**
     * 批量导入
     * @param indexName
     * @param isAutoId 使用自动id 还是使用传入对象的id
     * @param source
     * @return
     * @throws IOException
     */
    public BulkResponse importAll(String indexName, boolean isAutoId,  String  source) throws IOException{
        BulkRequest request = new BulkRequest();
        JSONArray array = JSON.parseArray(source);
        if (isAutoId) {
            for (Object s : array) {
                request.add(new IndexRequest(indexName).source(JSONObject.toJSONString(s), XContentType.JSON));
            }
        } else {
            for (Object s : array) {
                request.add(new IndexRequest(indexName)
                        .id(JSONObject.parseObject(s.toString()).getString("age"))
                        .source(JSONObject.toJSONString(s), XContentType.JSON));
            }
        }
        return client.bulk(request, RequestOptions.DEFAULT);
    }





}

