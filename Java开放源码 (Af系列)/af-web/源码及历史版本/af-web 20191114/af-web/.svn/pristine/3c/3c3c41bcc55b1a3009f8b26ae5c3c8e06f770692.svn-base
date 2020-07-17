package af.web.restful;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import af.web.AfWebException;

/**
 * 不依赖任何框架，快速地创建一个RESTful服务
 * @author shaofa
 */

public abstract class AfSimpleREST extends HttpServlet
{
	protected boolean enableErrorLog = true; // 是否打印异常输出
	protected boolean niceJSONFormat = true; // 输出的JSON是否综进 (缩进影响运行效率)
	protected int MAX_REQUEST_SIZE = 1024 * 512; // 允许上传的JSON最大长度
	protected String charset = "UTF-8";
	
	// 子类须重写这个方法，进行业务处理
	// 处理返回后，可以返回 JSONObject, JSONArray, 或int long String 等基本类型
	protected abstract Object execute(HttpServletRequest request, 
			HttpServletResponse response,
			JSONObject jreq ) throws Exception;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		// 无论是 GET/POST, 均统一处理
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{		
		String requestUri = request.getRequestURI();
		
		// 处理请求数据
		JSONObject jresp = new JSONObject();		
		try{
			// 读取请求数据, 转成字符串, 转成 JSON
			String reqText = readAsText(request.getInputStream(), charset, MAX_REQUEST_SIZE);
			JSONObject jreq = null;
			if(reqText.length()>0) jreq = new JSONObject(reqText);
			
			// 子类应重写 execute() 方法
			Object data = execute(request, response, jreq);
			jresp.put("error", 0); // 错误码,0表示成功
			jresp.put("reason", "OK"); // 错误原因描述, 如果没有错误则提示OK
			if(data != null)
				jresp.put("data", data); // 
		}
		catch(AfWebException e)
		{
			String reason = e.getMessage();		
			System.out.println("** 出错: " + requestUri + ", 原因： " + reason);
			
			if(enableErrorLog) e.printStackTrace();
			
			// 应签数据
			jresp.put("error", e.error); // 错误码,0表示成功
			jresp.put("reason", reason); // 错误原因描述, 如果没有错误则提示OK
		}
		catch(Exception e)
		{
			String reason = e.getMessage();
			if(reason == null) reason = e.getClass().getName();			
			System.out.println("** 出错: " + requestUri + ", 原因： " + reason);
			
			if(enableErrorLog) e.printStackTrace();
			
			// 应签数据
			jresp.put("error", -1); // 错误码,0表示成功
			jresp.put("reason", e.getMessage()); // 错误原因描述, 如果没有错误则提示OK
		}
		
		// 是否按可读风格生成JSON ( 缩进格式 or 紧凑格式 )
		String jsonstr = niceJSONFormat ? jresp.toString(2) : jresp.toString();
		
		// 发送应答给客户端
		response.setCharacterEncoding(charset);
		response.setContentType("text/plain");
		//response.setHeader("Connection", "close");
		Writer writer = response.getWriter();
		writer.write( jsonstr );
		writer.close();		
	}
	
	/////////////////// 工具方法 //////////////////
	// 从 Stream 中读取数据直到读完
	public static String readAsText(InputStream streamIn, String charset, int maxSize)
			throws IOException 
	{
		ByteArrayOutputStream cache = new ByteArrayOutputStream(1024*16);  
        byte[] data = new byte[1024]; 
        
        int numOfWait = 0;
        while (true)
        {
        	int n = streamIn.read(data); // n: 实际读取的字节数
        	if(n < 0) break; // 连接已经断开
        	if(n == 0) 
        	{
        		if( numOfWait ++ >= 3) break; // 此种情况不得连续3次
        		try{ Thread.sleep(5);}catch(Exception e){}
        		continue;// 数据未完 //  
        	}
        	numOfWait = 0;

        	// 缓存起来
        	cache.write(data, 0, n);        	
        	if(cache.size() > maxSize) // 上限, 最多读取512K
        		break;
        }  
        
        return cache.toString(charset);
	}
		
}
