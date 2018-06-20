package com.cheng.listener;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import com.cheng.core.IMConnection;
import com.cheng.dao.UserDao;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;

public class RegisterConfirmListener extends MessageSender<User> implements OnRecevieListener {
	private IMConnection conn = null;

	public RegisterConfirmListener(IMConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(byte[] data) {
		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_REGISTER_CODE) {

				User user = getByByte(data, User.class);
				System.err.println("注册验证请求:" + user.getEmail());
				UserDao userDao = new UserDao();
				try {
					User dbUser = userDao.select(user);
					System.out.println(dbUser.getIs_actived());
					System.out.println(user.getIs_actived());
					if (dbUser.getIs_actived() == user.getIs_actived()) {
						conn.writer.write(toProtocal(MsgType.MSG_TYPE_REGISTER_CODE_SUCCESS, "注册成功！您的用户名是："+dbUser.getId()));
						 
						String[] values = new String[4];
						values[0] = dbUser.getId().toString();
						values[1] = "1";
						values[2] = dbUser.getId().toString();
						values[3] = dbUser.getEmail();
						 
						userDao.update("username,is_actived,nickname", values);
					} else {
						conn.writer.write(toProtocal(MsgType.MSG_TYPE_REGISTER_CODE_FAILE, "验证码不正确！"));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IntrospectionException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
