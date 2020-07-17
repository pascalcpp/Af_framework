package my;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import af.swing.activity.AfActivity;
import af.swing.activity.AfActivityPane;
import my.activities.DataBaseActivity;
import my.activities.InfoActivity;

public class AppFrame extends JFrame
{	
	AfActivityPane activityPane = new AfActivityPane();
	
	// 注意是 javax.swing.Timer
	Timer timer;
	long lastActiveTime = 0; // 上一次用户活动的时间
	final int MAX_IDLE_TIME = 10; // 最大发呆间隔
	
	public AppFrame()
	{
		super("POJO生成器 for afsql");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 540);
		setResizable(false);
		
		JPanel root = new RootPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		
		// Content Pane		
		root.add(activityPane, BorderLayout.CENTER);
		activityPane.setOpaque(false);
		
		// 启动主界面
		DBInfo info = new DBInfo();
		info.load();
		activityPane.putParam("info", info);
		activityPane.startActivity(InfoActivity.class, null);

	}



}
