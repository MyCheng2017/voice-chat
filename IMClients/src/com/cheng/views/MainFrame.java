package com.cheng.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.cheng.core.Capture;
import com.cheng.core.Service;
import com.cheng.domain.LocalMsg;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.domain.RequestType;
import com.cheng.domain.User;
import com.cheng.listener.ChatP2PVoiceListener;
import com.cheng.listener.ViewHandler;
import com.cheng.main.App;

public class MainFrame extends JFrame implements MouseListener, ViewHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int mx, my, jmx, jmy;
	public static CardLayout cardLayout = new CardLayout();
	private int width = 780;
	private int height = 500;
	private int title = 64;
	private int menu = 60;
	private int left = 250;
	private JPanel contentPane;
	private JPanel menuPanel;
	private BuddyPanel buddyPanel;
	private JPanel morePanel;
	private JPanel contentPanel;
	private JLabel msgBtn;
	private JLabel head;
	private JPanel bodyPanel;
	private JLabel buddyBtn;

	private JLabel moreBtn;
	private JLabel settingBtn;
	private ChartPanel chatPanel;

	private ImageIcon buddyIcon[];
	private ImageIcon chatIcon[];
	private ImageIcon moreIcon[];
	private ImageIcon settingIcon[];

	private int index = 1;
	Service service = new Service();
	ChatP2PVoiceListener listener = new ChatP2PVoiceListener();

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
		buddyIcon = new ImageIcon[3];
		chatIcon = new ImageIcon[3];
		moreIcon = new ImageIcon[3];
		settingIcon = new ImageIcon[3];

		buddyIcon[0] = new ImageIcon("icon/logo/buddy_light.png");
		buddyIcon[1] = new ImageIcon("icon/logo/buddy_move.png");
		buddyIcon[2] = new ImageIcon("icon/logo/buddy_dark.png");

		chatIcon[0] = new ImageIcon("icon/logo/msg_light.png");
		chatIcon[1] = new ImageIcon("icon/logo/msg_move.png");
		chatIcon[2] = new ImageIcon("icon/logo/msg_dark.png");

		moreIcon[0] = new ImageIcon("icon/logo/more_light.png");
		moreIcon[1] = new ImageIcon("icon/logo/more_move.png");
		moreIcon[2] = new ImageIcon("icon/logo/more_dark.png");

		settingIcon[0] = new ImageIcon("icon/logo/set_light.png");
		settingIcon[1] = new ImageIcon("icon/logo/set_move.png");
		settingIcon[2] = new ImageIcon("icon/logo/set_dark.png");

		 
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
		App.getAplication().initModel();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2 - 50, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(200, 200, 200)));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		bodyPanel = new JPanel();
		contentPane.add(bodyPanel, BorderLayout.CENTER);
		bodyPanel.setLayout(null);

		menuPanel = new JPanel();
		menuPanel.setBackground(new Color(50, 50, 50));
		menuPanel.setBounds(0, 0, menu, height);
		bodyPanel.add(menuPanel);
		menuPanel.setLayout(null);

		head = new JLabel("", JLabel.CENTER);
		head.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\head.jpeg"));
		head.setBounds(10, 25, 40, 40);
		head.addMouseListener(this);
		menuPanel.add(head);

		msgBtn = new JLabel();
		msgBtn.setIcon(chatIcon[0]);
		msgBtn.setBounds(16, 90, 28, 28);
		msgBtn.addMouseListener(this);
		menuPanel.add(msgBtn);

		buddyBtn = new JLabel();
		buddyBtn.setIcon(buddyIcon[2]);
		buddyBtn.setBounds(16, 133, 28, 28);
		menuPanel.add(buddyBtn);
		buddyBtn.addMouseListener(this);

		moreBtn = new JLabel();
		moreBtn.addMouseListener(this);
		moreBtn.setIcon(moreIcon[2]);
		moreBtn.setBounds(16, 186, 28, 28);
		menuPanel.add(moreBtn);

		settingBtn = new JLabel();
		settingBtn.setIcon(settingIcon[2]);
		settingBtn.setBounds(16, height - 50, 28, 28);
		settingBtn.addMouseListener(this);
		menuPanel.add(settingBtn);

		contentPanel = new JPanel();
		contentPanel.setBounds(menu, 0, width - menu, height);
		bodyPanel.add(contentPanel, "contentPanel");
		contentPanel.setLayout(cardLayout);

		chatPanel = new ChartPanel(width - menu, height, left, title);
		contentPanel.add(chatPanel, "chatPanel");
		// chatPanel.setLayout(null);
		App.getAplication().setVoiceHandler(chatPanel);

		buddyPanel = new BuddyPanel(width - menu, height, left, title);
		contentPanel.add(buddyPanel, "buddyPanel");
		// buddyPanel.setLayout(null);

		JPanel settingPanel = new JPanel();
		contentPanel.add(settingPanel, "settingPanel");
		settingPanel.setLayout(null);

		morePanel = new JPanel();
		contentPanel.add(morePanel, "morePanel");
		morePanel.setLayout(null);

		chatPanel.getSmall().addMouseListener(this);
		head.addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source.equals(buddyBtn)) {
			index = 2;
			change();
			cardLayout.show(contentPanel, "buddyPanel");
		} else if (source.equals(chatPanel.getSmall()) || source.equals(buddyPanel.getSmall())) {
			setExtendedState(JFrame.ICONIFIED);
		} else if (source.equals(msgBtn)) {
			index = 1;
			change();
			cardLayout.show(contentPanel, "chatPanel");
		}else if (source.equals(head)) {
			
			//cardLayout.show(contentPanel, "morePanel");
		}

	}

	private void change() {
		buddyBtn.setIcon(buddyIcon[2]);
		msgBtn.setIcon(chatIcon[2]);
		moreBtn.setIcon(moreIcon[2]);
		if (index == 1) {
			msgBtn.setIcon(chatIcon[0]);
		} else if (index == 2) {
			buddyBtn.setIcon(buddyIcon[0]);

		} else if (index == 3) {
			moreBtn.setIcon(moreIcon[0]);
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
		if (source.equals(msgBtn)) {
			msgBtn.setIcon(chatIcon[1]);
		} else if (source.equals(buddyBtn)) {
			buddyBtn.setIcon(buddyIcon[1]);
		} else if (source.equals(moreBtn)) {
			moreBtn.setIcon(moreIcon[1]);
		} else if (source.equals(settingBtn)) {
			settingBtn.setIcon(settingIcon[1]);
		}

	}

	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();

		if (source.equals(msgBtn)) {
			if (index == 1) {
				msgBtn.setIcon(chatIcon[0]);
			} else {
				msgBtn.setIcon(chatIcon[2]);
			}

		} else if (source.equals(buddyBtn)) {
			if (index == 2) {
				buddyBtn.setIcon(buddyIcon[0]);
			} else {
				buddyBtn.setIcon(buddyIcon[2]);
			}
		} else if (source.equals(moreBtn)) {
			if (index == 3) {
				moreBtn.setIcon(moreIcon[0]);
			} else {
				moreBtn.setIcon(moreIcon[2]);
			}
		} else if (source.equals(settingBtn)) {

			settingBtn.setIcon(settingIcon[2]);

		}

	}

	public void handleMessage(Message msg) {
		if (msg.what == MsgType.MSG_TYPE_REQUEST) {
			 
			Request request = (Request) msg.obj;
			if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT) {
				String message = request.fromUser + "\u8bf7\u6c42\u8bed\u97f3\u901a\u8bdd\uff0c\u662f\u5426\u540c\u610f\uff1f";
				int result = JOptionPane.showConfirmDialog(this, message,"\u901a\u77e5", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					 
					try {
						Request bRequest = new Request(App.user, request.fromUser, "",
								RequestType.REQUEST_P2P_VOICE_CONNECT_ACCEPT);
						service.send(bRequest);

					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					try {

						Request bRequest = new Request(App.user, request.fromUser, "",
								RequestType.REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT);
						service.send(bRequest);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (request.type == RequestType.REQUEST_ADD_BUDDTY_NOT_ACCEPT) {
				JOptionPane.showMessageDialog(this, "\u5bf9\u65b9\u62d2\u7edd\u4e86\u60a8\u7684\u8bf7\u6c42", "\u901a\u77e5", JOptionPane.INFORMATION_MESSAGE);
			} else if (request.type == RequestType.REQUEST_ADD_BUDDTY) {
				int result = JOptionPane.showConfirmDialog(this, "\u7528\u6237" + request.fromUser + "\u8bf7\u6c42\u6dfb\u52a0\u60a8\u4e3a\u597d\u53cb\uff0c\u662f\u5426\u540c\u610f\uff1f",
						"֪ͨ", JOptionPane.YES_NO_CANCEL_OPTION);
				if (result == JOptionPane.YES_OPTION) { 
					User user = new User();
					user.setId(new BigInteger(request.fromUser + ""));
					user.setUsername(new BigInteger(request.fromUser + ""));
					user.setAvatar(request.fromAvatar);
					user.setNickname(request.fromNick);
					App.getAplication().getBuddies().add(user);
					App.getAplication().getModel().addElement(user);
					chatPanel.handleMessage(msg);
					try {
						Request bRequest = new Request(App.user, request.fromUser, "",
								RequestType.REQUEST_ADD_BUDDTY_ACCEPT);
						service.send(bRequest);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (result == JOptionPane.NO_OPTION) {
					try {
						Request bRequest = new Request(App.user, request.fromUser, "",
								RequestType.REQUEST_ADD_BUDDTY_NOT_ACCEPT);
						service.send(bRequest);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
				}
			} else if (request.type == RequestType.REQUEST_ADD_BUDDTY_FAILE
					|| request.type == RequestType.REQUEST_ADD_BUDDTY_SUCCESS) {
				JOptionPane.showMessageDialog(this, request.content, "\u901a\u77e5", JOptionPane.INFORMATION_MESSAGE);
			} else if (request.type == RequestType.REQUEST_ADD_BUDDTY_ACCEPT) {
				JOptionPane.showMessageDialog(this, request.fromUser + "\u540c\u610f\u4e86\u4f60\u7684\u597d\u53cb\u8bf7\u6c42", "\u901a\u77e5",
						JOptionPane.INFORMATION_MESSAGE);
				User user = new User();
				user.setId(new BigInteger(request.fromUser + ""));
				user.setUsername(new BigInteger(request.fromUser + ""));
				user.setAvatar(request.fromAvatar);
				user.setNickname(request.fromNick);
				App.getAplication().getBuddies().add(user);
				App.getAplication().getModel().addElement(user);
				chatPanel.handleMessage(msg);
			} else if (request.type == RequestType.REQUEST_P2P_VOICE_START) {
				// App.conn.addOnRecevieMsgListener(new ChatP2PVoiceListener());
				if (App.capture == null) {
					App.capture = new Capture();
				}
				// App.conn.addOnRecevieMsgListener(listener);
				// ChatP2PVoiceListener.start();
				new Thread(App.capture).start();
				cardLayout.show(contentPanel, "chatPanel");
				chatPanel.handleMessage(msg);

			} else if (request.type == RequestType.REQUEST_P2P_VOICE_STOP) {
				// App.conn.removeOnRecevieMsgListener(listener);
				// ChatP2PVoiceListener.stop();
				chatPanel.handleMessage(msg);
				if (App.capture != null) {
					App.capture.stopSelf();
				}
				chatPanel.handleMessage(msg);
				// App.capture=null;
				System.gc();
				JOptionPane.showMessageDialog(this, request.fromUser + "\u7ed3\u675f\u4e86\u8bed\u97f3\u901a\u8bdd\uff01", "\u901a\u77e5",
						JOptionPane.INFORMATION_MESSAGE);

			} else if (request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT) {

				chatPanel.handleMessage(msg);

				JOptionPane.showMessageDialog(this, request.fromUser + "\u62d2\u7edd\u4e86\u8bed\u97f3\u8bf7\u6c42", "\u901a\u77e5",
						JOptionPane.INFORMATION_MESSAGE);

			}

		} else if (msg.what == MsgType.MSG_TYPE_FRIEND_NOT_INLINE
				|| msg.what == MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE) {
			JOptionPane.showMessageDialog(this, msg.msg, "\u901a\u77e5", JOptionPane.INFORMATION_MESSAGE);
			chatPanel.handleMessage(msg);
		} else if (msg.what == LocalMsg.MSG_TYPE_CHANGE_TO_CHAT) {
			cardLayout.show(contentPanel, "chatPanel");
			chatPanel.handleMessage(msg);
		} else if (msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT || msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT_UNREAD
				|| msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT_READ) {
			chatPanel.handleMessage(msg);
		}

	}

}
