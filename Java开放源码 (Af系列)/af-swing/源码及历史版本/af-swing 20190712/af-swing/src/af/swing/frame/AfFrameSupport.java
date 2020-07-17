package af.swing.frame;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class AfFrameSupport extends MouseAdapter
{
	// 关联支持的控件
	public Component comp;
	public boolean canMove = true;
	public boolean canResize = false;

	//
	private boolean pressed = false; // 鼠标左键按下
	private boolean resizingX ;  // 是否 X方向正在 resizing
	private boolean resizingY ;  // 是否 Y方向正在 resizing
	public int frameWidth = 4; // 边框宽度 ( 用于拖动改变大小的边缘区域的宽度 )
	public int minWidth = 10; // 调整窗口大小时，窗口的最小宽度
	public int minHeight = 10;
	
	private Point startMousePos ; // 鼠标按下时、鼠标的初始位置
	private Point startWinPos ;// 鼠标按下时、窗口的初始位置
	private Dimension startWinSize; // 鼠标按下时、窗口的初始大小
	
	// comp : 监听的目标控件 
	// canMove: 支持窗口移动
	// canResize: 支持窗口改变大小
	public AfFrameSupport(Component comp, boolean canMove, boolean canResize)
	{
		this.comp  = comp;
		this.canMove = canMove;
		this.canResize = canResize;
		
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
	}

	// 获取该控件所在的顶层窗口
	private Window getWindow()
	{
		return SwingUtilities.windowForComponent(comp);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			//
			pressed = true;
			resizingX = isNearRight(e.getX());
			resizingY = isNearBottom(e.getY());
			
			// 获取鼠标的初始位置和大小
			startMousePos = e.getLocationOnScreen();
			
			// 获取窗口的初始位置和大小
			Window w = getWindow();
			startWinPos = w.getLocation();
			startWinSize = w.getSize();
		}		
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		pressed = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{	
		if(pressed) // pressed为true，表示当前鼠标左键被按下
		{
			// 根据鼠标位置，计算鼠标的位移
			Point pos = e.getLocationOnScreen();
			int dx = pos.x - startMousePos.x;
			int dy = pos.y - startMousePos.y;
				
			// 移动窗口位置
			Window w = getWindow();			
			
			// 改变大小，或者移动位置
			if(canResize && resizingX && resizingY)
				setWindowSize(w, startWinSize.width + dx, startWinSize.height + dy);
			else if(canResize && resizingX)
				setWindowSize(w,  startWinSize.width + dx , startWinSize.height);
			else if(canResize && resizingY)
				setWindowSize(w,  startWinSize.width, startWinSize.height + dy);
			else if(canMove)
				w.setLocation(startWinPos.x + dx, startWinPos.y + dy);	
		
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		// 判断是否允许改变大小
		if(! canResize ) return ;
		
		// 当鼠标到达右边缘、下边缘时，设置鼠标形状
		boolean isX = isNearRight(e.getX());
		boolean isY = isNearBottom(e.getY());
		
		if( isX && isY)
			comp.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR)); // 水平 & 竖直
		else if(isX)
			comp.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));  // 仅水平改变大小
		else if(isY)
			comp.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));  // 仅竖直改变大小
		else
			comp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));		
	}	
	
	// 判断鼠标是否在右边缘位置
	private boolean isNearRight (double x)
	{
		double w = getWindow().getBounds().getWidth();
		return x >= w- frameWidth  && x < w;
	}
	
	// 判断鼠标是否在下边缘位置
	private boolean isNearBottom ( double y)
	{
		double h = getWindow().getBounds().getHeight();
		return y >= h- frameWidth && y < h;
	}		

	// 调整窗口大小时，窗口最小尺寸为10 x 10
	private void setWindowSize(Window w, int width, int height)
	{
		if(width < minWidth ) width = minWidth;
		if(height < minHeight) height = minHeight;
		w.setSize( width, height);
	}
	
}
