package com.cheng.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * JDBC������
 * ���ڻ�ȡ���ݿ����Ӻ͹ر���Ӧ��Դ
 * @author ChengAcer
 * 
 */
public class JDBCUtils {
	// ���ݿ�����
	private static final String DRIVERCLASS;
	// ���ݿ�����url
	private static final String URL;
	// �û���
	private static final String USER;
	// ����
	private static final String PWD;
	// �������ļ���ʼ������
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");
		DRIVERCLASS = bundle.getString("DRIVERCLASS");
		URL = bundle.getString("URL");
		USER = bundle.getString("USER");
		PWD = bundle.getString("PWD");
	}

	/**
	 * ��������������
	 * 
	 * @return ���ݿ�����
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		// ��������
		loadDriver();
		// �������ݿ�����
		return DriverManager.getConnection(URL, USER, PWD);
	}

	// װ������
	private static void loadDriver() throws ClassNotFoundException {
		// ���÷��䣬������μ�������
		Class.forName(DRIVERCLASS);
	}

	/**
	 * 
	 * �ͷ���Դ
	 * 
	 * @param rs
	 *            ��Ҫ�رյ�ResultSet
	 * @param stmt
	 *            ��Ҫ�رյ�Statement
	 * @param conn
	 *            ��Ҫ�رյ�Connection
	 */
	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}

		release(stmt, conn);
	}

	/**
	 * �ͷ���Դ
	 * 
	 * @param stmt
	 *            ��Ҫ�رյ�Statement
	 * @param conn
	 *            ��Ҫ�رյ�Connection
	 */
	public static void release(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
