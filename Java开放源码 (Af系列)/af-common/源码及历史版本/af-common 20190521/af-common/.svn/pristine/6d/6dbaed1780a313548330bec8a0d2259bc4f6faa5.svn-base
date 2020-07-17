package my;

import java.util.ArrayList;
import java.util.List;

public class Test
{
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
	
	public static void main(String[] args)
	{
		//setShellCommand(" ffmpeg.exe \"c:\\program files\\abc.mp4\" -y -v ");
		parseShellCommand(" aa \"b b\" -cc -dd \"ee ff\" d");
	}

}
