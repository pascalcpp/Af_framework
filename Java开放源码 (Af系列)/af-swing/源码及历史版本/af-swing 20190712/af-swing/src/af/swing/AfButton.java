package af.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import af.swing.image.AfImageScaler;
import af.swing.style.AfBorderStyle;
import af.swing.style.AfFontStyle;

public class AfButton extends JPanel
{	
	//
	public Style normal = new Style();
	public Style hover = new Style();
	public int margin = 2;
	public int radius = 10;
	public int padding = 4;
	
	//
	private String text;
	private boolean isHover = false;
	private ActionListener clickListener;
	
	public AfButton(String text)
	{
		this.text = text;
		
		setOpaque(false);		
		enableEvents(MouseEvent.MOUSE_EVENT_MASK );
	
		// 默认颜色
		normal.bgColor = new Color(0xFDFDFD);
		normal.textColor = new Color(0x303030);
		normal.border = new AfBorderStyle(new Color(0xC0C0C0) );
		normal.font = new Font("微软雅黑", Font.PLAIN, 14);
		
		hover.bgColor = new Color(0xFFFFFF);
		hover.textColor = new Color(0x708090);
		hover.border = new AfBorderStyle(new Color(0x708090) );
	}
	
	public AfButton()
	{
		this("");
	}
	
	public static class Style
	{
		public AfBorderStyle border;
		public Color bgColor;
		public Font font;
		public Color textColor;
	}
	
	public void setText(String text)
	{
		this.text = text;
		repaint();
	}
	
	public String getText()
	{
		return this.text;
	}

	public void addActionListener(ActionListener l)
	{
		this.clickListener = l;
	}
	
	
	////////////////////////
	@Override
	public Dimension getPreferredSize()
	{
		// 
		FontMetrics fm = this.getFontMetrics(this.normal.font); 
		int fontSize = fm.getHeight(); // 字高
		int textWidth = fm.stringWidth(text);
//		int leading = fm.getLeading();
//		int descent = fm.getDescent(); 
		int w = textWidth + padding*2 + margin*2 + 4;
		int h = fontSize + padding*2 + margin*2 + 4;
		return new Dimension(w,h);
	}
	
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
	
		rect.grow(-margin, -margin);
		
		// 准备绘制参数
		Color bgColor = normal.bgColor;
		AfBorderStyle border = normal.border;
		Font font = normal.font;
		Color textColor = normal.textColor;
				
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
			// 字体
			font = hover.font;
			if(font == null)
				font = normal.font;
			// 字体颜色
			textColor = hover.textColor;
			if(textColor == null)
				textColor = normal.textColor;
		}
		
		rect.grow(-1, -1);
		Shape bg = new RoundRectangle2D.Double(rect.x, rect.y,
				rect.width, rect.height, radius, radius);
		if(bgColor != null)
		{
			g2d.setPaint( bgColor);
			g2d.fill(bg);
		}
		if(border != null)
		{
			g2d.setStroke(new BasicStroke(1.2f));
			if(border.color != null) g2d.setPaint(border.color);
			g2d.draw(bg);
		}
		
		if(text != null && font!=null)
		{
			Rectangle r = new Rectangle(rect);
			r.grow(-padding, -padding);
			//System.out.println("1: " + r);
			// 
			FontMetrics fm = g2d.getFontMetrics(font); 
			int fontSize = fm.getHeight(); // 字高
			int textWidth = fm.stringWidth(text);
			int leading = fm.getLeading();
			int descent = fm.getDescent(); // bottom->baseline 的高度
			//
			int x = 0, y = 0;
			x = r.x + (r.width - textWidth)/2; // 水平居中			
			y = r.y + r.height /2 + (fontSize-leading)/2 - descent; // 竖直居中
			
			//System.out.println("textWidth: " + textWidth);
			//System.out.println("x: " + x);
			
			if(font != null) g2d.setFont(font);
			if(textColor != null)g2d.setPaint(textColor);
			g2d.drawString(text, x, y); 
		}
	}
	


	@Override
	protected void processMouseEvent(MouseEvent e)
	{		
		//System.out.println("processMouseEvent:" + e.getPoint());
		
		int eventID = e.getID();
		
		// 鼠标点击, 调用监听器
		if(eventID == MouseEvent.MOUSE_RELEASED)
		{
			// 判断松手时的落点
			Rectangle r = new Rectangle(getWidth(),getHeight());
			if(! r.contains(e.getPoint()))
				return;
			
			if(clickListener != null)
			{
				ActionEvent ae = new ActionEvent(this, eventID, "clicked");
				clickListener.actionPerformed(ae);
			}
		}
		
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
		
		//super.processMouseEvent(e);
	}
	

}
