package af.common.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/* 调用者最好做一个HTTP的单例
 * 使用唯一的HTTP实例，以便保持Cookie数据
 */

public class HTTP
{
	CloseableHttpClient httpclient;
	int timeout = 3000;
	
	public HTTP()
	{
		CookieStore cookieStore = new BasicCookieStore();
		httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore)
				.build();
	}
	
	// 关闭所有连接
	public void close()
	{
		try
		{
			httpclient.close();
			httpclient = null;
		} catch (Exception e)
		{
		}
	}
	
	// 设置超时时间
	public HTTP timeout( int t)
	{
		this.timeout = t;
		return this;
	}
	
	public String get(String uri) throws Exception
	{
		// 1 创建HttpClient
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(this.timeout) // 读写超时设置
				.setConnectTimeout(this.timeout) // 连接超时设置
				.build();

		// 2 创建GET请求
		HttpGet httpget = new HttpGet(uri);
		httpget.setConfig(requestConfig);

		// 3 发起请求
		CloseableHttpResponse response = httpclient.execute(httpget);
		try
		{
			// 4 获取应答
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status != 200)
				throw new Exception("HTTP 出错:" + status + "," + statusLine.getReasonPhrase());

			// 下行数据
			HttpEntity dataRecv = response.getEntity();
			String ss = EntityUtils.toString(dataRecv);
			return ss;
		} finally
		{			
		}
	}

	// 请求: 上传一段普通文本
	public String post(String uri, String reqText) throws Exception
	{
		// 1 创建HttpClient
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000) // 读写超时设置
				.setConnectTimeout(3000) // 连接超时设置
				.build();

		// 2 创建POST请求
		HttpPost httppost = new HttpPost(uri);
		httppost.setConfig(requestConfig);

		// 设置请求参数
		StringEntity dataSent = new StringEntity(reqText, ContentType.create("text/plain", "UTF-8"));
		httppost.setEntity(dataSent);

		// 3 发起请求
		CloseableHttpResponse response = httpclient.execute(httppost);
		try
		{
			// 4 获取应答
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status != 200)
				throw new Exception("HTTP 出错:" + status + "," + statusLine.getReasonPhrase());

			// 下行数据
			HttpEntity dataRecv = response.getEntity();
			String ss = EntityUtils.toString(dataRecv);
			return ss;
		} finally
		{
			
		}
	}
	
	// 请求: 上传表单
	public String post(String uri, List<NameValuePair> pairs) throws Exception
	{
		// 1 创建HttpClient
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000) // 读写超时设置
				.setConnectTimeout(3000) // 连接超时设置
				.build();

		// 2 创建POST请求
		HttpPost httppost = new HttpPost(uri);
		httppost.setConfig(requestConfig);

		// 设置请求参数
		// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// formparams.add(new BasicNameValuePair("username", "android"));
		// formparams.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity dataSent = new UrlEncodedFormEntity(pairs, Consts.UTF_8);
		httppost.setEntity(dataSent);

		// 3 发起请求
		CloseableHttpResponse response = httpclient.execute(httppost);
		try
		{
			// 4 获取应答
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status != 200)
				throw new Exception("HTTP 出错:" + status + "," + statusLine.getReasonPhrase());

			// 下行数据
			HttpEntity dataRecv = response.getEntity();
			String ss = EntityUtils.toString(dataRecv);
			return ss;
		} finally
		{
			
		}
	}

	// 下载文件
	public File download(String uri, File localFile, int maxsize) throws Exception
	{
		// 1 创建客户端
		//CloseableHttpClient httpclient = HttpClients.createDefault();

		// 2 创建一个HTTP请求
		HttpGet httpget = new HttpGet(uri);

		// 设置超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(timeout)
				.setConnectTimeout(timeout)
				.build();
		httpget.setConfig(requestConfig);
		
		// 3 执行HTTP请求
		CloseableHttpResponse response = httpclient.execute(httpget);// 发起HTTP
	
		// 4 解析和处理服务器返回的数据
		// 检查状态码
		StatusLine statusLine = response.getStatusLine();
		int status = statusLine.getStatusCode();
		if (status != 200)
		{
			String reason = statusLine.getReasonPhrase();
			throw new Exception("Status Error: " + status + "," + reason);
		}

		// 保存文件
		final int MAXSIZE = 1024 * 1024 * 128; // 128M
		byte[] buf = new byte[1024 * 4];

		HttpEntity entity = response.getEntity();		
		if (entity != null)
		{
			InputStream inputStream = entity.getContent();
			OutputStream outputStream  = new FileOutputStream(localFile);
			try {
				streamCopy(inputStream, outputStream, maxsize);
			}finally
			{
				// 确保文件被关闭
				try {outputStream.close();} catch(Exception e) {}
			}
			
			return localFile;
			//System.out.println("** HTTP GET finished,  total: " + total);
		}
		
		return null;
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
	
	// 上传文件
	public String upload(String uri, File localFile, List<NameValuePair> pairs) throws Exception
	{
		// 1 创建客户端
		//CloseableHttpClient httpclient = HttpClients.createDefault();

		// 2 创建请求
		HttpPost httppost = new HttpPost(uri);

//		HttpEntity reqEntity = MultipartEntityBuilder.create()
//				.addTextBody("id", "123456", TEXT_PLAIN)
//				.addTextBody("name", "邵发", TEXT_PLAIN)
//				.addPart("file", filepart).build();
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		if(pairs != null)
		{
			// 添加普通表单域
			ContentType TEXT_PLAIN = ContentType.create("text/plain", Charset.forName("UTF-8"));
			for(NameValuePair e: pairs)
			{
				builder.addTextBody(e.getName(), e.getValue(), TEXT_PLAIN);
			}
		}
		
		// 添加文件域
		FileBody filepart = new FileBody(localFile);
		builder.addPart("file", filepart);
		
		// 
		HttpEntity dataSent = builder.build();
		httppost.setEntity(dataSent);
		
		// 3 执行HTTP请求
		CloseableHttpResponse response = httpclient.execute(httppost);// 发起HTTP
		try
		{
			// 4 解析和处理服务器返回的数据

			// 检查状态码
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status != 200)
			{
				String reason = statusLine.getReasonPhrase();
				throw new Exception("Status Error: " + status + "," + reason);
			}

			// 返回结果
			HttpEntity entity = response.getEntity();
			String reply = EntityUtils.toString(entity);
			return reply;

		} finally
		{
			
		}
	}

}
