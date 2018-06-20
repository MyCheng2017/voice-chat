package com.cheng.main;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import com.cheng.core.Capture;
import com.cheng.core.IMConnection;
import com.cheng.core.Service;
import com.cheng.domain.LocalMsg;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.Request;
import com.cheng.domain.RequestType;
import com.cheng.domain.User;
import com.cheng.listener.ChatP2PTextListener;
import com.cheng.listener.ChatP2PVoiceListener;
import com.cheng.listener.ChatRoomListener;
import com.cheng.listener.LoginListener;

import com.cheng.listener.RegisterConfirmListener;
import com.cheng.listener.RegisterListener;
import com.cheng.listener.RequestListener;
import com.cheng.listener.ViewHandler;
import com.cheng.utils.LogUtils;
import com.cheng.utils.Properties;
import com.cheng.views.CodePanel;
import com.cheng.views.EntryFrame;
import com.cheng.views.LoginPanel;
import com.cheng.views.MainFrame;
import com.cheng.views.RegisterPanel;
import com.cheng.views.WaitPanel;

public class App implements ViewHandler {
	private ViewHandler voiceHandler = null;
	public static Capture capture = new Capture();
	public static IMConnection conn;
	private static String HOST;
	private static int PORT;
	public EntryFrame entryFrame;
	private MainFrame frame;
	public static User user;
	private static ViewHandler buddyHandler = null;
	private List<User> buddies = new ArrayList<User>();
	DefaultListModel<User> model = new DefaultListModel<User>();

	public static LoginListener loginListener = null;
	public static RegisterListener registerListener = null;
	public static RegisterConfirmListener registerConfirmListener = null;
	public static ChatP2PVoiceListener chatP2PVoiceListener = null;
	public static ChatP2PTextListener chatP2PTextListener = null;

	private LoginPanel loginPanel = new LoginPanel(400, 360);
	private RegisterPanel registerPanel = new RegisterPanel(400, 360);
	private CodePanel codePanel = new CodePanel(400, 360);
	private WaitPanel waitPanel = new WaitPanel(400, 360);

