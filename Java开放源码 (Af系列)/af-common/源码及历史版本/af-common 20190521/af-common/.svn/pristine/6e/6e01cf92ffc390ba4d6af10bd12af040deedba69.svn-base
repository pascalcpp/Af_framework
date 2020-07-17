package my.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test
{

	public static void test1()
	{
		// .*? 表示非贪婪模式的匹配
		String html = "好图 <img src='http://127.0.0.1/abc.jpg'> 美图 <background:url(http://abc.com/123.png)欣赏";
		String rule = "http:.*?[\n\r\t\'\")]";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
        	System.out.println("** 匹配:" );
        	m.appendReplacement(sb, "Kevin"); 
        	
        }
        m.appendTail(sb); 
        System.out.println("最终内容是:"+ sb.toString()); 
	}
	public static void test2()
	{
		// .*? 表示非贪婪模式的匹配
		String html = "好图 <img src='http://127.0.0.1/abc.jpg'> 美图 <background:url(https://abc.com/123.png)欣赏";
		String rule = "https?://.*?[\n\r\t\'\")]";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
        	System.out.println("** 匹配:" + m.group(0) );
        	m.appendReplacement(sb, "Kevin"); 
        	
        }
        m.appendTail(sb); 
        System.out.println("最终内容是:"+ sb.toString()); 
	}
	
	public static void test3()
	{
		String html = "好图 <img src='http://127.0.0.1/abc.jpg'> 美图 <background:url(http://abc.com/123.png)欣赏";
		String rule = "(http:.*?)([\n\r\t\'\")])";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
        	System.out.println("** 匹配:" );
        	System.out.println("** group0:" + m.group(0)); // group(0)指全部
        	System.out.println("** group1:" + m.group(1)); // group(1) 第一个小括号组
        	System.out.println("** group2:" + m.group(2));
        	m.appendReplacement(sb, "Kevin" + m.group(2));         	
        }
        m.appendTail(sb); 
        System.out.println("最终内容是:"+ sb.toString()); 
	}
	
	public static void test4()
	{
		// .*? 表示非贪婪模式的匹配
		String html = "好图 <img src='http://127.0.0.1/abc.jpg'> 美图 <background:url(http://abc.com/123.png)欣赏";
		String rule = "//.*?[\n\r\t\'\")]";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
        	String url = m.group(0);
        	System.out.println("** 匹配:" + m.group(0) );
        	m.appendReplacement(sb, "Kevin"); 
        	
        	// 有些网站的URL不带http: 或 https
        	int start = m.start();
        	if(start > 5)
        	{
        		String prefix = html.substring(start-5, start);
        		if(prefix.equals("http:"))
        			System.out.println("http:" + url);
        		else if(prefix.equals("ttps:"))
        			System.out.println("https:" + url);
        	}
        }
        m.appendTail(sb); 
        System.out.println("最终内容是:"+ sb.toString()); 
	}
		
	
	public static void test5()
	{
		// .*? 表示非贪婪模式的匹配
		String html = "好图 <img src='http://127.0.0.1/abc.jpg'> 美图 <background:url(https://abc.com/123.png)欣赏 <img src='//xyz.com/a.jpg'>";
		String rule = "(http)?s?:?(//.*?)[\n\r\t\'\")]";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {

        	
        	//m.appendReplacement(sb, "Kevin"); 
//        	System.out.println("** group0:" + m.group(0)); // group(0)指全部
//        	System.out.println("** group1:" + m.group(1)); // group(1) 第一个小括号组
//        	System.out.println("** group2:" + m.group(2));

        	String full = m.group();
        	String url = m.group(2);
        	String prefix = "http:";
        	if(full.startsWith("https")) prefix= "https:";
        		
        	url = prefix + url;
        	System.out.println("** url: " + url);
        	
        }
        m.appendTail(sb); 
       // System.out.println("最终内容是:"+ sb.toString()); 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		test5();
	}

}
