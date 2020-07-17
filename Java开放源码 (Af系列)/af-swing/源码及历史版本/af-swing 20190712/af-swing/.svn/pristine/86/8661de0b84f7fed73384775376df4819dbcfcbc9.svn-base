package my.button;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import af.swing.AfButton;
import af.swing.AfEditText;
import af.swing.AfTextArea;

public class TestButton extends JFrame
{
	public TestButton()
	{
		super("test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,350);
		
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new FlowLayout());
		
		AfButton button = new AfButton("阿发你好 haha");
		//button.setPreferredSize(new Dimension(160,60));
		root.add(button);
		
		button.addActionListener( (e)->{
			System.out.println("clicked ");
		});
		
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				TestButton t = new TestButton();
				t.setVisible(true);
			}
		});
		
	}

}
