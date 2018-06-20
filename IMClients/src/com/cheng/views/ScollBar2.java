package com.cheng.views;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScollBar2 extends BasicScrollBarUI {

	@Override
	protected void configureScrollBarColors() {
		scrollBarWidth=0;
		 
		//trackColor = new Color(255, 255, 255);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		super.paintThumb(g, c, thumbBounds);
		// �ѻ�������x��y�����궨��Ϊ����ϵ��ԭ��
		// ���һ��һ��Ҫ���ϰ�����Ȼ�϶���ʧЧ��
		g.translate(thumbBounds.x, thumbBounds.y);
		// ���ð�����ɫ
		g.setColor(new Color(255, 255, 255));
		// ��һ��Բ�Ǿ���
		// ������ǰ�ĸ������Ͳ��ི�ˣ�����Ϳ��
		// ������������Ҫע��һ�£����������ƽ����Բ�ǻ���
		g.drawRoundRect(5, 0, 6, thumbBounds.height - 1, 5, 5);
		// �������
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// ��͸��
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		// ���Բ�Ǿ���
		g2.fillRoundRect(5, 0, 6, thumbBounds.height - 1, 5, 5);

	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}

}
