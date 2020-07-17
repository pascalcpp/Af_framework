package af.swing;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

/* Swing高级，18.4讲
 * 
 */
public class AfCardPane extends JPanel
{
	// 卡片布局
	CardLayout layout = new CardLayout();
	
	// 记录所有卡片的信息
	List<Card> cards = new ArrayList<>();
		
	public AfCardPane()
	{		
		// 默认设为卡片布局
		this.setLayout(layout);
	}

	// 添加卡片
	public void addCard(Component comp, String name)
	{		
		super.add(comp, name);		
		cards.add(new Card(comp, name));
	}
	
	// 获取卡片
	public Component getCard(String name)
	{
		Card card = find(name);
		if(card != null)
			return card.comp;
		
		return null;
	}
	// 显示卡片
	public void showCard(Component comp)
	{
		Card card = find(comp);
		if(card != null)
		{
			layout.show(this, card.name);
		}
	}
	// 显示卡片
	public void showCard(String name)
	{
		Card card = find(name);
		if(card != null)
		{
			layout.show(this, card.name);
		}
	}
	// 删除卡片
	public void removeCard(Component comp)
	{
		Iterator<Card> iter = cards.iterator();
		while(iter.hasNext())
		{
			Card card = iter.next();
			if(card.comp == comp)
			{
				iter.remove();
				this.remove(card.comp);
				break;
			}
		}
	}
	// 删除卡片
	public void removeCard(String name)
	{
		Iterator<Card> iter = cards.iterator();
		while(iter.hasNext())
		{
			Card card = iter.next();
			if(card.name.equals(name))
			{
				iter.remove();
				this.remove(card.comp);
				break;
			}
		}
		
	}
	// 查找卡片
	private Card find(Component comp)
	{
		for(Card card : cards)
		{
			if(card.comp == comp)
				return card;
		}
		return null;
	}
	// 查找卡片
	private Card find(String name)
	{
		for(Card card : cards)
		{
			if(card.name.equals(name))
				return card;
		}
		return null;
	}
	
	
	// 卡片信息
	private static class Card
	{
		Component comp; // 卡片对应的控件
		String name; // 卡片名称
		
		public Card(Component comp, String name)
		{
			this.comp = comp;
			this.name = name;
		}
	}


	@Override
	public void add(Component comp, Object constraints)
	{
		addCard(comp, (String)constraints );
	}

	@Override
	public Component add(Component comp)
	{
		System.out.println("AfCardPane: 添加卡片应设置名称!");
		return null;
	}

	@Override
	public void setLayout(LayoutManager mgr)
	{
		// 不允许外部再设置布局器
		if(mgr != this.layout) return;
		
		super.setLayout(mgr);		
	}	
	
}

