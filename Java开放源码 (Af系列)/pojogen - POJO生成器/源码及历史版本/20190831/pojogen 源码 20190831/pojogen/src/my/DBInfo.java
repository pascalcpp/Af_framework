package my;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import af.common.json.AfJSON;
import af.sql.AfSqlConnection;

public class DBInfo
{
	public String server;
	public String user;
	public String password;
	
	public String database; // 数据库名
	public List<String> tables = new ArrayList<>(); // 表名
	
	public String pkgName;
	public String apiType = "afsql"; // afsql, general
	
	public AfSqlConnection conn;
	
	public File file = new File("config.json");
	
	public void load()
	{
		if(! file.exists()) return;
		
		try {
			JSONObject json = (JSONObject) AfJSON.fromFile(file, "UTF-8");
			server = json.optString("server","");
			user = json.optString("user","");
			password = json.optString("password", "");
			
			database = json.optString("database", "");
			
			// 表名
			JSONArray jarray = json.optJSONArray("tables");
			if(jarray != null)
			{
				tables.clear();
				for(int i=0; i<jarray.length(); i++)
				{
					String name = jarray.optString(i, null);
					if(name != null)
						tables.add( name);					
				}
			}
			
			// 包名
			pkgName = json.optString("pkgName", "my.db");
			apiType = json.optString("apiType", "afsql");
		}catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	public void save()
	{
		try {
			JSONObject json = new JSONObject();
			json.put("server", server);
			json.put("user", user);
			json.put("password", password);
			
			json.put("database", database);
			
			if(tables != null)
				json.put("tables", new JSONArray(tables));
			
			json.put("pkgName", pkgName);
			json.put("apiType", apiType);
			
			AfJSON.toFile(json, file, "UTF-8");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
