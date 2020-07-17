package my.activities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import af.sql.AfMySQL;
import af.sql.AfSqlConnection;
import af.swing.AfButton;
import af.swing.AfEditText;
import af.swing.AfPanel;
import af.swing.AfToaster;
import af.swing.activity.AfActivity;
import af.swing.layout.AfColumnLayout;
import af.swing.layout.AfRowLayout;
import af.swing.thread.AfShortTask;
import my.DBInfo;
import my.Version;
import my.controls.DefaultButton;

public class InfoActivity extends AfActivity
{
	//
	AfEditText serverField = new AfEditText();
	AfEditText userField = new AfEditText();
	AfEditText passwordField = new AfEditText();
	
	//
	AfButton nextButton = new DefaultButton("下一步");
	
	// 
	DBInfo info;
	
	public InfoActivity()
	{	
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		// 
		this.add(initTop(), BorderLayout.PAGE_START);	
		// 
		this.add(initCenter(), BorderLayout.CENTER);		
		// 
		this.add(initToolBar(), BorderLayout.PAGE_END);		
	}
	
	private JComponent initTop()
	{
		AfPanel panel = new AfPanel();
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xC0C0C0)));
		panel.setLayout(new AfRowLayout(4));
		panel.padding(2);
		panel.preferredHeight(30);
		panel.setOpaque(false);
		
		JLabel heading = new JLabel("1. 连接到MySQL");
		heading.setForeground(new Color(0xE0393D49));
		panel.add(heading);
		return panel;
	}
	
	private JComponent initCenter()
	{
		AfPanel panel = new AfPanel();
		panel.setLayout(new AfColumnLayout(4));
		panel.padding(50,10,10,10);
		panel.setOpaque(false);
		
		panel.add(serverField, "30px");
		panel.add(userField, "30px");
		panel.add(passwordField, "30px");
		panel.add(new JLabel(), "1w");
		
		JLabel a1 = new JLabel("本软件是《Java学习指南 - 数据库篇》配套工具");
		a1.setFont(new Font("宋体", Font.PLAIN,12));
		a1.setForeground(new Color(0xA0A0A0));
		panel.add(a1, "20px");
		
		JLabel a2 = new JLabel("http://afanihao.cn");
		a2.setFont(new Font("宋体", Font.PLAIN,12));
		a2.setForeground(new Color(0xA0A0A0));
		panel.add(a2, "20px");

		JLabel a3 = new JLabel(Version.build);
		a3.setFont(new Font("宋体", Font.PLAIN,12));
		a3.setForeground(new Color(0xA0A0A0));
		panel.add(a3, "20px");
		
		serverField.setPlaceHolder("服务器IP");
		userField.setPlaceHolder("数据库用户名");
		passwordField.setPlaceHolder("数据库密码");
		
		return panel;
	}
	
	private JComponent initToolBar()
	{
		AfPanel panel = new AfPanel();
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0xC0C0C0)));
		panel.setLayout(new AfRowLayout(4));
		panel.padding(8);
		panel.preferredHeight(50);
		panel.setOpaque(false);
		panel.setBackground(new Color(0xA0FFFFFF,true));
		
		panel.add(new JLabel(), "1w");
		panel.add(nextButton);
		//panel.add(new JLabel(), "1w");
		
		// 
		nextButton.addActionListener( (e)->{
			onNext();
		});
		
		return panel;
	}
	
	@Override
	public void onCreate(Object arg0)
	{
		
	}

	@Override
	public void onStart()
	{
		info = (DBInfo) context.getParam("info", null);
		
		serverField.setText(info.server);
		userField.setText(info.user);
		passwordField.setText(info.password);
	}
	
	// '下一步'
	private void onNext()
	{
		new ConnectTask().execute();
	}
	
	private class ConnectTask extends AfShortTask
	{
		@Override
		protected void doInBackground() throws Exception
		{
			info.server = serverField.getText().trim();
			info.user = userField.getText().trim();
			info.password = passwordField.getText().trim();
			if(info.server.isEmpty() || info.user.isEmpty())
				throw new Exception("服务器 或 用户名 不得为空!");
			
			info.conn = new AfSqlConnection();
			String jdbcUrl = AfMySQL.jdbcUrl(info.server, 3306, "information_schema");
			info.conn.connect(jdbcUrl, info.user, info.password);
		}

		@Override
		protected void done()
		{
			if(this.err != null)
			{
				AfToaster.show(serverField, AfToaster.WARN, err.getMessage());
				return;
			}
			
			info.save();
			
			//
			finish();
			startActivity(DataBaseActivity.class,null);
		}
		
	}
}
