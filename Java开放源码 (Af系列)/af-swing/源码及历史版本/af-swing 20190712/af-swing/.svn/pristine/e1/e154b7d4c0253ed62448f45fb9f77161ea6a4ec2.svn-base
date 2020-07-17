package my.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import af.swing.AfEditText;
import af.swing.AfLabel;
import af.swing.AfLoadingBar;
import af.swing.AfTextArea;
import af.swing.layout.AfYLayout;

public class TestLayout extends JFrame
{
	AfLoadingBar bar = new AfLoadingBar();
	JLabel label = new JLabel("haha");
	JButton btn = new JButton("click");
	
	public TestLayout()
	{
		super("test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,350);
		
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		root.setOpaque(false);
		
		JPanel center = new JPanel();
		center.setLayout(new AfYLayout(4));
		root.add(center, BorderLayout.CENTER);
		
		center.add(bar, "30px");
		center.add(label, "30px");
		center.add(btn, "30px");
		center.add(new JTextArea(), "1w");
		
		
		btn.addActionListener( (e)->{
			bar.setVisible( false );
			bar.invalidate();
		});
		
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				TestLayout t = new TestLayout();
				t.setVisible(true);
			}
		});
		
	}

}
