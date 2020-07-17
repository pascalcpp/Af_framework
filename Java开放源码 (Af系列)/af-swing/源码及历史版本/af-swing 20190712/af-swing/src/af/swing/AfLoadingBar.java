package af.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.Timer;

/* Swing高级篇, 10.5讲
 * 
 */
public class AfLoadingBar extends JPanel
{
	// 外部可以直接设置的参数
	public Color bgColor = new Color(0xFFFFFF);
	public Color fgColor = new Color(0xDCE7F5); // 主色
	public int padding = 2; // 边距
	public double speed = 0.15; // 控制转动速度 0.1-0.5
	
	// 内部属性
	private Timer timer; // 定时器
	private int offset = 0;
	
	public AfLoadingBar()
	{
		this.setOpaque(false);
		// 设置最佳尺寸（主要指高度),一般宽度由外部设置
		setPreferredSize(new Dimension(100,20));
	}
	
	// 启动定时器
	public void startAnimation()
	{
		if(timer != null) return;
		
		timer = new Timer(100, (e)->{
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

		// 边框及底色
		Rectangle rect = new Rectangle(0,0,width, height);
		rect.grow(-padding, -padding);
		g2d.setPaint(bgColor);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
		g2d.setPaint(fgColor);
		g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		
		double unit = 20; // 每一小格的宽度
		double halfUnit = unit *0.6; // 亮色宽度		
		offset +=  unit * speed; // 动画因子：调整起始位移，产生动画效果
		if(offset > unit) offset = 0;
		
		int bx = rect.x;
		int by = rect.y;
		for(double x=0-offset; x<width; x+= unit)
		{
			// 斜平行四边形
			Path2D path = new Path2D.Double();			
			path.moveTo(bx + x, by + 0);
			path.lineTo(bx + x + halfUnit, by + 0);
			path.lineTo(bx + x, by + rect.height);
			path.lineTo(bx + x - halfUnit, by + rect.height);
			path.closePath();
			
			g2d.setPaint(fgColor);
			g2d.fill(path);
		}
	}
	

}
