package af.common.html;

import java.io.File;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 本类线程不安全
 * 
 * 调用者可以在每个线程里创建一个Parser（反正只是小工具)
 * 
 */
public class AfHtmlLocalizer
{
	private AfHtmlHttp http = new AfHtmlHttp();
	public File tmpDir ;
	public int maxSize = 1000000;
	public String defaultProtocol = "http";
	
	String baseUrl = null; // 基准URL
	
	// 调用者要负责把 File 处理一下，得到一个URL
	public AfHtmlImageConverter converter;
	public Object converterContext; 
	
	public AfHtmlLocalizer(File tmpDir)
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
	
	
	// html : 要处理的html
	// context: 外部可以透传一个参数，该参数在回调里再传回去
	// sourceUrl: 源URL
	// baseUrl : 有了基准url, 就可以下载相对路径
	public String process(String baseUrl, String html, Object context) throws Exception
	{
		this.baseUrl = baseUrl;
		this.converterContext = context;
		//String rule = "(https?:.*?)([\n\r\t\'\")])";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		
		// url有多种格式
		// http://xyz.com/123.jpg
		// https://xyz.com/123.jpg
		// 省略协议,如 //xyz.com/133.jpg 表示按照默认协议
		// 相对URL暂不支持抓取
		//String rule = "(http)?s?:?(//.*?)([\n\r\t\'\">)])";  // 结尾标志: 换行 制表 单引号 双引号 空格 小括号
		// 现在网站上的url很多并不带http:或https:，表示并支持
		// 例如 //www.xyz.com/abc.jpg
    	
		html = process1(baseUrl, html, context);
//		html = process2(baseUrl, html, context);
		html = process3(baseUrl, html, context);
		return html;
	}
	
	private String process1(String baseUrl, String html, Object context) throws Exception
	{
		URL ref = null;
		if(baseUrl != null)
		{
			ref = new URL(baseUrl);
		}
		
		String rule = "(src\\s*=\\s*['\"]?)(.*?)(['\" ])" ;  
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
//        	System.out.println("** 匹配:" );
//        	System.out.println("** group0:" + m.group(0)); // group(0)指全部
//        	System.out.println("** group1:" + m.group(1)); // group(1) 第一个小括号组
//        	System.out.println("** group2:" + m.group(2));
//        	System.out.println("** group3:" + m.group(3));
        	
        	
        	String fullstr = m.group();
        	String url = m.group(2);
        	
        	url = processUrl(ref, url);	
        	if(url != null)
        	{
            	String convertedUrl = convert( url ); // 转换
            	m.appendReplacement(sb, m.group(1) + convertedUrl + m.group(3));
        	}
        	else
        	{
        		m.appendReplacement(sb, fullstr); // 不转换
        	}    		
        }
        m.appendTail(sb);         
        return sb.toString();
	}

	private String process2(String baseUrl, String html, Object context) throws Exception
	{
		URL ref = null;
		if(baseUrl != null)
		{
			ref = new URL(baseUrl);
		}
		
		String rule = "href\\s*=\\s*['\"]?(.*?)(['\" ])" ;  
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {
        	System.out.println("** 匹配:" );
        	System.out.println("** group0:" + m.group(0)); // group(0)指全部
        	System.out.println("** group1:" + m.group(1)); // group(1) 第一个小括号组
        	//System.out.println("** group2:" + m.group(2));
        	
        	String fullstr = m.group();
        	String url = m.group(1);
        	
        	url = processUrl(ref, url);	
        	if(url != null)
        	{
            	String convertedUrl = convert( url ); // 转换
            	m.appendReplacement(sb, convertedUrl + m.group(2));
        	}
        	else
        	{
        		m.appendReplacement(sb, fullstr); // 不转换
        	}    		
        }
        m.appendTail(sb);         
        return sb.toString();
	}
	
	private String process3(String baseUrl, String html, Object context) throws Exception
	{
		URL ref = null;
		if(baseUrl != null)
		{
			ref = new URL(baseUrl);
		}
		
		// url ( 'xxx' ) 或 url ("xxx") 或 url(xxx) 
		String rule = "url\\s*\\((.*?)\\)" ;  
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(html);
        StringBuffer sb = new StringBuffer(); 
        while( m.find())
        {     	
        	String fullstr = m.group();
        	String url = m.group(1);
        	        	
        	url = processUrl(ref, url);	
        	if(url != null)
        	{
            	String convertedUrl = convert( url ); // 转换
            	m.appendReplacement(sb, "url(" + convertedUrl + ")");
        	}
        	else
        	{
        		m.appendReplacement(sb, fullstr); // 不转换
        	}    		
        }
        m.appendTail(sb);         
        return sb.toString();
	}	
	
	
	protected String processUrl( URL ref, String url)
	{
		// 去除两边的空格或引号
		int p1 = -1, p2=-1;
		for(int i=0; i<url.length(); i++)
		{
			char ch = url.charAt(i);
			if(ch != '\'' && ch != '\"' && ch != ' ' && ch != '\t')
			{
				if(p1<0) p1 = i;				
			}
			else if(p1>=0)
			{
				if(p2<0) 
				{
					p2 = i; break;
				}
			}
		}
		
		if(p1>=0)
		{
			if(p2>= 0) url = url.substring(p1, p2);
			else url = url.substring(p1);
		}
		
		if(url.startsWith("http") || url.startsWith("https")) 
    	{}
    	else if(url.startsWith("//"))
    	{
    		url = this.defaultProtocol + ":" + url;
    	}
    	else
    	{
    		// 相对URL
    		try {
    			URL u2 = new URL(ref, url);
    			url = u2.toString();
    		}catch(Exception e) {
    			url = null;
    		}
    	}
		return url;
	}
	
	// 将目标url转到本地
	protected String convert(String url) 
	{
		if(this.converter == null) return url;
		
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
