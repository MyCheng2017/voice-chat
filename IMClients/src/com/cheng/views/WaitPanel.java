package com.cheng.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.cheng.domain.Message;
import com.cheng.listener.ViewHandler;

public class WaitPanel extends JPanel implements MouseListener, ViewHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JLabel title;
	private JLabel confirmBtn;
	private int width = 400;
	private int height = 360;
	private JLabel loginBtn;
	private JLabel reCodeBtn;
	private JLabel exit;
	private JLabel small;
	private Font font12 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 12);
	private Font font18 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 18);
	private Font font21 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 21);

	/**
	 * Create the panel.
	 */
	public WaitPanel(int w, int h) {
		width = w;
		height = h;

		setBounds(0, 0, width, height);
		setBackground(Color.WHITE);
		setLayout(null);

		title = new JLabel("\u8bf7\u7a0d\u5019...");
		title.setFont(font21);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(width / 2 - 70, 100, 140, 42);
		add(title);

		confirmBtn = new JLabel("\u53d6\u6d88");
		confirmBtn.setOpaque(true);
		confirmBtn.setHorizontalAlignment(SwingConstants.CENTER);
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setFont(font18);
		confirmBtn.setBorder(new LineBorder(new Color(11, 87, 230), 1, true));
		confirmBtn.setBackground(new Color(60, 155, 238));
		confirmBtn.setBounds(width / 2 - 40, 200, 80, 36);
		add(confirmBtn);

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

		exit.addMouseListener(this);
		small.addMouseListener(this);
		new Gif().start();
	}

	static class Gif extends Thread {
		@Override
		public void run() {
			super.run();
			while (true) {
				try {
					sleep(800);
					title.setText("\u8bf7\u7a0d\u5019.");
					sleep(800);
					title.setText("\u8bf7\u7a0d\u5019..");
					sleep(800);
					title.setText("\u8bf7\u7a0d\u5019...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(confirmBtn)) {
			EntryFrame.cardLayout.show(getParent(), "loginPanel");
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

	}
}
