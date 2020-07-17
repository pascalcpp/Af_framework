package af.common.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/* XML相关方法
 * 
 * 此源码最新版在 《网站中级篇》的网盘目录下，af-common 源码包中
 * 
 */

public class AfXml
{
	
	// encoding: 一般为 "UTF-8" 或 "GBK"
	public static void toFile(Document doc, File file, String encoding) throws Exception
	{
		  // 输出到文件	    
	    OutputStream outputStream = new FileOutputStream(file);
	    try {
		    OutputFormat format = OutputFormat.createPrettyPrint();  
		    format.setEncoding(encoding);	// 字符编码为UTF-8
		    XMLWriter writer = new XMLWriter(outputStream , format);
		    writer.write( doc ); // 把xml文档输出到文件里
		    writer.close();
	    }finally
	    {
	    	// 确保文件句柄被关闭
	    	try { outputStream.close();}catch(Exception e) {}
	    }
	}
	
	// SAXReader会自己识别XML文件第一行里的encoding设定
	public static Document fromFile(File file) throws Exception
	{
		FileInputStream inputStream = new FileInputStream(file);		
		try {
			SAXReader xmlReader = new SAXReader(); 	
			Document doc = xmlReader.read(inputStream);
			return doc;
		}finally {
			// 确保关闭文件句柄 
			try{ inputStream.close(); }catch(Exception e) {}
		}			
	}
	
	// 转成字符串
	public static String toString(Document doc,String encoding) throws Exception
	{
		doc.setXMLEncoding(encoding);
		return doc.asXML();
	}
	
	// 解析字符串
	public static Document fromString(String xml) throws Exception
	{
		Document doc = DocumentHelper.parseText(xml);
		return doc;
	}
	
	// 查找元素, path：以斜杠分隔的路径
	public static Element getElement(Element parent, String path) throws Exception
	{		
		String[] ppp = path.split("/");
		String badpath = "";
		
		Element child = parent;		
		for(String p: ppp)
		{
			badpath += p;
			child = child.element(p.trim());
			if(child == null)
				throw new Exception("元素不存在: " + badpath);
		}
		return child;
	}
	
	///////////////// 元素的值 ////////////////////
	public static String getTextString (Element parent, String path, String defValue)
	{
		try {
			Element child = getElement(parent, path);
			return  child.getText().trim();
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static int getTextInt (Element parent, String path, int defValue)
	{
		try {
			Element child = getElement(parent, path);
			return Integer.valueOf( child.getText().trim());
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static long getTextLong (Element parent, String path, long defValue)
	{
		try {
			Element child = getElement(parent, path);
			return Long.valueOf( child.getText().trim());
		}catch(Exception e)
		{
			return defValue;
		}
	}
	
	public static double getTextDouble (Element parent, String path, double defValue)
	{
		try {
			Element child = getElement(parent, path);
			return Double.valueOf( child.getText().trim());
		}catch(Exception e)
		{
			return defValue;
		}
	}
	
	public static Boolean getTextBoolean (Element parent, String path, Boolean defValue)
	{
		try {
			Element child = getElement(parent, path);
			String val = child.getText().trim().toLowerCase();
			return val.equals("1") || val.equals("true") || val.equals("yes");
		}catch(Exception e)
		{
			return defValue;
		}
	}
	
	// 属性的值
	public static String getAttrString (Element elem, String attr, String defValue)
	{
		try {
			return elem.attributeValue(attr).trim();
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static Integer getAttrInt (Element elem, String attr, Integer defValue)
	{
		try {
			return Integer.valueOf( elem.attributeValue(attr).trim());
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static Long getAttrLong (Element elem, String attr, Long defValue)
	{
		try {
			return Long.valueOf( elem.attributeValue(attr).trim());
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static Boolean getAttrBoolean (Element elem, String attr, Boolean defValue)
	{
		try {
			String val = elem.attributeValue(attr).trim().toLowerCase();
			return val.equals("1") || val.equals("true") || val.equals("yes");
		}catch(Exception e)
		{
			return defValue;
		}
	}
	
	//////////// 把值转成String
	public static String v(int value )
	{
		return String.valueOf(value);
	}
	public static String v(long value)
	{
		return String.valueOf(value);
	}
	public static String v(boolean value)
	{
		return value? "1" : "0";
	}
	public static String v(String value)
	{
		return value!=null ? value : "null";
	}
	
	/////////// 添加子元素 
	public static Element addElement(Element parent, String name, String value)
	{
		return parent.addElement(name).addText(value);
	}
	public static Element addElement(Element parent, String name, Integer value)
	{
		return parent.addElement(name).addText( v(value));
	}
	public static Element addElement(Element parent, String name, Long value)
	{
		return parent.addElement(name).addText( v(value));
	}
	public static Element addElement(Element parent, String name, Boolean value)
	{
		return parent.addElement(name).addText(v(value));
	}
}
