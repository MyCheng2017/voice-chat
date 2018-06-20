package com.cheng.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.cheng.domain.Message;
import com.cheng.listener.ViewHandler;
import com.sun.awt.AWTUtilities;

public class EntryFrame extends JFrame implements MouseListener ,ViewHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static int mx, my, jmx, jmy;
	public static CardLayout cardLayout = new CardLayout();
	private int width = 404;
	private int height = 344;
	public static EntryFrame frame;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntryFrame frame = new EntryFrame();
					JFrame.setDefaultLookAndFeelDecorated(true);
					AWTUtilities.setWindowShape(frame,
							new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(), frame.getHeight(), 5.0D, 5.0D));

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
	public EntryFrame() {
		
		// 设置窗口可拖动
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

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2 - 50, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(245, 245, 245), 1, true));
		setContentPane(contentPane);
		 
		contentPane.setLayout(cardLayout);
		
		/*WaitPanel waitPanel = new WaitPanel(400, 340);
		waitPanel.setBorder(null);
		contentPane.add(waitPanel, "waitPanel");

		codePanel = new CodePanel(400, 340);
		codePanel.setBorder(null);
		contentPane.add(codePanel, "codePanel");
		loginPanel = new LoginPanel(400, 340);
		loginPanel.setBorder(null);
		contentPane.add(loginPanel, "loginPanel");
		registerPanel = new RegisterPanel(400, 340);
		registerPanel.setBorder(null);
		contentPane.add(registerPanel, "registerPanel");*/

	}

	public void mouseClicked(MouseEvent e) {
		
		
		

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



	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
