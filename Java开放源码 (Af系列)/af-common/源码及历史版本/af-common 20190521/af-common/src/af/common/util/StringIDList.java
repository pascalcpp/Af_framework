package af.common.util;

import java.util.ArrayList;
import java.util.List;


public class StringIDList extends ArrayList<String>
{
	
	// 检查是否包含
	public boolean contains (String s)
	{
		return this.indexOf(s) >= 0;
	}	
	
	public void append(String s)
	{
		if( ! contains ( s ))
			add( s );
	}
	
	// 与另一个StringList合并
	public StringIDList append(StringIDList b)
	{
		for(String s : b)
		{
			append( s);
		}
		return this;
	}
	
	///////////////////////////////////
	
	// 从一个逗号分割的字符串获取
	public StringIDList fromString(String s)
	{
		return fromString(s, ",");
	}
	
	@Override
	public String toString()
	{
		return toString(",");
	}
	
	// 指定分隔符
	public StringIDList fromString(String s, String delimiter)
	{
		String[] aaa = s.split(",");
		for(String a: aaa)
		{
			a = a.trim();
			if(a.length() == 0) continue;
			add( a );
		}
		return this;
	}
	
	// 转成以逗号分割的字符串
	public String toString(String delimiter)
	{
		String result = "";
		for(int i=0; i<size(); i++)
		{
			if(result.length() > 0) result += delimiter;
			result += get(i);			
		}
		return result;
	}
	
	///////////////////////////////
	public List<Integer> toIntList()
	{
		List<Integer> result = new ArrayList();
		for( String s : this)
		{
			try{
				Integer a = Integer.valueOf(s);
				result.add( a );
			}catch(Exception e){}
		}
		return result;
	}
	
	public List<Long> toLongList()
	{
		List<Long> result = new ArrayList();
		for( String s : this)
		{
			try{
				Long a = Long.valueOf(s);
				result.add( a );
			}catch(Exception e){}
		}
		return result;		
	}
	
	///////////////////////////////////
	// "AAA,BBB,CCC" -> StringList
	public static StringIDList from(String s)
	{
		return new StringIDList().fromString(s);
	}	
	
	// List<Integer>, List<Long> , List<String> -> StringList
	public static StringIDList from(List<Object> list)
	{
		StringIDList sl = new StringIDList();
		for(Object o : list)
		{
			sl.append( o.toString());
		}
		return sl;
	}
	
	// int[], long[], String[] -> StringList
	public static StringIDList from(Object[] list)
	{
		StringIDList sl = new StringIDList();
		for(Object o : list)
		{
			sl.append( o.toString());
		}
		return sl;
	}
	

}


