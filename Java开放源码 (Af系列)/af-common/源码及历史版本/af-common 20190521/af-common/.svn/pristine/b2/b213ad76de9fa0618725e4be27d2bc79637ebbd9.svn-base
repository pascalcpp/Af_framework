package af.common.html;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 本类线程不安全
 * 
 * 调用者可以在每个线程里创建一个Parser（反正只是小工具)
 * 
 */
public class AfHtmlImageParser
{
	private AfHtmlHttp http = new AfHtmlHttp();
	public File tmpDir ;
	public int maxSize = 1000000;
	public String defaultProtocol = "http";
	
	// 调用者要负责把 File 处理一下，得到一个URL
	public AfHtmlImageConverter converter;
	public Object converterContext; 
	public int count = 0; // 被转换处理的URL的个数
	
	public AfHtmlImageParser(File tmpDir)
	{		
		this.tmpDir = tmpDir;
	}
	
	// 图片大小限制
	public void setMaxSize(int maxSize)
	{
		this.maxSize = maxSize;
	}
		
	public void setConverter(AfHtmlImageConverter converter)
	{
		this.converter = converter;		
	}
	
	// 设置支持的图片格式
	public void clearSupportTypes()
	{
		http.clearSupportTypes();
	}

	public void addSupportType(String...types)
	{
		for(String type: types)
			http.addSupportType(type);
	}
	public boolean isSupportedType(String type)
	{
		return http.supportSuffix.indexOf(type) >= 0;
	}
	
	// 
	public void setDefaultProtocol( String protocol)
	{
		this.defaultProtocol = protocol;
	}
	
	public int getCount()
	{
		return count;
	}
	
	// html : 要处理的html
	// context: 外部可以透传一个参数，该参数在回调里再传回去
	// sourceUrl: 源URL
	public String process(String html, Object context) throws Exception
	{
		this.converterContext = context;
		this.count = 0; 
		//String rule = "(https?:.*?)([\n\r\t\'\")])";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		// url有多种格式
		// http://xyz.com/123.jpg
		// https://xyz.com/123.jpg
		// 省略协议,如 //xyz.com/133.jpg 表示按照默认协议
		// 相对URL暂不支持抓取
		String rule = "(http)?s?:?(//.*?)([\n\r\t\'\">)])";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		// 现在网站上的url很多并不带http:或https:，表示并支持
		// 例如 //www.xyz.com/abc.jpg
    	
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
        	//System.out.println("** 匹配:" );
        	//System.out.println("** group0:" + m.group(0)); // group(0)指全部
        	//System.out.println("** group1:" + m.group(1)); // group(1) 第一个小括号组
        	//System.out.println("** group2:" + m.group(2));
        	
        	String fullstr = m.group();
        	String url = m.group(2);
        	
        	// 协议
        	String protocol = "http";
        	if(fullstr.startsWith("https")) protocol= "https";
        	else if(fullstr.startsWith("http")) protocol= "http";
        	else protocol = this.defaultProtocol;
        	
        	url = protocol + ":" + url;
        	
        	// 转换
        	String convertedUrl = convert( url );
        	m.appendReplacement(sb, convertedUrl + m.group(3));    		
        }
        m.appendTail(sb); 
        
        return sb.toString();
	}
	
	// 将目标url转到本地
	protected String convert(String url) 
	{
		if(this.converter == null) return url;
		if( this.converter.filter(url)) return url;
		
		count ++; // 转换的URL的个数
		try{
			File file = http.downloadImage(url, tmpDir, maxSize);
			if( file != null)
			{
				// 本地图片文件转换URL
				String convertedUrl = converter.file2Url(url, file, converterContext);
				return convertedUrl;
			}				
		}
		catch(Exception e)
		{		
			System.out.println("ERROR: " + e.getMessage() + " (" + url + ") " );
			e.printStackTrace();
		}
		return url;
	}
	
	public static String getProtocol(String url)
	{
		int i = url.indexOf(':');
		if(i<=0) return "";
		
		String p = url.substring(0, i);
		return p.trim().toLowerCase();
	}
	
}
