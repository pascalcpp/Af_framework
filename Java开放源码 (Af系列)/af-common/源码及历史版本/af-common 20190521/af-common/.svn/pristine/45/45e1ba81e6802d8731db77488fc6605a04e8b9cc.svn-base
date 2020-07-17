package af.common.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AfProcess
{
	// 命令行
	private String[] args = null;
	private File workingDir = null;
	private boolean wantOutput = false;
	private String outputEncoding = "UTF-8"; // OR "GBK"
	private String outputText = ""; // 目标进程的打印输出
	
	private int retCode = 0;   // 目标进程的返回码 , 一般0表示成功
	private Observer observer ; // 监听器, 如果想实时检查目标进程的输出, 则添加监听器
	private Process process; // 启动的目标进程
	
	public AfProcess()
	{		
	}
	
	// 监听器机制
	public interface Observer
	{
		public void stdout(String line);  // 进程有输出		
	}
	
	// 自动给每个参数加上双引号
	public AfProcess setCommand(String[] args)
	{
		int n = args.length;
		this.args = new String[n];
		for(int i=0; i<n; i++)
		{
			String a = args[i];
			// 如果没有双引号，则给它加上双引号
			if(! a.startsWith("\""))
			{
				if(a.indexOf(' ') >= 0 || a.indexOf('\t')>=0)
				{
					a = "\"" + a + "\"";
				}				
			}
			this.args[i] = a;
		}
		
		return this;
	}
	
	public AfProcess setCommand(String shellCommand)
	{
		List<String> parts = parseShellCommand(shellCommand);
		//this.args = (String[]) parts.toArray();
		this.args = new String[ parts.size()];
		for(int i=0; i<parts.size(); i++)
		{
			this.args[i] = parts.get(i);
		}
		return this;
	}
	
	// 获取完全命令, 用于打印调试
	public String getShellCommand()
	{
		if(args == null) return "null";
		
		String shellCommand = "";
		
		for(String s : args)
		{
			shellCommand += s + " ";
		}
		return shellCommand;
	}
	
	// 设置工作目录 
	// 如未设置，则默认取程序文件所在的目录为工作目录 
	public AfProcess setWorkingDir(File dir)
	{
		this.workingDir = dir;
		return this;
	}
	
	public File getWorkingDir()
	{
		return this.workingDir;
	}

	
	// 是否需要接收程序输出的文本, 默认为false
	public AfProcess setWantOutput(boolean wantOutput)
	{
		this.wantOutput = wantOutput;
		return this;
	}
	
	public boolean isWantOutput()
	{
		return this.wantOutput;
	}
	
	// 设置是否需要接收程序的打印输出， 默认为 UTF-8
	public AfProcess setOutputEncoding(String charset)
	{
		this.outputEncoding = charset;
		return this;
	}
	
	public String getOutputEncoding()
	{
		return this.outputEncoding;
	}
	
	// 设置监听器，默认为null
	public AfProcess setObserver( Observer observer)
	{
		this.observer = observer;
		return this;
	}
	
	// 获取进程的返回值	
	public int getReturnCode()
	{
		return this.retCode;
	}
	
	// 获取进程输出的文本
	public String getOutputText()
	{
		return this.outputText;
	}
	
	public Process getProcess()
	{
		return this.process;
	}
	
	// 启动进程, waitForResult为true则等待其结束
	public void execute(boolean waitForResult) throws Exception
	{
	    // 设置工具目录 
		if(this.workingDir == null)
		{	       
        	try {
        		String program = args[0];
        		if(program.charAt(0) == '\"')
        			program = program.substring(1, program.length()-1);
        		
        		// 获取程序文件所在的目录为Working Directory
        		File programFile = new File(program);
        		if(programFile.exists() && programFile.isFile())
        		{
        			this.workingDir = programFile.getParentFile();
        		}
        	}catch(Exception e) {}
	       
		}
		      
        // 用 ProcessBuilder 创建进程
        ProcessBuilder pBuilder = new ProcessBuilder(args); 
        if(workingDir!= null) pBuilder.directory(workingDir);
        
        // 启动进程
        process = pBuilder.start();      
        
        // 如果不需要等待结束, 则直接返回
        if(! waitForResult) return;
        
        // 获取程序的输出
        try {
        	InputStream inputStream = process.getInputStream();
            
            if(wantOutput)
            {
            	this.outputText = "";
            	 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, outputEncoding));
                 while(true)
                 {
                 	String line = reader.readLine();
                 	if(line == null) break;
                 	
                 	line += "\r\n";
                 	outputText += line;
                 	if(observer != null)
                 		observer.stdout(line);
                 }
            }
            else
            {
            	// 读取并丢弃
            	byte[] buf = new byte[1024];
            	while(true)
            	{
            		int n = inputStream.read(buf);
            		if(n <= 0) break;
            	}
            }  
            
        }catch(Exception e)
        {
        	
        }
        
        // 取得返回码
        this.retCode  = process.waitFor();        
	}
	
	public static List<String> parseShellCommand(String shellCommand)
	{
		List<String> parts = new ArrayList<>();
		
		int start = -1;
		boolean quoted = false;
		
		char[] chars = shellCommand.toCharArray();
		
		// i=chars.length 便于用统一的算法计算最后一个分段
		for(int i=0; i<= chars.length; i++)
		{
			int stop = -1;
			char ch = ' ';
			if(i < chars.length)  ch = chars[i]; // 末尾的分段不要漏掉
			
			if(ch == ' ' || ch == '\t') // 空格或制表符 
			{
				if(quoted) continue;
				if(start < 0) continue;
				stop = i;
			}
			else if(ch == '\"') // 双引号
			{
				if(!quoted)
				{
					// 开时分段
					start = i;
					quoted = true;
					continue;
				}
				else
				{
					// 结束分段
					quoted = false;
					stop = i+1; // 包含结尾的分号
				}
			}
			else
			{
				// 开始分段
				if(start < 0) start = i;
				continue;
			}
			
			if(stop >= 0)
			{
				String part = new String(chars, start, stop-start);
				start = -1; // 归于初始状态
				//System.out.println("[" + part + "]");
				
				// 去掉空白字符后加到列表中
				part = part.trim(); 
				if(part.length() > 0)
					parts.add( part);
			}			
		}
		
		return parts;
		
	}
	
	///////////////////// 包装方法 ///////////////
	// 运行进程，不等待进程结束
	public static AfProcess run(String shellCommand) throws Exception
	{
		AfProcess p = new AfProcess();
		p.setCommand(shellCommand);
		p.execute(false);
		
		return p;
	}
	
	// 运行进程，等待进程结束
	public static AfProcess runAndWait(String shellCommand) throws Exception
	{
		AfProcess p = new AfProcess();
		p.setCommand(shellCommand);
		p.execute(true);
		return p;
	}
	
	// 运行进程，等待进程结束, 并取得输出结果
	public static AfProcess runAndWait(String shellCommand, String outputEncoding) throws Exception
	{
		AfProcess p = new AfProcess();
		p.setCommand(shellCommand);
		p.setWantOutput(true);
		p.setOutputEncoding(outputEncoding);
		p.execute(true);
		return p;
	}
	
	// 运行进程，等待进程结束, 并取得输出结果, 并设置监听器
	public static AfProcess runAndWait(String shellCommand, String outputEncoding, Observer observer) throws Exception
	{
		AfProcess p = new AfProcess();
		p.setCommand(shellCommand);
		p.setWantOutput(true);
		p.setOutputEncoding(outputEncoding);
		p.setObserver(observer);
		p.execute(true);
		return p;
	}

}
