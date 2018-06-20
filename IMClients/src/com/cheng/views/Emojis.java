package com.cheng.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

import com.cheng.domain.LocalMsg;
import com.cheng.domain.Message;
import com.cheng.listener.ViewHandler;

public class Emojis extends JDialog implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	ViewHandler handler = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Emojis dialog = new Emojis(200, 200, null);
		dialog.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public Emojis(int x, int y, ViewHandler handler) {
		this.handler = handler;
		setUndecorated(true);
		setAlwaysOnTop(true);
		setBounds(x, y, 450, 280);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel
				.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(245, 245, 245), new Color(240, 240, 240)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			for (int i = 0; i < 135; i++) {
				JLabel label = new JLabel();
				label.setText("<html><img src=\"file:emojis/" + i + ".gif\"/></html>");
				label.addMouseListener(this);
				contentPanel.add(label);
			}

		}
	}

	public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		if (handler != null) {
			Message msg = new Message();
			msg.what = LocalMsg.MSG_TYPE_GET_EMOJIS;
			msg.msg = label.getText().replace("<html>","").replace("</html>", "");
			//System.out.println(msg.msg);
			handler.handleMessage(msg);
		}
		//dispose();
		//setVisible(false);

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
