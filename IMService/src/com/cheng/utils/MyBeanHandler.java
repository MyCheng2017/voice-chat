package com.cheng.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 通用handler ，将数据库查询到的结果集转化成javabean
 * <ul>
 * 步骤：
 * <li>通过javabean对象的class获得javabean实例，</li>
 * <li>通过实例获取BeanInfo，</li>
 * <li>从Beaninfo中获取属性描述器，</li>
 * <li>依次查找属性描述器中的属性名跟结果集中列名是否匹配，匹配则存入javabean中，</li>
 * <li>最后返回javabean。</li>
 * </ul>
 * 
 * @author ChengAcer
 * 
 * @param <T>javabean泛型
 */
public class MyBeanHandler<T> implements MyResultHandler<T> {

	private Class<T> domainClass;

	/**
	 * 构造方法，传入javabean的class
	 * 
	 * @param domainClass
	 *            javabean的class
	 */
	public MyBeanHandler(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	public T handle(ResultSet rs) {
		try {
			// 获得结果集元素
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			// 获得总数量
			int count = resultSetMetaData.getColumnCount();
			// 获得BeanInfo，从中获取属性描述器
			BeanInfo beanInfo = Introspector.getBeanInfo(domainClass);
			// 获得属性描述器
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			if (rs != null) {
				// 获得javabean实例
				T t = domainClass.newInstance();

				for (int i = 1; i <= count; i++) {
					// 获得结果集列名
					String columnName = resultSetMetaData.getColumnName(i);

					// 查找匹配属性
					for (PropertyDescriptor propertyDescriptor : descriptors) {
						if (columnName.equals(propertyDescriptor.getName())) {
							// 列名 存在 同名属性
							Method writeMethod = propertyDescriptor.getWriteMethod();
							// 查看类型是否匹配
							Object object = rs.getObject(columnName);
							/*
							 * Class<?>[] type = writeMethod.getParameterTypes();
							 * System.out.println(object.getClass() + "---" + columnName);
							 * System.out.println(type[0] + "--->" + writeMethod.getName());
							 * System.out.println("-----------");
							 */

							if (object != null) {
								writeMethod.invoke(t, object);
							}
							// 列值 存到属性里

						}
					}
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
