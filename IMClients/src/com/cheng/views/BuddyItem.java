package com.cheng.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.cheng.domain.User;

public class BuddyItem extends JPanel implements ListCellRenderer<Object>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel buddyHead;
	private JLabel buddyShuoshuo;
	private JLabel buddyNickname;
	private JLabel buddyTime;
	private int height = 60;
	private int width = 250;

	/**
	 * Create the panel.
	 */
	public BuddyItem() {
		setBounds(0, 0, width, height);
		setLayout(null);

		buddyHead = new JLabel("");
		buddyHead.setIcon(new ImageIcon("C:\\Users\\ChengAcer\\Desktop\\\u6BD5\u8BBE\\icon\\head.jpeg"));
		buddyHead.setBounds(10, 10, 40, 40);
		add(buddyHead);

		buddyNickname = new JLabel("\u5C18\u5C01\u4F60\u597D");
		buddyNickname.setFont(new Font("华文细黑", Font.PLAIN, 15));
		buddyNickname.setBounds(64, 10, 130, 21);
		add(buddyNickname);

		buddyShuoshuo = new JLabel("\u968F\u98CE\u98D8\u626C...");
		buddyShuoshuo.setFont(new Font("华文细黑", Font.PLAIN, 12));
		buddyShuoshuo.setForeground(Color.GRAY);
		buddyShuoshuo.setBounds(64, 37, 180, 15);
		add(buddyShuoshuo);

		buddyTime = new JLabel("13:25", JLabel.CENTER);
		buddyTime.setFont(new Font("华文细黑", Font.PLAIN, 12));
		buddyTime.setForeground(Color.GRAY);
		buddyTime.setBounds(200, 14, 46, 15);
		add(buddyTime);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Capture/Playback");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", new BuddyItem());
		f.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 720;
		int h = 340;
		f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
		f.setSize(w, h);
		f.setVisible(true);
	}

	public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		 if (value instanceof User) {  
	             User user=(User) value;
	              
	                ImageIcon icon = new ImageIcon(user.getAvatar());  
	                this. buddyHead.setIcon(icon);
	                this. buddyNickname.setText(user.getNickname());
	              
	            System.err.println(user.getNickname());
	  
	        }  
	 this.setBackground(Color.CYAN);
	 this.setBounds(0, 0, 250, 160);
		return (Component) value;
	}
}
