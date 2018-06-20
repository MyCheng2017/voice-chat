package com.cheng.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cheng.domain.LocalMsg;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;
import com.cheng.listener.ViewHandler;
import com.cheng.main.App;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class BuddyPanel extends JPanel implements MouseListener, ViewHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel exit;
	private JLabel small;
	private JScrollPane buddyScrollPane;
	private JList<User> buddyList;
	protected User user;
	private JLabel buddySendBtn;
	private JLabel from;
	private JLabel count;
	private JLabel remark;
	private JLabel place;
	private JLabel buddyHeadLab;
	private JLabel signLab;
	private JLabel nicknameLab;
	private Font font12 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 12);
	private Font font15 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 15);
	private Font font18 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 18);
	private Font font20 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 20);
	

	int width = 720;
	int height = 500;
	int left = 250;
	int title = 64;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", new BuddyPanel(780, 500, 250, 65));
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
	public BuddyPanel(int width, int height, int left, int title) {
		// public BuddyPanel() {
		setLayout(null);

		JPanel buddyLeftPanel = new JPanel();
		buddyLeftPanel.setBorder(null);
		buddyLeftPanel.setLayout(null);
		buddyLeftPanel.setBounds(0, 0, left, height);
		add(buddyLeftPanel);

		JPanel buddySearchPanel = new JPanel();
		buddySearchPanel.setBounds(0, 0, left, title);
		buddyLeftPanel.add(buddySearchPanel);
		buddySearchPanel.setLayout(null);

		JTextField searchTextField = new JTextField();
		searchTextField.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));

		searchTextField.setBackground(new Color(210, 210, 210));
		searchTextField.setBounds(10, 33, 196, 23);

		buddySearchPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JLabel addBtn = new JLabel("+", JLabel.CENTER);
		addBtn.setBorder(new LineBorder(new Color(210, 210, 210), 2, true));
		addBtn.setOpaque(true);
		addBtn.setBackground(new Color(210, 210, 210));
		addBtn.setFont(font20);
		addBtn.setBounds(216, 33, 24, 23);
		buddySearchPanel.add(addBtn);

		buddyList = new JList<User>(App.getAplication().getModel());
		buddyList.setBackground(new Color(230, 230, 230));
		buddyList.setBorder(new EmptyBorder(0, 0, 0, 0));
		buddyList.setBounds(0, 0, left, height - title);
		buddyList.setSelectionBackground(new Color(220, 220, 220));
		buddyList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = buddyList.getSelectedIndex();
				user = App.getAplication().getBuddies().get(index);
				nicknameLab.setText(user.getNickname());
				buddyHeadLab.setIcon(new ImageIcon("icon/head/"+user.getAvatar()+".jpeg"));
				place.setText(user.getProvince()+"  "+user.getTown());
				count.setText(user.getId().longValue()+"");
			}
		});

		buddyList.setCellRenderer(new ImageCellRender());
		buddyScrollPane = new JScrollPane(buddyList);
		buddyScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		buddyScrollPane.setOpaque(false);
		buddyScrollPane.getVerticalScrollBar().setUI(new ScollBar());
		buddyScrollPane.setBounds(0, title, left, height - title);

		buddyLeftPanel.add(buddyScrollPane);

		JPanel buddyRightPanel = new JPanel();
		buddyRightPanel.setBounds(left, 0, width - left, height);
		buddyRightPanel.setBackground(new Color(245, 245, 245));
		add(buddyRightPanel);
		buddyRightPanel.setLayout(null);

		JPanel rightTitlePanel = new JPanel();
		rightTitlePanel.setLayout(null);
		rightTitlePanel.setBorder(null);
		rightTitlePanel.setBackground(new Color(245, 245, 245));
		rightTitlePanel.setBounds(0, 0, width - left, title);
		buddyRightPanel.add(rightTitlePanel);

		exit = new JLabel("X", SwingConstants.CENTER);
		exit.setFont(font12);
		exit.setOpaque(true);
		exit.setBackground(new Color(245, 245, 245));
		exit.setBounds(width - left - 38, 0, 38, 27);
		rightTitlePanel.add(exit);

		small = new JLabel("\u2014", SwingConstants.CENTER);
		small.setOpaque(true);
		small.setBackground(new Color(245, 245, 245));
		small.setBounds(width - left - 38 - 38, 0, 38, 27);
		rightTitlePanel.add(small);

		nicknameLab = new JLabel("");
		nicknameLab.setFont(font18);
		nicknameLab.setBounds(69, 81, 230, 41);
		buddyRightPanel.add(nicknameLab);

		signLab = new JLabel("\u968F\u98CE\u98D8\u626C");
		signLab.setForeground(Color.GRAY);
		signLab.setFont(font15);
		signLab.setBounds(69, 123, 254, 24);
		buddyRightPanel.add(signLab);

		buddyHeadLab = new JLabel("");
		//buddyHeadLab.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\head.jpeg"));
		buddyHeadLab.setBounds(356, 90, 61, 57);
		buddyRightPanel.add(buddyHeadLab);

		JLabel line1 = new JLabel("");
		line1.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		line1.setBounds(69, 157, 348, 9);
		buddyRightPanel.add(line1);

		JLabel label_14 = new JLabel("\u5907\u6CE8");
		label_14.setForeground(Color.GRAY);
		label_14.setFont(font12);
		label_14.setBounds(69, 196, 54, 15);
		buddyRightPanel.add(label_14);

		JLabel label_15 = new JLabel("\u5730\u533A");
		label_15.setFont(font12);
		label_15.setForeground(Color.GRAY);
		label_15.setBounds(69, 233, 54, 15);
		buddyRightPanel.add(label_15);

		JLabel label_16 = new JLabel("\u8D26\u53F7");
		label_16.setFont(font12);
		label_16.setForeground(Color.GRAY);
		label_16.setBounds(69, 271, 54, 15);
		buddyRightPanel.add(label_16);

		JLabel label_17 = new JLabel("\u6765\u6E90");
		label_17.setFont(font12);
		label_17.setForeground(Color.GRAY);
		label_17.setBounds(69, 310, 54, 15);
		buddyRightPanel.add(label_17);

		remark = new JLabel("\u5C0F\u660E");
		remark.setFont(font15);
		remark.setBounds(143, 190, 262, 24);
		buddyRightPanel.add(remark);

		place = new JLabel("");
		place.setFont(font15);
		place.setBounds(143, 224, 262, 30);
		buddyRightPanel.add(place);

		count = new JLabel("");
		count.setFont(font15);
		count.setBounds(143, 264, 262, 26);
		buddyRightPanel.add(count);

		from = new JLabel("");
		from.setFont(font15);
		from.setBounds(143, 304, 262, 24);
		buddyRightPanel.add(from);

		JLabel line2 = new JLabel("");
		line2.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		line2.setBounds(69, 356, 348, 9);
		buddyRightPanel.add(line2);

		buddySendBtn = new JLabel("\u53D1\u6D88\u606F", JLabel.CENTER);
		buddySendBtn.setOpaque(true);
		buddySendBtn.setBackground(new Color(50, 205, 50));
		buddySendBtn.setForeground(Color.WHITE);
		buddySendBtn.setFont(font18);
		buddySendBtn.setBounds((width - left) / 2 - 70, 395, 140, 42);
		buddyRightPanel.add(buddySendBtn);

		exit.addMouseListener(this);
		small.addMouseListener(this);
		buddySendBtn.addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(buddySendBtn)) {
			Message message = new Message();
			message.what = LocalMsg.MSG_TYPE_CHANGE_TO_CHAT;
			message.obj = user;
			App.getAplication().handleMessage(message);
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
		} else if (source.equals(buddySendBtn)) {
			buddySendBtn.setBackground(new Color(50, 180, 50));
		}

	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(new Color(245, 245, 245));
		} else if (source.equals(small)) {
			small.setBackground(new Color(245, 245, 245));
		} else if (source.equals(buddySendBtn)) {
			buddySendBtn.setBackground(new Color(50, 205, 50));
		}

	}

	public JLabel getSmall() {
		return small;

	}

	public void handleMessage(Message msg) {
		if (msg.what == MsgType.MSG_TYPE_MODIFY_INFO) {

		}
	}
}
