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
import com.cheng.utils.Utils;

public class UserDao {
	public static final int DELETE_BY_ID = 1;
	public static final int DELETE_BY_USERNAME = 2;
	public static final int DELETE_BY_EMAIL = 3;
	public static final int DELETE_BY_PHONE = 4;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User insert(User user) throws SQLException {
		User exitResult = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		ResultSet resultSet = null;

		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "INSERT INTO `im_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `avatar`, `province`, "
					+ "`town`, `address`, `is_actived`, `gmt_modified`, `gmt_create`) VALUES (NULL, NULL, ?, NULL, "
					+ "?, NULL, 1, NULL, NULL, NULL, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setString(1, user.getPassword());
			stmt.setString(2, user.getEmail());
			stmt.setLong(3, user.getIs_actived());
			stmt.executeUpdate();
			resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
				user.setId(new BigInteger(resultSet.getString("GENERATED_KEY")));
				exitResult = user;
			}

		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;

	}

	/**
	 * 根据条件删除用户
	 * 
	 * @param user
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public int delete(User user, int type) throws SQLException {
		if (user == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = null;
		int result = 0;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			switch (type) {
			case DELETE_BY_ID:
				sql = "DELETE FROM `im_user` WHERE id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, user.getId().longValue());
				break;
			case DELETE_BY_USERNAME:
				sql = "DELETE FROM `im_user` WHERE username=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, user.getUsername().longValue());
				break;
			case DELETE_BY_EMAIL:
				sql = "DELETE FROM `im_user` WHERE email=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, user.getEmail());
				break;
			case DELETE_BY_PHONE:
				sql = "DELETE FROM `im_user` WHERE phone=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, user.getPhone());
				break;
			default:
				sql = "DELETE FROM `im_user` WHERE id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, user.getId().longValue());
				break;
			}

			// 设置参数

			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	/**
	 * 根据列名更新
	 * 
	 * @param user
	 *            更新的数据,必须有id
	 * @param field
	 *            列名，多列以逗号隔开，必须参数
	 * @return 成功返回1，失败返回0
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public int update(User user, String field) throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		if (user == null || user.getId() == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		List<String> datas = new ArrayList<String>();
		try {
			String[] fields = field.split(",");
			if (fields.length <= 0)
				return 0;
			StringBuffer buffer = new StringBuffer("UPDATE `im_user` SET ");

			// 获得BeanInfo，从中获取属性描述器
			BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
			// 获得属性描述器
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < fields.length; i++) {
				for (PropertyDescriptor pd : descriptors) {
					if (fields[i].equals(pd.getName())) {
						buffer.append(fields[i]);
						buffer.append("=?");
						Method read = pd.getReadMethod();
						if ("username".equals(pd.getName())) {
							datas.add(((BigInteger) read.invoke(user)).longValue() + "");
						} else if ("is_actived".equals(pd.getName())) {
							datas.add(((Integer) read.invoke(user)).intValue() + "");
						} else {
							datas.add((String) read.invoke(user));
						}

					}

				}
			}
			buffer.append("WHERE id=?;");
			conn = JDBCDataSource.getInstance().getConnection();

			String sql = buffer.toString();
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			int i = 0;
			for (; i < datas.size(); i++) {
				stmt.setString(i + 1, datas.get(i));
			}

			stmt.setLong(i + 1, user.getId().longValue());
			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	public int update(User user) throws IntrospectionException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SQLException {
		if (user == null || user.getId() == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		try {
			String sql = "UPDATE `im_user` SET `nickname` = ?,  `phone` = ?, `province` = ?, `town` = ?, `address` = ? WHERE `id` = ?; ";
			conn = JDBCDataSource.getInstance().getConnection();

			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译

			stmt.setString(1, user.getNickname());
			stmt.setString(2, user.getPhone());
			stmt.setString(3, user.getProvince());
			stmt.setString(4, user.getTown());
			stmt.setString(5, user.getAddress());
			stmt.setString(6, user.getAvatar());
			stmt.setLong(7, user.getId().longValue());
			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	/**
	 * 修改密码
	 * @param user
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	public int updatePassword(User user) throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {
		if (user == null || user.getId() == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		try {
			String sql = "UPDATE `im_user` SET `password` = ? WHERE `id` = ?; ";
			conn = JDBCDataSource.getInstance().getConnection();
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			stmt.setString(1, user.getPassword());
			stmt.setLong(2, user.getId().longValue());
			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	public int update(String field, String[] values) throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;

		try {
			String[] fields = field.split(",");
			if (fields.length <= 0)
				return 0;
			StringBuffer buffer = new StringBuffer("UPDATE `im_user` SET ");

			for (int i = 0; i < fields.length; i++) {

				buffer.append(fields[i]);

				buffer.append(" = ? ");
				if (i != fields.length - 1) {
					buffer.append(", ");
				}
			}
			buffer.append("WHERE email=? ");

			conn = JDBCDataSource.getInstance().getConnection();
			String sql = buffer.toString();
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			int i = 0;
			for (; i < values.length - 1; i++) {
				if ("username".equals(fields[i])) {
					stmt.setLong(i + 1, new BigInteger(values[i]).longValue());
				} else if (Utils.isInteger(fields[i])) {
					stmt.setInt(i + 1, new Integer(values[i]));
				} else {
					stmt.setString(i + 1, values[i]);
				}

			}
			stmt.setString(i + 1, values[i]);
			result = stmt.executeUpdate();
			System.out.println("================更新=================");
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;

	}

	/**
	 * 
	 * @param user
	 * @param page
	 *            大于0的整数，页码
	 * @return
	 * @throws SQLException
	 */
	public List<User> select(User user, int page) throws SQLException {
		if (user == null)
			return null;
		List<User> exitResult = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT * FROM `im_user`  WHERE username like %?% OR nickname like %?% OR email like %?% OR phone like %?% ORDER BY id DESC LIMIT ?,20";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setString(1, user.getUsername() + "");
			stmt.setString(2, user.getNickname() + "");
			stmt.setString(3, user.getEmail() + "");
			stmt.setString(4, user.getPhone() + "");
			stmt.setInt(5, page);
			result = stmt.executeQuery();
			while (result.next()) {
				MyBeanHandler<User> beanHandler = new MyBeanHandler<User>(User.class);
				exitResult.add(beanHandler.handle(result));
			}

		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	public List<User> select(User user, List<Relationship> relationships) throws SQLException {
		if (user == null || relationships == null)
			return null;
		long id = user.getId().longValue();
		List<User> exitResult = new ArrayList<User>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			for (Relationship r : relationships) {
				if (id == r.getUser_a_id().longValue()) {
					exitResult.add(select(r.getUser_b_id().longValue(), conn));
				} else if (id == r.getUser_b_id().longValue()) {
					exitResult.add(select(r.getUser_a_id().longValue(), conn));
				}
			}
		} finally {
			JDBCUtils.release(null, conn);
		}
		return exitResult;
	}

	private User select(long id, Connection conn) throws SQLException {
		User exitResult = new User();
		PreparedStatement stmt = null;
		ResultSet result = null;
		String sql = "SELECT * FROM `im_user`  WHERE id =? limit 1";
		// 数据库编译时
		stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
		// 设置参数
		stmt.setLong(1, id);
		result = stmt.executeQuery();
		if (result.next()) {
			// MyBeanHandler<User> beanHandler = new MyBeanHandler<User>(User.class);
			// exitResult = beanHandler.handle(result);
			exitResult = set2user(result);
			exitResult.setIs_actived(0);
		} else {
			exitResult = null;
		}
		if (result != null) {
			result.close();
		}
		result = null;
		if (stmt != null) {
			stmt.close();
		}
		stmt = null;
		return exitResult;
	}

	/**
	 * 是否存在相同的Email
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean hasEmail(String email) throws SQLException {
		if (email == null)
			return true;
		boolean exitResult = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT id FROM `im_user`  WHERE  email=? limit 1 ";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setString(1, email);
			result = stmt.executeQuery();
			if (result.next()) {
				exitResult = result.getObject("id") == null ? false : true;
			} else {
				exitResult = false;
			}

		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User select(User user) throws SQLException {
		User exitResult = new User();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT * FROM `im_user` where email=? limit 1 ";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setString(1, user.getEmail());

			result = stmt.executeQuery();
			if (result.next()) {
				// MyBeanHandler<User> beanHandler = new MyBeanHandler<User>(User.class);
				// exitResult = beanHandler.handle(result);
				exitResult = set2user(result);
			} else {
				exitResult = null;
			}
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	public User find(User user) throws SQLException {
		User exitResult = new User();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = null;
			if (user.getId() != null) {
				sql = "SELECT * FROM `im_user` WHERE id=? limit 1 ";
				// 数据库编译时
				stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
				// 设置参数
				stmt.setLong(1, user.getId().longValue());
			} else {
				sql = "SELECT * FROM `im_user` WHERE  email=?   limit 1 ";
				// 数据库编译时
				stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
				// 设置参数
				stmt.setString(1, user.getEmail());
			}

			result = stmt.executeQuery();
			if (result.next()) {
				int is_actived = result.getInt("is_actived");
				if (is_actived != 1) {
					return null;
				}
				// MyBeanHandler<User> beanHandler = new MyBeanHandler<User>(User.class);
				// exitResult = beanHandler.handle(result);
				exitResult = set2user(result);
			} else {
				exitResult = null;
			}
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User login(User user) throws SQLException {
		User exitResult = new User();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT * FROM `im_user` where id=? and password=? limit 1";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setLong(1, user.getId().longValue());
			stmt.setString(2, user.getPassword());
			result = stmt.executeQuery();
			if (result.next()) {
				// MyBeanHandler<User> beanHandler = new MyBeanHandler<User>(User.class);
				// exitResult = beanHandler.handle(result);
				exitResult = set2user(result);
			} else {
				exitResult = null;
			}
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	private User set2user(ResultSet result) throws SQLException {
		User user = new User();
		user.setId(new BigInteger(result.getObject("id").toString()));
		user.setUsername(new BigInteger(result.getObject("id").toString()));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setNickname(result.getString("nickname"));
		user.setIs_actived(result.getInt("is_actived"));
		user.setPhone(result.getString("phone") + "");
		user.setAvatar(result.getString("avatar") + "");
		user.setProvince(result.getString("province"));
		user.setTown(result.getString("town") );
		user.setAddress(result.getString("address"));
		user.setGmt_modified(result.getTimestamp("gmt_modified"));
		user.setGmt_create(result.getTimestamp("gmt_create"));
		return user;

	}

	public static void main(String[] args) throws SQLException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IntrospectionException {
		UserDao dao = new UserDao();
		User user = new User();
		user.setId(new BigInteger("100004"));
		user.setEmail("511641726@qq.com");
		user.setPassword("3cf84af30821e4474b05835c52c2c234");
		// user = dao.login(user);
		String[] values = new String[4];
		values[0] = "123421";
		values[1] = "1";
		values[2] = "123421";
		values[3] = "511641762@qq.com";

		dao.update("username,is_actived,nickname", values);
		// System.out.println(user.getId());

	}
}
