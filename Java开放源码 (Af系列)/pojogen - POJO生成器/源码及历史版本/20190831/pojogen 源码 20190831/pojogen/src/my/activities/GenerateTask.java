package my.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;

import af.sql.AfSql;
import af.sql.AfSqlReflect;
import af.sql.AfSqlWhere;
import af.sql.mapping.AfSqlPojo;
import my.DBInfo;

public class GenerateTask extends Thread
{
	public DBInfo info;	
	public Runnable callback;	
	public File dir = new File("output");
	
	public void startWork(DBInfo info, Runnable callback)
	{
		this.info = info;
		this.callback = callback;
		
		start();
	}
	
	@Override
	public void run()
	{						
		// 先清除原有文件
		try {
			//if(generated.exists()) generated.delete();
			FileUtils.deleteQuietly(dir);
		}catch(Exception e) {}
		
		dir.mkdirs();
		
		for(String table: info.tables)
		{
			try {
				AfSqlPojo pojo = tableInfo ( table);
				
				pojo.name = AfSql.className4Table(table);
				File file = new File(dir, pojo.name + ".java");
				generateJavaClass(pojo,  file);
				showProgress(true, "生成: " + file.getAbsolutePath());
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
		
		// 任务已完成 ...
		if(callback != null)
			SwingUtilities.invokeLater( callback);
	}
	
	public void showProgress(boolean ok, String text)
	{
		System.out.println(text);
	}
	
	private AfSqlPojo tableInfo (String table) throws Exception
	{
		AfSqlPojo pojo = new AfSqlPojo();
		pojo.table = table;
		
		AfSqlWhere where = new AfSqlWhere();
		where.add2("table_schema", info.database);
		where.add2("table_name", table);
		
		String sql = "select column_name,data_type,column_type,extra from columns" + where;
		ResultSet rs = info.conn.executeQuery(sql);
		while(rs.next())
		{
			AfSqlPojo.Property c = new AfSqlPojo.Property();
			c.name = rs.getString(1);
			c.type = rs.getString(2);
			c.arg1 = rs.getString(3);
			c.arg2 = rs.getString(4);
			pojo.properties.add( c );
		}	
		
		return pojo;
	}
	

	
	private void generateJavaClass(AfSqlPojo pojo, File file) throws Exception
	{
		String attributes = "";
		String functions = "";
		
		String autoIncrementKey = null;
		
		// 生成 Insert语句
		String s1="" , s2 = "", s3="";
		// insert int tableName (`s`,`s`) values(#{id},#{name})
		
		// 生成属性和方法
		for(AfSqlPojo.Property p : pojo.properties)
		{
			String attr = p.name;
			String type = "String";
			if(p.arg2.indexOf("auto_increment")>= 0)
				autoIncrementKey = attr;
		
			if(p.type.equals("bigint")) type = "Long";
			if(p.type.equals("int")) type = "Integer";
			if(p.type.equals("smallint")) type = "Short";
			if(p.type.equals("tinyint")) type = "Byte";

			if(p.type.equals("double")) type = "Double";
			if(p.type.equals("float")) type = "Float";
			
			if(p.type.equals("datetime")) type = "Date";
			if(p.type.equals("date")) type = "Date";
			if(p.type.equals("timestamp")) type = "Date";
			
			if(p.type.equals("bit")) type = "Boolean";

			if(p.type.equals("tinyint")) 
			{
				type = "Byte";
				if(p.arg1.equals("tinyint(1)")) type= "Boolean";
			}
			
			// insert语句
			if(true)
			{
				String field = p.name;
				if(s1.length()>0) s1 += ", ";
				s1 += "`" + field + "`";
				
				if(s2.length() > 0) s2 += ", ";
				s2 += "?";
				
				if(s3.length() > 0) s3 += ", ";
				s3 += "#{" + field + "}";
			}
			
			// attributes
			if(true)
			{
				attributes += String.format("\tpublic %s %s ; \r\n", type, attr);
			}
				
			// setters and getters
			if(true)
			{					
				String func = AfSqlReflect.setter(attr );
				String fmt = "\tpublic void %s(%s %s)\r\n"
						+ "\t{\r\n"
						+ "\t\tthis.%s=%s;\r\n"
						+ "\t}\r\n";
				String str = String.format(fmt, func, type, attr, attr, attr);
				functions += str;
			}
			if(true)
			{					
				String func = AfSqlReflect.getter(attr );
				String fmt = "\tpublic %s %s()\r\n"
						+ "\t{\r\n"
						+ "\t\treturn this.%s;\r\n"
						+ "\t}\r\n";
				String str = String.format(fmt, type, func, attr);
				functions += str;
			}
		}
		
		String text = "";
		
		text += String.format("package %s; \r\n\r\n",  info.pkgName);
		
		// 如果生成afsql的POJO，则需添加注解
		if("afsql".equals(info.apiType))
		{
			text += "import af.sql.annotation.AFCOLUMNS; \r\n";
			text += "import af.sql.annotation.AFTABLE; \r\n";
		}
		
		text += "import java.util.Date; \r\n";
		text += "\r\n";
		
		// 加上日期日期
		if(true)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			text += "/** 本类由 POJO生成器 自动生成于 " + sdf.format(new Date()) + "\r\n";
			text += "    作者：阿发你好      官网: http://afanihao.cn " + "\r\n";
			text += "*/ \r\n";
			text += "\r\n";		
		}
		
		// 显示INSERT语句 ( 预处理方式 )
		if(true)
		{
			text += "/** INSERT语句 ( 预处理方式 ) \r\n";
			text += "  INSERT INTO `" + pojo.table + "`" + "\r\n";
			text += "        (" + s1 + ") \r\n";
			text += "  VALUES(" + s2 + ") \r\n";
			text += "*/ \r\n";
			text += "\r\n";
		}
		// 显示INSERT语句 ( MyBatis方式 )
		if(true)
		{
			text += "/** INSERT语句 ( MyBatis方式 ) \r\n";
			text += "  INSERT INTO `" + pojo.table + "`" + "\r\n";
			text += "        (" + s1 + ") \r\n";
			text += "  VALUES(" + s3 + ") \r\n";
			
			text += "\r\n" + "  自增主键: " ;
			if(autoIncrementKey != null) text += autoIncrementKey;
			else text += "无";
			text += "\r\n";
			
			text += "*/ \r\n";
			
			text += "\r\n";
		}
		
		if("afsql".equals(info.apiType))
		{
			text += String.format("@AFTABLE(name=\"%s\")  \r\n", pojo.table);
			if(autoIncrementKey == null)
				text += String.format("@AFCOLUMNS() \r\n");
			else
				text += String.format("@AFCOLUMNS(generated=\"%s\") \r\n", autoIncrementKey);		
		}
		
		text += String.format("public class %s \r\n", pojo.name);
		text += "{ \r\n ";
		// 生成方法
		text += "\r\n" + attributes + "\r\n";
		text += "\r\n" + functions + "\r\n";
		text += "} \r\n ";	
		
		FileOutputStream ostream = new FileOutputStream(file);
		ostream.write( text.getBytes("UTF-8") );
		ostream.close();
	}
	
}
