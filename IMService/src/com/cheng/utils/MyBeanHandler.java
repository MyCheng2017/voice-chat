package com.cheng.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * ͨ��handler �������ݿ��ѯ���Ľ����ת����javabean
 * <ul>
 * ���裺
 * <li>ͨ��javabean�����class���javabeanʵ����</li>
 * <li>ͨ��ʵ����ȡBeanInfo��</li>
 * <li>��Beaninfo�л�ȡ������������</li>
 * <li>���β��������������е���������������������Ƿ�ƥ�䣬ƥ�������javabean�У�</li>
 * <li>��󷵻�javabean��</li>
 * </ul>
 * 
 * @author ChengAcer
 * 
 * @param <T>javabean����
 */
public class MyBeanHandler<T> implements MyResultHandler<T> {

	private Class<T> domainClass;

	/**
	 * ���췽��������javabean��class
	 * 
	 * @param domainClass
	 *            javabean��class
	 */
	public MyBeanHandler(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	public T handle(ResultSet rs) {
		try {
			// ��ý����Ԫ��
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			// ���������
			int count = resultSetMetaData.getColumnCount();
			// ���BeanInfo�����л�ȡ����������
			BeanInfo beanInfo = Introspector.getBeanInfo(domainClass);
			// �������������
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			if (rs != null) {
				// ���javabeanʵ��
				T t = domainClass.newInstance();

				for (int i = 1; i <= count; i++) {
					// ��ý��������
					String columnName = resultSetMetaData.getColumnName(i);

					// ����ƥ������
					for (PropertyDescriptor propertyDescriptor : descriptors) {
						if (columnName.equals(propertyDescriptor.getName())) {
							// ���� ���� ͬ������
							Method writeMethod = propertyDescriptor.getWriteMethod();
							// �鿴�����Ƿ�ƥ��
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
							// ��ֵ �浽������

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
