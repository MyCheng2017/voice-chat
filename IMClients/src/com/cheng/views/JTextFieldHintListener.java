package com.cheng.views;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JTextFieldHintListener implements FocusListener {
	private String hintText = "";
	Color focusGained = Color.BLACK;
	Color focusLost = Color.GRAY;

	public JTextFieldHintListener(String hintText, Color focusGained, Color focusLost) {
		if (hintText != null) {
			this.hintText = hintText;
		}
		if (focusGained != null) {
			this.focusGained = focusGained;
		}
		if (focusLost != null) {
			this.focusLost = focusLost;
		}

	}

	@SuppressWarnings("deprecation")
	public void focusGained(FocusEvent e) {
		Object source = e.getSource();
		if (source instanceof JPasswordField) {
			JPasswordField field = (JPasswordField) source;
			field.setEchoChar('*');
			if (hintText.equals(field.getText() )) {
				field.setText("");
				field.setForeground(focusGained);
			} else {
				field.setForeground(focusGained);
			}
			return;
		}
		if (source instanceof JTextField) {
			JTextField field = (JTextField) source;
			
			if (hintText.equals(field.getText())) {
				field.setText("");
				field.setForeground(focusGained);
			} else {
				field.setForeground(focusGained);
			}
		}

	}

	@SuppressWarnings("deprecation")
	public void focusLost(FocusEvent e) {
		Object source = e.getSource();
		
		if (source instanceof JPasswordField) {
			JPasswordField field = (JPasswordField) source;
		
			if (hintText.equals(field.getText())||"".equals(field.getText())) {
				field.setEchoChar((char) 0);
				field.setText(hintText);
				
				field.setForeground(focusLost);
			} else {
				field.setForeground(focusLost);
			}
			return;
		}
		if (source instanceof JTextField) {
			JTextField field = (JTextField) source;
			if (hintText.equals(field.getText())||"".equals(field.getText())) {
				field.setText(hintText);
				
				field.setForeground(focusLost);
			} else {
				field.setForeground(focusLost);
			}
		}

	}

}
