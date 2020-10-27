package com.know.util;
/**
 * 用来存储Excel标题的对象
 * 
 * @author SongLee
 *
 */
public class ExcelHeader implements Comparable<ExcelHeader> {
	
	public ExcelHeader(){
		super();
	}

	public ExcelHeader(String title, int order, int width, String fieldName) {
		super();
		this.title = title;
		this.order = order;
		this.width = width;
		this.fieldName = fieldName;
	}
	/**
	 *  excel的标题名称
	 */
	private String title;
	/**
	 *  第一个标题的顺序
	 */
	private int order;
	/**
	 *  单元格的宽度
	 */
	private int width;
	/**
	 * 对应字段名称
	 */
	private String fieldName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public int compareTo(ExcelHeader o) {
		return order > o.order ? 1 : (order < o.order ? -1 : 0);
	}

	@Override
	public String toString() {
		return "ExcelHeader [title=" + title + ", order=" + order + ", width="
				+ width + ", fieldName=" + fieldName + "]";
	}

}
