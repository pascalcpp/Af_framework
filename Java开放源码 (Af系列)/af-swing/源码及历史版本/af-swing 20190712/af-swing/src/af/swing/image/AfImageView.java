package af.swing.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import af.swing.style.AfBorderStyle;

/* Swing入门篇 10.4节 */

/*
 * 2019-01-27: 最后优化，主要是支持透明的PNG的绘制
 */

public class AfImageView extends JPanel
{
	// 缩放类型 (也可以用枚举语法 Enum 来定义)
	public static final int FIT_XY = 0;
	public static final int FIT_CENTER = 1;
	public static final int FIT_CENTER_INSIDE = 2;
	
	public int scaleType = FIT_CENTER_INSIDE;
	public int radius = 0;
	public int margin = 2;
	public int padding = 2;
	public Style normal = new Style();
	public Style hover = new Style();
	
	private boolean isHover = false;
	
	public AfImageView()
	{
		this.setOpaque(false);
		this.enableEvents(MouseEvent.MOUSE_EVENT_MASK);
	}
	
	public Image getImage()
	{
		return normal.image;
	}

	public void setImage(Image image)
	{
		normal.image = image;
		repaint();
	}
		
	@Override
	protected void paintComponent(Graphics g)
	{
		// 先调用父类的 paintComponent(), 绘制必要的边框和背景
		super.paintComponent(g);
		
		// 本控件的宽度和高度
		int w = this.getWidth();
		int h = this.getHeight();
		Rectangle rect = new Rectangle(w,h);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		// 边距
		rect.grow(-margin, -margin);
		
		// 获取绘制参数
		Image image = normal.image;
		Color bgColor = normal.bgColor;
		AfBorderStyle border = normal.border;
		
		if(isHover)
		{
			// 背景色
			bgColor = hover.bgColor;
			if(bgColor == null) 
				bgColor = normal.bgColor;
			// 边框
			border = hover.border;
			if(border == null)
				border=normal.border;
			// 图片
			image = hover.image;
			if(image == null)
				image = normal.image;
		}
		
		// 绘制背景及边框
		Shape bg = new RoundRectangle2D.Double(rect.x, rect.y,
				rect.width, rect.height, radius, radius);		
		if(bgColor != null)
		{
			g2d.setPaint(bgColor);
			g2d.fill(bg);
		}
		if(border != null && border.color != null)
		{
			g2d.setPaint(border.color);
			g2d.draw(bg);
		}
		
		/////////////////////////
		if(image != null)
		{
			int imgW = image.getWidth(null);
			int imgH = image.getHeight(null);
						
			Rectangle r = new Rectangle(rect);
			r.grow(-padding, -padding);
			
			// 使用  AfImageScaler 来计算
			AfImageScaler scaler = new AfImageScaler(imgW, imgH, r.width,r.height);
			
			// 根据缩放类型，来计算目标区域
			Rectangle fit = scaler.fitXY();
			if(scaleType == FIT_CENTER)
				fit = scaler.fitCenter();
			else if(scaleType == FIT_CENTER_INSIDE)
				fit = scaler.fitCenterInside();

			// 绘制
			g.drawImage(image, r.x+fit.x, r.y+fit.y, fit.width, fit.height,	null);
		}
	}

	public static class Style
	{
		public AfBorderStyle border;
		public Color bgColor;
		public Image image;
	}
	
	@Override
	protected void processMouseEvent(MouseEvent e)
	{		
		//System.out.println("processMouseEvent:" + e.getPoint());		
		int eventID = e.getID();		
		// 鼠标移入, 进入hover状态
		if(eventID == MouseEvent.MOUSE_ENTERED)
		{
			isHover = true;
			repaint();
			return;
		}		
		// 鼠标移出, 退出hover状态
		if(eventID == MouseEvent.MOUSE_EXITED)
		{
			isHover = false;
			repaint();
			return;
		}
		
		super.processMouseEvent(e);
	}
}
