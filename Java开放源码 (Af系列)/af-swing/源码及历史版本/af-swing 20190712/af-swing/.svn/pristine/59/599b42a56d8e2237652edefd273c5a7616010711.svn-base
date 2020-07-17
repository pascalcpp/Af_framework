package af.swing.popup;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;

import af.swing.popup.AfPopup.ClosingPolicy;

/* Swing高级，16.6 
 * 
 */

public class AfPopupPanel extends JPanel
{
	protected Popup popup;
	protected AfPopupMouseGrabber mouseGrabber;
	
	public void showPopup(Component owner, int x, int y)
	{
		showPopup(owner, x,y , new ClosingPolicy()); // 使用默认关闭策略
	}

	// 显示Popup
	public void showPopup(Component owner, int x, int y, ClosingPolicy policy)
	{
		Point pt = new Point(x,y);
		SwingUtilities.convertPointToScreen(pt, owner);
		
//		PopupFactory factory = PopupFactory.getSharedInstance();
//		this.popup = factory.getPopup(owner, this, pt.x, pt.y);
		
		JWindow popup = new JWindow(SwingUtilities.windowForComponent(owner));
		// 设置 ContentPane ( 固定用法 )
		popup.getRootPane().setContentPane(this);
		// 设置窗口大小
		popup.setSize( this.getPreferredSize());
		// 设置窗口显示位置
		popup.setLocation(pt.x, pt.y);
		
		// 添加监控 （grabber内部已经实现“当点击在popup窗口之外时自动关闭popup”的逻辑)
		this.mouseGrabber = new AfPopupMouseGrabber(owner,this,popup);
		mouseGrabber.installListeners(policy);
		
		// 显示弹出式窗口
		popup.setVisible(true);
	}	
	
	// 隐藏 Popup
	public void hidePopup()
	{
		if(mouseGrabber != null)
		{
			mouseGrabber.cancelPopupMenu();
			mouseGrabber = null;
		}
	}
	

}
