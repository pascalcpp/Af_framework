package af.common.net;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AfTcpSend
{
	OutputStream outstream;

	public AfTcpSend(Socket s) throws Exception
	{
		outstream = s.getOutputStream();
	}
	
	public void close()
	{
		try { outstream.close();}catch(Exception e) {}
	}
	
	public void send(short type, short length, byte[] data) throws Exception
	{
		byte[] h = new byte[4];
		ByteBuffer hbuf = ByteBuffer.wrap(h).order(ByteOrder.BIG_ENDIAN);
		hbuf.putShort(type);
		hbuf.putShort(length);
		outstream.write(h);
		
		if(length > 0)
		{
			outstream.write(data, 0, length);
		}
	}
	
	public void sendString (short type, String text, String charset) throws Exception
	{
		byte[] data = text.getBytes(charset);
		send(type, (short)data.length, data);
	}
}
