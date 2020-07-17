package af.web.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import af.web.AfWebException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public abstract class AfSimpleMVC extends HttpServlet
{
	// 全局单一实例
	protected static Configuration frmkConfig;
	
	@Override
	public void init() throws ServletException
	{
		if(frmkConfig == null) initFreeMarker();
	}
	
	private void initFreeMarker()
	{
		// 取得APP所在目录 
		File appRoot = new File(getServletContext().getRealPath("/"));
		try{
			frmkConfig = new Configuration(Configuration.VERSION_2_3_28);
			frmkConfig.setDirectoryForTemplateLoading(appRoot); // 设置模板根目录
			frmkConfig.setDefaultEncoding("UTF-8");
			frmkConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			frmkConfig.setLogTemplateExceptions(false);
		}
		catch(Exception e)
		{		
			System.out.println("This Should Not Happen!");
		}
	}	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		// 生成 model
		Map<String,Object> model = new HashMap<String,Object>();
		String view = null;
		try{
			// execute()的返回值为 view
			view = execute(req, resp, model);
			
		}catch(AfWebException e)
		{
			if(e.error == 302) 
				resp.sendRedirect(e.reason); // 302 重定向
			else if(e.error > 0)
				resp.sendError(e.error, e.reason); // 404等其他标准错误
			return;
		}
		catch(Exception e)
		{
			// 一般性错误
			resp.sendError(500, e.getMessage());
			return;
		}
		
		// 以下为固定套路, 根据 model 和 view 作出应答
		Template tp = null;
		try{
			tp = frmkConfig.getTemplate(view); 
		}catch(TemplateNotFoundException ex)
		{
			resp.sendError(404, "Cannot find view: " + view );
			return; // 目标HTML不存在，则直接返回404
		}
		
		// 处理并返回应答
		try{			
			tp.process(model, resp.getWriter()); // 输出给客户端
		}catch(Exception e)
		{
			//e.printStackTrace();
			resp.sendError(500, e.getMessage());
		}
	}
	
	// 子类必重写这个方法: 生成数据到model, 并返回指定的view
	// 此方法必须支持线程重入！
	protected abstract String execute(HttpServletRequest req 
			, HttpServletResponse resp
			, Map<String,Object> model ) throws Exception;

}
