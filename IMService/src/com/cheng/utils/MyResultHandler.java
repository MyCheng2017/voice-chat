package com.cheng.utils;

import java.sql.ResultSet;

/**
 * 
 * @author ChengAcer
 *
 * @param <T>
 */
public interface MyResultHandler<T> {
	/**
	 * 将结果集变成javabean对象
	 * @param rs 结果集
	 * @return 
	 */
	public T handle(ResultSet rs);
	
}
