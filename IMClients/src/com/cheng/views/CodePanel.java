package com.cheng.views;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;

import com.cheng.core.Service;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;
import com.cheng.listener.ViewHandler;
import com.cheng.main.App;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.SwingConstants;

public class CodePanel extends JPanel implements MouseListener, ViewHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField codeTf;
	private JLabel title;
	private JLabel confirmBtn;
	private int width = 400;
	private int height = 360;
	private JLabel loginBtn;
	private JLabel reCodeBtn;
	private JLabel exit;
	private JLabel small;
	private Font font12 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 12);
	private Font font15 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 15);
	private Font font18 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 18);
	


	/**
	 * Create the panel.
	 */
	public CodePanel(int w, int h) {
		width = w;
		height = h;

		setBounds(0, 0, width, height);
		setBackground(Color.WHITE);
		setLayout(null);

		title = new JLabel("\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801");
		title.setHorizontalTextPosition(SwingConstants.CENTER);
		title.setFont(font18);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 40, width, 80);
		add(title);

		codeTf = new JTextField();
		codeTf.setFont(font15);
		codeTf.setBounds(width / 2 - 100, 120, 200, 38);
		add(codeTf);
		codeTf.setColumns(10);

		confirmBtn = new JLabel("\u786E\u8BA4");
		confirmBtn.setOpaque(true);
		confirmBtn.setHorizontalAlignment(SwingConstants.CENTER);
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setFont(font18);
		confirmBtn.setBorder(new LineBorder(new Color(11, 87, 230), 1, true));
		confirmBtn.setBackground(new Color(60, 155, 238));
		confirmBtn.setBounds(width / 2 - 100, 200, 200, 36);
		add(confirmBtn);

		loginBtn = new JLabel("\u53bb\u767b\u9646");
		loginBtn.setForeground(Color.GRAY);
		loginBtn.setFont(font12);
		loginBtn.setBounds(width / 2 - 100, 260, 100, 24);
		add(loginBtn);

		reCodeBtn = new JLabel("\u91cd\u65b0\u53d1\u9001");
		reCodeBtn.setForeground(Color.GRAY);
		reCodeBtn.setFont(font12);
		reCodeBtn.setBounds(width / 2 + 50, 260, 100, 24);
		add(reCodeBtn);
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

		confirmBtn.addMouseListener(this);
		reCodeBtn.addMouseListener(this);
		loginBtn.addMouseListener(this);
		exit.addMouseListener(this);
		small.addMouseListener(this);

	}

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(small)) {
			App.getAplication().entryFrame.setExtendedState(JFrame.ICONIFIED);
		} else if (source.equals(loginBtn)) {
			EntryFrame.cardLayout.show(getParent(), "loginPanel");
		} else if (source.equals(confirmBtn)) {
			if (!confirmBtn.isEnabled()) {
				return;
			}
			 
			String code = codeTf.getText();

			Service service = new Service();
			User user = App.user;
			user.setIs_actived(new Integer(code));
			try {
				service.registerConfirm(user);
				EntryFrame.cardLayout.show(getParent(), "waitPanel");
				title.setText("\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801");
			} catch (IOException e1) {
				title.setText("\u8fde\u63a5\u5931\u8d25");
				e1.printStackTrace();
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
		} else if (source.equals(loginBtn) || source.equals(reCodeBtn)) {
			((Component) source).setForeground(Color.BLACK);
		} else if (source.equals(confirmBtn)) {
			confirmBtn.setBackground(new Color(20, 130, 200));
		}
	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(Color.WHITE);
		} else if (source.equals(small)) {
			small.setBackground(Color.WHITE);
		} else if (source.equals(loginBtn) || source.equals(reCodeBtn)) {
			((Component) source).setForeground(Color.GRAY);
		} else if (source.equals(confirmBtn)) {
			confirmBtn.setBackground(new Color(60, 155, 238));
		}
	}

	public JLabel getSmall() {
		return small;
	}

	public void handleMessage(Message msg) {
		 if (msg.what == MsgType.MSG_TYPE_REGISTER_CODE_SUCCESS) {
			 title.setText(msg.msg);
			} else if (msg.what == MsgType.MSG_TYPE_REGISTER_CODE_FAILE) {
				 title.setText(msg.msg);
			}
		
	}
}
