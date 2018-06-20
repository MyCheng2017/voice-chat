package com.cheng.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cheng.domain.Relationship;
import com.cheng.domain.User;
import com.cheng.utils.JDBCDataSource;
import com.cheng.utils.JDBCUtils;
import com.cheng.utils.MyBeanHandler;

public class RelationshipDao {
	/**
	 * ɾ��ĳ������
	 */
	public static final int DELETE_BY_ID = 1;
	/**
	 * ɾ���Լ������к���
	 */
	public static final int DELETE_BY_USER_A_ID = 2;

	public static final int DELETE_BY_USER_B_ID = 3;

	/**
	 * ������ѹ�ϵ
	 * 
	 * @param relationship
	 * @return
	 * @throws SQLException
	 */
	public Relationship insert(Relationship relationship) throws SQLException {
		Relationship exitResult = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		exitResult = select(relationship);
	
		if (exitResult != null&&exitResult.getId()!=null) {
				System.err.println(exitResult.getId());
			return null;
		}else {
			exitResult=relationship;
		}
		
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "INSERT INTO `im_relationship` (`id`, `user_a_id`, `user_b_id`, `request`, `request_time`,`result`, "
					+ "`result_time`, `relationship`, `permission_b`, `permission_a`, `gmt_modified`, `gmt_create`) VALUES "
					+ "(NULL, ?, ?, ?, CURRENT_TIMESTAMP, NULL, NULL, 1, '33554431', '33554431', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
			// ���ݿ����ʱ
			stmt = conn.prepareStatement(sql); // ��sql ���͸����ݿ���б���
			// ���ò���
			stmt.setLong(1, relationship.getUser_a_id().longValue());
			stmt.setLong(2, relationship.getUser_b_id().longValue());
			stmt.setLong(3, relationship.getRequest());
			stmt.executeUpdate();
			resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
				
				exitResult.setId(new BigInteger(resultSet.getString("GENERATED_KEY")));
			}
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;

	}

	/**
	 * ����ɾ������ɾ������
	 * 
	 * @param relationship
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public int delete(Relationship relationship, int type) throws SQLException {
		if (relationship == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = null;
		int result = 0;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			switch (type) {
			case DELETE_BY_ID:
				sql = "DELETE FROM `im_relationship` WHERE id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, relationship.getId().longValue());
				break;
			case DELETE_BY_USER_A_ID:
				sql = "DELETE FROM `im_relationship` WHERE user_a_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, relationship.getUser_a_id().longValue());
				break;
			case DELETE_BY_USER_B_ID:
				sql = "DELETE FROM `im_relationship` WHERE user_b_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, relationship.getUser_b_id().longValue());
				break;

			default:
				sql = "DELETE FROM `im_relationship` WHERE id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, relationship.getId().longValue());
				break;
			}
			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	public int update(Relationship relationship, String field) throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		if (relationship == null || relationship.getId() == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		List<Integer> datas = new ArrayList<Integer>();
		try {
			String[] fields = field.split(",");
			if (fields.length <= 0)
				return 0;
			StringBuffer buffer = new StringBuffer("UPDATE `im_relationship` SET result_time=CURRENT_TIMESTAMP ");

			// ���BeanInfo�����л�ȡ����������
			BeanInfo beanInfo = Introspector.getBeanInfo(Relationship.class);
			// �������������
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < fields.length; i++) {
				for (PropertyDescriptor pd : descriptors) {
					if (fields[i].equals(pd.getName())) {
						buffer.append(fields[i]);
						buffer.append("=?");
						Method read = pd.getReadMethod();
						datas.add((Integer) read.invoke(relationship, ""));
					}

				}
			}
			buffer.append("WHERE id=?;");
			conn = JDBCDataSource.getInstance().getConnection();

			String sql = buffer.toString();
			// ���ݿ����ʱ
			stmt = conn.prepareStatement(sql); // ��sql ���͸����ݿ���б���
			// ���ò���
			int i = 0;
			for (; i < datas.size(); i++) {
				stmt.setLong(i + 1, datas.get(i));
			}

			stmt.setLong(i + 1, relationship.getId().longValue());
			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	/**
	 * ��ѯuser�ĺ����б�
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public List<Relationship> select(User user) throws SQLException {
		if (user == null || user.getId() == null)
			return null;
		List<Relationship> exitResult = new ArrayList<Relationship>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT * FROM `im_relationship`  WHERE user_a_id=? OR user_b_id=? ";
			// ���ݿ����ʱ
			stmt = conn.prepareStatement(sql); // ��sql ���͸����ݿ���б���
			// ���ò���
			stmt.setLong(1, user.getId().longValue());
			stmt.setLong(2, user.getId().longValue());
			result = stmt.executeQuery();
			while (result.next()) {
				MyBeanHandler<Relationship> beanHandler = new MyBeanHandler<Relationship>(Relationship.class);
				exitResult.add(beanHandler.handle(result));
			}

		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	public Relationship select(Relationship ship) throws SQLException {
		if (ship == null)
			return null;
		Relationship exitResult = new Relationship();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT * FROM `im_relationship`  WHERE (user_a_id=? AND user_b_id=?) OR (user_a_id=? AND user_b_id=?) limit 1";
			// ���ݿ����ʱ
			stmt = conn.prepareStatement(sql); // ��sql ���͸����ݿ���б���
			// ���ò���
			stmt.setLong(1, ship.getUser_a_id().longValue());
			stmt.setLong(2, ship.getUser_b_id().longValue());
			stmt.setLong(3, ship.getUser_b_id().longValue());
			stmt.setLong(4, ship.getUser_a_id().longValue());
			result = stmt.executeQuery();
			if (result.next()) {
				MyBeanHandler<Relationship> beanHandler = new MyBeanHandler<Relationship>(Relationship.class);
				exitResult = beanHandler.handle(result);
			}

		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	public static void main(String[] args) throws SQLException {
		RelationshipDao dao = new RelationshipDao();
		Relationship relationship = new Relationship();
		relationship.setUser_a_id(new BigInteger("1001"));
		relationship.setUser_b_id(new BigInteger("1002"));
		relationship.setRequest(1);
		
		relationship = dao.insert(relationship);
		
	}
}
