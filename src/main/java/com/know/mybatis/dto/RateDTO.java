package com.know.mybatis.dto;


import com.know.util.ExcelResources;

import java.io.Serializable;
import java.util.List;

/**
 * @author: FaceCat
 * @date: 2020/8/17 21:10
 */
public class RateDTO implements Serializable {
    private static final long serialVersionUID = -4937320322523069126L;
    private long questionId;
    private int questionModelId;
    @ExcelResources(title="题目内容",order=1,width=300)
    private  String questionContent;
    @ExcelResources(title="选项",order=2,width=300)
    private List<LearnOptionDTO> rateList;
    @ExcelResources(title="正确率",order=3,width=80)
    private List<LearnOptionDTO> optionDTOList;
    @ExcelResources(title="选择人数",order=4,width=80)
    private String questionAnswer;

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public List<LearnOptionDTO> getRateList() {
        return rateList;
    }

    public void setRateList(List<LearnOptionDTO> rateList) {
        this.rateList = rateList;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getQuestionModelId() {
        return questionModelId;
    }

    public void setQuestionModelId(int questionModelId) {
        this.questionModelId = questionModelId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public List<LearnOptionDTO> getOptionDTOList() {
        return optionDTOList;
    }

    public void setOptionDTOList(List<LearnOptionDTO> optionDTOList) {
        this.optionDTOList = optionDTOList;
    }
}
