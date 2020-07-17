package af.web.freemarker;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class AfCustomTag implements TemplateDirectiveModel
{
	// ObjectWrapper是 FreeMarker设计的一个机制，可以把普通对象放到model里
	protected ObjectWrapper objectWrapper;
	
	public AfCustomTag(ObjectWrapper objectWrapper)
	{
		this.objectWrapper = objectWrapper;
	}
	
	@Override
	public abstract void execute(Environment env,
            Map params, 
            TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException,IOException
	;
	
	//////////// 获取标签参数 /////////////
	protected String getParamString(Map params, String key, String defVal)
	{
		try{
			return params.get(key).toString() ;
		}catch(Exception ex)
		{
			return defVal;
		}
	}
	
	protected Integer getParamInt(Map params, String key, Integer defVal)
	{
		try{
			return Integer.valueOf( params.get(key).toString() );
		}catch(Exception ex)
		{
			return defVal;
		}
	}
	
	protected Long getParamLong(Map params, String key, Long defVal)
	{
		try{
			return Long.valueOf( params.get(key).toString() );
		}catch(Exception ex)
		{
			return defVal;
		}
	}
	
	protected Boolean getParamBoolean(Map params, String key, Boolean defVal)
	{
		try{
			String v = params.get(key).toString();
			if(v == null) return defVal;
			return "true".equals(v) || "yes".equals(v) || "1".equals(v);
		}catch(Exception ex)
		{
			return defVal;
		}
	}	

	
	//////////// 从Model中获取变量的值 //////////////
	protected Long getEnvLong(Environment env, String key, Long defVal)
	{		
		try{
			return Long.valueOf( env.getVariable(key).toString() );
		}catch(Exception ex)
		{
			return defVal;
		}
	}
	
	protected Integer getEnvInt(Environment env, String key, Integer defVal)
	{
		try{
			return Integer.valueOf( env.getVariable(key).toString() );
		}catch(Exception ex)
		{
			return defVal;
		}
	}
	
	protected String getEnvString(Environment env, String key, String defVal)
	{
		try{
			return env.getVariable(key).toString() ;
		}catch(Exception ex)
		{
			return defVal;
		}
	}
	
	protected Boolean getEnvBoolean(Environment env, String key, Boolean defVal)
	{
		try{
			String v = env.getVariable(key).toString();
			if(v == null) return defVal;
			return "true".equals(v) || "yes".equals(v) || "1".equals(v);
		}catch(Exception ex)
		{
			return defVal;
		}
	}		
}
