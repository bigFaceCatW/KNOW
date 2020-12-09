//package com.know.es.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.hollycrm.hollybeacons.system.okhttp.util.OkHttpUtil;
//import com.hollycrm.hollybeacons.system.util.StringUtils;
//import org.apache.lucene.search.join.ScoreMode;
//import org.elasticsearch.ElasticsearchException;
//import org.elasticsearch.action.DocWriteResponse;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
//import org.elasticsearch.action.bulk.BulkItemResponse;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchScrollRequest;
//import org.elasticsearch.action.support.replication.ReplicationResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.InnerHitBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.join.query.HasChildQueryBuilder;
//import org.elasticsearch.rest.RestStatus;
//import org.elasticsearch.search.Scroll;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.io.*;
//import java.nio.file.Files;
//import java.util.*;
//
////import org.apache.commons.lang3.StringUtils;
//
//@Component
//public class ElasticsearchRestClientUtils {
//    public final static Logger log = LoggerFactory.getLogger(ElasticsearchRestClientUtils.class);
//
//    @Value("${elasticsearch.timer.queue.size:10000}")
//    private int request_queue_size;
//    @Value("${elasticsearch.timer.queue.timeout:500}")
//    private int request_queue_timeout;
//    @Value("${kafka.consumer.topicName}")
//    private String topicName;
//
//
//    @Resource(name = "restHighLevelClient")
//    private RestHighLevelClient restHighLevelClient;
//    private static RestHighLevelClient client;
//
//    @Value("${elasticsearch.cluster.nodes}")
//    private String elasticsearch_nodes;
//    private static String nodes;
//
//    @PostConstruct
//    public void init() {
//        client = this.restHighLevelClient;
//        nodes = this.elasticsearch_nodes;
//    }
//
//    public static final Integer ONCE_SCROLL_NUM = 1000;//一次滚动查询的数量
//
//    public static final String INDEX = "index";
//    public static final String TYPE = "type";
//
//
//    public static Integer deleteByParams(String index, String type, String json) throws Exception {
//        try {
//            SearchSourceBuilder searchRequestBuilder = searchRequestBuilder(json);
//            log.mybatis("删除的查询语句：\n{}", searchRequestBuilder);
//            String ip = nodes.split(",")[0];
//            String rtn = OkHttpUtil.postJsonParams("http://" + ip + "/" + index + "/" + type + "/_delete_by_query", searchRequestBuilder.toString());
//            log.mybatis("删除结果：\n{}", rtn);
//            if (null != rtn && !rtn.isEmpty()) {
//                com.alibaba.fastjson.JSONObject jSONObject = JSON.parseObject(rtn);
//                if (null != jSONObject.get("deleted") && !jSONObject.get("deleted").toString().isEmpty()) {
//                    return Integer.valueOf(jSONObject.get("deleted").toString());
//                }
//            } else {
//                return 0;
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 创建索引
//     *
//     * @param index
//     * @return
//     */
//    public static boolean createIndex(String index) throws Exception {
//        CreateIndexRequest request = new CreateIndexRequest(index.toLowerCase()); //index名必须全小写，否则报错
//        CreateIndexResponse indexResponse = client.indices().create(request);
//        //创建的每个索引都可以有与之关联的特定设置。
//        request.settings(Settings.builder()
//                .put("index.number_of_shards", 5)
//                .put("index.number_of_replicas", 2)
//                .put("index.mapping.ignore_malformed", true)
//        );
//        return indexResponse.isAcknowledged();
//    }
//
//    public static boolean deleteIndex(String index) throws Exception {
//        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
//        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
//        deleteIndexResponse.isAcknowledged();
//        return deleteIndexResponse.isAcknowledged();
//    }
//
//    /**
//     * 数据添加，正定ID
//     *
//     * @param jsonObject 要增加的数据
//     * @param index      索引，类似数据库
//     * @param type       类型，类似表
//     * @param id         数据ID
//     * @return
//     */
//    public static String addData(Map jsonObject, String index, String type, String id) throws Exception {
//        IndexRequest indexRequest = new IndexRequest(index, type, id);
//        indexRequest.source(jsonObject, XContentType.JSON);
//        IndexResponse indexResponse = client.index(indexRequest);
//        return indexResponse.getId();
//    }
//
//    public static String upsertDataById(String index, String type, String id, Object parentId, Map t) throws Exception {
//        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
//        updateRequest.index(index).type(type).id(id).docAsUpsert(true).doc(t, XContentType.JSON);
//
//        //父子结构数据处理
//        if (null != parentId && !parentId.toString().isEmpty() && null != t.get(parentId.toString())) {
//            updateRequest.routing(t.get(parentId.toString()).toString());
//        }
//
//        return client.update(updateRequest).getId();
//    }
//
//    public static List<String> bulk(List<Map> jsonObjects, String index, String type) throws Exception {
//        List<String> failures_id = new ArrayList<String>();
//        BulkRequest bulkRequest = new BulkRequest();
//        for (Map jsonObject : jsonObjects) {
//            if (jsonObject.get("operation").toString().equalsIgnoreCase("CREATE")) {
//                bulkRequest.add(new IndexRequest(index, type, jsonObject.get("id").toString()).source(jsonObject, XContentType.JSON));
//            } else if (jsonObject.get("operation").toString().equalsIgnoreCase("UPDATE")) {
//                bulkRequest.add(new UpdateRequest(index, type, jsonObject.get("id").toString()).doc(jsonObject, XContentType.JSON));
//            } else if (jsonObject.get("operation").toString().equalsIgnoreCase("DELETE")) {
//                bulkRequest.add(new DeleteRequest(index, type, jsonObject.get("id").toString()));
//            }
//        }
//        BulkResponse bulkResponse = client.bulk(bulkRequest);
//        for (BulkItemResponse bulkItemResponse : bulkResponse) {
//            if (bulkItemResponse.isFailed()) {
//                failures_id.add(bulkItemResponse.getFailure().getId());
//            }
//        }
//
//        return failures_id;
//    }
//
//    /**
//     * 通过ID删除数据
//     *
//     * @param index 索引，类似数据库
//     * @param type  类型，类似表
//     * @param id    数据ID
//     */
//    public static void deleteDataById(String index, String type, String id) throws Exception {
//        DeleteRequest request = new DeleteRequest(index, type, id);
//        DeleteResponse response = null;
//        try {
//            response = client.delete(request);
//        } catch (ElasticsearchException e) {
//            if (e.status() == RestStatus.CONFLICT) {
//                log.error("版本冲突！");
//            }
//            log.error("删除失败!");
//        }
//        if (Objects.nonNull(response)) {
//            if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
//                log.error("不存在该文档，请检查参数！");
//            }
//            log.mybatis("文档已删除！");
//            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
//            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
//                log.error("部分分片副本未处理");
//            }
//            if (shardInfo.getFailed() > 0) {
//                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
//                    String reason = failure.reason();
//                    log.error("失败原因：{}", reason);
//                }
//            }
//        }
//    }
//
//    /**
//     * 通过ID获取数据
//     *
//     * @param index  索引，类似数据库
//     * @param type   类型，类似表
//     * @param id     数据ID
//     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
//     * @return
//     */
//    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) throws
//            Exception {
//        GetRequest request = new GetRequest(index, type, id);
//        request.realtime(false); // 实时(否)
//        request.refresh(true);  // 检索之前执行刷新(是)
//        GetResponse response = client.get(request);
//        return response.getSourceAsMap();
//    }
//
//    public static Map<String, Object> searchDataById_alias(String index, String type, String keyField, String id) throws
//            Exception {
//        Map<String, Object> rtnMap = null;
//        SearchRequest request = new SearchRequest(index).types(type);
//        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.filter(QueryBuilders.termQuery(keyField, id));
//        searchRequestBuilder.query(boolQuery);
//        request.source(searchRequestBuilder);
//        SearchResponse searchResponse = client.search(request);
//        SearchHit[] querySearchHit = searchResponse.getHits().getHits();
//        if (null != querySearchHit && querySearchHit.length > 0) {
//            for (SearchHit searchHit : querySearchHit) {//第一次查询結果
//                rtnMap = searchHit.getSourceAsMap();
//            }
//        }
//        return rtnMap;
//    }
//
//    public static Map query(String json) throws Exception {
//        JSONObject jSONObject = JSON.parseObject(json);
//        String index = jSONObject.get(ElasticsearchRestClientUtils.INDEX).toString();
//        String type = jSONObject.get(ElasticsearchRestClientUtils.TYPE).toString();
//        Integer currentPage = jSONObject.get("currentPage") == null ? null : Integer.valueOf(jSONObject.get("currentPage").toString());
//        Integer pageSize = jSONObject.get("pageSize") == null ? null : Integer.valueOf(jSONObject.get("pageSize").toString());
//        Integer maxExportNum = jSONObject.get("maxExportNum") == null ? null : Integer.valueOf(jSONObject.get("maxExportNum").toString());
//        Boolean isExport = jSONObject.get("isExport") == null ? null : Boolean.valueOf(jSONObject.get("isExport").toString());
//
//        //仅仅只查询数量，不需要数据
//        Boolean onlyCount = jSONObject.get("onlyCount") == null ? false : Boolean.valueOf(jSONObject.get("onlyCount").toString());
//        Map map = getSearchResponse(
//                json,
//                index, type,
//                currentPage, pageSize,
//                isExport, maxExportNum,
//                onlyCount);
//        return map;
//    }
//
//    public static SearchSourceBuilder searchRequestBuilder(String json) throws Exception {
//        JSONObject jSONObject = JSON.parseObject(json);
//        String hasChild= StringUtils.asString(jSONObject.get("hasChild"));
//        //防止影响到以前的es查询
//        if("true".equals(hasChild)){
//            return searchChildRequestBuilder(jSONObject);
//        }
//        String existFields = jSONObject.get("existFields") == null ? "" : jSONObject.get("existFields").toString();
//        String missingFields = jSONObject.get("missingFields") == null ? "" : jSONObject.get("missingFields").toString();
//        Map<String, Object> andParam = jSONObject.get("andParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("andParam").toString());
//        Map<String, Object> timeBetweenParam = jSONObject.get("timeBetweenParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("timeBetweenParam").toString());
//        Map<String, Object> numberBetweenParam = jSONObject.get("numberBetweenParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("numberBetweenParam").toString());
//        Map<String, Object> inParam = jSONObject.get("inParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("inParam").toString());
//        Map<String, Object> notinParam = jSONObject.get("notinParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("notinParam").toString());
//        Map<String, Object> likeParam = jSONObject.get("likeParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("likeParam").toString());
//        Map<String, Object> textParam = jSONObject.get("textParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("textParam").toString());
//        Map<String, Object> orParam = jSONObject.get("orParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("orParam").toString());
//        Map<String, Object> likeParams = jSONObject.get("likeParams") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("likeParams").toString());
//        JSONArray orArrParam = jSONObject.get("orArrParam") == null ? null : (JSONArray) JSON.parseArray(jSONObject.get("orArrParam").toString());
//
//        String sortField = jSONObject.get("sortField") == null ? null : jSONObject.get("sortField").toString();
//        String sortDirection = jSONObject.get("sortDirection") == null ? "desc" : jSONObject.get("sortDirection").toString();
//        String queryFields = jSONObject.get("queryFields") == null ? null : jSONObject.get("queryFields").toString();
//
//        String sortArray = jSONObject.get("sortArray") == null ? null : jSONObject.get("sortArray").toString();
//
//        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//        //existParam中List<String>
//        if(!com.hollycrm.hollybeacons.system.util.StringUtils.isNullOrBlank(existFields)){
//            String [] existStrArray = existFields.split(",");
//                for(String existStr : existStrArray){
//                    boolQuery.filter(QueryBuilders.existsQuery(existStr));
//            }
//
//        }
//
//        if(!com.hollycrm.hollybeacons.system.util.StringUtils.isNullOrBlank(missingFields)){
//            String [] missingStrArray = missingFields.split(",");
//                for(String missingStr : missingStrArray){
//                    boolQuery.mustNot(QueryBuilders.existsQuery(missingStr));
//            }
//
//        }
//
//
//        //字段精准查询
//        if (null != andParam && andParam.size() > 0) {
//            for (Map.Entry<String, Object> map : andParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    boolQuery.filter(QueryBuilders.termQuery(map.getKey(), map.getValue()));
//                }
//            }
//        }
//
//        //时间区间查询
//        if (null != timeBetweenParam && timeBetweenParam.size() > 0) {
//            String[] timeBetween = null;
//            for (Map.Entry<String, Object> map : timeBetweenParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    timeBetween = value.toString().split(",");
//                    boolQuery.filter(QueryBuilders.rangeQuery(map.getKey())
//                            .format("yyyy-MM-dd HH:mm:ss")
//                            .from(timeBetween[0])
//                            .to(timeBetween[1])
//                            .includeLower(true)//true包含下界<=，false 不包含下界<
//                            .includeUpper(true));//true包含上界>=，false 不包含上界>
//                }
//            }
//        }
//
//        //数字区间查询
//        if (null != numberBetweenParam && numberBetweenParam.size() > 0) {
//            String[] numberBetween = null;
//            for (Map.Entry<String, Object> map : numberBetweenParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    numberBetween = value.toString().split(",");
//                    boolQuery.filter(QueryBuilders.rangeQuery(map.getKey())
//                            .from(numberBetween[0])
//                            .to(numberBetween[1])
//                            .includeLower(true)//true包含下界<=，false 不包含下界<
//                            .includeUpper(true));//true包含上界>=，false 不包含上界>
//                }
//            }
//        }
//
//        //in查询
//        if (null != inParam && inParam.size() > 0) {
//            for (Map.Entry<String, Object> map : inParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    boolQuery.filter(QueryBuilders.termsQuery(map.getKey(), map.getValue().toString().split(",")));
//                }
//            }
//        }
//
//        //not in查询
//        if (null != notinParam && notinParam.size() > 0) {
//            BoolQueryBuilder inFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : notinParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String notinValue : map.getValue().toString().split(",")) {
//                        if (null != notinValue && !notinValue.equals("")) {
//                            inFilter.mustNot(QueryBuilders.termQuery(map.getKey(), notinValue));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(inFilter);
//        }
//
//        //likeParam
//        if (null != likeParam && likeParam.size() > 0) {
//            BoolQueryBuilder likeFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : likeParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            likeFilter.must(QueryBuilders.wildcardQuery(map.getKey(), "*" + phraseValue + "*"));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(likeFilter);
//        }
//
//        //likeParams - should查询
//        if (null != likeParams && likeParams.size() > 0) {
//            BoolQueryBuilder likeFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : likeParams.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            likeFilter.should(QueryBuilders.wildcardQuery(map.getKey(), "*" + phraseValue + "*"));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(likeFilter);
//        }
//
//        //textParam
//        if (null != textParam && textParam.size() > 0) {
//            BoolQueryBuilder textFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : textParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            //match全文搜索，包含关键分词的都匹配，按照score排序
//                            // match_phrase精确匹配关键词
//                            textFilter.must(QueryBuilders.matchPhraseQuery(map.getKey(), phraseValue));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(textFilter);
//        }
//
//        //orParam
//        if (null != orParam && orParam.size() > 0) {
//            BoolQueryBuilder orFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : orParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    orFilter.should(QueryBuilders.termsQuery(map.getKey(), value.toString().split(",")));
//                }
//            }
//            boolQuery.filter(orFilter);
//        }
//
//        //多个or字段条件
//        if (null != orArrParam && orArrParam.size() > 0) {
//            for(Object m: orArrParam){
//                Map<String,Object> typeMap = (Map<String,Object>)m;
//                BoolQueryBuilder orFilter = QueryBuilders
//                        .boolQuery();
//                for (Map.Entry<String, Object> map : typeMap.entrySet()) {
//                    Object value = map.getValue();
//                    if (null != value && !value.toString().trim().equals("")) {
//                        orFilter.should(QueryBuilders.termsQuery(map.getKey(), value.toString().split(",")));
//                    }
//                }
//                boolQuery.filter(orFilter);
//            }
//        }
//
//   /*     // 高亮（xxx=111,aaa=222）
//        if (StringUtils.isNotEmpty(highlightField)) {
//            HighlightBuilder highlightBuilder = new HighlightBuilder();
//            highlightBuilder.preTags("<span style='color:red' >");//设置前缀
//            highlightBuilder.postTags("</span>");//设置后缀
//            // 设置高亮字段
//            highlightBuilder.field(highlightField);
//            searchRequestBuilder.highlighter(highlightBuilder);
//        }*/
//
//        searchRequestBuilder.query(boolQuery);
//
//        //只查询某些字段
//        if (null != queryFields && StringUtils.isNotEmpty(queryFields)) {
//            searchRequestBuilder.fetchSource(queryFields.split(","), null);
//        }
//        searchRequestBuilder.fetchSource(true);
//
//        //排序
//        if (StringUtils.isNotEmpty(sortField)) {
//            searchRequestBuilder.sort(sortField, SortOrder.fromString(sortDirection));
//        } else if (StringUtils.isNotEmpty(sortArray)) {
//            if(sortArray != null){
//                String[] sortFieldArr = sortArray.split(",");
//                for (String sortFieldStr : sortFieldArr) {
//                    String[] sortFieldItem = sortFieldStr.split("=");
//                    searchRequestBuilder.sort(sortFieldItem[0], SortOrder.fromString(sortFieldItem[1]));
//                }
//            }
//        } else {
//            // 设置是否按查询匹配度排序
//            searchRequestBuilder.explain(true);
//        }
//
//        return searchRequestBuilder;
//    }
//
//    public static SearchSourceBuilder searchChildRequestBuilder(JSONObject jSONObject) {
//        String existFields = jSONObject.get("existFields") == null ? "" : jSONObject.get("existFields").toString();
//        String missingFields = jSONObject.get("missingFields") == null ? "" : jSONObject.get("missingFields").toString();
//        Map<String, Object> andParam = jSONObject.get("andParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("andParam").toString());
//        Map<String, Object> timeBetweenParam = jSONObject.get("timeBetweenParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("timeBetweenParam").toString());
//        Map<String, Object> numberBetweenParam = jSONObject.get("numberBetweenParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("numberBetweenParam").toString());
//        Map<String, Object> inParam = jSONObject.get("inParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("inParam").toString());
//        Map<String, Object> notinParam = jSONObject.get("notinParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("notinParam").toString());
//        Map<String, Object> likeParam = jSONObject.get("likeParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("likeParam").toString());
//        Map<String, Object> textParam = jSONObject.get("textParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("textParam").toString());
//        Map<String, Object> orParam = jSONObject.get("orParam") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("orParam").toString());
//        JSONArray orArrParam = jSONObject.get("orArrParam") == null ? null : (JSONArray) JSON.parseArray(jSONObject.get("orArrParam").toString());
//
//        String sortField = jSONObject.get("sortField") == null ? null : jSONObject.get("sortField").toString();
//        String sortDirection = jSONObject.get("sortDirection") == null ? "desc" : jSONObject.get("sortDirection").toString();
//        String queryFields = jSONObject.get("queryFields") == null ? null : jSONObject.get("queryFields").toString();
//
//        String sortArray = jSONObject.get("sortArray") == null ? null : jSONObject.get("sortArray").toString();
//
//        SearchSourceBuilder searchRequestBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//        //子类型字段
//        Map<String, Object> child = new HashMap<>();
//        child = jSONObject.get("childQuery") == null ? null : (Map<String, Object>) JSON.parseObject(jSONObject.get("childQuery").toString());
//        Map<String, Object> childAndParam = child.get("andParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("andParam").toString());
//        Map<String, Object> childTimeBetweenParam = child.get("timeBetweenParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("timeBetweenParam").toString());
//        Map<String, Object> childNumberBetweenParam = child.get("numberBetweenParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("numberBetweenParam").toString());
//        Map<String, Object> childInParam = child.get("inParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("inParam").toString());
//        Map<String, Object> childNotinParam = child.get("notinParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("notinParam").toString());
//        Map<String, Object> childLikeParam = child.get("likeParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("likeParam").toString());
//        Map<String, Object> childTextParam = child.get("textParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("textParam").toString());
//        Map<String, Object> childOrParam = child.get("orParam") == null ? null : (Map<String, Object>) JSON.parseObject(child.get("orParam").toString());
//        String childType=StringUtils.asString(child.get("type"));
//
//        BoolQueryBuilder childBoolQuery = QueryBuilders.boolQuery();
//
//
//        //字段精准查询
//        if (null != childAndParam && childAndParam.size() > 0) {
//            for (Map.Entry<String, Object> map : childAndParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    childBoolQuery.filter(QueryBuilders.termQuery(map.getKey(), map.getValue()));
//                }
//            }
//        }
//
//        //时间区间查询
//        if (null != childTimeBetweenParam && childTimeBetweenParam.size() > 0) {
//            String[] timeBetween = null;
//            for (Map.Entry<String, Object> map : childTimeBetweenParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    timeBetween = value.toString().split(",");
//                    childBoolQuery.filter(QueryBuilders.rangeQuery(map.getKey())
//                            .format("yyyy-MM-dd HH:mm:ss")
//                            .from(timeBetween[0])
//                            .to(timeBetween[1])
//                            .includeLower(true)//true包含下界<=，false 不包含下界<
//                            .includeUpper(true));//true包含上界>=，false 不包含上界>
//                }
//            }
//        }
//
//        //数字区间查询
//        if (null != childNumberBetweenParam && childNumberBetweenParam.size() > 0) {
//            String[] numberBetween = null;
//            for (Map.Entry<String, Object> map : childNumberBetweenParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    numberBetween = value.toString().split(",");
//                    childBoolQuery.filter(QueryBuilders.rangeQuery(map.getKey())
//                            .from(numberBetween[0])
//                            .to(numberBetween[1])
//                            .includeLower(true)//true包含下界<=，false 不包含下界<
//                            .includeUpper(true));//true包含上界>=，false 不包含上界>
//                }
//            }
//        }
//
//        //in查询
//        if (null != childInParam && childInParam.size() > 0) {
//            for (Map.Entry<String, Object> map : childInParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    childBoolQuery.filter(QueryBuilders.termsQuery(map.getKey(), map.getValue().toString().split(",")));
//                }
//            }
//        }
//
//        //not in查询
//        if (null != childNotinParam && childNotinParam.size() > 0) {
//            BoolQueryBuilder inFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : childNotinParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String notinValue : map.getValue().toString().split(",")) {
//                        if (null != notinValue && !notinValue.equals("")) {
//                            inFilter.mustNot(QueryBuilders.termQuery(map.getKey(), notinValue));
//                        }
//                    }
//                }
//            }
//            childBoolQuery.filter(inFilter);
//        }
//
//        //likeParam
//        if (null != childLikeParam && childLikeParam.size() > 0) {
//            BoolQueryBuilder likeFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : childLikeParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            likeFilter.must(QueryBuilders.wildcardQuery(map.getKey(), "*" + phraseValue + "*"));
//                        }
//                    }
//                }
//            }
//            childBoolQuery.filter(likeFilter);
//        }
//
//        //textParam
//        if (null != childTextParam && childTextParam.size() > 0) {
//            BoolQueryBuilder textFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : childTextParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            //match全文搜索，包含关键分词的都匹配，按照score排序
//                            // match_phrase精确匹配关键词
//                            textFilter.must(QueryBuilders.matchPhraseQuery(map.getKey(), phraseValue));
//                        }
//                    }
//                }
//            }
//            childBoolQuery.filter(textFilter);
//        }
//
//        //orParam
//        if (null != childOrParam && childOrParam.size() > 0) {
//            BoolQueryBuilder orFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : childOrParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    orFilter.should(QueryBuilders.termsQuery(map.getKey(), value.toString().split(",")));
//                }
//            }
//            childBoolQuery.filter(orFilter);
//        }
//
//        //多个or字段条件
//        if (null != orArrParam && orArrParam.size() > 0) {
//            for(Object m: orArrParam){
//                Map<String,Object> typeMap = (Map<String,Object>)m;
//                BoolQueryBuilder orFilter = QueryBuilders
//                        .boolQuery();
//                for (Map.Entry<String, Object> map : typeMap.entrySet()) {
//                    Object value = map.getValue();
//                    if (null != value && !value.toString().trim().equals("")) {
//                        orFilter.should(QueryBuilders.termsQuery(map.getKey(), value.toString().split(",")));
//                    }
//                }
//                boolQuery.filter(orFilter);
//            }
//        }
//
//        HasChildQueryBuilder childBuilder = new HasChildQueryBuilder(childType,childBoolQuery, ScoreMode.Total);
//
//        childBuilder.innerHit(new InnerHitBuilder().setSize(99));//子查询数据最多99条
//        boolQuery.filter(childBuilder);
//
//        //existParam中List<String>
//        if(!com.hollycrm.hollybeacons.system.util.StringUtils.isNullOrBlank(existFields)){
//            String [] existStrArray = existFields.split(",");
//            for(String existStr : existStrArray){
//                boolQuery.filter(QueryBuilders.existsQuery(existStr));
//            }
//
//        }
//
//        if(!com.hollycrm.hollybeacons.system.util.StringUtils.isNullOrBlank(missingFields)){
//            String [] missingStrArray = missingFields.split(",");
//            for(String missingStr : missingStrArray){
//                boolQuery.mustNot(QueryBuilders.existsQuery(missingStr));
//            }
//
//        }
//
//
//        //字段精准查询
//        if (null != andParam && andParam.size() > 0) {
//            for (Map.Entry<String, Object> map : andParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    boolQuery.filter(QueryBuilders.termQuery(map.getKey(), map.getValue()));
//                }
//            }
//        }
//
//        //时间区间查询
//        if (null != timeBetweenParam && timeBetweenParam.size() > 0) {
//            String[] timeBetween = null;
//            for (Map.Entry<String, Object> map : timeBetweenParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    timeBetween = value.toString().split(",");
//                    boolQuery.filter(QueryBuilders.rangeQuery(map.getKey())
//                            .format("yyyy-MM-dd HH:mm:ss")
//                            .from(timeBetween[0])
//                            .to(timeBetween[1])
//                            .includeLower(true)//true包含下界<=，false 不包含下界<
//                            .includeUpper(true));//true包含上界>=，false 不包含上界>
//                }
//            }
//        }
//
//        //数字区间查询
//        if (null != numberBetweenParam && numberBetweenParam.size() > 0) {
//            String[] numberBetween = null;
//            for (Map.Entry<String, Object> map : numberBetweenParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    numberBetween = value.toString().split(",");
//                    boolQuery.filter(QueryBuilders.rangeQuery(map.getKey())
//                            .from(numberBetween[0])
//                            .to(numberBetween[1])
//                            .includeLower(true)//true包含下界<=，false 不包含下界<
//                            .includeUpper(true));//true包含上界>=，false 不包含上界>
//                }
//            }
//        }
//
//        //in查询
//        if (null != inParam && inParam.size() > 0) {
//            for (Map.Entry<String, Object> map : inParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    boolQuery.filter(QueryBuilders.termsQuery(map.getKey(), map.getValue().toString().split(",")));
//                }
//            }
//        }
//
//        //not in查询
//        if (null != notinParam && notinParam.size() > 0) {
//            BoolQueryBuilder inFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : notinParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String notinValue : map.getValue().toString().split(",")) {
//                        if (null != notinValue && !notinValue.equals("")) {
//                            inFilter.mustNot(QueryBuilders.termQuery(map.getKey(), notinValue));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(inFilter);
//        }
//
//        //likeParam
//        if (null != likeParam && likeParam.size() > 0) {
//            BoolQueryBuilder likeFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : likeParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            likeFilter.must(QueryBuilders.wildcardQuery(map.getKey(), "*" + phraseValue + "*"));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(likeFilter);
//        }
//
//        //textParam
//        if (null != textParam && textParam.size() > 0) {
//            BoolQueryBuilder textFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : textParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    for (String phraseValue : map.getValue().toString().split(",")) {
//                        if (null != phraseValue && !phraseValue.equals("")) {
//                            //match全文搜索，包含关键分词的都匹配，按照score排序
//                            // match_phrase精确匹配关键词
//                            textFilter.must(QueryBuilders.matchPhraseQuery(map.getKey(), phraseValue));
//                        }
//                    }
//                }
//            }
//            boolQuery.filter(textFilter);
//        }
//
//        //orParam
//        if (null != orParam && orParam.size() > 0) {
//            BoolQueryBuilder orFilter = QueryBuilders
//                    .boolQuery();
//            for (Map.Entry<String, Object> map : orParam.entrySet()) {
//                Object value = map.getValue();
//                if (null != value && !value.toString().trim().equals("")) {
//                    orFilter.should(QueryBuilders.termsQuery(map.getKey(), value.toString().split(",")));
//                }
//            }
//            boolQuery.filter(orFilter);
//        }
//
//        searchRequestBuilder.query(boolQuery);
//
//        //只查询某些字段
//        if (null != queryFields && StringUtils.isNotEmpty(queryFields)) {
//            searchRequestBuilder.fetchSource(queryFields.split(","), null);
//        }
//        searchRequestBuilder.fetchSource(true);
//
//        //排序
//        if (StringUtils.isNotEmpty(sortField)) {
//            searchRequestBuilder.sort(sortField, SortOrder.fromString(sortDirection));
//        } else if (StringUtils.isNotEmpty(sortArray)) {
//            if(sortArray !=null){
//                String[] sortFieldArr = sortArray.split(",");
//                for (String sortFieldStr : sortFieldArr) {
//                    String[] sortFieldItem = sortFieldStr.split("=");
//                    searchRequestBuilder.sort(sortFieldItem[0], SortOrder.fromString(sortFieldItem[1]));
//                }
//            }
//        } else {
//            // 设置是否按查询匹配度排序
//            searchRequestBuilder.explain(true);
//        }
//        return searchRequestBuilder;
//    }
//
//
//    public static Map getSearchResponse(
//            String json,
//            String index, String type,
//            Integer currentPage, Integer pageSize,
//            Boolean isExport,
//            Integer maxExportNum,
//            Boolean onlyCount) throws Exception {
//        SearchRequest request = new SearchRequest(index).types(type);
//
//        SearchSourceBuilder searchRequestBuilder = searchRequestBuilder(json);
//
//        Map result = new HashMap();
//        if (null != isExport && isExport) {//是否导出
//            //导出使用scroll滚动查询
//            return scrollQuery(maxExportNum, searchRequestBuilder, request);
//        } else {//非导出使用简单查询
//            if (null != currentPage && null != pageSize) {//当分页查询的数据超过10000，则需要滚动查询
//                if (currentPage * pageSize > 10000) {
//                    maxExportNum = currentPage * pageSize;
//                    Map<String, Object> var = scrollQuery(maxExportNum, searchRequestBuilder, request);
//                    List<Map<String, Object>> total = var.get("rows") == null ? new ArrayList<Map<String, Object>>() : (List<Map<String, Object>>) var.get("rows");
//                    result.put("rows", total.subList((currentPage - 1) * pageSize, total.size()));
//                    result.put("totalRows", var.get("totalRows"));
//                    return result;
//                } else {
//                    searchRequestBuilder.from((currentPage - 1) * pageSize).size(pageSize);//分页
//                }
//            } else {
//                searchRequestBuilder.size(10000);  //不分页查询全部（不设置size默认值返回10条记录）
//            }
//            log.mybatis("\n{}", searchRequestBuilder);//打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
//            request.source(searchRequestBuilder);
//            SearchResponse searchResponse = client.search(request);
//
//            if (null != onlyCount && !onlyCount) {//仅查询数据量， 不需要数据
//                List<Map<String, Object>> total = new ArrayList<Map<String, Object>>();
//                SearchHit[] querySearchHit = searchResponse.getHits().getHits();
//                if (null != querySearchHit && querySearchHit.length > 0) {
//                    for (SearchHit searchHit : querySearchHit) {//第一次查询結果
//                        Map<String, Object> map=searchHit.getSourceAsMap();
//                        if(null!=searchHit.getInnerHits()){
//                            Map<String, SearchHits> hitsMap=searchHit.getInnerHits();
//                            for (Map.Entry<String, SearchHits> entry:hitsMap.entrySet()) {
//                                String key=entry.getKey();
//                                SearchHits hits=entry.getValue();
//                                List<Map<String, Object>> child=new ArrayList<>();
//                                for (SearchHit searchHitChild : hits.getHits()) {//第一次查询結果
//                                    Map<String, Object> mapChild=searchHitChild.getSourceAsMap();
//                                    child.add(mapChild);
//                                }
//                                map.put(key,child);
//                            }
//                        }
//                        total.add(map);
//                    }
//                }
//                result.put("rows", total);
//            }
//
//            result.put("totalRows", searchResponse.getHits().totalHits);
//            return result;
//        }
//    }
//
//    public static Map<String, Object> scrollQueryOne(Integer maxExportNum,
//                                                     SearchSourceBuilder searchRequestBuilder,
//                                                     SearchRequest request) throws Exception {
//        Map<String, Object> result = new HashMap<String, Object>();
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        final Scroll scroll = new Scroll(TimeValue.timeValueSeconds(6000));
//        request.scroll(scroll);
//        if (null != maxExportNum && maxExportNum < ONCE_SCROLL_NUM) {//最大导出条数
//            searchRequestBuilder.size(maxExportNum.intValue());
//        } else {
//            searchRequestBuilder.size(ONCE_SCROLL_NUM);
//        }
//
//        log.mybatis("查询语句：{}", searchRequestBuilder);
//        request.source(searchRequestBuilder);
//        try {
//            SearchResponse searchResponse = client.search(request);
//            SearchHits hits = searchResponse.getHits();
//
//            SearchHit[] querySearchHit = hits.getHits();//第一次命中数量
//            if (null != querySearchHit && querySearchHit.length > 0) {
//                for (SearchHit searchHit : querySearchHit) {//第一次查询結果
//                    Map<String, Object> map=searchHit.getSourceAsMap();
//                    if(null!=searchHit.getInnerHits()){
//                        Map<String, SearchHits> hitsMap=searchHit.getInnerHits();
//                        for (Map.Entry<String, SearchHits> entry:hitsMap.entrySet()) {
//                            String key=entry.getKey();
//                            SearchHits chits=entry.getValue();
//                            List<Map<String, Object>> child=new ArrayList<>();
//                            for (SearchHit searchHitChild : chits.getHits()) {//第一次查询結果
//                                Map<String, Object> mapChild=searchHitChild.getSourceAsMap();
//                                child.add(mapChild);
//                            }
//                            map.put(key,child);
//                        }
//                    }
//                    list.add(map);
//                }
//            }
//
//            String scrollId = searchResponse.getScrollId();//下一次
//            long totalHis = hits.totalHits;//总命中数
//            long queryHis = hits.getHits().length;
//            Long remainHis = 0l;
//            if (totalHis < maxExportNum) {
//                remainHis = totalHis - queryHis;//剩余查询数量
//            } else {
//                remainHis = maxExportNum - queryHis;//剩余查询数量
//            }
//
//            result.put("list", list);
//            result.put("totalHis", totalHis);
//            result.put("queryHis", queryHis);
//            result.put("remainHis", remainHis);
//            result.put("scrollId", scrollId);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return result;
//    }
//
//    public static Map<String, Object> scrollQueryMore(Map<String, Object> resultOne,
//                                                      Integer maxExportNum) throws Exception {
//
//        Map<String, Object> result = new HashMap<String, Object>();
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//        String scrollId = resultOne.get("scrollId").toString();
//        Long totalHis = Long.valueOf(resultOne.get("totalHis").toString());
//        Long queryHis = Long.valueOf(resultOne.get("queryHis").toString());
//        Long remainHis = Long.valueOf(resultOne.get("remainHis").toString());
//
//        SearchResponse searchScrollResponse = null;
//        SearchHit[] querySearchHit = null;//命中数量
//
//        if (queryHis < totalHis && remainHis > 0) {
//            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//            scrollRequest.scroll(TimeValue.timeValueSeconds(6000));
//            searchScrollResponse = client.searchScroll(scrollRequest);
//            querySearchHit = searchScrollResponse.getHits().getHits();
//            if (null != querySearchHit && querySearchHit.length > 0) {//合并滚动查询的结果
//                List<SearchHit> his = null;
//                if (remainHis < ONCE_SCROLL_NUM) {
//                    if (querySearchHit.length < remainHis) {
//                        his = Arrays.asList(querySearchHit);
//                    } else {
//                        his = Arrays.asList(querySearchHit).subList(0, remainHis.intValue());
//                    }
//                } else {
//                    his = Arrays.asList(querySearchHit);
//                }
//                for (SearchHit searchHit : his) {
//                    Map<String, Object> map=searchHit.getSourceAsMap();
//                    if(null!=searchHit.getInnerHits()){
//                        Map<String, SearchHits> hitsMap=searchHit.getInnerHits();
//                        for (Map.Entry<String, SearchHits> entry:hitsMap.entrySet()) {
//                            String key=entry.getKey();
//                            SearchHits chits=entry.getValue();
//                            List<Map<String, Object>> child=new ArrayList<>();
//                            for (SearchHit searchHitChild : chits.getHits()) {//第一次查询結果
//                                Map<String, Object> mapChild=searchHitChild.getSourceAsMap();
//                                child.add(mapChild);
//                            }
//                            map.put(key,child);
//                        }
//                    }
//                    list.add(map);
//                }
//
//            }
//            queryHis = queryHis + searchScrollResponse.getHits().getHits().length;
//            if (totalHis < maxExportNum) {
//                remainHis = totalHis - queryHis;//剩余查询数量
//            } else {
//                remainHis = maxExportNum - queryHis;//剩余查询数量
//            }
//        }
//        result.put("list", list);
//        result.put("totalHis", totalHis);
//        result.put("queryHis", queryHis);
//        result.put("remainHis", remainHis);
//        if (remainHis > 0 && null != searchScrollResponse) {
//            result.put("scrollId", searchScrollResponse.getScrollId());
//        }
//        return result;
//    }
//
//    private static Map<String, Object> scrollQuery(Integer maxExportNum,
//                                                   SearchSourceBuilder searchRequestBuilder,
//                                                   SearchRequest request) throws Exception {
//
//        Map<String, Object> var = new HashMap<String, Object>();
//        final Scroll scroll = new Scroll(new TimeValue(60));
//        request.scroll(scroll);
//        if (null != maxExportNum && maxExportNum < ONCE_SCROLL_NUM) {//最大导出条数
//            searchRequestBuilder.size(maxExportNum.intValue());
//        } else {
//            searchRequestBuilder.size(ONCE_SCROLL_NUM);
//        }
//
//        log.mybatis("查询语句：{}", searchRequestBuilder);
//        long firstStartTime = System.currentTimeMillis();
//        request.source(searchRequestBuilder);
//        SearchResponse searchResponse = client.search(request);
//        long firstEndTime = System.currentTimeMillis();
//        String scrollId = searchResponse.getScrollId();//下一次
//        SearchHits hits = searchResponse.getHits();
//        long totalHis = hits.totalHits;//总命中数
//        long queryHis = hits.getHits().length;
//        Long remainHis = maxExportNum - queryHis;//剩余查询数量
//        long costTime = 0l;
//
//        List<Map<String, Object>> total = new ArrayList<Map<String, Object>>();
//        SearchHit[] querySearchHit = hits.getHits();//第一次命中数量
//        String tempFileName = null;
//        if (null != querySearchHit && querySearchHit.length > 0) {
//            for (SearchHit searchHit : querySearchHit) {//第一次查询結果
//                total.add(searchHit.getSourceAsMap());
//            }
//            tempFileName = writeObjectToFile(tempFileName, total);
//        }
//        costTime = firstEndTime - firstStartTime;
//        log.mybatis("query:    1  costTime:" + costTime + "    queryMum:" + total.size());
//        SearchResponse searchScrollResponse = null;
//
//        long secondStartTime = 0;
//        long secondEndTime = 0;
//        int i = 1;
//        long totalTime = firstEndTime - firstStartTime;
//        while (queryHis < totalHis && remainHis > 0) {
//            i++;
//            secondStartTime = System.currentTimeMillis();
//            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//            scrollRequest.scroll(TimeValue.timeValueSeconds(60));
//            searchScrollResponse = client.searchScroll(scrollRequest);
//            querySearchHit = searchScrollResponse.getHits().getHits();
//            if (null != querySearchHit && querySearchHit.length > 0) {//合并滚动查询的结果
//                total = new ArrayList<Map<String, Object>>();
//                List<SearchHit> list = null;
//                if (remainHis < ONCE_SCROLL_NUM) {
//                    if (querySearchHit.length < remainHis) {
//                        list = Arrays.asList(querySearchHit);
//                    } else {
//                        list = Arrays.asList(querySearchHit).subList(0, remainHis.intValue());
//                    }
//                } else {
//                    list = Arrays.asList(querySearchHit);
//                }
//                for (SearchHit searchHit : list) {
//                    total.add(searchHit.getSourceAsMap());
//                }
//                tempFileName = writeObjectToFile(tempFileName, total);
//            }
//            queryHis = queryHis + searchScrollResponse.getHits().getHits().length;
//            secondEndTime = System.currentTimeMillis();
//            totalTime = totalTime + (secondEndTime - secondStartTime);
//            remainHis = maxExportNum - queryHis;//剩余查询数量
//            costTime = costTime + secondEndTime - secondStartTime;
//            log.mybatis("query:    " + i + "   costTime:" + (secondEndTime - secondStartTime) + "    queryNum:" + total.size());
//        }
//        log.mybatis("查询总耗时：" + costTime);
//        var.put("totalRows", totalHis);
//        var.put("rows", tempFileName == null ? new ArrayList<Map<String, Object>>() : readFileByLines(tempFileName));
//        return var;
//    }
//
//    /**
//     * 判断字符串中是否包含中文
//     *
//     * @param str 待校验字符串
//     * @return 是否为中文
//     * @warn 不能校验是否为中文标点符号
//
//    public static boolean isContainChinese(String str) {
//    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//    Matcher m = p.matcher(str);
//    if (m.find()) {
//    return true;
//    }
//    return false;
//    }
//     **/
//
//    /**
//     * 将多次滚动查询的结果追加到临时文件
//     *
//     * @param fileName 临时文件名
//     * @param obj      滚动查询结果
//     * @return
//     * @throws IOException
//     */
//    public static String writeObjectToFile(String fileName, Object obj) {
//        File tempFile = null;
//        FileWriter fw = null;
//        PrintWriter pw = null;
//        try {
//            tempFile = createTempFile(fileName);
//            fw = new FileWriter(tempFile, true);
//            pw = new PrintWriter(fw);
//            pw.println(JSON.toJSONString(obj));
//            pw.flush();
//            fw.flush();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (null != pw) {
//                pw.close();
//            }
//            if (null != fw) {
//                try {
//                    fw.close();
//                } catch (Exception e) {
//                    log.error(e.getMessage(), e);
//                }
//            }
//        }
//        return tempFile == null ? "" : tempFile.getPath();
//    }
//
//    public static File createTempFile(String fileName) throws IOException {
//        File tempFile = null;
//        if (null != fileName && !fileName.isEmpty()) {
//            tempFile = new File(fileName);
//        } else {
//            tempFile = Files.createTempFile(null, null).toFile();//创建临时文件
//        }
//
//        return tempFile;
//    }
//
//    /**
//     * 从临时文件中取出多次滚动查询的结果集
//     *
//     * @param fileName 临时文件名
//     * @return
//     */
//    public static List<Map<String, Object>> readFileByLines(String fileName) {
//        List<Map<String, Object>> total = new ArrayList<Map<String, Object>>();
//        File file = new File(fileName);
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader(file));
//            String tempString = null;
//            List<Map> list = null;
//            while ((tempString = reader.readLine()) != null) {
//                list = JSONArray.parseArray(tempString, Map.class);
//                total.addAll((List) list);
//            }
//            reader.close();
//
//            if (!file.delete()) {
//                log.mybatis("file delete error");
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                    log.error(e1.getMessage(), e1);
//                }
//            }
//        }
//        return total;
//    }
//}
