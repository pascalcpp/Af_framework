package my.label;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import af.swing.AfEditText;
import af.swing.AfLabel;
import af.swing.AfTextArea;

public class TestLabel extends JFrame
{
	public TestLabel()
	{
		super("test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,350);
		
		JPanel root = new JPanel();
		this.setContentPane(root);
		
		AfLabel label = new AfLabel("阿发你好");
		
		root.add(label);
		
		
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				TestLabel t = new TestLabel();
				t.setVisible(true);
			}
		});
		
	}

}
