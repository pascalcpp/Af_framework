package af.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.Timer;

/* Swing高级篇, 10.5讲
 * 
 */

public class AfLoadingIcon extends JPanel
{
	public Color bgColor = new Color(0,0,0,0);
	public Color lineColor = new Color(0x66, 0x66, 0x66, 200);
	public int step = 5; // 每次转动的角度，可以控制转动速度
	public int maxRadius = 64; // 最大
	
	private Timer timer; // 定时器
	private int startAngle = 0;
	
	public AfLoadingIcon()
	{
	}
	
	// 启动定时器
	public void startAnimation()
	{
		if(timer != null) return;
		
		timer = new Timer(100, (e)->{
			// 通过改变角度，产生轮转效果
			startAngle += step;
			if(startAngle >=360) startAngle = 0;
			repaint(); 
		} );	
		
		timer.start();
	}
	
	// 停止定时器
	public void stopAnimation()
	{
		if(timer != null)
		{
			timer.stop();
			timer = null;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, width, height);
		
		// 平滑绘制 （ 反锯齿 )
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setPaint(bgColor);
		g2d.fillRect(0, 0, width, height);
				
		// 取得一个最大的正文形
		//Rectangle rect = new Rectangle(0,0,200,200);
		int w = width;
		int h = width;
		if( h > height)
		{
			h = height;
			w = height;
		}
		Rectangle rect = new Rectangle((width-w)/2, (height-h)/2, w, h);		
		rect.grow(-4,-4); // 往里缩一点
		
		// 中心点及半径
		int centerX = (int)rect.getCenterX();
		int centerY = (int)rect.getCenterY();
		int radius = (int)rect.width / 2;
		if(radius > maxRadius) radius = maxRadius; // 最大半径32
				
		// 辐条的长度
		int size = (int)(radius * 0.9);
		if(size <2) size = 2;
		if(size >4) size = 4;
		
		// 绘制辐条
		g2d.setPaint(lineColor);
		g2d.setStroke(new BasicStroke(6));		
		for(double angle=0; angle<360; angle += 30)
		{			
			double a = angle + startAngle; // 转动因素在此
			drawRadialLine(g2d,	centerX, centerY, radius, radius-size, a);
		}		
		
		// 绘制圆圈
		if(true)
		{
			int r = radius - size - 4;
			Shape shape = new Ellipse2D.Double(centerX-r, centerY-r, r*2, r*2);
			g2d.setPaint(bgColor);			
			g2d.fill(shape);
			g2d.setStroke(new BasicStroke(4));
			g2d.setPaint(lineColor);			
			g2d.draw(shape);
		}
		
	}
	
	// 绘制从圆心发散出的辐条线条，中心centerX, centerY, 角度angle
	private void drawRadialLine(Graphics2D g2d,
			double centerX, double centerY,
			double r1, double r2, double angle)
	{
		double radian = angle / 180 * Math.PI; // 角度转成弧度
		
		double x1 = centerX + r1 * Math.cos( radian );
		double y1 = centerY + r1 * Math.sin( radian );		
		double x2 = centerX + r2 * Math.cos( radian );
		double y2 = centerY + r2 * Math.sin( radian );	
		
		Line2D line = new Line2D.Double(x1,y1, x2, y2);
		g2d.draw( line );
	}
}
