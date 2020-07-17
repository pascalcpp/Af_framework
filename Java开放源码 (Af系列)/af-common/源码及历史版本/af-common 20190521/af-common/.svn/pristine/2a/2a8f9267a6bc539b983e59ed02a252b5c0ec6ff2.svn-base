package af.common.net;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AfTcpRecv
{
	InputStream instream;

	public short type; 
	public short length; 
	public byte[] data = new byte[1024*128]; 
	
	
	public AfTcpRecv(Socket s) throws Exception
	{
		instream = s.getInputStream();
	}
	
	// 仅关闭socket还不够，还要把这个stream关闭，否则 recvN时可能会卡住
	public void close()
	{
		//System.out.println("close the stream...");
		try { instream.close();}catch(Exception e) {}
	}
	
	public void clear()
	{
		type = 0;
		length = 0;
	}
		
	public void recv() throws Exception
	{
		byte[] h = new byte[4];	
		recvN(h, 0,4);
		ByteBuffer hbuf = ByteBuffer.wrap(h).order(ByteOrder.BIG_ENDIAN);
		this.type = hbuf.getShort();
		this.length = hbuf.getShort();
		
		if(this.length > this.data.length)
		{
			throw new Exception("数据封包太大! must < " + this.data.length);
		}
		if(length > 0)
		{
			recvN(data, 0, length);
		}
	}
	public String asString(String charset)
	{
		try{
			String str = new String(this.data, 0, this.length, charset);
			return str;
		}catch(Exception e)
		{
			return "";
		}
	}
	
	///////////////////////
	private void recvN(byte[] buf, int off, int size) throws Exception
	{
		int bytesGot = 0;
		while (true)
        {
			int remain = size - bytesGot;
			if(remain == 0) break;
			
        	int n = instream.read(buf, off+bytesGot, remain);
        	if(n <= 0) 
        	{
        		throw new Exception("接收出错!连接可能已经中断！");
        	}
        	bytesGot += n;
        } 		
	}
	
}
