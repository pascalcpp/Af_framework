package af.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JTextArea;

public class AfTextArea extends JTextArea
{
	private String placeHolder;
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		String text = this.getText();
		if(text.isEmpty() && placeHolder !=null)
		{			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint( Color.GRAY);
			
			Rectangle rect = new Rectangle(getWidth(),getHeight());
			//rect.grow(-2, -2);
			
			FontMetrics fm = g2d.getFontMetrics(g2d.getFont()); 
			int fontSize = fm.getHeight(); // 字高
			int textWidth = fm.stringWidth(placeHolder);
			int leading = fm.getLeading();
			int ascent = fm.getAscent(); // top -> baseline 的高度
			int descent = fm.getDescent(); // bottom->baseline 的高度

			int x = 0, y = 0;
			x = rect.x + 4;
			y = rect.y +  leading + ascent; // 竖直居中

			g2d.drawString(placeHolder, x, y); 
		}
	}

	public String getPlaceHolder()
	{
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder)
	{
		this.placeHolder = placeHolder;
		this.repaint();
	}
	
}
