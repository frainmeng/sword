package com.sword.util.poi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PoiCell {
	/**
	 * 列索引
	 * @return
	 */
	public int cellIndex();
	/**
	 * 数据类型
	 * @return
	 */
	public DataTypeEnum dataType() default DataTypeEnum.STRING;
	
	
}
