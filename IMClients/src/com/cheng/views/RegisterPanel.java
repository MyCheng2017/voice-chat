package com.cheng.views;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.LineBorder;

import com.cheng.core.Service;
import com.cheng.domain.Message;
import com.cheng.domain.User;
import com.cheng.listener.ViewHandler;
import com.cheng.main.App;
import com.cheng.utils.MD5;

import javax.swing.SwingConstants;

public class RegisterPanel extends JPanel implements MouseListener, ViewHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailTf;
	private JLabel title;
	private JLabel registerBtn;
	private JLabel loginBtn;
	private JPasswordField passwordTf;
	private JPasswordField confirmTf;
	private int width = 380;
	private int height = 420;
	private JLabel exit;
	private JLabel small;
	private JLabel msgLab;
	private Font font12 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 12);
	private Font font15 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 15);
	private Font font18 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 18);
	private Font font24 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 24);

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", new RegisterPanel(400, 500));
		f.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 780;
		int h = 500;
		f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
		f.setSize(w, h);
		f.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public RegisterPanel(int w, int h) {
		width = w;
		height = h;

		setBounds(0, 0, width, height);
		setBackground(Color.WHITE);
		setLayout(null);

		title = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(51, 102, 255));
		title.setFont(font24);
		title.setBounds(0, 10, width, 80);
		add(title);

		msgLab = new JLabel("");
		msgLab.setForeground(Color.red);
		msgLab.setFont(font12);
		msgLab.setBounds(width / 2 - 100, 224, 200, 28);
		add(msgLab);

		registerBtn = new JLabel("\u6CE8\u518C");
		registerBtn.setOpaque(true);
		registerBtn.setHorizontalAlignment(SwingConstants.CENTER);
		registerBtn.setForeground(Color.WHITE);
		registerBtn.setFont(font18);
		registerBtn.setBorder(new LineBorder(new Color(11, 87, 230), 1, true));
		registerBtn.setBackground(new Color(60, 155, 238));
		registerBtn.setBounds(width / 2 - 100, 250, 200, 36);
		add(registerBtn);

		loginBtn = new JLabel("\u5df2\u6709\u8d26\u53f7\uff0c\u53bb\u767b\u5f55");
		loginBtn.setForeground(Color.GRAY);
		loginBtn.setFont(font12);
		loginBtn.setBounds(width / 2 - 100, 293, 200, 24);
		add(loginBtn);

		emailTf = new JTextField();
		emailTf.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));
		emailTf.setForeground(Color.GRAY);
		emailTf.setFont(font15);
		emailTf.setColumns(1);
		emailTf.setBounds(width / 2 - 100, 77, 200, 38);
		emailTf.setText("  \u90ae\u7bb1/Email");
		emailTf.addFocusListener(new JTextFieldHintListener("  \u90ae\u7bb1/Email", null, null));
		add(emailTf);

		passwordTf = new JPasswordField();
		passwordTf.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));
		passwordTf.setEchoChar((char) 0);
		passwordTf.setText("  \u5BC6\u7801/Password");
		passwordTf.addFocusListener(new JTextFieldHintListener("  \u5BC6\u7801/Password", null, null));
		passwordTf.setForeground(Color.GRAY);
		passwordTf.setFont(font15);
		passwordTf.setColumns(1);
		passwordTf.setBounds(width / 2 - 100, 131, 200, 38);
		add(passwordTf);

		confirmTf = new JPasswordField();
		confirmTf.setBorder(new LineBorder(new Color(240, 240, 240), 1, true));
		confirmTf.setText("  \u786E\u8BA4\u5BC6\u7801/Confirm");
		confirmTf.addFocusListener(new JTextFieldHintListener("  \u786E\u8BA4\u5BC6\u7801/Confirm", null, null));
		confirmTf.setForeground(Color.GRAY);
		confirmTf.setFont(font15);
		confirmTf.setColumns(1);
		confirmTf.setEchoChar((char) 0);
		confirmTf.setBounds(width / 2 - 100, 186, 200, 38);
		add(confirmTf);

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

		exit.addMouseListener(this);
		small.addMouseListener(this);
		loginBtn.addMouseListener(this);
		registerBtn.addMouseListener(this);

	}

	@SuppressWarnings("deprecation")
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(small)) {
			App.getAplication().entryFrame.setExtendedState(JFrame.ICONIFIED);
		} else if (source.equals(loginBtn)) {
			EntryFrame.cardLayout.show(getParent(), "loginPanel");
		} else if (source.equals(registerBtn)) {

			if (!registerBtn.isEnabled()) {
				return;
			}
			String email = emailTf.getText();
			String password = passwordTf.getText();
			String confirm = confirmTf.getText();
			if (password == null || "".equals(password)) {
				msgLab.setText("\u8bf7\u8f93\u5165\u5bc6\u7801");
			} else if (!password.equals(confirm)) {
				msgLab.setText("\u4e24\u6b21\u5bc6\u7801\u4e0d\u4e00\u81f4");
			} else {
				User user = new User();
				user.setEmail(email);
				user.setPassword(MD5.md5(password));
				App.user = user;
				Service service = new Service();
				try {
					service.register(user);
					EntryFrame.cardLayout.show(getParent(), "waitPanel");
					msgLab.setText("");
				} catch (IOException e1) {
					msgLab.setText("\u8fde\u63a5\u5931\u8d25");
					e1.printStackTrace();
				}
			}
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(new Color(220, 0, 0));
		} else if (source.equals(small)) {
			small.setBackground(new Color(230, 230, 230));
		} else if (source.equals(loginBtn)) {
			((Component) source).setForeground(Color.BLACK);
		} else if (source.equals(registerBtn)) {
			registerBtn.setBackground(new Color(20, 130, 200));
		}
	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(Color.WHITE);
		} else if (source.equals(small)) {
			small.setBackground(Color.WHITE);
		} else if (source.equals(loginBtn)) {
			((Component) source).setForeground(Color.GRAY);
		} else if (source.equals(registerBtn)) {
			registerBtn.setBackground(new Color(60, 155, 238));
		}

	}

	public JLabel getSmall() {
		return small;
	}

	public void handleMessage(Message msg) {
		
		msgLab.setText(msg.msg);
	}
}
