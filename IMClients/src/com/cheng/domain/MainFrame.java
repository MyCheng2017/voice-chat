package com.cheng.domain;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.JPopupMenu;
import javax.swing.JDesktopPane;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int mx, my, jmx, jmy;
	private static CardLayout cardLayout = new CardLayout();
	private int width = 780;
	private int high = 500;
	private int title = 64;
	private int menu = 60;
	private int left = 250;
	private JPanel contentPane;
	private JPanel menuPanel;
	private JPanel morePanel;
	private JPanel contentPanel;
	private JLabel msgBtn;
	private JLabel head;
	private JPanel bodyPanel;
	private JLabel buddyBtn;
	private JTextField searchTextField;
	private JLabel moreBtn;
	private JLabel exit;
	private JLabel small;
	private JLabel settingBtn;
	private JLabel sendBtn;
	private JLabel phoneBtn;
	private JLabel faceBtn;
	private JLabel fileBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		// ���ô��ڿ��϶�
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				setLocation(jmx + (e.getXOnScreen() - mx), jmy + (e.getYOnScreen() - my));
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mx = e.getXOnScreen();
				my = e.getYOnScreen();
				jmx = getX();
				jmy = getY();
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - high / 2 - 50, width, high);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(200, 200, 200)));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		bodyPanel = new JPanel();
		contentPane.add(bodyPanel, BorderLayout.CENTER);
		bodyPanel.setLayout(null);

		menuPanel = new JPanel();
		menuPanel.setBackground(new Color(50, 50, 50));
		menuPanel.setBounds(0, 0, menu, high);
		bodyPanel.add(menuPanel);
		menuPanel.setLayout(null);

		head = new JLabel("", JLabel.CENTER);
		head.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\head.jpeg"));
		head.setBounds(10, 25, 40, 40);
		head.addMouseListener(this);
		menuPanel.add(head);

		msgBtn = new JLabel();
		msgBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\msg_light.png"));
		msgBtn.setBounds(16, 90, 28, 28);
		msgBtn.addMouseListener(this);
		menuPanel.add(msgBtn);

		buddyBtn = new JLabel();
		buddyBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\buddy_dark.png"));
		buddyBtn.setBounds(16, 133, 28, 28);
		menuPanel.add(buddyBtn);
		buddyBtn.addMouseListener(this);

		moreBtn = new JLabel();
		moreBtn.addMouseListener(this);
		moreBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\more_dark.png"));
		moreBtn.setBounds(16, 186, 28, 28);
		menuPanel.add(moreBtn);

		settingBtn = new JLabel();
		settingBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\set_dark.png"));
		settingBtn.setBounds(16, high - 50, 28, 28);
		settingBtn.addMouseListener(this);
		menuPanel.add(settingBtn);

		contentPanel = new JPanel();
		contentPanel.setBounds(menu, 0, width - menu, high);
		bodyPanel.add(contentPanel, "contentPanel");
		contentPanel.setLayout(cardLayout);

		JPanel chatPanel = new JPanel();
		contentPanel.add(chatPanel, "chatPanel");
		chatPanel.setLayout(null);

		JPanel chatLeftPanel = new JPanel();
		chatLeftPanel.setBounds(0, 0, left, high);
		chatPanel.add(chatLeftPanel);
		chatLeftPanel.setLayout(null);
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setSize(100, 20);
		popupMenu.setLocation(0, 10);
		popupMenu.show(null, 0, 0);
		addPopup(chatLeftPanel, popupMenu);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 300, 300);
		panel_2.setBackground(Color.blue);
		popupMenu.add(panel_2);

		JLabel buddyNickname = new JLabel();
		buddyNickname.setText("记住密码");
		buddyNickname.setVerticalAlignment(SwingConstants.TOP);
		buddyNickname.setDisplayedMnemonicIndex(1);
		buddyNickname.setDoubleBuffered(true);
		buddyNickname.setDisplayedMnemonic(KeyEvent.VK_COPY);
		buddyNickname.setAutoscrolls(true);
		buddyNickname.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buddyNickname.setHorizontalTextPosition(SwingConstants.LEADING);
		buddyNickname.setHorizontalAlignment(SwingConstants.RIGHT);
		buddyNickname.setVerticalTextPosition(SwingConstants.TOP);
		buddyNickname.setBounds(41, 264, 178, 136);
		chatLeftPanel.add(buddyNickname);
		buddyNickname.setIconTextGap(0);
		
		buddyNickname.setOpaque(true);

		buddyNickname.setBackground(Color.CYAN);
		buddyNickname.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\head.jpeg"));
		buddyNickname.setFont(new Font("����ϸ��", Font.PLAIN, 12));

		JEditorPane e = new JEditorPane();
		e.setBounds(20, 138, 220, 116);
		chatLeftPanel.add(e);
		
		e.setContentType("text/html");
		e.setText("<img src='file:emojis/00.gif'/>");
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(188, 301, 110, -72);
		chatLeftPanel.add(desktopPane);
		desktopPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("New label");
		desktopPane.add(lblNewLabel);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(0, 26, 203, 85);
		chatLeftPanel.add(editorPane_1);

		JPanel chatRightPanel = new JPanel();
		chatRightPanel.setBounds(left, 0, width - left - menu, high);
		chatPanel.add(chatRightPanel);
		chatRightPanel.setLayout(null);

		JPanel titlePanel = new JPanel();

		titlePanel.setBounds(0, 0, width - menu - left, title);
		titlePanel.setBackground(new Color(245, 245, 245));
		titlePanel.setLayout(null);
		chatRightPanel.add(titlePanel);
		titlePanel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		exit = new JLabel("X", JLabel.CENTER);
		exit.setOpaque(true);
		exit.setBackground(new Color(245, 245, 245));
		exit.setBounds(width - left - menu - 38, 0, 38, 27);
		exit.addMouseListener(this);
		titlePanel.add(exit);

		small = new JLabel("\u2014", JLabel.CENTER);
		small.setBackground(new Color(245, 245, 245));
		small.setOpaque(true);
		small.setBounds(width - left - menu - 38 - 38, 0, 38, 27);
		small.addMouseListener(this);
		titlePanel.add(small);

		JLabel lblTitle = new JLabel("\u6807\u9898");
		lblTitle.setFont(new Font("����ϸ��", Font.PLAIN, 20));
		lblTitle.setBounds(32, 15, 360, 36);
		titlePanel.add(lblTitle);

		JPanel rightContentPanel = new JPanel();
		rightContentPanel.setBounds(0, title, width - left, high - title);
		rightContentPanel.setBackground(new Color(245, 245, 245));
		chatRightPanel.add(rightContentPanel);
		rightContentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(0, high - title - 146, width - left, 146);
		panel.setBorder(new MatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
		rightContentPanel.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(0, 0, width - left, 40);

		panel.add(panel_1);
		panel_1.setLayout(null);

		phoneBtn = new JLabel("", JLabel.CENTER);
		phoneBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\phone_dark.png"));
		phoneBtn.setBounds(411, 10, 30, 30);
		phoneBtn.addMouseListener(this);
		panel_1.add(phoneBtn);

		faceBtn = new JLabel("");
		faceBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\face_dark.png"));
		faceBtn.setBounds(26, 10, 30, 30);
		faceBtn.addMouseListener(this);
		panel_1.add(faceBtn);

		fileBtn = new JLabel("");
		fileBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\file_dark.png"));
		fileBtn.setBounds(59, 10, 30, 30);
		fileBtn.addMouseListener(this);
		panel_1.add(fileBtn);

		final JEditorPane editorPane = new JEditorPane();
		editorPane.setOpaque(false);
		editorPane.setBounds(0, 40, 470, 55);
		editorPane.setFocusable(false);
		panel.add(editorPane);

		sendBtn = new JLabel("\u53D1\u9001", JLabel.CENTER);
		sendBtn.setOpaque(true);
		sendBtn.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		sendBtn.setBorder(new LineBorder(new Color(230, 230, 230), 1, true));
		sendBtn.addMouseListener(this);
		sendBtn.setBounds(377, 105, 64, 28);
		panel.add(sendBtn);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 0, 10, 10);
		panel.add(panel_4);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 40, 121, 25);
		rightContentPanel.add(splitPane);
		
		JPanel panel_5 = new JPanel();
		splitPane.setLeftComponent(panel_5);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_5.add(lblNewLabel_1);
		
		JPanel panel_6 = new JPanel();
		splitPane.setRightComponent(panel_6);
		
		JButton btnNewButton = new JButton("New button");
		panel_6.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 132, 131, 25);
		rightContentPanel.add(separator);
		
		JPanel panel2=new JPanel();
		panel2.setSize(20, 50);

		JPanel buddyPanel = new JPanel();
		contentPanel.add(buddyPanel, "buddyPanel");
		buddyPanel.setLayout(null);

		JPanel buddyLeftPanel = new JPanel();
		buddyLeftPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		buddyLeftPanel.setLayout(null);
		buddyLeftPanel.setBounds(0, 0, left, high);
		buddyPanel.add(buddyLeftPanel);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(0, title, left, title);
		buddyLeftPanel.add(panel_3);

		JLabel label_4 = new JLabel("");
		label_4.setBounds(10, 10, 44, 42);
		panel_3.add(label_4);

		JLabel label_5 = new JLabel("\u5C18\u5C01\u4F60\u597D");
		label_5.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		label_5.setBounds(64, 10, 129, 21);
		panel_3.add(label_5);

		JLabel label_6 = new JLabel("\u968F\u98CE\u98D8\u626C...");
		label_6.setForeground(Color.GRAY);
		label_6.setFont(new Font("����ϸ��", Font.PLAIN, 12));
		label_6.setBounds(64, 37, 179, 15);
		panel_3.add(label_6);

		JLabel label_7 = new JLabel("13:25");
		label_7.setForeground(Color.GRAY);
		label_7.setFont(new Font("����ϸ��", Font.PLAIN, 12));
		label_7.setBounds(199, 14, 44, 15);
		panel_3.add(label_7);

		JPanel buddySearchPanel = new JPanel();
		buddySearchPanel.setBounds(0, 0, left, title);
		buddyLeftPanel.add(buddySearchPanel);
		buddySearchPanel.setLayout(null);

		searchTextField = new JTextField();
		searchTextField.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));

		searchTextField.setBackground(new Color(210, 210, 210));
		searchTextField.setBounds(10, 33, 196, 23);

		buddySearchPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JLabel addBtn = new JLabel("+", JLabel.CENTER);
		addBtn.setBorder(new LineBorder(new Color(210, 210, 210), 2, true));
		addBtn.setOpaque(true);
		addBtn.setBackground(new Color(210, 210, 210));
		addBtn.setFont(new Font("����", Font.PLAIN, 20));
		addBtn.setBounds(216, 33, 24, 23);
		buddySearchPanel.add(addBtn);

		JPanel buddyRightPanel = new JPanel();
		buddyRightPanel.setBounds(left, 0, width - left - menu, high);
		buddyRightPanel.setBackground(new Color(245, 245, 245));
		buddyPanel.add(buddyRightPanel);
		buddyRightPanel.setLayout(null);

		JPanel rightTitlePanel = new JPanel();
		rightTitlePanel.setLayout(null);
		rightTitlePanel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		rightTitlePanel.setBackground(new Color(245, 245, 245));
		rightTitlePanel.setBounds(0, 0, 470, 64);
		buddyRightPanel.add(rightTitlePanel);

		JLabel exit1 = new JLabel("X", SwingConstants.CENTER);
		exit1.setOpaque(true);
		exit1.setBackground(new Color(245, 245, 245));
		exit1.setBounds(432, 0, 38, 27);
		rightTitlePanel.add(exit1);

		JLabel small1 = new JLabel("\u2014", SwingConstants.CENTER);
		small1.setOpaque(true);
		small1.setBackground(new Color(245, 245, 245));
		small1.setBounds(394, 0, 38, 27);
		rightTitlePanel.add(small1);

		JLabel nicknameLab = new JLabel("\u5C18\u5C01\u4F60\u597D");
		nicknameLab.setFont(new Font("����ϸ��", Font.PLAIN, 18));
		nicknameLab.setBounds(69, 81, 230, 41);
		buddyRightPanel.add(nicknameLab);

		JLabel signLab = new JLabel("\u968F\u98CE\u98D8\u626C");
		signLab.setForeground(Color.GRAY);
		signLab.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		signLab.setBounds(69, 123, 254, 24);
		buddyRightPanel.add(signLab);

		JLabel buddyHeadLab = new JLabel("");
		buddyHeadLab.setBounds(356, 90, 61, 57);
		buddyRightPanel.add(buddyHeadLab);

		JLabel line1 = new JLabel("");
		line1.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		line1.setBounds(69, 157, 348, 9);
		buddyRightPanel.add(line1);

		JLabel label_14 = new JLabel("\u5907\u6CE8");
		label_14.setForeground(Color.GRAY);
		label_14.setFont(new Font("����ϸ��", Font.PLAIN, 12));
		label_14.setBounds(69, 196, 54, 15);
		buddyRightPanel.add(label_14);

		JLabel label_15 = new JLabel("\u5730\u533A");
		label_15.setForeground(Color.GRAY);
		label_15.setBounds(69, 233, 54, 15);
		buddyRightPanel.add(label_15);

		JLabel label_16 = new JLabel("\u8D26\u53F7");
		label_16.setForeground(Color.GRAY);
		label_16.setBounds(69, 271, 54, 15);
		buddyRightPanel.add(label_16);

		JLabel label_17 = new JLabel("\u6765\u6E90");
		label_17.setForeground(Color.GRAY);
		label_17.setBounds(69, 310, 54, 15);
		buddyRightPanel.add(label_17);

		JLabel remark = new JLabel("\u5C0F\u660E");
		remark.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		remark.setBounds(143, 190, 262, 24);
		buddyRightPanel.add(remark);

		JLabel place = new JLabel("\u8FBD\u5DE5\u5927");
		place.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		place.setBounds(143, 224, 262, 30);
		buddyRightPanel.add(place);

		JLabel count = new JLabel("1000001");
		count.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		count.setBounds(143, 264, 262, 26);
		buddyRightPanel.add(count);

		JLabel from = new JLabel("\u8D26\u53F7\u5BC6\u7801\u6DFB\u52A0");
		from.setFont(new Font("����ϸ��", Font.PLAIN, 15));
		from.setBounds(143, 304, 262, 24);
		buddyRightPanel.add(from);

		JLabel line2 = new JLabel("");
		line2.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		line2.setBounds(69, 356, 348, 9);
		buddyRightPanel.add(line2);

		JLabel buddySendBtn = new JLabel("\u53D1\u6D88\u606F", JLabel.CENTER);
		buddySendBtn.setOpaque(true);
		buddySendBtn.setBackground(new Color(50, 205, 50));
		buddySendBtn.setForeground(Color.WHITE);
		buddySendBtn.setFont(new Font("����ϸ��", Font.PLAIN, 18));
		buddySendBtn.setBounds(182, 395, 141, 41);
		buddyRightPanel.add(buddySendBtn);

		JPanel settingPanel = new JPanel();
		contentPanel.add(settingPanel, "settingPanel");
		settingPanel.setLayout(null);

		morePanel = new JPanel();
		contentPanel.add(morePanel, "morePanel");
		morePanel.setLayout(null);
	}

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(small)) {
			setExtendedState(JFrame.ICONIFIED);
		} else if (source.equals(buddyBtn)) {
			cardLayout.show(contentPanel, "buddyPanel");
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
		} else if (source.equals(msgBtn)) {
			msgBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\msg_move.png"));
		} else if (source.equals(buddyBtn)) {
			buddyBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\buddy_move.png"));
		} else if (source.equals(moreBtn)) {
			moreBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\more_move.png"));
		} else if (source.equals(settingBtn)) {
			settingBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\set_move.png"));
		} else if (source.equals(sendBtn)) {
			sendBtn.setBackground(new Color(0, 180, 0));
		} else if (source.equals(phoneBtn)) {
			phoneBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\phone_move.png"));
		} else if (source.equals(faceBtn)) {
			faceBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\face_move.png"));
		} else if (source.equals(fileBtn)) {
			fileBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\file_move.png"));
		}

	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(new Color(245, 245, 245));
		} else if (source.equals(small)) {
			small.setBackground(new Color(245, 245, 245));
		} else if (source.equals(msgBtn)) {
			msgBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\msg_dark.png"));
		} else if (source.equals(buddyBtn)) {
			buddyBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\buddy_dark.png"));
		} else if (source.equals(moreBtn)) {
			moreBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\more_dark.png"));
		} else if (source.equals(settingBtn)) {
			settingBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\set_dark.png"));
		} else if (source.equals(sendBtn)) {
			sendBtn.setBackground(new Color(245, 245, 245));
		} else if (source.equals(phoneBtn)) {
			phoneBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\phone_dark.png"));
		} else if (source.equals(faceBtn)) {
			faceBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\face_dark.png"));
		} else if (source.equals(fileBtn)) {
			fileBtn.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\file_dark.png"));
		}

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
