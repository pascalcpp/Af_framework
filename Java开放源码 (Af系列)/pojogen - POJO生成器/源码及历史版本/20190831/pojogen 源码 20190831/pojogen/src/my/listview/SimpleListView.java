package my.listview;

import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class SimpleListView<T> extends JList<T>
{
	public DefaultListModel<T> model = new DefaultListModel<>();
	
	public SimpleListView()
	{
		setModel(model);
		
		setCellRenderer(new SimpleListCellRenderer());
		
//		enableEvents(MouseEvent.MOUSE_EVENT_MASK);
//		enableEvents(MouseEvent.MOUSE_MOTION_EVENT_MASK);
		
	}
	
	public void addItem(T value)
	{
		model.addElement( value );
	}
	
	public void clear()
	{
		model.removeAllElements();
	}
		
	public void selectItem(T value, boolean selected)
	{
		for(int i=0; i<model.size(); i++)
		{
			T item = model.getElementAt(i);
			if(item.equals( value ))
			{
				if(selected)
					this.addSelectionInterval(i, i);
				else
					this.removeSelectionInterval(i,i);
				
				break;
			}
		}			
	}
}
