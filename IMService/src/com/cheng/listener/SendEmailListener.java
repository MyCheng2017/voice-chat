package com.cheng.listener;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.cheng.core.IMConnection;
import com.cheng.dao.UserDao;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;
import com.cheng.utils.EmailUtils;

public class SendEmailListener extends MessageSender<User> implements OnRecevieListener {
	private IMConnection conn = null;

	public SendEmailListener(IMConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(byte[] data) {
		if (checkByte(data)) {

			User user = getByByte(data, User.class);
			System.err.println("发邮件请求" + user.getEmail());
			UserDao userDao = new UserDao();
			try {
				// 发送邮件
				String[] values = new String[2];
				values[0] = EmailUtils.sendEmail(user.getEmail()) + "";
				values[1] = user.getId().longValue() + "";
				userDao.update("is_actived", values);
				conn.writer.write(toProtocal(MsgType.MSG_TYPE_SUCCESS, "已经将验证码发送到您的邮箱，请查收！"));

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
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
