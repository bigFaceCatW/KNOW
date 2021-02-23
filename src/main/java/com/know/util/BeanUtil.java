package com.know.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;


@Slf4j
public class BeanUtil {

    /**
     * 将mybatis的IPage转化为千行的PageVO
     * @param iPage
     * @param <T>
     * @return
     */
    public static <T> Page<T> copyPage(IPage<T> iPage) {
        try{
            if(iPage==null){
                return new Page<T>();
            }
            Page<T> page = new Page<>();
            page.setCurrent(iPage.getCurrent());
            page.setSize(iPage.getSize());
            page.setTotal(iPage.getTotal());
            page.setMaxLimit(iPage.getPages());
            page.setRecords(iPage.getRecords());
            return page;
        } catch(Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }


}
