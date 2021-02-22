package com.know.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface optLog {
	String optModul() default ""; // 操作模块
	optLogConst optType() default optLogConst.DEFAULT;  // 操作类型
	String optDesc() default "";  // 操作说明
}
