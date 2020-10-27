package com.know.info.dto;

import java.io.Serializable;

/**
 * Demo class
 *
 * @author test
 * @date 2018/10/31
 */
public class LearnOptionDTO implements Serializable {

    private static final long serialVersionUID = -4030823792505142997L;
    /**
     *   题目选项I
     */
    private long questionOptionId;
    /**
     *   试题ID
     */
    private long questionId ;
    /**
     *   A.B.C.D.E
     */
    private String questionOptionMark;
    /**
     *   选项内容
     */
    private String questionOptionContent;




    /**
     *   选项内容
     */



    public long getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(long questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionOptionMark() {
        return questionOptionMark;
    }

    public void setQuestionOptionMark(String questionOptionMark) {
        this.questionOptionMark = questionOptionMark;
    }

    public String getQuestionOptionContent() {
        return questionOptionContent;
    }

    public void setQuestionOptionContent(String questionOptionContent) {
        this.questionOptionContent = questionOptionContent;
    }
}
