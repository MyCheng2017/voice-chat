package com.cheng.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cheng.domain.IMMessage;
import com.cheng.domain.User;
import com.cheng.utils.JDBCDataSource;
import com.cheng.utils.JDBCUtils;

public class MessageDao {

	public int insert(IMMessage message) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int resultSet = 0;
		if (message == null) {
			return resultSet;
		}

		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "INSERT INTO `im_message` (`id`, `fromUser`, `fromNick`, `fromAvatar`, `toUser`, `content`, `createTime`)"
					+ " VALUES (NULL, ?, ?, ?, ?, ?, ?);";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setLong(1, message.fromUser);
			stmt.setString(2, message.fromNick);
			stmt.setString(3, message.fromAvatar);
			stmt.setLong(4, message.toUser);
			stmt.setString(5, message.content);
			stmt.setLong(6, message.createTime);
			resultSet = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return resultSet;
	}

	public int delete(User user) throws SQLException {
		if (user == null || user.getId() == null)
			return 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM `im_message` WHERE toUser=?";
		int result = 0;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, user.getId().longValue());
			result = stmt.executeUpdate();
		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return result;
	}

	public List<IMMessage> select(User user) throws SQLException {
		if (user == null || user.getId() == null)
			return null;
		List<IMMessage> exitResult = new ArrayList<IMMessage>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = JDBCDataSource.getInstance().getConnection();
			String sql = "SELECT * FROM `im_message`  WHERE toUser=?";
			// 数据库编译时
			stmt = conn.prepareStatement(sql); // 将sql 发送给数据库进行编译
			// 设置参数
			stmt.setLong(1, user.getId().longValue());
			result = stmt.executeQuery();
			while (result.next()) {
				IMMessage message = new IMMessage();
				message.fromUser = result.getLong("fromUser");
				message.content = result.getString("content");
				message.createTime = result.getLong("createTime");
				message.fromAvatar = result.getString("fromAvatar");
				message.fromNick = result.getString("fromNick");
				message.toUser = result.getLong("toUser");
				message.type = result.getString("type");
				exitResult.add(message);
			}

		} finally {
			JDBCUtils.release(stmt, conn);
		}
		return exitResult;
	}

	public static void main(String[] args) throws SQLException {
		User user = new User();
		user.setId(new BigInteger("10002"));
		MessageDao dao = new MessageDao();
		//dao.select(user);
		System.out.println(dao.select(user).get(0).toUser);
	}
}
