package my.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import af.swing.AfEditText;
import af.swing.AfPanel;
import af.swing.AfTextArea;
import af.swing.image.AfImageButton;
import af.swing.image.AfImageView;
import af.swing.layout.AfXLayout;

public class TestImageButton extends JFrame
{
	AfImageButton imageButton = new AfImageButton();
	
	public TestImageButton()
	{
		super("test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,350);
		
		
		JPanel root = new JPanel();
		this.setContentPane(root);		
		root.setLayout( new BorderLayout());
		
		AfPanel top = new AfPanel();
		root.add(top, BorderLayout.PAGE_START);
		top.setLayout(new AfXLayout());
		top.preferredHeight(30);
		top.setBackground(Color.YELLOW);
		top.setOpaque(true);
		
		//
		imageButton.setPreferredSize(new Dimension(30,30));
		top.add(imageButton);
		
		URL imageUrl = this.getClass().getResource("ic_play.png");
		try
		{
			Image icon = ImageIO.read(imageUrl);
			imageButton.normal.image = icon;
			imageButton.scaleType = AfImageView.FIT_CENTER_INSIDE;			
			imageButton.normal.bgColor = new Color(0xA0FF0000, true);
			imageButton.radius = 4;
			imageButton.padding = 2;
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		imageButton.addActionListener( (e)->{
			System.out.println("clicked!");
		});
//		
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				TestImageButton t = new TestImageButton();
				t.setVisible(true);
			}
		});
		
	}

}