	public static DataOutputStream writer = null;
	public static DataInputStream reader = null;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("host");
		HOST = bundle.getString("HOST");
		PORT = new Integer(bundle.getString("PORT"));
	}

	public static void main(String[] args) {
		System.out.println("\u5ba2\u6237\u7aef\u542f\u52a8:" + new Date().toString());

		// Holder.APP.home();
		// Holder.APP.start("loginPanel");
		conntion();
		// 读取系统配置
		Properties properties = Properties.getInstance();
		boolean autoLogin = properties.getBoolean("autoLogin", false);
		if (autoLogin) {
			// 自动登录

			user = new User();
			String username = properties.getString("username", "");
			String ps = properties.getString("password", "");
			if ("".equals(username) || "".equals(ps)) {
				Holder.APP.start("loginPanel");

				return;
			}
			user.setId(new BigInteger(username));
			user.setUsername(new BigInteger(username));
			user.setPassword(ps);
			if (user != null) {
				Service service = new Service();
				try {
					service.login(user);
				} catch (IOException e) {
					e.printStackTrace();
					LogUtils.log("App", e.getStackTrace().toString());
				}

			} else {
				Holder.APP.start("waitPanel");
				// Holder.APP.start("loginPanel");
			}

		} else {
			// 不自动登录，进入登录界面
			Holder.APP.start("loginPanel");
		}

	}

	private static void conntion() {
		loginListener = new LoginListener();
		registerListener = new RegisterListener();
		registerConfirmListener = new RegisterConfirmListener();
		loginListener.setHandler(Holder.APP);
		registerListener.setHandler(Holder.APP);
		registerConfirmListener.setHandler(Holder.APP);
		// 连接服务器
		try {
			conn = new IMConnection(HOST, PORT);

			conn.connect();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			LogUtils.log("App", e.toString());
			System.err.println("无法连接服务器！");
			// System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.log("App", e.toString());
			System.err.println("无法连接服务器！");
			// System.exit(1);
		}

		conn.addOnRecevieMsgListener(loginListener);
		conn.addOnRecevieMsgListener(registerListener);
		conn.addOnRecevieMsgListener(registerConfirmListener);
	}

	public void start(final String name) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					entryFrame = new EntryFrame();
					entryFrame.getContentPane().add(loginPanel, "loginPanel");
					entryFrame.getContentPane().add(registerPanel, "registerPanel");
					entryFrame.getContentPane().add(codePanel, "codePanel");
					entryFrame.getContentPane().add(waitPanel, "waitPanel");
					EntryFrame.cardLayout.show(entryFrame.getContentPane(), name);
					entryFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void home() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainFrame();

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public List<User> getBuddies() {
		return buddies;
	}

	public void setBuddies(List<User> buddies) {
		Message msg = new Message();
		msg.obj = buddies;
		if (buddyHandler != null) {
			buddyHandler.handleMessage(msg);
		}

		this.buddies = buddies;
	}

	public DefaultListModel<User> getModel() {
		return model;
	}

	public DefaultListModel<User> initModel() {
		for (int i = 0; i < buddies.size(); i++) {
			model.addElement(buddies.get(i));
		}

		return model;
	}

	public void setModel(DefaultListModel<User> model) {
		this.model = model;
	}

	public static void setBuddyHandler(ViewHandler handler) {
		App.buddyHandler = handler;
	}

	private static class Holder {
		private static App APP = new App();
	}

	public static App getAplication() {
		return Holder.APP;

	}

	public void show(String name) {
		entryFrame.setVisible(true);
		EntryFrame.cardLayout.show(entryFrame.getContentPane(), name);
	}

	public void handleMessage(Message msg) {
		System.err.println("接收到：app"+msg.what );
		if (msg.what == MsgType.MSG_TYPE_LOGIN_SUCCESS) {
			System.err.println("接收到：登录成功");
			// 添加其他监听
			conn.addOnRecevieMsgListener(new ChatP2PTextListener());
			conn.addOnRecevieMsgListener(new ChatP2PVoiceListener());
			conn.addOnRecevieMsgListener(new ChatRoomListener());
			conn.addOnRecevieMsgListener(new RequestListener());
			// 登录成功进入主界面

			Holder.APP.home();
			entryFrame.setVisible(false);
		} else if (msg.what == MsgType.MSG_TYPE_LOGIN_FAILE) {
			System.err.println("接收到：登录失败");
			// 登录失败,返回登录界面
			EntryFrame.cardLayout.show(entryFrame.getContentPane(), "loginPanel");
			loginPanel.handleMessage(msg);
		} else if (msg.what == MsgType.MSG_TYPE_FRIEND_NOT_INLINE
				|| msg.what == MsgType.MSG_TYPE_CHAT_P2P_VOICE_FAILE) {
			if (frame != null) {
				frame.handleMessage(msg);
			}
		} else if (msg.what == MsgType.MSG_TYPE_REGISTER_CODE_SUCCESS) {
			System.err.println("接收到：注册验证成功");
			// 发送验证码成功,回到登录界面
			EntryFrame.cardLayout.show(entryFrame.getContentPane(), "codePanel");
			codePanel.handleMessage(msg);

		} else if (msg.what == MsgType.MSG_TYPE_REGISTER_CODE_FAILE) {
			System.err.println("接收到：登录成功");
			// 发送验证码失败，回到验证码界面
			EntryFrame.cardLayout.show(entryFrame.getContentPane(), "codePanel");
			codePanel.handleMessage(msg);
		} else if (msg.what == MsgType.MSG_TYPE_REGISTER_SUCCESS) {
			// 注册成功
			// user = (User) msg.obj;
			EntryFrame.cardLayout.show(entryFrame.getContentPane(), "codePanel");

		} else if (msg.what == MsgType.MSG_TYPE_REGISTER_FAILE) {
			// 注册失败,回到注册页面
			EntryFrame.cardLayout.show(entryFrame.getContentPane(), "registerPanel");
			registerPanel.handleMessage(msg);

		} else if (msg.what == MsgType.MSG_TYPE_REQUEST) {
			// 请求类
			Request request = (Request) msg.obj;
			if ((request.type == RequestType.REQUEST_P2P_VOICE_CONNECT
					|| request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_ACCEPT
					|| request.type == RequestType.REQUEST_P2P_VOICE_CONNECT_NOT_ACCEPT
					|| request.type == RequestType.REQUEST_P2P_VOICE_START
					|| request.type == RequestType.REQUEST_P2P_VOICE_STOP
					|| request.type == RequestType.REQUEST_ADD_BUDDTY
					|| request.type == RequestType.REQUEST_ADD_BUDDTY_FAILE
					|| request.type == RequestType.REQUEST_ADD_BUDDTY_ACCEPT
					|| request.type == RequestType.REQUEST_ADD_BUDDTY_NOT_ACCEPT
					|| request.type == RequestType.REQUEST_ADD_BUDDTY_SUCCESS)) {

				// 发送给主界面
				if (frame != null) {
					frame.handleMessage(msg);
				}

			} 

		}else if (msg.what == LocalMsg.MSG_TYPE_CHANGE_TO_CHAT || msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT
				|| msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT_UNREAD
				|| msg.what == MsgType.MSG_TYPE_CHAT_P2P_TEXT_READ) {
			System.out.println("App接收");
			if (frame != null) {
				frame.handleMessage(msg);
			}

		}

	}

	public ViewHandler getVoiceHandler() {
		return voiceHandler;
	}

	public void setVoiceHandler(ViewHandler voiceHandler) {
		this.voiceHandler = voiceHandler;
	}
}
