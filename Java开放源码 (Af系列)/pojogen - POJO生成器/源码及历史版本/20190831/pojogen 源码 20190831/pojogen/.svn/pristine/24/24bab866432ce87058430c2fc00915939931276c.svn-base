package my.listview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import af.swing.AfPanel;
import af.swing.image.AfImageView;
import af.swing.layout.AfRowLayout;

public class SimpleListCellRenderer extends AfPanel
	implements ListCellRenderer
{
	//
	AfImageView iconField = new AfImageView();
	JLabel valueField = new JLabel();
	
	//
	private Image icCheck, icUncheck;
	
	public SimpleListCellRenderer()
	{		
		icCheck = loadIcon("check.png");
		icUncheck = loadIcon("uncheck.png");
		
		this.setLayout(new AfRowLayout(4));
		this.preferredHeight(30);
		this.padding(2);
		
		this.add(iconField, "30px");
		this.add(valueField, "1w");
		
		this.setOpaque(false);
		
		//
		iconField.setScaleType(AfImageView.FIT_CENTER_INSIDE);
	}
	
	private Image loadIcon(String name)
	{
		try {
			URL url = getClass().getResource(name);
			return ImageIO.read(url);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus)
	{
		// 要显示的文本
		String text = "null";
		if(value !=null) text = value.toString();
		
		//
		valueField.setText(text);
		
		//
		iconField.setImage(isSelected ? icCheck: icUncheck);
		
		return this;
	}

}
