package com.cheng.utils;

import java.io.PrintWriter;  
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

import java.sql.Connection; 

/**
 * �������ݿ����ӳأ����л��� 1���ṩgetConnection����������ȡ���� 2�����ô���close������������Ϊ�����ӷ������ӳ�
 * 
 * ����������JDBCUtils
 * 
 * @author ChengAcer
 * 
 */
public class JDBCDataSource implements DataSource {
	/*
	 * ���ݿ���������
	 */
	private static final int NUM = 15;

	/*
	 * �������ݿ����ӣ�����ѭ������ʹ��
	 */
	private LinkedList<Connection> dataSourceList = new LinkedList<Connection>();

	/*
	 * һ���Դ��� NUM �����ݿ�����,���������
	 */
	private JDBCDataSource() {
		for (int i = 0; i < NUM; i++) {
			
				Connection connection=null;
				try {
					connection = JDBCUtils.getConnection();
					dataSourceList.add(connection);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			
		}
		
	}

	/*
	 * �ڲ��࣬��ʼ������
	 */
	private static class Holder {
		private static final JDBCDataSource DATA_SOURCE = new JDBCDataSource();
	}

	/**
	 * �����ڲ���ʵ�����ĵ���
	 * 
	 * @return ���ݿ����ӳ�
	 */
	public static JDBCDataSource getInstance() {
		return Holder.DATA_SOURCE;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	

	public void setLogWriter(PrintWriter arg0) throws SQLException {

	}

	public void setLoginTimeout(int arg0) throws SQLException {

	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	/**
	 * 1��ȡ����һ�����ӣ����ô���close������д 2�����ظ�д�������
	 * Ϊʲô��д���������ʹ�ø�����ʱ����ʹ��ԭ���Ĺرշ������������ӳص�����Խ��Խ��
	 */
	public Connection getConnection() throws SQLException {
		System.out.println(dataSourceList.size()+"---->��ȡ���ݿ�����");
		// ȡ�����ӳ���һ������
		final Connection conn = dataSourceList.removeFirst(); // ɾ����һ�����ӷ���

		// ��Ŀ��Connection���������ǿ������close��������
		Connection connProxy = (Connection) Proxy.newProxyInstance(conn
				.getClass().getClassLoader(), conn.getClass().getInterfaces(),
				new InvocationHandler() {
					// ִ�д�������κη��� ����ִ�� invoke
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						if (method.getName().equals("close")) {
							// ��Ҫ��ǿ�ķ���
							// �������������رգ������ӷŻ����ӳ�
							releaseConnection(conn);
							return null;
						} else {
							// ����Ҫ��ǿ�ķ���
							return method.invoke(conn, args); // ������ʵ���󷽷�
						}
					}
				});
		// ���ش��������Ӷ���
		return connProxy;
	}

	/**
	 *  �����ӷŻ����ӳ�
	 * @param conn
	 */
	public void releaseConnection(Connection conn) {
		dataSourceList.add(conn);
	}

	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return null;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}
