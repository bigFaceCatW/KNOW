package com.know.util;

import java.lang.annotation.*;

/**
 * 用来在对象的field上加入的注解,通过该annotation说明某个属性所对应的标题
 * 
 * @author SongLee
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelResources {

	// 属性的标题名称
	String title();

	// 在excel的顺序
	int order() default 9999;

	// 每列的宽度
	int width() default 150;
}
