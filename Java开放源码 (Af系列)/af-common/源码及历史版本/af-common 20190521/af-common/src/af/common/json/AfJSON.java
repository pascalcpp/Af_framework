package af.common.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class AfJSON
{
	// 0: 肯定是一个错误的格式 ( 没有找到{ 或 [ 开头，说明肯定是一个错误的JSON）
	// 1: 可能是一个JSONObject
	// 2: 可能是一个JSONArray
	public static int guessType(String jsonstr)
	{
		int N = jsonstr.length();
		for(int i=0; i<N; i++)
		{
			char ch = jsonstr.charAt(i);
			
			if(ch == '{') 
				return 1; // 有可能是JSONObject
			else if(ch == '[') 
				return 2; // 有可能是JSONObject
			else if(ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n')
				continue;
			else 
				return 0; // 发现以无效字符开头, 肯定是一个错误的JSON
		}
		return 0; // 没有找到{ 或 [ 开头，说明肯定是一个错误的JSON		
	}
	
	// json: 可以是JSONObject 或 JSONArray
	// encoding: "UTF-8", "GBK"
	public static void toFile(Object json, File file, String encoding) throws Exception
	{
		String jsonstr = null;
		if(json instanceof JSONObject)
			jsonstr = ((JSONObject)json).toString(2);
		else if(json instanceof JSONArray)
			jsonstr = ((JSONObject)json).toString(2);
		else 
			throw new Exception("Must be org.json.JSONObject or org.json.JSONArray");
		
		// 存入文件
		OutputStream outputStream = new FileOutputStream(file);
		try {
			// UTF-8写入BOM头部
			encoding=encoding.toUpperCase();
			if(encoding.equals("UTF-8"))
			{
				byte[] bom = { (byte)0xEF, (byte)0xBB,(byte) 0xBF };
				outputStream.write(bom);
			}
			
			byte[] data = jsonstr.getBytes(encoding);
			outputStream.write(data);
		}finally {
			// 确保文件被关闭
			try { outputStream.close();} catch(Exception e) {}
		}
	}
	
	public static Object fromFile(File file, String encoding) throws Exception
	{		
		
		InputStream inputStream = new FileInputStream(file);
		try {
			int fileSize = (int)file.length();
			byte[] data = new byte[fileSize];
			int n = inputStream.read(data);
			
			int offset = 0;
			encoding=encoding.toUpperCase();
			if(n > 3 && encoding.equals("UTF-8"))
			{
				if(data[0] == (byte)0xEF && data[1]==(byte)0xBB && data[2] == (byte)0xBF)
					offset = 3; // 前3个字节是BOM
			}
			
			String jsonstr = new String(data, offset, n-offset, encoding);
			
			// 找第一个非空白字符, 从而判断它是JSONObject 还是JSONArray
			int type = guessType ( jsonstr);
			
			// 如果以{开头，则转成JSONObject; 如果以[开头，则转成 JSONArray
			if(type == 1)
			{
				return new JSONObject( jsonstr );
			}
			else if( type == 2)
			{
				return new JSONArray ( jsonstr);
			}
			else
			{
				throw new Exception("JSON must begin with { or [ !");
			}
			
		}finally {
			try {inputStream.close();} catch(Exception e) {}
		}
	}
	
	//////////////////////////////////////////////////
	// 带缺省值得get方法
	public static int getInt (JSONObject json, String key, int defValue)
	{
		try {
			return json.getInt(key);
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static long getLong (JSONObject json, String key, long defValue)
	{
		try {
			return json.getLong(key);
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static String getString (JSONObject json, String key, String defValue)
	{
		try {
			return json.getString(key);
		}catch(Exception e)
		{
			return defValue;
		}
	}
	public static boolean getBoolean (JSONObject json, String key, boolean defValue)
	{
		try {
			return json.getBoolean(key);
		}catch(Exception e)
		{
			return defValue;
		}
	}
}
