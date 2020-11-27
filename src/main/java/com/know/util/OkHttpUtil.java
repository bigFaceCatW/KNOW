package com.know.util;

import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 描    述: OKHTTP的工具类
 * 创建时间: 2018/05/22
 */
public class OkHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    static OkHttpClient okHttpClient = SpringUtil.getBean(OkHttpClient.class);

    /**
     * 描  述: OKHTTP GET 方式请求
     * 参  数:url 请求的url
     * 参  数:queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String get(String url, Map<String, Object> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = url.contains("?") ? false : true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, Object>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }

        Request request = createRequestBuild(sb.toString()).build();
        return executeAsString(request);
    }

    public static String get(String url, Map<String, Object> queries, String token) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = url.contains("?") ? false : true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, Object>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }

        Request request = createRequestBuild(sb.toString(), token).build();
        return executeAsString(request);
    }

    /**
     * 描  述: OKHTTP POST 方式请求
     * 参  数:url 请求的url
     * 参  数:params form表单POST提交的map
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, params, WebUtils.getToken());
    }

    /**
     * 描  述: OKHTTP POST 方式请求
     * 参  数:url 请求的url
     * 参  数:params form表单POST提交的map
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String post(String url, Map<String, String> params, String token) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = createRequestBuild(url, token)
                .post(builder.build())
                .build();
        return executeAsString(request);
    }

    /**
     * 描  述: OKHTTP POST 方式请求
     * 参  数:url 请求的url
     * 参  数:params form表单POST提交的map
     * 参  数:file 上传的文件
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String post(String url, Map<String, String> params, File file, String token) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
        }
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            builder.addFormDataPart("fileData", file.getName(), body);

        }
        Request request = createRequestBuild(url, token)
                .post(builder.build())
                .build();
        return executeAsString(request);
    }

    /**
     * 描  述: OKHTTP HEADER 方式请求
     * 参  数:url 请求的url
     * 参  数:queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String getForHeader(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = url.contains("?") ? false : true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = createRequestBuild(sb.toString())
                .addHeader("key", "value")
                .build();

        return executeAsString(request);
    }

    /**
     * 描  述: OKHTTP POST 方式请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参  数:url 请求的url
     * 参  数:jsonParams 请求的JSON格式字符串
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String postJsonParams(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = createRequestBuild(url)
                .post(requestBody)
                .build();
        return executeAsString(request);
    }


    public static String postJsonParams(String url, String jsonParams, String token) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = createRequestBuild(url, token).post(requestBody).build();
        return executeAsString(request);
    }

    /**
     * 描  述: OKHTTP POST 方式请求发送xml数据
     * 参  数:url 请求的url
     * 参  数:xml 请求的xmlString
     * 返回值: java.lang.String
     * 创建时间: 2018/5/25
     */
    public static String postXmlParams(String url, String xml) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = createRequestBuild(url)
                .post(requestBody)
                .build();
        return executeAsString(request);
    }


    /**
     * 创建请求构建器
     */
    private static Request.Builder createRequestBuild(String url) {
        return createRequestBuild(url, WebUtils.getToken());
    }

    private static Request.Builder createRequestBuild(String url, String token) {
        //创建构建器
        Request.Builder build = new Request.Builder().url(url);
        //尝试添加token
        if (!StringUtils.isEmpty(token)) {
            build.addHeader("Authorization", "Bearer " + token);
        }
        //返回构建器
        return build;
    }

    /**
     * 请求处理，返回body字符串
     */
    private static String executeAsString(Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            logger.error("okhttp3 post error >> ex = {}", e);
        }
        return StringUtils.EMPTY;
    }
}