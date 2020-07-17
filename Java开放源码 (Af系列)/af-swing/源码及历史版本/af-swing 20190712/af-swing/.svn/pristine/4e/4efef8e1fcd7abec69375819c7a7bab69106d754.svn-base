package af.swing.layout;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JLayeredPane;

import af.swing.AfPanel;

public class AfLayeredPane extends JLayeredPane
{
	
	public AfLayeredPane()
	{		
		this.setLayout(new AfAnyWhere());
	}
	
	@Deprecated
	@Override
	public Component add(Component comp)
	{
		System.out.println("** Use AfLayeredPane.addLayer() instead !");
		return null;
	}
	
	@Deprecated
	@Override
	public void add(Component comp, Object constraints)
	{
		System.out.println("** Use AfLayeredPane.addLayer() instead !");		
	}

	@Deprecated
	@Override
	public Component add(String name, Component comp)
	{
		System.out.println("** Use AfLayeredPane.addLayer() instead !");		
		return null;
	}

	// 添加一个控件，同时指写层级
	public void addLayer(Component comp, int layer, Object constraints)
	{
		super.setLayer(comp, layer);
		super.add(comp, constraints);
	}
	
}
