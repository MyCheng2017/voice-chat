package com.cheng.listener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;

import com.cheng.core.MessageSender;
import com.cheng.domain.IMMessage;
import com.cheng.domain.Message;
import com.cheng.domain.MsgType;
import com.cheng.domain.User;
import com.cheng.main.App;
import com.cheng.utils.ChatLog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class LoginListener extends MessageSender implements OnRecevieListener {
	private ViewHandler handler = App.getAplication();

	@SuppressWarnings("null")
	public void onReceive(byte[] b) {
		if (checkByte(b)) {
			if (b[3] == MsgType.MSG_TYPE_LOGIN_SUCCESS) {
				System.err.println("接收到：登录成功");
				Gson gson = new Gson();

				User u = null;
				try {
					u = gson.fromJson(new String(b, 5, b[1] * 100 + b[2],"utf-8"), User.class);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				App.user = u;
				Message msg = new Message();
				msg.what = b[3];
				msg.msg = "登录成功！";

				handler.handleMessage(msg);
			} else if (b[3] == MsgType.MSG_TYPE_LOGIN_FAILE) {
				System.err.println("接收到：登录失败");
				Message msg = new Message();
				msg.what = b[3];
				msg.msg = new String(b, 5, b[1] * 100 + b[2]);
				handler.handleMessage(msg);
			} else if (b[3] == MsgType.MSG_TYPE_LOGIN_SUCCESS_DATA_BUDDY) {
				ArrayList<User> users = new ArrayList<User>();
				try {
					users = fromJsonList(new String(b, 5, b[1] * 100 + b[2],"utf-8"), User.class);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (users == null || users.size() <= 0) {
					App.getAplication().setBuddies(new ArrayList<User>());
				} else {
					App.getAplication().setBuddies(users);
				}

			} else if (b[3] == MsgType.MSG_TYPE_LOGIN_SUCCESS_DATA_MESSAGE) {System.out.println("接收未读消息");
				ArrayList<IMMessage> messages = new ArrayList<IMMessage>();
				messages = fromJsonList(new String(b, 5, b[1] * 100 + b[2]), IMMessage.class);
				if (messages != null) {
					HashMap<Long, Integer> map = new HashMap<Long, Integer>();

					for (IMMessage imMessage : messages) {
						try {
							ChatLog.insertFromUser(imMessage);
							if (map.containsKey(imMessage.fromUser)) {
								map.put(imMessage.fromUser, map.get(imMessage.fromUser) + 1);
							} else {
								map.put(imMessage.fromUser, 1);
							}
						} catch (IOException e) {
							e.printStackTrace();
							continue;
						}
					}
					DefaultListModel<User> users = App.getAplication().getModel();
					if (users != null||users.size()>0) {
						int len = users.size();
						for (int i = 0; i < len; i++) {
							if (map.containsKey(users.get(i).getId().longValue())) {
								App.getAplication().getModel().get(i)
										.setIs_actived(map.get(users.get(i).getId().longValue()));
							}
						}
					}

				}

			}
		}

	}

	public <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
		Gson mGson = new Gson();
		ArrayList<T> mList = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			mList.add(mGson.fromJson(elem, cls));
		}
		return mList;
	}

	public void setHandler(ViewHandler handler) {
		this.handler = handler;
	}
}