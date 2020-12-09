//package com.know.es.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowire;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class RestHighLevelClientClusterConfig {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RestHighLevelClientConfig.class);
//
//    @Value("${elasticsearch.cluster.nodes}")
//    private String nodes;
//
//    @Value("${elasticsearch.userName}")
//    private String userName;
//
//    @Value("${elasticsearch.password}")
//    private String password;
//
//    private static final String SCHEMA = "http";
//    private int connectTimeOut = 1000;
//    private int socketTimeOut = 3000000;
//    private int connectionRequestTimeOut = 500;
//    private int maxConnectNum = 100;
//    private int maxConnectPerRoute = 100;
//    private List<HttpHost> httpHost = new ArrayList<HttpHost>();
//    private boolean uniqueConnectTimeConfig = true;
//    private boolean uniqueConnectNumConfig = true;
//    private RestClientBuilder builder;
//    private RestHighLevelClient client;
//
//    @Bean(autowire = Autowire.BY_NAME, name = "restHighLevelClient")
//    public RestHighLevelClient client() {
//        LOGGER.mybatis("ES Rest客户端初始化开始。。。。。");
//        String hostName = "";
//        int port = 0;
//        for (String node : nodes.split(",")) {
//            //配置信息Settings自定义,下面设置为EMPTY
//            hostName = node.split(":")[0];
//            port = Integer.valueOf(node.split(":")[1]);
//            httpHost.add(new HttpHost(hostName, port, SCHEMA));
//        }
//        HttpHost[] httpHostArr = new HttpHost[httpHost.size()];
//        builder = RestClient.builder(httpHost.toArray(httpHostArr));
//
//        if (uniqueConnectTimeConfig) {
//            setConnectTimeOutConfig();
//        }
//        if (uniqueConnectNumConfig) {
//            setMutiConnectConfig();
//        }
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials(userName, password));
//        client = new RestHighLevelClient(builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            }
//        }));
//        LOGGER.mybatis("ES Rest客户端初始化结束。。。。。");
//        return client;
//    }
//
//    /**
//     * 异步httpclient的连接延时配置
//     */
//    public void setConnectTimeOutConfig() {
//        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//            @Override
//            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
//                requestConfigBuilder.setConnectTimeout(connectTimeOut);
//                requestConfigBuilder.setSocketTimeout(socketTimeOut);
//                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
//                return requestConfigBuilder;
//            }
//        });
//    }
//
//    /**
//     * 异步httpclient的连接数配置
//     */
//    public void setMutiConnectConfig() {
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                httpClientBuilder.setMaxConnTotal(maxConnectNum);
//                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
//                return httpClientBuilder;
//            }
//        });
//    }
//}
