package af.web;

/**
 * 通用的异常类,带 error和reason两个属性
 * @author shaofa
 * @since 20190601
 *
 */
public class AfWebException extends Exception
{
	public int error;
	public String reason;
	
	public AfWebException()
	{		
	}
	public AfWebException(int error, String reason)
	{
		this.error = error;
		this.reason = reason;
	}
	@Override
	public String getMessage()
	{
		return reason + "(" + error + ")";
	}
	
	
}
