package af.swing.popup;

public class AfPopup
{
	// 窗口关闭策略
	public static class ClosingPolicy
	{
		public boolean autoClose = true; // 自动关闭
		
		public ClosingPolicy()
		{			
		}
		public ClosingPolicy(boolean autoClose)
		{
			this.autoClose = autoClose;
		}
	}
}
