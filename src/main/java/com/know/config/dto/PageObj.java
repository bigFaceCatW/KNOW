package com.know.config.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;



/**
 * fe
 * @Author: Facecat
 * @Date: 2020/2/15 15:01
 */
@Component
public class PageObj implements Serializable {

        private static final long serialVersionUID = 1L;

        /**每一页的记录数*/
        private int rows;

        /**当前页*/
        private int page;

        /**总记录数*/
        private int total;

        private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
         * 获取每一页的记录数
         * @return 每一页的记录数
         */
        public int getRows() {
        return rows;
    }

        /**
         * 设置每一页的记录数
         * @param rows 每一页的记录数
         */
        public void setRows(int rows) {
        this.rows = rows;
    }

        /**
         * 获取当前页数
         * @return 当前页数
         */
        public int getPage() {
        return page;
    }

        /**
         * 设置当前页数
         * @param page 当前页数
         */
        public void setPage(int page) {
        this.page = page;
    }

        /**
         * 获取记录总数
         * @return 记录总数
         */
        public int getTotal() {
        return total;
    }

        /**
         * 设置记录总数
         * @param total 记录总数
         */
        public void setTotal(int total) {
        this.total = total;
    }

        @Override
        public String toString() {
        return "PageObj [rows=" + rows + ", page=" + page + ", total=" + total + "]";
    }
    }



