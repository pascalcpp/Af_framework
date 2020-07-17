package af.common.crypto;

public class AfCrypto
{
	// 将Key限制在N字节，若超长则截断，若太短则补零
	public static byte[] prepareKey(byte[] key, int N)
	{
		if(key.length == N)
			return key;
		if(key.length > N)
			return truncate(key, N);		
		if(key.length < N)
			return padding(key, N, (byte)0);
		return null;
	}
	
	// 如果 data超长，则截断为 N 字节
	public static byte[] truncate(byte[] data, int N)
	{
		if(data.length <= N) 	return data;
		
		byte[] result = new byte[N];
		int i=0;
		for( ; i< N; i++)
			result[i] = data[i];
		return result;			
	}
	
	// 如果data长度不足N，则补齐为N字节
	public static byte[] padding(byte[] data, int N, byte p)
	{
		int r = data.length % N; // 是否已经为N对齐
		if( r == 0) 
			return data;
		
		// 复制
		int N2 = data.length + (N-r);		
		byte[] result = new byte[N2];
		int i=0;
		for( ; i< data.length; i++)
			result[i] = data[i];

		// 补齐
		for( ;i < N2 ; i++)
			result[i] = p;
		return result;
	}
	
	// 检查 data长度是否N的整数倍。  如果不对齐，则在后面补充几个零，并返回新的数组
	public static byte[] zeroPadding(byte[] data, int N)
	{
		int r = data.length % N; 
		if( r == 0) return data; // 如果已经N字节对齐，则直接返回
		
		// 复制
		int N2 = data.length + (N-r);		
		byte[] result = new byte[N2];
		int i=0;
		for( ; i< data.length; i++)
			result[i] = data[i];

		// 补充几个零
		for( ;i < N2 ; i++)
			result[i] = 0;
		return result;
	}
	
	// 按 PKCS#5 方式补齐
	// data 输入 数据
	// N 字节对齐  ( DES为8字节一组, AES为16字节一组)
	public static byte[] pkcs5Padding(byte[] data, int N)
	{
		int r = data.length % N; 
		
		// 复制
		int N2 = data.length + (N-r);		
		byte[] result = new byte[N2];
		int i=0;
		for( ; i< data.length; i++)
			result[i] = data[i];

		// 补齐
		byte p = (byte) (N-r); // 要补齐的值
		for( ;i < N2 ; i++)
			result[i] = p;
		return result;
	}
	
	// 去除末尾补充的数据
	public static byte[] pkcs5Trim(byte[] data, int N)
	{
		// PKCS5方式的最后一个值，就是补充的个数
		int size = data.length;
		int numPadding = data[ size - 1]; 
		
		// 复制
		int N2 = size - numPadding;
		byte[] result = new byte[N2];
		for(int i=0; i<N2; i++)
			result[i] = data[i];
		
		return result;
	}
	
}
