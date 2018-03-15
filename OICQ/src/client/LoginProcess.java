package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginProcess extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginProcess(int number) {
		super();
		
		setTitle("登录");
		ImageIcon imagelIcon=new ImageIcon(getClass().getResource("\\pictures\\qq.png"));
		JLabel imageLableJLabel=new JLabel(imagelIcon);
		ImageIcon portraItimageIcon=new ImageIcon(getClass().getResource("\\pictures\\portrait-"+number+".jpg"));
		portraItimageIcon.setImage(portraItimageIcon.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT));
		JLabel portraItImageLabel=new JLabel(portraItimageIcon);
		JPanel mainLayout=new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		JPanel jPanel_0 =new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		JPanel jPanel_1 =new JPanel(new FlowLayout(FlowLayout.CENTER,0,10));
		JPanel jPanel_2 =new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		JButton loginButton=new JButton("正在登陆...");
		loginButton.setPreferredSize(new Dimension(170,30));
		loginButton.setForeground(Color.white);
		loginButton.setBackground(new Color(30,144,255));
		jPanel_2.add(loginButton);
		jPanel_0.add(jPanel_0);
		jPanel_1.add(portraItImageLabel);
		JPanel jPanel_3=new JPanel(new GridLayout(2,1,5,5));
		jPanel_3.add(jPanel_1);
		jPanel_3.add(jPanel_2);
		mainLayout.add(jPanel_3);
		add(mainLayout);
		setSize(445,350);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Dimension screenSizeDimension=toolkit.getScreenSize();
		int height=getHeight();
		int width=getWidth();
		int screenWight=screenSizeDimension.width/2;
		int screenHeight=screenSizeDimension.height/2;
		setLocation(screenWight-width/2,screenHeight-height/2);
		setVisible(true);
	}

	 

}
