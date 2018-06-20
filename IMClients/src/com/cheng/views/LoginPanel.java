package com.cheng.views;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.cheng.core.Service;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;
import com.cheng.listener.ViewHandler;
import com.cheng.main.App;
import com.cheng.utils.MD5;
import com.cheng.utils.Properties;

public class LoginPanel extends JPanel implements MouseListener, ViewHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameTf;
	private JLabel newBtn;
	private JLabel forgetBtn;
	private JLabel exit;
	private JLabel small;
	private JLabel loginBtn;
	private JPasswordField passwordTf;
	private JLabel title;
	private JLabel msgLab;
	private Checkbox rememberPs;
	private Checkbox autoLogin;
	private Font font12 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 12);
	private Font font15 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 15);
	private Font font18 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 18);
	private Font font24 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 24);

	Properties properties = Properties.getInstance();
	boolean remPs;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", new LoginPanel(400, 360));
		f.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 450;
		int h = 350;
		f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
		f.setSize(w, h);

		f.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public LoginPanel(int width, int height) {

		setBounds(0, 0, width, height);
		setBorder(null);
		setBackground(Color.WHITE);
		setLayout(null);

		title = new JLabel("\u7528\u6237\u767B\u5F55");
		title.setForeground(new Color(51, 102, 255));
		title.setFont(font24);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(width / 2 - 50, 10, 100, 38);
		add(title);

		usernameTf = new JTextField("  \u8d26\u53f7");
		usernameTf.setFont(font15);
		usernameTf.setForeground(Color.GRAY);
		usernameTf.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));
		usernameTf.setBounds(width / 2 - 100, 38 + 30 + 10, 200, 38);
		usernameTf.addFocusListener(new JTextFieldHintListener("  \u8d26\u53f7", null, null));
		add(usernameTf);
		usernameTf.setColumns(1);

		passwordTf = new JPasswordField("  \u5bc6\u7801");
		passwordTf.setEchoChar((char) 0);

		passwordTf.setFont(font15);
		passwordTf.setForeground(Color.GRAY);
		passwordTf.setBounds(width / 2 - 100, 38 * 2 + 30 * 2 + 10, 200, 38);

		passwordTf.addFocusListener(new JTextFieldHintListener("  \u5bc6\u7801", null, null));
		passwordTf.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));
		add(passwordTf);
		passwordTf.setColumns(1);

		remPs = properties.getBoolean("rememberPs", false);
		if (remPs) {
			usernameTf.setText(properties.getString("username", "  \u8d26\u53f7"));
			passwordTf.setText(properties.getString("password", "  \u5bc6\u7801"));
		}
		msgLab = new JLabel("");
		msgLab.setForeground(Color.red);
		msgLab.setFont(font12);
		msgLab.setBounds(width / 2 - 100, 38 * 2 + 30 * 3 + 20, 200, 28);
		add(msgLab);

		rememberPs = new Checkbox("\u8bb0\u4f4f\u5bc6\u7801");
		rememberPs.setForeground(Color.GRAY);
		rememberPs.setFont(font12);

		rememberPs.setBounds(100, 210, 95, 23);
		add(rememberPs);

		autoLogin = new Checkbox("\u81EA\u52A8\u767B\u5F55");

		autoLogin.setForeground(Color.GRAY);

		autoLogin.setFont(font12);

		autoLogin.setBounds(300 - 65, 210, 95, 23);
		add(autoLogin);

		loginBtn = new JLabel("\u767B\u5F55");
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setBackground(new Color(60, 155, 238));
		loginBtn.setOpaque(true);
		loginBtn.setBorder(new LineBorder(new Color(11, 87, 230), 1, true));
		loginBtn.setHorizontalAlignment(SwingConstants.CENTER);
		loginBtn.setFont(font18);
		loginBtn.setBounds(100, 38 * 3 + 30 * 4, 200, 36);
		add(loginBtn);

		newBtn = new JLabel("\u65B0\u7528\u6237");
		newBtn.setForeground(Color.GRAY);

		newBtn.setFont(font12);
		newBtn.setHorizontalAlignment(SwingConstants.CENTER);
		newBtn.setBounds(50, 39 * 4 + 30 * 4 + 15, 56, 18);
		add(newBtn);

		forgetBtn = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		forgetBtn.setForeground(Color.GRAY);
		forgetBtn.setFont(font12);
		forgetBtn.setHorizontalAlignment(SwingConstants.CENTER);
		forgetBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		forgetBtn.setBounds(width - 56 - 50, 39 * 4 + 30 * 4 + 15, 56, 18);
		add(forgetBtn);

		exit = new JLabel("X");
		exit.setBackground(Color.WHITE);
		exit.setOpaque(true);
		exit.setHorizontalAlignment(SwingConstants.CENTER);
		exit.setFont(font12);
		exit.setBounds(width - 32, 0, 32, 24);
		add(exit);

		small = new JLabel("\u2014");
		small.setBackground(Color.WHITE);
		small.setOpaque(true);
		small.setHorizontalAlignment(SwingConstants.CENTER);
		small.setBounds(width - 32 - 32, 0, 32, 24);
		add(small);

		loginBtn.addMouseListener(this);
		forgetBtn.addMouseListener(this);
		newBtn.addMouseListener(this);
		exit.addMouseListener(this);
		small.addMouseListener(this);

	}

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(small)) {
			App.getAplication().entryFrame.setExtendedState(JFrame.ICONIFIED);
		} else if (source.equals(newBtn)) {
			EntryFrame.cardLayout.show(getParent(), "registerPanel");
		} else if (source.equals(loginBtn)) {
			if (!loginBtn.isEnabled()) {
				return;
			}
			String username = usernameTf.getText();
			@SuppressWarnings("deprecation")
			String password = passwordTf.getText();
			if ("".equals(username) || "  \u8d26\u53f7".equals(username)) {
				msgLab.setText("\u8d26\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
				return;
			} else if ("".equals(password) || "  \u5bc6\u7801".equals(password)) {
				msgLab.setText("\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a");
				return;
			}
			User user = new User();
			user.setId(new BigInteger(username));
			if (properties.getString("password", "").equals(password)) {
				user.setPassword(password);
			} else {
				user.setPassword(MD5.md5(password));
			}

			Service service = new Service();
			App.user = user;
			try {

				properties.putBoolean("autoLogin", autoLogin.getState());
				if (rememberPs.getState()) {
					properties.putString("password", MD5.md5(password));
					properties.putString("username", username);
				}
				if (autoLogin.getState()) {
					properties.putString("password", MD5.md5(password));
					properties.putString("username", username);
				}
				service.login(user);
				msgLab.setText("");
				EntryFrame.cardLayout.show(getParent(), "waitPanel");
			} catch (IOException e1) {
				msgLab.setText("");
				e1.printStackTrace();
			}

		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(new Color(220, 0, 0));
		} else if (source.equals(small)) {
			small.setBackground(new Color(230, 230, 230));
		} else if (source.equals(newBtn) || source.equals(forgetBtn)) {
			((Component) source).setForeground(Color.BLACK);
		} else if (source.equals(loginBtn)) {
			loginBtn.setBackground(new Color(20, 130, 200));
		}
	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(new Color(255, 255, 255));
		} else if (source.equals(small)) {
			small.setBackground(new Color(255, 255, 255));
		} else if (source.equals(newBtn) || source.equals(forgetBtn)) {
			((Component) source).setForeground(Color.GRAY);
		} else if (source.equals(loginBtn)) {
			loginBtn.setBackground(new Color(60, 155, 238));
		}
	}

	public JLabel getSmall() {
		return small;
	}

	public void handleMessage(Message msg) {
		loginBtn.setEnabled(true);
		if (msg.what == MsgType.MSG_TYPE_LOGIN_FAILE) {
			msgLab.setText(msg.msg);
		} else if (msg.what == MsgType.MSG_TYPE_LOGIN_SUCCESS) {
			msgLab.setText("");
		}
	}
}
