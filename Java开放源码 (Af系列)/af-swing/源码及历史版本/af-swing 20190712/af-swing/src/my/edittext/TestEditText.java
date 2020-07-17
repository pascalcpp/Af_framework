package my.edittext;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import af.swing.AfEditText;
import af.swing.AfTextArea;

public class TestEditText extends JFrame
{
	public TestEditText()
	{
		super("test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,350);
		
		JPanel root = new JPanel();
		this.setContentPane(root);
		
		AfEditText edit = new AfEditText();
		root.add(edit);
		edit.setPlaceHolder("请输入姓名");
		edit.setPreferredSize(new Dimension(100,24));
		
		AfTextArea edit2 = new AfTextArea();
		root.add(edit2);
		edit2.setPlaceHolder("请输入内容");
		edit2.setPreferredSize(new Dimension(200,60));
		
		
		
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				TestEditText t = new TestEditText();
				t.setVisible(true);
			}
		});
		
	}

}
