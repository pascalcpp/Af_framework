package af.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import af.swing.popup.AfPopupPanel;

/* Swing高级篇, 16.7
 * 
 */
public class AfColorPicker extends AfPopupPanel
{
	// 8x5 的阵型
	int[][] colorTable = {
			{0x000000 ,	0xA52A00, 0x404040, 0x008000, 0x00005E, 0x000080,0x4B0082,0x282828 },
			{0x800000 ,	0xFF6820, 0x808000, 0x009300, 0xECF1FA, 0x0000FF,0x7B7BC0,0x404040 },
			{0xFF0000 ,	0xFFAD5B, 0x32CD32, 0x3CB371, 0x00FFFF, 0x404040,0x800080,0x808080 },
			{0x404040 ,	0xFFD700, 0xFFFF00, 0x00FF00, 0x40E0D0, 0x80FFFF,0xFF00FF,0xC0C0C0 },
			{0xFFE4E1 ,	0xD2B48C, 0xFFFF80, 0x80FF80, 0xAFEEEE, 0x68838B,0xE6E6FA,0xFFFFFF },
	};
	final int ROWS = 5; // 行数
	final int COLS = 8; // 列数
	Color[][] colors = new Color[ROWS][COLS];
	
	int unit = 30; // 单元格大小
	int padding = 4;
	Rectangle[][] cells = new Rectangle[ROWS][COLS];
	
	private Point cursorPos = new Point(0,0); // 当前鼠标位置
	
	public AfColorPicker()
	{
		// 初始化颜色
		for(int i =0; i<ROWS; i++)
		{
			for(int k=0; k<COLS; k++)
			{
				colors[i][k] = new Color(colorTable[i][k]);
			}
		}
		
		// 鼠标事件处理，也可以使用 addXXXListener()方式来实现
		this.enableEvents(MouseEvent.MOUSE_EVENT_MASK);
		this.enableEvents(MouseEvent.MOUSE_MOTION_EVENT_MASK);
	}
	
	// 计算窗口所需的大小 
	@Override
	public Dimension getPreferredSize()
	{
		int width = unit* COLS + padding * 2;
		int height = unit* ROWS + padding * 2;
		return new Dimension(width, height);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new Color(0x7A8A99));
		g2d.fillRect(0, 0, width, height);
		
		Rectangle rect = new Rectangle(width, height);
		rect.grow(-padding, -padding);
		
		for(int i =0; i<ROWS; i++)
		{
			for(int k=0; k<COLS; k++)
			{
				// 计算出每一个小单元格 
				// TODO: 这个计算可以优化，没必要每次都重新计算一次
				Color color = colors[i][k] ;
				int x = unit * k + padding;
				int y = unit * i + padding;
				Rectangle cell =  new Rectangle(x,y,unit, unit);
				cells[i][k] = cell;
				cell.grow(-1,-1);
				
				// 绘制颜色方格，填充为彩色
				g2d.setPaint(color);
				g2d.fill(cell);
				
				// 单元格边框
				Color lineColor = Color.white;
				if(cell.contains(this.cursorPos))
					lineColor = new Color(0x7A8A99); // 高亮的边框色
				
				g2d.setStroke(new BasicStroke(2));
				g2d.setPaint(lineColor);
				g2d.draw(cell);
			}
		}
	}

	@Override
	protected void processMouseEvent(MouseEvent e)
	{
		if(e.getID()== MouseEvent.MOUSE_PRESSED)
		{
			for(int i =0; i<ROWS; i++)
			{
				for(int k=0; k<COLS; k++)
				{
					Rectangle cell =  cells[i][k];
					
					// 如果鼠标点击的位置在这个单元格内
					if(cell.contains(e.getPoint()))
					{
						Color color = colors[i][k] ;
						if(this.selectListener != null)
						{
							// 调用监听器
							System.out.println("选中了: " + color);
							selectListener.colorSelected(this,color);
						}
					}
				}
			}
		}
		super.processMouseEvent(e);
	}
	
	@Override
	protected void processMouseMotionEvent(MouseEvent e)
	{
		if(e.getID()== MouseEvent.MOUSE_MOVED)
		{
			this.cursorPos = e.getPoint();
			repaint();
		}
		
		super.processMouseMotionEvent(e);
	}
	
	
	/////////// 监听器设置 /////////
	public interface SelectListener
	{
		void colorSelected(AfColorPicker picker, Color color);
	}
	public SelectListener selectListener;
	
	public void setSelectListener( SelectListener listener)
	{
		this.selectListener = listener;
	}


}
