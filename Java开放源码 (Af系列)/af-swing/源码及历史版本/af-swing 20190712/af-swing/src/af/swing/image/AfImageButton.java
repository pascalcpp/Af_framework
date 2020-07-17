package af.swing.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class AfImageButton extends AfImageView
{
	private ActionListener clickListener;
	
	public AfImageButton()
	{
	}
	
	public void addActionListener(ActionListener l)
	{
		this.clickListener = l;
	}
	
	
	@Override
	protected void processMouseEvent(MouseEvent e)
	{		
		//System.out.println("processMouseEvent:" + e.getPoint());		
		int eventID = e.getID();
		
		// 鼠标点击, 调用监听器
		if(eventID == MouseEvent.MOUSE_PRESSED)
		{
			if(clickListener != null)
			{
				ActionEvent ae = new ActionEvent(this, eventID, "clicked");
				clickListener.actionPerformed(ae);
			}
		}
		
		super.processMouseEvent(e);
	}
	

}
