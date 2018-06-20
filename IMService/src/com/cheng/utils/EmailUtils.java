package com.cheng.utils;

import java.util.Properties; 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailUtils {

	
	public void text() throws AddressException, NoSuchProviderException,
			MessagingException {
		sendEmail("chengzi2018@sohu.com", "511641726@qq.com", "l.17181637993",
				"测试", "<h1>你好</h1>");
		//sendEmail( "511641726@qq.com");
	}

	/**
	 * 发送邮件，返回验证码
	 * 
	 * @param to
	 *            收件人
	 * @return 验证码
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static int sendEmail(final String to) throws AddressException,
			MessagingException {
		final String subject = "及时雨";
		int code = (int) ((Math.random() * 9 + 1) * 100000);
		final StringBuffer buffer = new StringBuffer("<h1>及时雨通讯激活邮件</h1>");
		buffer.append("<div>您的激活验证码是&nbsp;<span color=blue >" + code
				+ "</span>&nbsp;。</div>");
		buffer.append("<div>验证码将在十分钟后失效！</div>");
		EmailUtils.sendEmail(to, subject, buffer.toString());
		return code;
	}

	/**
	 * 系统发送邮件
	 * 
	 * @param to
	 *            收件人
	 * @param subject
	 *            主题
	 * @param text
	 *            内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendEmail(String to, String subject, String text)
			throws AddressException, MessagingException {
		String from = "gc05776701@163.com";
		from = "chengzi2018@sohu.com";
		String password = "l.17181637993";
		sendEmail(from, to, password, subject, text);
	}

	/**
	 * 发送邮件
	 * 
	 * @param from
	 *            发件人
	 * @param to
	 *            收件人
	 * @param password
	 *            发件人密码
	 * @param subject
	 *            邮件主题
	 * @param html
	 *            邮件内容
	 * @throws AddressException
	 *             地址异常
	 * @throws MessagingException
	 */
	public static void sendEmail(String from, String to, String password,
			String subject, String html) throws AddressException,
			MessagingException {

		// 步骤一 创建与邮件服务器连接会话
		Properties properties = new Properties();// 配置与服务器连接参数
		// 设置properties 属性
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.transport.protocol", "smtp");

		if (from.endsWith("@163.com")) {
			properties.put("mail.smtp.host", "smtp.sohu.com");
		} else if (from.endsWith("@sohu.com")) {
			properties.put("mail.smtp.host", "smtp.sohu.com");
		} else if (from.endsWith("@qq.com")) {
			properties.put("mail.smtp.host", "smtp.qq.com");
		} else if (from.endsWith("@sina.com")) {
			properties.put("mail.smtp.host", "smtp.sina.com");
		} else if (from.endsWith("@exmail.qq.com")) {
			properties.put("mail.smtp.host", "smtp.exmail.qq.com");
		} else if (from.endsWith("@yahoo.cn")) {
			properties.put("mail.smtp.host", "smtp.mail.yahoo.cn");
		} else if (from.endsWith("@126.com")) {
			properties.put("mail.smtp.host", "smtp.126.com");
		} else if (from.endsWith("@gmail.com")) {
			properties.put("mail.smtp.host", "smtp.gmail.com");
		}

		properties.put("mail.smtp.auth", "true");// 连接认证
		properties.put("mail.debug", "true");// 在控制台显示连接日志信息
		Session session = Session.getInstance(properties);// 与邮件服务器连接会话

		// 步骤二 编写Message
		MimeMessage message = new MimeMessage(session);// 代表一封邮件
		// from字段
		message.setFrom(new InternetAddress(from));
		// to 字段
		message.setRecipients(Message.RecipientType.TO, to);
		// subject字段
		message.setSubject(subject);
		// 邮件正文内容
		// message.setText(text,".GBK");
		message.setContent(html, "text/html;charset=gbk");
		// 步骤三 使用Transport发送邮件
		Transport transport = session.getTransport();
		// 发邮件前进行身份校验
		transport.connect(from, password);
		transport.sendMessage(message, message.getAllRecipients());
	}

}
