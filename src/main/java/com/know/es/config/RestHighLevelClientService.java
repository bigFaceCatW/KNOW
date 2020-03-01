package com.know.es.config;

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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

//DeleteRequest GetRequest UpdateRequest 都是根据 id 操作文档

/**
 * @author anqi
 */
@Service
public class RestHighLevelClientService {

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
                request.add(new IndexRequest(indexName).id(JSONObject.parseObject(s.toString()).getString("age")).source(JSONObject.toJSONString(s), XContentType.JSON));
            }
        }
        return client.bulk(request, RequestOptions.DEFAULT);
    }





}

