package com.cheng.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Date;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.cheng.domain.IMMessage;
import com.cheng.domain.User;
import com.cheng.main.App;

public class ImageCellRender extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		setFont(new Font("»ªÎÄÏ¸ºÚ", Font.PLAIN, 15));

		if (value instanceof User) {
			setBorder(new EmptyBorder(10, 15, 10, 10));
			
			//setBackground(new Color(230, 230, 230));
			User user = (User) value;
			ImageIcon icon = new ImageIcon("icon/head/" + user.getAvatar() + ".jpeg");
			setIcon(icon);
			if (user.getIs_actived() == 0) {
				setText("<html><font>" + user.getNickname() + "</font><br/><font color='#cfcfcf'  size=3 >"
						+ user.getId() + "</font></html>");
			} else {
				setText("<html><font>" + user.getNickname()
						+ "</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'  size=3>Î´¶Á£º"
						+ user.getIs_actived() + "</font><br/><font color='#cfcfcf'  size=3 >" + user.getId()
						+ "</font></html>");
			}
			setIconTextGap(10);

		} else if (value instanceof IMMessage) {
			setVerticalTextPosition(SwingConstants.TOP);
			setVerticalTextPosition(SwingConstants.TOP);
			setBorder(new EmptyBorder(10, 15, 10, 15));
			setBackground(new Color(245, 245, 245));
			IMMessage message = (IMMessage) value;

			if (App.user != null && message.fromUser == App.user.getId().longValue()) {
				setHorizontalAlignment(SwingConstants.RIGHT);
				setHorizontalTextPosition(SwingConstants.LEFT);
				Date date = new Date(message.createTime);
				StringBuffer buffer = new StringBuffer();

				buffer.append(
						"<html><head></head><body ><p style=\"margin-top: 0; color:blue;font-size:9px;text-align: right;\">");

				buffer.append(date.getHours());
				buffer.append(":");
				buffer.append(date.getMinutes());
				buffer.append(":");
				buffer.append(date.getSeconds());
				buffer.append(
						"&nbsp;&nbsp;&nbsp;ÎÒ</p>");
				buffer.append(message.content);
				buffer.append("</body></html>");
				setText(buffer.toString());
			//	 System.out.println(buffer.toString());
			} else {
				setHorizontalAlignment(SwingConstants.LEFT);
				setHorizontalTextPosition(SwingConstants.RIGHT);
				Date date = new Date(message.createTime);
				StringBuffer buffer = new StringBuffer();
				buffer.append(
						"<html><head></head><body ><p style=\"margin-top: 0; color:blue;font-size:9px;text-align: left;\">");
				buffer.append(message.fromNick);
				buffer.append("&nbsp;&nbsp;&nbsp;");

				buffer.append((date.getMonth() + 1));
				buffer.append("-");
				buffer.append(date.getDate());
				buffer.append(" ");
				buffer.append(date.getHours());
				buffer.append(":");
				buffer.append(date.getMinutes());
				buffer.append(":");
				buffer.append(date.getSeconds());
				buffer.append("</p>");
				buffer.append(message.content);
				buffer.append("</body></html>");
				setText(buffer.toString());
				// System.out.println(buffer.toString());
			}

			ImageIcon icon = new ImageIcon("icon/head/" + message.fromAvatar + ".jpeg");
			setIcon(icon);

			setIconTextGap(10);

		}

		return this;
	}

}
