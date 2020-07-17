package af.web.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

/** 快速SSI框架
 *  使用时，须派生子类并重写相应的方法
 */
public abstract class AfSimpleSSI implements Filter
{
	// 单一实例
	protected static Configuration frmkConfig;
	
	@Override
	public void init(FilterConfig filterCfg) throws ServletException
	{	
		// 取得APP所在目录 
		if(frmkConfig == null)
		{
			File docBase = new File(filterCfg.getServletContext().getRealPath("/"));
			initFreeMarker(docBase);
		}	
	}

	private void initFreeMarker(File docBase)
	{
		try{
			frmkConfig = new Configuration(Configuration.VERSION_2_3_28);
			frmkConfig.setDirectoryForTemplateLoading(docBase); // 设置模板根目录
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
	public void destroy()
	{
	}
	
	// 子类重写这个方法, 决定是否作为SSI处理
	protected abstract boolean useSSI(HttpServletRequest req,String servletPath);

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// 解出 ServletPath
		// 注意: 不能使用 getServlet()方法, 因为在映射路径规则为 /path/* 这种时，无法得到真正的路径 
		String contextPath = request.getServletContext().getContextPath();
		String requestUri = request.getRequestURI();
		String servletPath = requestUri.substring(contextPath.length());		
		// System.out.println("Filter: " + servletPath);
			
		// 允许部分路径不使用SSI
		if( ! useSSI ( request, servletPath ))
		{
			chain.doFilter(req, resp);
			return; // 此文件不使用SSI，直接放行
		}		
		
		// 首次运行时，加载HTML文件，解析预处理得到Template对象，并存到Configuration内的Template Cache里
		// 再次运行时，直接从Template Cache获取Template对象
		Template tp = null;
		try{
			tp = frmkConfig.getTemplate(servletPath); 
		}catch(TemplateNotFoundException ex)
		{
			response.sendError(404, "File Not Exist: " + servletPath );
			return; // 目标HTML不存在，则直接返回404
		}
		
		// 处理并返回应答
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		try{
			Map<String,Object> model = new HashMap<String,Object>();
			tp.process(model, resp.getWriter()); // 输出给客户端
		}catch(Exception e)
		{
			e.printStackTrace();
			response.sendError(500, e.getMessage());
		}
	}	
	

	
	
}
