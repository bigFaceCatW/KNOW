package com.know.mybatis.dto;


import com.know.util.ExcelResources;

import java.io.Serializable;
import java.util.List;

/**
 * Demo class
 *
 * @author Test
 * @date 2018/10/31
 */
public class LearnQuestionDTO implements Serializable {

	private static final long serialVersionUID = -5199457532832203760L;

	private long questionId;

	private int questionClassId;

	/**
	 * 题型ID
	 */
	private int questionModelId;
	/**
	 * 题型名称
	 */
	@ExcelResources(title="题型",order=1,width=200)
	private String questionModelName;
	/**
	 * 题目内容
	 */
    @ExcelResources(title="题干内容",order=2,width=600)
	private String questionContent;
	/**
	 * 参考答案
	 */
    @ExcelResources(title="参考答案",order=3,width=200)
	private String questionAnswer;

    private double questionScore;

    @ExcelResources(title="分数",order=4,width=100)
    private int exportScore;
	/**
	 * 创建时间
	 */
	private String createDate;

	private String answerContent;
	private String recordContent;

	private int totalNum;

	private String optionMark;
	private String optionContent;

	private List<LearnOptionDTO> learnOptionDTOS;


//	导出使用
    @ExcelResources(title="选项A",order=5,width=300)
	private String optionContentA;
    @ExcelResources(title="选项B",order=6,width=300)
	private String optionContentB;
    @ExcelResources(title="选项C",order=7,width=300)
	private String optionContentC;
    @ExcelResources(title="选项D",order=8,width=300)
	private String optionContentD;

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public List<LearnOptionDTO> getLearnOptionDTOS() {
		return learnOptionDTOS;
	}

	public void setLearnOptionDTOS(List<LearnOptionDTO> learnOptionDTOS) {
		this.learnOptionDTOS = learnOptionDTOS;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public int getExportScore() {
        return exportScore;
    }

    public void setExportScore(int exportScore) {
        this.exportScore = exportScore;
    }

    public String getOptionContentA() {
		return optionContentA;
	}

	public void setOptionContentA(String optionContentA) {
		this.optionContentA = optionContentA;
	}

	public String getOptionContentB() {
		return optionContentB;
	}

	public void setOptionContentB(String optionContentB) {
		this.optionContentB = optionContentB;
	}

	public String getOptionContentC() {
		return optionContentC;
	}

	public void setOptionContentC(String optionContentC) {
		this.optionContentC = optionContentC;
	}

	public String getOptionContentD() {
		return optionContentD;
	}

	public void setOptionContentD(String optionContentD) {
		this.optionContentD = optionContentD;
	}

	public String getOptionMark() {
		return optionMark;
	}

	public void setOptionMark(String optionMark) {
		this.optionMark = optionMark;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	private List<LearnOptionDTO> optionList;

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public int getQuestionClassId() {
		return questionClassId;
	}

	public void setQuestionClassId(int questionClassId) {
		this.questionClassId = questionClassId;
	}

	public double getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(double questionScore) {
		this.questionScore = questionScore;
	}

	public int getQuestionModelId() {
		return questionModelId;
	}

	public void setQuestionModelId(int questionModelId) {
		this.questionModelId = questionModelId;
	}

	public String getQuestionModelName() {
		return questionModelName;
	}

	public void setQuestionModelName(String questionModelName) {
		this.questionModelName = questionModelName;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public List<LearnOptionDTO> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<LearnOptionDTO> optionList) {
		this.optionList = optionList;
	}
}
