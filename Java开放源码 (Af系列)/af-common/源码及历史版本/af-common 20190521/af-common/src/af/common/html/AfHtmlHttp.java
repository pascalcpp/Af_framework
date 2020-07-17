package af.common.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class AfHtmlHttp
{
	CloseableHttpClient httpclient = HttpClients.createDefault();
	
	List<String> supportSuffix = new ArrayList<String>();
	Map<String,String> supportTypes = new HashMap<String,String>();
	
	public void clearSupportTypes()
	{
		supportTypes.clear();
		supportSuffix.clear();
	}
	public void addSupportType(String type)
	{
		supportSuffix.add(type);
		if(type.equals("jpg")) supportTypes.put("image/jpeg", "jpg");
		if(type.equals("jepg")) supportTypes.put("image/jpeg", "jpg");
		if(type.equals("png")) supportTypes.put("image/png", "png");
		if(type.equals("gif")) supportTypes.put("image/gif", "gif");
		if(type.equals("webp")) supportTypes.put("image/webp", "webp");
	}
	
	// 防止 //x.y.com/abc/123393
	//  必须在最后一个/后面找.号 ，不然可能误认为域名里的点号
	private String getSuffix(String url)
	{
		int p1 = url.lastIndexOf('/');
		int p2 = url.lastIndexOf('.');
		if(p2 > p1)
			return url.substring(p2+1).toLowerCase();
		return null;
	}
	
	// 下载: 根据Content-Type来决定后缀 (jpg/png)
	// tmpDir : 临时文件目录
	// maxSize: 微信要中插入的新闻图片不得大于1M
	// 如果返回null, 表明该url不是图片
	public File downloadImage(String url, File tmpDir, int maxSize)
			throws Exception
	{
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpget);

		try
		{
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status != 200)
			{
				throw new Exception("HTTP GET出错:" + status + ","
						+ statusLine.getReasonPhrase());
			}

			// 判断localFile有没有后缀
			String fileName = UUID.randomUUID().toString();
			try
			{
				// 从url里获取后缀
				String suffix = getSuffix(url);
				if(suffix == null || suffix.length() == 0)
				{
					// 如果url里没有后缀, 则从content-type里获取后缀
					String contentType = response.getFirstHeader("Content-Type").getValue();
					suffix = supportTypes.get(contentType);					
				}
				
				// url 里存在后缀, 则使用此后缀
				if(supportSuffix.indexOf( suffix ) < 0) return null; //不是一个图片, 或不支持的图片格式

				// 本地临时文件名
				fileName += "." + suffix;
				
			} catch (Exception e)
			{
				System.out.println("无法获取Content-Type ");
			}

			// 保存时带上探测到的文件类型后缀
			HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				FileOutputStream outStream = null;
				try
				{
					if (!tmpDir.isDirectory())
						tmpDir.mkdirs();
					File tmpFile = new File(tmpDir, fileName);
					outStream = new FileOutputStream(tmpFile);
					InputStream inStream = entity.getContent();

					// 下载文件到本地
					byte[] buf = new byte[4096];
					int total = 0;
					while (true)
					{
						int n = inStream.read(buf);
						if (n <= 0)
							break;
						outStream.write(buf, 0, n);
						total += n;
						if (total >= maxSize)
							throw new Exception("图片文件超过限制( " + maxSize
									+ "), 放弃下载!");
					}
					return tmpFile;
				} finally
				{
					try
					{
						outStream.close();
					} catch (Exception e)
					{
					}
				}

			}
		} finally
		{
			try
			{
				response.close();
			} catch (Exception e)
			{
			}

		}
		return null;
	}

}
