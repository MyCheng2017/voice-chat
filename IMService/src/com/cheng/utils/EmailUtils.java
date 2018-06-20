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
				"����", "<h1>���</h1>");
		//sendEmail( "511641726@qq.com");
	}

	/**
	 * �����ʼ���������֤��
	 * 
	 * @param to
	 *            �ռ���
	 * @return ��֤��
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static int sendEmail(final String to) throws AddressException,
			MessagingException {
		final String subject = "��ʱ��";
		int code = (int) ((Math.random() * 9 + 1) * 100000);
		final StringBuffer buffer = new StringBuffer("<h1>��ʱ��ͨѶ�����ʼ�</h1>");
		buffer.append("<div>���ļ�����֤����&nbsp;<span color=blue >" + code
				+ "</span>&nbsp;��</div>");
		buffer.append("<div>��֤�뽫��ʮ���Ӻ�ʧЧ��</div>");
		EmailUtils.sendEmail(to, subject, buffer.toString());
		return code;
	}

	/**
	 * ϵͳ�����ʼ�
	 * 
	 * @param to
	 *            �ռ���
	 * @param subject
	 *            ����
	 * @param text
	 *            ����
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
	 * �����ʼ�
	 * 
	 * @param from
	 *            ������
	 * @param to
	 *            �ռ���
	 * @param password
	 *            ����������
	 * @param subject
	 *            �ʼ�����
	 * @param html
	 *            �ʼ�����
	 * @throws AddressException
	 *             ��ַ�쳣
	 * @throws MessagingException
	 */
	public static void sendEmail(String from, String to, String password,
			String subject, String html) throws AddressException,
			MessagingException {

		// ����һ �������ʼ����������ӻỰ
		Properties properties = new Properties();// ��������������Ӳ���
		// ����properties ����
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

		properties.put("mail.smtp.auth", "true");// ������֤
		properties.put("mail.debug", "true");// �ڿ���̨��ʾ������־��Ϣ
		Session session = Session.getInstance(properties);// ���ʼ����������ӻỰ

		// ����� ��дMessage
		MimeMessage message = new MimeMessage(session);// ����һ���ʼ�
		// from�ֶ�
		message.setFrom(new InternetAddress(from));
		// to �ֶ�
		message.setRecipients(Message.RecipientType.TO, to);
		// subject�ֶ�
		message.setSubject(subject);
		// �ʼ���������
		// message.setText(text,".GBK");
		message.setContent(html, "text/html;charset=gbk");
		// ������ ʹ��Transport�����ʼ�
		Transport transport = session.getTransport();
		// ���ʼ�ǰ�������У��
		transport.connect(from, password);
		transport.sendMessage(message, message.getAllRecipients());
	}

}
