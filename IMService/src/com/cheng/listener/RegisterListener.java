package com.cheng.listener;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.cheng.core.IMConnection;
import com.cheng.dao.UserDao;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;
import com.cheng.utils.EmailUtils;
import com.cheng.utils.MD5;

public class RegisterListener extends MessageSender<User> implements OnRecevieListener {
	private IMConnection conn = null;

	public RegisterListener(IMConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(byte[] data) {

		if (checkByte(data)) {
			if (data[3] == MsgType.MSG_TYPE_REGISTER) {
				User user = getByByte(data, User.class);
				System.err.println("◊¢≤·«Î«Û" + user.getEmail());
				UserDao userDao = new UserDao();
				try {
					// ≤Èø¥” œ‰ «∑Ò◊¢≤·
					if (userDao.hasEmail(user.getEmail())) {
						conn.writer.write(toProtocal(MsgType.MSG_TYPE_REGISTER_FAILE, "∏√” œ‰“—◊¢≤·£°"));
					} else {
						System.err.println("◊¢≤·«Î«Û" + user.getEmail());
						// ∑¢ÀÕ” º˛
						user.setIs_actived(EmailUtils.sendEmail(user.getEmail()));
						user.setPassword(MD5.md5(user.getPassword()));
						user = userDao.insert(user);

						if (user == null) {
							conn.writer.write(toProtocal(MsgType.MSG_TYPE_REGISTER_FAILE, "◊¢≤· ß∞‹£°"));
						} else {
							System.out.println(user.getId());
							System.out.println(user.getEmail());
							user.setIs_actived(0);
							conn.writer.write(toProtocal(MsgType.MSG_TYPE_REGISTER_SUCCESS, "◊¢≤·≥…π¶£°"));
						}

					}

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
