package my.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import af.swing.AfEditText;
import af.swing.AfLabel;
import af.swing.AfLoadingBar;
import af.swing.AfPanel;
import af.swing.AfTextArea;
import af.swing.border.AfBorder;
import af.swing.layout.AfAnyWhere;
import af.swing.layout.AfLayeredPane;
import af.swing.layout.AfMargin;
import af.swing.layout.AfYLayout;

public class TestLayerLayout extends JFrame
{
	
	JButton label = new JButton("haha");
	JButton btn = new JButton("click");
	
	public TestLayerLayout()
	{
		super("test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,350);
		
		AfLayeredPane root = new AfLayeredPane();
		root.setLayout(new AfAnyWhere());
		
		this.setContentPane(root);
		root.setOpaque(true);
		root.setBackground(Color.YELLOW);
		AfBorder.addPadding(root, 10);
		
		label.setBackground(Color.GREEN);
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(300,200));
		
		
		root.addLayer(btn, 2,AfMargin.CENTER);		
		root.addLayer(label,1, AfMargin.TOP_CENTER);
		
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				TestLayerLayout t = new TestLayerLayout();
				t.setVisible(true);
			}
		});
		
	}

}
