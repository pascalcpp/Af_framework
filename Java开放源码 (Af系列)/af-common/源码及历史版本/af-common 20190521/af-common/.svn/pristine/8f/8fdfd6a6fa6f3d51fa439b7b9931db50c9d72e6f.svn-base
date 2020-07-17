package my.html;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import af.common.html.AfHtmlImageConverter;
import af.common.html.AfHtmlImageParser;
import af.common.html.AfHtmlLocalizer;

public class TestSDK2
{
	AfHtmlLocalizer parser;
	
	public void init()
	{
		// 设置一个临时目录 ，parser将把图片文件存到这个目录下
		File tmpDir = new File("/tmp/htmlimageparser/");
		tmpDir.mkdirs();
		
		parser = new AfHtmlLocalizer(tmpDir);
		parser.setDefaultProtocol("http");
		
		// 设置支持的文件类型 (内部将检测 HTTP Content-Type)
		parser.addSupportType("gif", "jpg", "jpeg","png", "webp");
		
		// 调用者要负责转成文件为URL
		parser.setConverter(new AfHtmlImageConverter(){

			@Override
			public String file2Url(String originalUrl, File localFile,
					Object context) throws Exception
			{
				System.out.println("originalUrl:" + originalUrl);
				System.out.println("localFile:" + localFile.getAbsolutePath());
				return originalUrl; // 自己处理一下localFile，得到一个新的url，并返回
			}

			@Override
			public boolean filter(String oritinalUrl)
			{
				// TODO Auto-generated method stub
				return true;
			}
		});
	}
	
	public void process() throws Exception
	{
//		String baseUrl = "http://image2.135editor.com/cache/remote/";
//		String html = " src=\"http://image2.135editor.com/cache/remote/aHR0cHM6Ly9tbWJpei5xbG9nby5jbi9tbWJpel9wbmcvN1FSVHZrSzJxQzZmS0FqczJRMjBpYjNrNWZzSERRV3o0REdNd1FSWU9SYUpBNjlzOEsyS2U1SVZMZmliWERTcmlhdzFtTUpneWM4R010c3dNMzR2VlNwSFEvMA==\" data-";
//		String html = " src=\"../remote/aHR0cHM6Ly9tbWJpei5xbG9nby5jbi9tbWJpel9wbmcvN1FSVHZrSzJxQzZmS0FqczJRMjBpYjNrNWZzSERRV3o0REdNd1FSWU9SYUpBNjlzOEsyS2U1SVZMZmliWERTcmlhdzFtTUpneWM4R010c3dNMzR2VlNwSFEvMA==\" data-";
//		String baseUrl = "<img src='http://renshi.people.com.cn/NMediaFile/2018/0912/MAIN201809121615000127908999987.jpg' alt='haha'>";

//		String html = "<img src='http://renshi.people.com.cn/NMediaFile/2018/0912/MAIN201809121615000127908999987.jpg' alt='haha'>";

		String html = "backgournd: url( http://renshi.people.com.cn/NMediaFile/2018/0912/MAIN201809121615000127908999987.jpg  ) ; color:#12334; ";
		String str = parser.process(null, html, this);
		System.out.println("处理之后:" + str);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//String html = "好图 <img src='https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2939765917,1014801368&fm=27&gp=0.jpg'> 美图 <background:url(http://abc.com/123.png)欣赏";
		//String html = "好图 <img src='http://p0.qhimgs4.com/t01ffded5fc89841504.webp'> 美图 欣赏";
		//String html ="看这个图美不？<img src='http://p0.qhimgs4.com/t0166c5450afc8d938f.webp'>";
		
		TestSDK2 sdk = new TestSDK2();
		sdk.init();
		
		try
		{
			sdk.process();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
