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
	 * ����������javabean����
	 * @param rs �����
	 * @return 
	 */
	public T handle(ResultSet rs);
	
}
