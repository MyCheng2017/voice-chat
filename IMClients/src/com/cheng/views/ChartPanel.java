package com.cheng.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cheng.core.Service;
import com.cheng.domain.IMMessage;
import com.cheng.domain.LocalMsg;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.domain.RequestType;
import com.cheng.domain.User;
import com.cheng.listener.ChatP2PVoiceListener;
import com.cheng.listener.ViewHandler;
import com.cheng.main.App;
import com.cheng.utils.ChatLog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ChartPanel extends JPanel implements MouseListener, ViewHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel exit;
	private JLabel small;
	private JLabel nicknameLab;
	private JPanel chatLeftPanel;
	private JScrollPane buddyScrollPane;
	private JList<?> buddyList;
	private JPanel chatRightPanel;
	private JPanel titlePanel;
	private JPanel rightContentPanel;
	private JPanel panel;
	private JPanel facePanel;
	private JLabel phoneBtn;
	private JLabel faceBtn;
	private JLabel fileBtn;
	private JEditorPane editorPane;
	private JLabel sendBtn;
	private JScrollPane msgScrollPane;
	private User user;
	private int width = 720;
	private int height = 460;
	private int title = 64;
	private int menu = 60;
	private int left = 250;
	Service service = new Service();
	Timer timer;
	CardLayout cardLayout = new CardLayout();
	private JLabel voiceIcon;
	private JLabel timeLab;
	private JLabel stopVoice;

	private JPanel voiceView;
	private JTextField searchTf;
	private JLabel addBtn;
	private JPanel chatView;
	protected User textUser;
	private JList<User> chatList;
	Emojis emojis = null;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneMsg;
	private JList<IMMessage> msgList;
	DefaultListModel<IMMessage> model = new DefaultListModel<IMMessage>();
	ImageIcon phoneIcon[];
	ImageIcon faceIcon[];
	ImageIcon fileIcon[];
	private Font font12 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 12);
	private Font font15 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 15);
	private Font font20 = new Font("\u534e\u6587\u7ec6\u9ed1", Font.PLAIN, 20);

	private LineBorder border = new LineBorder(SystemColor.textHighlight);
	private JLabel label_high;
	private JLabel label_low;
	private JLabel bg_bird;
	private JLabel bg_sea;
	private JLabel bg_normal;
	private JLabel label_11;
	private JLabel label_12;
	private JLabel label_13;
	private JLabel label_14;
	private JLabel label_10;

	/**
	 * Create the panel.
	 */
	//public ChartPanel() {
		 public ChartPanel(int width, int height, int left, int title) {
		setBounds(0, 0, width, height);

		this.setLayout(null);

		phoneIcon = new ImageIcon[3];
		faceIcon = new ImageIcon[3];
		fileIcon = new ImageIcon[3];

		phoneIcon[2] = new ImageIcon("icon/logo/phone_light.png");
		phoneIcon[1] = new ImageIcon("icon/logo/phone_move.png");
		phoneIcon[0] = new ImageIcon("icon/logo/phone_dark.png");

		faceIcon[2] = new ImageIcon("icon/logo/face_light.png");
		faceIcon[1] = new ImageIcon("icon/logo/face_move.png");
		faceIcon[0] = new ImageIcon("icon/logo/face_dark.png");

		fileIcon[2] = new ImageIcon("icon/logo/file_light.png");
		fileIcon[1] = new ImageIcon("icon/logo/file_move.png");
		fileIcon[0] = new ImageIcon("icon/logo/file_dark.png");

		chatLeftPanel = new JPanel();
		chatLeftPanel.setBounds(0, 0, left, height);
		chatLeftPanel.setBackground(new Color(240, 240, 240));
		this.add(chatLeftPanel);
		chatLeftPanel.setLayout(null);

		buddyList = new JList<User>(App.getAplication().getModel());
		buddyList.setBorder(null);
		buddyList.setOpaque(true);
		buddyList.setBackground(new Color(240, 240, 240));
		buddyList.setBounds(0, 0, left, height - title);

		buddyList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				int index = buddyList.getSelectedIndex();
				textUser = App.getAplication().getModel().get(index);
				if (phoneBtn.isEnabled()) {
					user = textUser;
				}

				nicknameLab.setText(textUser.getNickname());
				App.getAplication().getModel().get(index).setIs_actived(0);

				try {
					LinkedList<IMMessage> messages = ChatLog.read("chatlog/" + user.getId().longValue() + ".cheng");

					model.clear();

					for (IMMessage imMessage : messages) {
						model.addElement(imMessage);
					}

					scrollPaneMsg.getVerticalScrollBar().setValue(scrollPaneMsg.getVerticalScrollBar().getMaximum());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		buddyList.setCellRenderer(new ImageCellRender());
		buddyList.setSelectionBackground(new Color(220, 220, 220));
		buddyScrollPane = new JScrollPane(buddyList);
		buddyScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		// buddyScrollPane.setOpaque(false);
		buddyScrollPane.setBackground(new Color(240, 240, 240));
		buddyScrollPane.getVerticalScrollBar().setUI(new ScollBar());
		buddyScrollPane.setBounds(0, title, left, height - title);

		chatLeftPanel.add(buddyScrollPane);

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setBounds(0, 0, 250, 64);
		searchPanel.setBackground(new Color(240, 240, 240));
		chatLeftPanel.add(searchPanel);

		searchTf = new JTextField();
		searchTf.setColumns(1);
		searchTf.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));
		searchTf.setBackground(new Color(210, 210, 210));
		searchTf.setBounds(10, 33, 196, 23);
		searchPanel.add(searchTf);

		addBtn = new JLabel("+", SwingConstants.CENTER);
		addBtn.setOpaque(true);
		addBtn.setFont(font20);
		addBtn.setBorder(new LineBorder(new Color(210, 210, 210), 2, true));
		addBtn.setBackground(new Color(210, 210, 210));
		addBtn.setBounds(216, 33, 24, 23);
		searchPanel.add(addBtn);
		chatRightPanel = new JPanel();
		chatRightPanel.setBounds(left, 0, width - left, height);
		this.add(chatRightPanel);
		chatRightPanel.setLayout(null);

		titlePanel = new JPanel();

		titlePanel.setBounds(0, 0, width - left, title);
		titlePanel.setBackground(new Color(245, 245, 245));
		titlePanel.setLayout(null);
		chatRightPanel.add(titlePanel);
		titlePanel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
		exit = new JLabel("X", JLabel.CENTER);
		exit.setFont(font12);
		exit.setOpaque(true);
		exit.setBackground(new Color(245, 245, 245));
		exit.setBounds(width - left - 38, 0, 38, 27);
		titlePanel.add(exit);

		small = new JLabel("\u2014", JLabel.CENTER);
		small.setBackground(new Color(245, 245, 245));
		small.setOpaque(true);
		small.setBounds(width - left - 38 - 38, 0, 38, 27);
		titlePanel.add(small);

		nicknameLab = new JLabel("");
		nicknameLab.setFont(font20);
		nicknameLab.setBounds(32, 15, 288, 36);
		titlePanel.add(nicknameLab);

		rightContentPanel = new JPanel();
		rightContentPanel.setBounds(0, title, width - left, height - title);
		rightContentPanel.setBackground(new Color(245, 245, 245));
		chatRightPanel.add(rightContentPanel);
		rightContentPanel.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(0, height - title - 146, width - left, 146);
		panel.setBorder(new MatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
		rightContentPanel.add(panel);
		panel.setLayout(null);

		facePanel = new JPanel();
		facePanel.setOpaque(false);
		facePanel.setBounds(0, 0, width - left, 40);

		panel.add(facePanel);
		facePanel.setLayout(null);

		phoneBtn = new JLabel("", JLabel.CENTER);
		phoneBtn.setIcon(phoneIcon[0]);
		phoneBtn.setBounds(411, 10, 30, 30);
		facePanel.add(phoneBtn);

		faceBtn = new JLabel("");
		faceBtn.setIcon(faceIcon[0]);
		faceBtn.setBounds(26, 10, 30, 30);
		facePanel.add(faceBtn);

		fileBtn = new JLabel("");
		fileBtn.setIcon(fileIcon[0]);
		fileBtn.setBounds(59, 10, 30, 30);
		facePanel.add(fileBtn);

		sendBtn = new JLabel("\u53D1\u9001", JLabel.CENTER);
		sendBtn.setOpaque(true);
		sendBtn.setFont(font15);
		sendBtn.setBorder(new LineBorder(new Color(230, 230, 230), 1, true));
		sendBtn.setBounds(377, 105, 64, 28);
		panel.add(sendBtn);

		editorPane = new JEditorPane();
		editorPane.setBackground(new Color(245, 245, 245));
		editorPane.setContentType("text/html");
		editorPane.setBounds(28, 40, width - left - 60, 60);
		// panel.add(editorPane);
		editorPane.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				panel.setBackground(new Color(245, 245, 245));
				editorPane.setBackground(new Color(245, 245, 245));
			}

			public void focusGained(FocusEvent e) {
				panel.setBackground(Color.WHITE);
				editorPane.setBackground(Color.WHITE);

			}
		});
		scrollPane = new JScrollPane(editorPane);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
		scrollPane.setBounds(0, 40, width - left, 60);
		scrollPane.getVerticalScrollBar().setUI(new ScollBar2());
		scrollPane.getHorizontalScrollBar().setUI(new ScollBar());
		panel.add(scrollPane);

		chatView = new JPanel();
		chatView.setBounds(0, 0, width - left, height - title - 150);
		chatView.setBorder(null);
		chatView.setBackground(new Color(245, 245, 245));
		chatView.setLayout(cardLayout);
		rightContentPanel.add(chatView);

		voiceView = new JPanel();
		voiceView.setBounds(0, 0, width - left, height - title - 150);
		voiceView.setBorder(null);
		voiceView.setBackground(new Color(245, 245, 245));

		voiceView.setLayout(null);

		voiceIcon = new JLabel("");
		voiceIcon.setIcon(new ImageIcon("icon/logo/voice60.png"));
		voiceIcon.setHorizontalAlignment(SwingConstants.CENTER);
		voiceIcon.setBounds((width - left) / 2 - 35, 30, 70, 70);
		voiceView.add(voiceIcon);

		timeLab = new JLabel("19:12");
		timeLab.setFont(font15);
		timeLab.setHorizontalAlignment(SwingConstants.CENTER);
		timeLab.setBounds(0, 110, width - left, 15);
		voiceView.add(timeLab);

		stopVoice = new JLabel("\u9000\u51FA");
		stopVoice.setForeground(Color.WHITE);
		stopVoice.setBorder(new LineBorder(new Color(60, 155, 238), 2, true));
		stopVoice.setOpaque(true);
		stopVoice.setFont(font15);
		stopVoice.setHorizontalAlignment(SwingConstants.CENTER);
		stopVoice.setBounds(210, 206, 60, 30);
		stopVoice.setBackground(new Color(60, 155, 238));
		voiceView.add(stopVoice);
		rightContentPanel.setBackground(new Color(245, 245, 245));

		chatList = new JList<User>();
		chatList.setBorder(new EmptyBorder(0, 0, 0, 0));
		chatList.setBackground(new Color(245, 245, 245));
		chatList.setBounds(0, 0, width - left, height - title - 150);

		// chatList.setCellRenderer(new ImageCellRender());

		msgScrollPane = new JScrollPane();
		msgScrollPane.setBounds(0, 0, width - left, height - title - 150);
		msgScrollPane.setBorder(null);
		msgScrollPane.setOpaque(false);

		msgScrollPane.setBackground(new Color(245, 245, 245));
		msgScrollPane.getVerticalScrollBar().setUI(new ScollBar());

		msgList = new JList<IMMessage>(model);
		msgList.setBackground(new Color(245, 245, 245));
		msgList.setCellRenderer(new ImageCellRender());
		scrollPaneMsg = new JScrollPane(msgList);
		scrollPaneMsg.setBorder(null);
		scrollPaneMsg.setBackground(new Color(245, 245, 245));
		scrollPaneMsg.getVerticalScrollBar().setUI(new ScollBar());
		scrollPaneMsg.getHorizontalScrollBar().setUI(new ScollBar2());
		chatView.add(scrollPaneMsg, "textPanel");

		chatView.add(voiceView, "voicePanel");

		bg_bird = new JLabel("\u9e1f\u53eb");
		bg_bird.setFont(font12);
		bg_bird.setHorizontalAlignment(SwingConstants.CENTER);
		bg_bird.setBounds(21, 10, 54, 21);
		voiceView.add(bg_bird);

		bg_sea = new JLabel("\u6d77\u6d6a");
		bg_sea.setFont(font12);
		bg_sea.setHorizontalAlignment(SwingConstants.CENTER);
		bg_sea.setBounds(21, 45, 54, 21);
		voiceView.add(bg_sea);

		label_10 = new JLabel("0");
		label_10.setBorder(border);
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setBounds(211, 135, 54, 52);
		voiceView.add(label_10);

		label_11 = new JLabel("-2");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setBounds(122, 166, 54, 21);
		voiceView.add(label_11);

		label_12 = new JLabel("-1");
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setBounds(122, 135, 54, 21);
		voiceView.add(label_12);

		label_13 = new JLabel("+1");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setBounds(292, 135, 54, 21);
		voiceView.add(label_13);

		label_14 = new JLabel("+2");
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setBounds(292, 166, 54, 21);
		voiceView.add(label_14);

		bg_normal = new JLabel("\u6b63\u5e38");
		bg_normal.setBorder(border);
		bg_normal.setFont(font12);
		bg_normal.setHorizontalAlignment(SwingConstants.CENTER);
		bg_normal.setBounds(21, 85, 54, 21);
		voiceView.add(bg_normal);

		label_high = new JLabel("\u9ad8\u97f3\u8d28");
		label_high.setHorizontalAlignment(SwingConstants.CENTER);
		label_high.setFont(font12);
		label_high.setBorder(border);
		label_high.setBounds(389, 10, 54, 21);
		voiceView.add(label_high);

		label_low = new JLabel("\u4f4e\u97f3\u8d28");
		label_low.setHorizontalAlignment(SwingConstants.CENTER);
		label_low.setFont(font12);
		label_low.setBounds(389, 79, 54, 21);
		voiceView.add(label_low);

		exit.addMouseListener(this);
		small.addMouseListener(this);
		phoneBtn.addMouseListener(this);
		faceBtn.addMouseListener(this);
		fileBtn.addMouseListener(this);
		sendBtn.addMouseListener(this);
		stopVoice.addMouseListener(this);
		addBtn.addMouseListener(this);
		editorPane.addMouseListener(this);
		label_high.addMouseListener(this);
		label_low.addMouseListener(this);
		bg_bird.addMouseListener(this);
		bg_normal.addMouseListener(this);
		bg_sea.addMouseListener(this);
		label_10.addMouseListener(this);
		label_11.addMouseListener(this);
		label_12.addMouseListener(this);
		label_13.addMouseListener(this);
		label_14.addMouseListener(this);

	}

	private void movePitchBorder(int i) {

		label_10.setBorder(null);
		label_11.setBorder(null);
		label_12.setBorder(null);
		label_13.setBorder(null);
		label_14.setBorder(null);

		if (i == 0) {
			label_10.setBorder(border);
		} else if (i == 1) {
			label_11.setBorder(border);
		} else if (i == 2) {
			label_12.setBorder(border);
		} else if (i == 3) {
			label_13.setBorder(border);
		} else if (i == 4) {
			label_14.setBorder(border);
		}

	}

	private void moveBgBorder(int i) {
		bg_bird.setBorder(null);
		bg_normal.setBorder(null);
		bg_sea.setBorder(null);
		if (i == 0) {
			bg_bird.setBorder(border);
		} else if (i == 1) {
			bg_normal.setBorder(border);
		} else if (i == 2) {
			bg_sea.setBorder(border);
		}
	}

	private void moveCodeBorder(int i) {
		label_high.setBorder(null);
		label_low.setBorder(null);
		if (i == 0) {
			label_high.setBorder(border);
		} else if (i == 1) {
			label_low.setBorder(border);
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", new ChartPanel(780, 500, 250, 65));
		f.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 780;
		int h = 500;
		f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
		f.setSize(w, h);
		f.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getXOnScreen();
		int y = e.getYOnScreen();

		Object source = e.getSource();

		if (source.equals(exit)) {
			System.exit(0);
		} else if (source.equals(phoneBtn)) {
 
			if (!phoneBtn.isEnabled()) {
				return;
			}

			if (user == null) {
				JOptionPane.showConfirmDialog(this, "\u8BF7\u9009\u62E9\u8BED\u97F3\u5BF9\u8C61\uFF01", "",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			try {
				Request bRequest = new Request(App.user, user.getId().longValue(), "",
						RequestType.REQUEST_P2P_VOICE_CONNECT);
				service.send(bRequest);
			} catch (IOException e1) {
				JOptionPane.showConfirmDialog(this, "\u4E0E\u670D\u52A1\u5668\u5931\u53BB\u8FDE\u63A5\uFF01",
						"\u8B66\u544A", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();return;
			}

			phoneBtn.setEnabled(false);
		} else if (source.equals(faceBtn)) {

			if (emojis == null) {
				emojis = new Emojis(x - 200, y - 295, this);
			}
			emojis.setLocation(x - 200, y - 295);
			if (emojis.isVisible()) {
				emojis.setVisible(false);
			} else {
				emojis.setVisible(true);
			}

		} else if (source.equals(sendBtn)) {
			Elements elements = Jsoup.parse(editorPane.getText()).select("p");
			String html = "";

			for (Element element : elements) {

				html += "<p style=\"width: 160px;height: auto;word-wrap:break-word;word-break:break-all;margin-top: 0;text-align: right;\">"
						+ element.html() + "</p>";

			}

			IMMessage message = new IMMessage(App.user, textUser.getId().longValue(), html, IMMessage.TYPE_TEXT);
			message.id = model.size();
			model.addElement(message);

			try {
				ChatLog.insertToUser(message);

				service.send(message);
				editorPane.setText("");
			} catch (IOException e1) {
				
				e1.printStackTrace();return;
			}
			scrollPaneMsg.getVerticalScrollBar().setValue(scrollPaneMsg.getVerticalScrollBar().getMaximum());
		} else if (source.equals(editorPane)) {
			editorPane.setEnabled(true);
			editorPane.setBackground(Color.white);
			panel.setBackground(Color.white);
		} else if (source.equals(stopVoice)) {
			phoneBtn.setEnabled(true);
			cardLayout.show(chatView, "textPanel");
			if (timer != null) {
				timer.stop = true;
			}
			 
			App.capture.stopSelf();
			ChatP2PVoiceListener.stop();

			try {
				Request bRequest = new Request(App.user, user.getId().longValue(), "",
						RequestType.REQUEST_P2P_VOICE_STOP);
				service.send(bRequest);
				user = textUser;
			} catch (Exception e1) {

				e1.printStackTrace();
				return;
			}
		} else if (source.equals(addBtn)) {
			String id = searchTf.getText();
			if (id == null || id.length() <= 0) {
				JOptionPane.showMessageDialog(this,
						"\u8F93\u5165\u90AE\u7BB1\u6216\u8005\u5BF9\u65B9\u8D26\u53F7\u5373\u53EF\u8BF7\u6C42\u6DFB\u52A0\u5BF9\u65B9\u4E3A\u597D\u53CB\uFF01",
						"\u901A\u77E5", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (id.equals(App.user.getId().longValue() + "") || id.equals(App.user.getEmail())) {
				JOptionPane.showMessageDialog(this,
						"\u4E0D\u80FD\u6DFB\u52A0\u81EA\u5DF1\u4E3A\u4E3A\u597D\u53CB\uFF01", "\u901A\u77E5",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			try {
				Request bRequest = new Request(App.user, 0L, id, RequestType.REQUEST_ADD_BUDDTY);
				service.send(bRequest);
				searchTf.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();return;
			}
		} else if (source.equals(label_10)) {
			movePitchBorder(0);
			App.capture.change(0);
		} else if (source.equals(label_11)) {
			movePitchBorder(1);
			App.capture.change(1);
		} else if (source.equals(label_12)) {
			movePitchBorder(2);
			App.capture.change(2);
		} else if (source.equals(label_13)) {
			movePitchBorder(3);
			App.capture.change(3);

		} else if (source.equals(label_14)) {
			movePitchBorder(4);
			App.capture.change(4);
		} else if (source.equals(label_high)) {
			moveCodeBorder(0);
			App.capture.removeCode();
		} else if (source.equals(label_low)) {
			moveCodeBorder(1);
			App.capture.addCode();
		} else if (source.equals(bg_bird)) {
			moveBgBorder(0);
			try {
				App.capture.addBg("bird.au");
			} catch (IOException e1) {
				e1.printStackTrace();return;
			}
			
		} else if (source.equals(bg_sea)) {
			moveBgBorder(2);
			try {
				App.capture.addBg("sea.au");
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		} else if (source.equals(bg_normal)) {
			moveBgBorder(1);
			App.capture.removeBg(null);
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
		} else if (source.equals(sendBtn)) {
			sendBtn.setBackground(new Color(0, 180, 0));
		} else if (source.equals(phoneBtn)) {
			phoneBtn.setIcon(phoneIcon[1]);
		} else if (source.equals(faceBtn)) {
			faceBtn.setIcon(faceIcon[1]);
		} else if (source.equals(fileBtn)) {
			fileBtn.setIcon(fileIcon[1]);
		}

	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(exit)) {
			exit.setBackground(new Color(245, 245, 245));
		} else if (source.equals(small)) {
			small.setBackground(new Color(245, 245, 245));
		} else if (source.equals(sendBtn)) {
			sendBtn.setBackground(new Color(245, 245, 245));
		} else if (source.equals(phoneBtn)) {
			phoneBtn.setIcon(phoneIcon[0]);
		} else if (source.equals(faceBtn)) {
			faceBtn.setIcon(faceIcon[0]);
		} else if (source.equals(fileBtn)) {
			fileBtn.setIcon(fileIcon[0]);
		}

	}

	class Timer implements Runnable {
		int h = 0;
		int m = 0;
		int s = 0;
		StringBuffer buf = new StringBuffer();
		boolean stop = false;

		public void run() {
			h = 0;
			m = 0;
			s = 0;
			stop = false;
			while (!stop) {
				buf.delete(0, buf.length());
				if (m >= 60) {
					h++;
					m = 0;
				}
				if (s >= 60) {
					m++;
					s = 0;
				}
				if (h < 10) {
					buf.append("0");
					buf.append(h);
					buf.append(":");
				} else {
					buf.append(h);
					buf.append(":");
				}
				if (m < 10) {
					buf.append("0");
					buf.append(m);
					buf.append(":");
				} else {
					buf.append(m);
					buf.append(":");
				}
				if (s < 10) {
					buf.append("0");
					buf.append(s);
				} else {
					buf.append(s);
				}
				// System.out.println(buf.toString());
				timeLab.setText(buf.toString());
				try {
					Thread.sleep(1000);
					s++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public JLabel getSmall() {
		return small;

	}

	public void handleMessage(Message msg) {
		if (msg.what == MsgType.MSG_TYPE_REQUEST) {
			
			Request request = (Request) msg.obj;

			if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT) {
				phoneBtn.setEnabled(true);
				cardLayout.show(chatView, "textView");
			} else if (request.type == RequestType.REQUEST_P2P_VOICE_START) {
				
				if (request.fromUser == App.user.getId().longValue()) {
					user = new User();
					user.setId(new BigInteger(request.toUser + ""));
				} else {
					user = new User();
					user.setId(new BigInteger(request.fromUser + ""));
				}
				if (timer == null) {
					timer = new Timer();
				}
				new Thread(timer).start();
				if (phoneBtn.isEnabled()) {
					phoneBtn.setEnabled(false);
				}
				cardLayout.show(chatView, "voicePanel");
			} else if (request.type == RequestType.REQUEST_P2P_VOICE_STOP) {
				phoneBtn.setEnabled(true);
				cardLayout.show(chatView, "textPanel");
				if (timer != null) {
					timer.stop = true;
				}
				user = textUser;
				if (App.capture != null) {
					App.capture.stopSelf();
				}

				// App.capture = null;
			} else if (request.type == RequestType.REQUEST_ADD_BUDDTY_SUCCESS
					|| request.type == RequestType.REQUEST_ADD_BUDDTY) {

			}
		} else if (msg.what == MsgType.MSG_TYPE_FRIEND_NOT_INLINE
				|| msg.what == MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE) {
			if (!phoneBtn.isEnabled()) {
				phoneBtn.setEnabled(true);
				cardLayout.show(chatView, "textPanel");
				if (timer != null) {
					timer.stop = true;
				}
				timer = null;
				if (App.capture != null) {
					App.capture.stopSelf();
				}

				//App.capture = null;
			}
		} else if (msg.what == LocalMsg.MSG_TYPE_CHANGE_TO_CHAT) {
			textUser = (User) msg.obj;
			if (!phoneBtn.isEnabled()) {
				user = textUser;
			}
			
			nicknameLab.setText(textUser.getNickname());
		} else if (msg.what == LocalMsg.MSG_TYPE_GET_EMOJIS) {

			String value = msg.msg;
			emojis.setVisible(false);

			Document document = Jsoup.parse(editorPane.getText());
			Elements p = document.select("p");
			if (p != null) {
				p.last().append(value);
			}

			editorPane.setText(document.html());

			System.out.println(editorPane.getText());

		} else if (msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT) {
			IMMessage message = (IMMessage) msg.obj;

			if (textUser!=null&&message.fromUser == textUser.getId().longValue()) {
				model.addElement(message);
				System.out.println("--->>>" + message.fromUser);

				scrollPaneMsg.getVerticalScrollBar().setValue(scrollPaneMsg.getVerticalScrollBar().getMaximum());
			} else {
				DefaultListModel<User> dModel = App.getAplication().getModel();
				for (int i = 0; i < dModel.size(); i++) {
					if (dModel.getElementAt(i).getId().longValue() == message.fromUser) {
						App.getAplication().getModel().getElementAt(i)
								.setIs_actived(dModel.getElementAt(i).getIs_actived() + 1);
						break;
					}
				}
			
			}
		} else if (msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT_READ || msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT_READ) {
			IMMessage message = (IMMessage) msg.obj;
			if (message.fromUser == textUser.getId().longValue()) {
				
				model.getElementAt(message.id).isRead = message.isRead;
			}
		}
	}
}
