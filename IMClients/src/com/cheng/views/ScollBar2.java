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
		// 把绘制区的x，y点坐标定义为坐标系的原点
		// 这句一定一定要加上啊，不然拖动就失效了
		g.translate(thumbBounds.x, thumbBounds.y);
		// 设置把手颜色
		g.setColor(new Color(255, 255, 255));
		// 画一个圆角矩形
		// 这里面前四个参数就不多讲了，坐标和宽高
		// 后两个参数需要注意一下，是用来控制角落的圆角弧度
		g.drawRoundRect(5, 0, 6, thumbBounds.height - 1, 5, 5);
		// 消除锯齿
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// 半透明
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		// 填充圆角矩形
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
