package com.know.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: FaceCat
 * @Date: 2020/10/18 9:52
 */
public class ContentUtil {

    public static final Logger logger = LoggerFactory.getLogger(ContentUtil.class);
    public static  final Integer  ZERO=0;
    public static  final Integer  ONE=1;
    public static  final Integer  TOW=2;
    public static  final Integer  THREE=3;
    public static  final Integer  FOUR=4;
    public static  final String   REDIS_LOCK="{face}.Cat";  //评论缓存访问人数redis

    public static final String TOPIC_COURSE_INFO ="SSX0001.000"; //课程信息表
    public static final String TOPIC_COURSEACCESS_DETAIL ="SSX0002.000";//课程访问明细表
    public static final String TOPIC_COURSELIKE_DETAIL ="SSX0003.000"; //课程点赞明细表
    public static final String TOPIC_EXAM_INFO ="SSX0004.000"; //考试信息表
    public static final String TOPIC_EXAM_DETAIL ="SSX0005.000"; //考试信息明细表

}
