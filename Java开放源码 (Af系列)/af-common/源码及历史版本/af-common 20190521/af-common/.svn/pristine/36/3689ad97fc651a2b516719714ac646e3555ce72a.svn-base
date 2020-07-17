package af.common.html;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import af.common.util.TextFileUtil;

public class AfHtmlGetter
{
	CloseableHttpClient httpclient = HttpClients.createDefault();
		
	// 仅仅是初始大小,可以动态增长的
	ByteArrayOutputStream buffer = new ByteArrayOutputStream(1024* 800);
	
	public String charset = null;
	
	public AfHtmlGetter()
	{
		
	}
	
	public String getCharset()
	{
		return charset;
	}
	
	public String get(int maxSize, String url) throws Exception
	{
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpget);

		buffer.reset();
		charset = null;

		StatusLine statusLine = response.getStatusLine();
		int status = statusLine.getStatusCode();
		if (status != 200)
		{
			throw new Exception("HTTP GET出错:" + status + ","
					+ statusLine.getReasonPhrase());
		}
		
		// 如何判断网页的字符集设置
		// 1 从HTTP头部获取
		// Content-Type: text/plain;charset=UTF-8
		// 2 从<html><head>里提取<meta>
		// <meta chraset='utf-8'>			
		// 3 从<html><head>里提取<metah>
		// <meta http-equiv="content-type" content="text/html; charset=UTF-8">
		//	HTML 4.01： <meta http-equiv="content-type" content="text/html; charset=UTF-8">
		// HTML5： <meta charset="UTF-8">
		
		// 保存时带上探测到的文件类型后缀
		HttpEntity entity = response.getEntity();
		if (entity != null)
		{
			try
			{
				InputStream inputStream = entity.getContent();
				streamCopy (inputStream, buffer, maxSize);					
			} finally
			{		
				try	{	response.close();	} catch (Exception e)	{	}
			}
		}
		
		byte[] rawdata = buffer.toByteArray();
		
		// 获取charset
		String contentType = response.getFirstHeader("Content-Type").getValue();
		this.charset = this.getCharsetFromHttpHeader(contentType);
		if(charset == null)
		{
			int n = 2000;
			if(rawdata.length > 1000) n = rawdata.length;
			String sss = new String(rawdata, 0, n);
			
			charset = this.getChrasetFromHtmlMeta1(sss);
//			if(charset == null)
//				charset = this.getChrasetFromHtmlMeta2(sss);				
		}
		
		if(charset == null)
			throw new Exception("无法识别字符编码:" + url);
		
		return new String(rawdata, charset);
				
	}
	
	// maxSize 为0时，表示不限制大小	
	private void streamCopy(InputStream inputStream, OutputStream outputStream, int maxSize) throws Exception
	{
		// 下载文件到本地
		byte[] buf = new byte[4096];
		int total = 0;
		while (true)
		{
			int n = inputStream.read(buf);
			if (n <= 0)
				break;
			outputStream.write(buf, 0, n);
			total += n;
			if (maxSize >0 && total >= maxSize)
				throw new Exception("文件大小超过限制( " + maxSize	+ "), 放弃下载!");
		}
	}
	
	// 	// Content-Type: text/plain;charset=UTF-8	
	private String getCharsetFromHttpHeader(String contentType)
	{
		if(contentType == null ) return null;
		
		contentType = contentType.toLowerCase();
		int p1 = contentType.indexOf("charset=");
		if(p1<0) return null;
		
		try {
			String encoding = contentType.substring(p1 + 8).trim();
			return encoding;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	
	//	HTML 4.01： <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	//  HTML5： <meta charset="UTF-8">	
	private String getChrasetFromHtmlMeta1(String rawdata)
	{
		// charset=UTF-8"
		// charset='UTF-8'
		// charset="UTF-8"
		String rule = "charset=['\"]?(.*?)['\" ]"; 
		// 现在网站上的url很多并不带http:或https:，表示并支持
		// 例如 //www.xyz.com/abc.jpg
    	
		Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(rawdata);
        if( m.find())
        {
        	return m.group(1);
        }
        return null;
	}

	// NotUsed
	// <meta http-equiv="content-type" content="text/html;charset=GB2312"/>
	private String getChrasetFromHtmlMeta2(String rawdata)
	{
		//http-equiv="charset" content="iso-8859-1"
		// \s+ 表示一个或多个空格
		String rule = "http-equiv=['\"]charset['\"]\\s+content=['\"](.*?)['\"]"; 
		// 现在网站上的url很多并不带http:或https:，表示并支持
		// 例如 //www.xyz.com/abc.jpg
   	
		Pattern pattern = Pattern.compile(rule);
       Matcher m = pattern.matcher(rawdata);
       if( m.find())
       {
       	return m.group(1);
       }
       return null;
	}
	
	
	
	public static void main(String[] args) throws Exception 
	{
		AfHtmlGetter h = new AfHtmlGetter();
		String result1 = h.get(0, "http://politics.people.com.cn/n1/2018/0820/c1001-30237345.html");
		TextFileUtil.write(new File("c:/1.txt"), result1, "GBK");
		
		String resultx = h.get(0, "http://sports.qq.com/a/20180819/030010.htm");
//		TextFileUtil.write(new File("c:/1.txt"), result1, "GBK");
		
//		
//		String result2 = h.get(0, "http://politics.people.com.cn/n1/2018/0820/c1001-30237345.html");
//		TextFileUtil.write(new File("c:/2.txt"), result2, "GBK");
		System.out.println("exit");
		
		
//		URL baseUri = new  URL("http://www.enet.com.cn/enews/inforcenter/designmore.jsp");
//		URL absoluteUri = new URL( null, "../test.html");
//		System.out.println(absoluteUri.toString());
		
	}

}
