package my.activities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import af.sql.AfMySQL;
import af.sql.AfSqlConnection;
import af.swing.AfButton;
import af.swing.AfPanel;
import af.swing.AfToaster;
import af.swing.activity.AfActivity;
import af.swing.layout.AfColumnLayout;
import af.swing.layout.AfRowLayout;
import af.swing.thread.AfShortTask;
import my.DBInfo;
import my.controls.CommonButton;
import my.controls.DefaultButton;
import my.listview.SimpleListView;

public class DataBaseActivity extends AfActivity
{	
	SimpleListView<String> listView = new SimpleListView<>();
	//
	AfButton nextButton = new DefaultButton("下一步");
	AfButton backButton = new CommonButton("上一步");
	
	// 
	DBInfo info;
	
	
	public DataBaseActivity()
	{
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		// 
		this.add(initTop(), BorderLayout.PAGE_START);	
		// 
		this.add(initCenter(), BorderLayout.CENTER);
		//
		this.add(initToolBar(), BorderLayout.PAGE_END);
		
		//
//		listView.addItem("af_school");
//		listView.addItem("af_library");
//		listView.addItem("mysql");
	}
	
	private JComponent initTop()
	{
		AfPanel panel = new AfPanel();
		//panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xC0C0C0)));
		panel.setLayout(new AfRowLayout(4));
		panel.padding(2);
		panel.preferredHeight(30);
		panel.setOpaque(false);
		
		JLabel heading = new JLabel("2. 选择数据库名");
		heading.setForeground(new Color(0xE0393D49));
		panel.add(heading);
		return panel;
	}
	
	// 中央面板
	private JComponent initCenter()
	{
		AfPanel panel = new AfPanel();
		panel.setLayout(new AfColumnLayout(4));
		panel.padding(2);
		panel.setOpaque(false);
		
		panel.add(new JScrollPane(listView), "1w");
		
		// 单选
		listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		return panel;
	}
	
	// 工具按钮
	private JComponent initToolBar()
	{
		AfPanel panel = new AfPanel();
		panel.setLayout(new AfRowLayout(4));
		panel.padding(8);
		panel.preferredHeight(50);
		panel.setOpaque(false);
		
		
		panel.add(backButton);
		panel.add(new JLabel(), "1w");		
		panel.add(nextButton);
		//panel.add(new JLabel(), "1w");
		
		// 
		nextButton.addActionListener( (e)->{
			onNext();
		});
		
		backButton.addActionListener( (e)->{
			finish();
			startActivity(InfoActivity.class, null);
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
		
		listView.clear();
		
		new QueryTask().execute();
	}
	
	// '下一步'
	private void onNext()
	{
		String dbName = listView.getSelectedValue();
		if(dbName == null) 
		{
			AfToaster.show(this, "请选择数据库!");
			return ;
		}
		
		//
		info.database = dbName;
		info.save();
		
		//
		finish();
		startActivity(TableActivity.class, null);
		
	}	
	
	private class QueryTask extends AfShortTask
	{
		List<String> nameList = new ArrayList<>();
		
		@Override
		protected void doInBackground() throws Exception
		{
			String sql = "select schema_name from schemata";
			ResultSet rs = info.conn.executeQuery(sql);
			while(rs.next())
			{
				String name = rs.getString(1);
				
				// 不显示系统数据库
				if(true)
				{
					if(name.compareToIgnoreCase("mysql")==0
							|| name.compareToIgnoreCase("information_schema")==0
							|| name.compareToIgnoreCase("performance_schema")==0)
					{
						continue;
					}
				}
				
				nameList.add(name);
			}				
		}

		@Override
		protected void done()
		{
			if(this.err != null)
			{
				AfToaster.show(listView, AfToaster.WARN, err.getMessage());
				return;
			}
			for(String name: nameList)
			{
				listView.addItem( name );
			}
			
			// 默认选中
			if(info.database.length() >0)
			{
				listView.selectItem(info.database, true);
			}
		}
		
	}
}
