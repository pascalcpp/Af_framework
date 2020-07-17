package af.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/* Swing高级篇 22.5 
 * 
 */
// 由于 JList 是一个泛型，所以在定义子类里，也按照泛型来定义
// T 表示是 JList 的数据项的类型
public class AfDragableList<T> extends JList<T>
	implements DragGestureListener,DropTargetListener
{
	public DefaultListModel<T> model = new DefaultListModel<>();
	
	public AfDragableList()
	{		
		this.setModel(model);
		
		// 初始化 Drag Source 
		DragSource dragSource = DragSource.getDefaultDragSource(); 
        dragSource.createDefaultDragGestureRecognizer( 
        		this, 
        		DnDConstants.ACTION_COPY_OR_MOVE, 
        		this 
        		);
        
        // 初始化 Drop Target
		DropTarget dropTarget = new DropTarget(this	,this );

	}
	
	// 从 oldIndex 移动到 newIndex
	public void moveItem(int oldIndex, int newIndex)
	{
		// 新老相同，不必称动
		if(oldIndex == newIndex) return;
		
		// newIndex为-1，表示移动到最末尾
		if(newIndex < 0) newIndex = model.getSize();
		
		// 先从旧的位置删除
		T oldValue = model.getElementAt(oldIndex);		
		model.remove(oldIndex);
		
		// 再插到新的位置
		if(newIndex > oldIndex)
		{
			newIndex --; // 因删除旧记录，索引可能发生变化
		}
		
		model.insertElementAt(oldValue, newIndex);
		
		// 选中新的项
		this.setSelectedIndex(newIndex);
	}
	
	// 给列表项创建一张拖拽提示用的图片
	// Swing高级篇 14.1节
	public static BufferedImage createItemImage(JList list, int index)
	{
		// 得到列表项的值
		Object itemValue = list.getModel().getElementAt(index);
		String text = itemValue.toString();
		
		// 文本的绘制，Swing高级篇 3.4
		// 字体测量
		Font font = new Font("宋体", Font.PLAIN, 14);			
		FontMetrics fm = list.getFontMetrics(font);
		int w = fm.stringWidth(text);
		int h = fm.getHeight();
	
		//  
		BufferedImage image = new BufferedImage(w,h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = image.createGraphics();
		g2d.setFont(font);
		g2d.setPaint(Color.BLUE);
		int x = 0, y = 0;
		y = h /2 + (h-fm.getLeading())/2 - fm.getDescent(); 
		g2d.drawString(text, x, y);
		g2d.dispose();  // 记得销毁 Graphics2D 对象
		
		return image;
	}
	
	// 根据拖放的起始位置，找到被拖放的项
	public static int getLocationIndex(JList list,Point pos)
	{
		// locationToIndex()只是返回最近的索引, 需要校正一下
		int index = list.locationToIndex(pos);
		if(index >= 0)
		{
			// 进一步确认
			// getCellBounds()返回相应项的位置
			Rectangle rect = list.getCellBounds(index,index);
			if( rect.contains( pos ))
			{
				return index;
			}
		}
		return -1; // 未点中有效的索引 
	}
	
	
	/////////////// DragGestureListener /////////
	@Override
	public void dragGestureRecognized(DragGestureEvent dge)
	{
		Point pos = dge.getDragOrigin(); // 拖拽的起点
		int index = getLocationIndex(this, pos);
		if(index < 0) return;
		
		// 获取该项的图片 ( API 内部创建了一个图片 )
		Image image = createItemImage(this, index);
		
		// 从一个特殊的字符串来传递拖放信息
		String str = "drag:" + index; 
		Transferable transfer = new StringSelection(str);			
		//dge.startDrag(DragSource.DefaultMoveDrop,transfer);
		
		dge.startDrag(DragSource.DefaultMoveDrop, 
				image, new Point(-4,14),
				transfer, null);
	}	
	
	
	///////////////// DropTargetListener //////////
	@Override
	public void dragEnter(DropTargetDragEvent dtde)
	{
		Transferable transfer = dtde.getTransferable();
		if(! transfer.isDataFlavorSupported(DataFlavor.stringFlavor))
		{
			dtde.rejectDrag();
		}
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde)
	{
		// 目标位置提示
		int newIndex = getLocationIndex(this, dtde.getLocation());
		if(newIndex < 0)
		{
			// 将要插入到列表的最下面，所以不选中任何条目 
			this.getSelectionModel().clearSelection();
		}
		else
		{
			// 将要插入到该条记录之上
			this.setSelectedIndex(newIndex);
		}			
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde)
	{
	}

	@Override
	public void dragExit(DropTargetEvent dte)
	{			
	}

	@Override
	public void drop(DropTargetDropEvent dtde)
	{				
		Transferable transfer = dtde.getTransferable();

		dtde.acceptDrop( DnDConstants.ACTION_MOVE);	

		try
		{
			// 调用 getTransferData() 来取得数据
			String data = (String) transfer.getTransferData(DataFlavor.stringFlavor);
			if(data.startsWith("drag:"))
			{
				// 从字符串里解析出有效信息
				String strIndex = data.substring(5);
				int oldIndex = Integer.valueOf(strIndex);
				
				// 新的插入位置
				int newIndex = getLocationIndex(this, dtde.getLocation());
				// 移动条目 
				moveItem(oldIndex, newIndex);
			}			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}				

		dtde.dropComplete(true);			
	}	
	
}
