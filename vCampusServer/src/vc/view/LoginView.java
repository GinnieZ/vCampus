package vc.view;

/*
 * *
 */

import javax.swing.*;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI.NormalColor;

import vc.common.UserInfo;
import vc.helper.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginView extends JFrame
{

    GridBagConstraints gbs=new GridBagConstraints();//实例化这个对象用来对组件进行管理
    
    JTextField txtField;
    JPasswordField pwdField;
    ServerView serverWin;
	
	int userType = 1;

	public LoginView()
	{
		this.setSize(900,600);	
		this.setTitle("服务器端登录界面");
 	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// 
		initComponents();
	}


	/**
	 * ��ʼ��UI����
	 */
	private void initComponents() {	
		JPanel btnPane = new JPanel();
		JButton btnLogIn = new JButton("登录");
		JButton btnSignIn = new JButton("注册");
		JButton btnClean = new JButton("清除");
		btnLogIn.setUI(new BEButtonUI().setNormalColor(NormalColor.lightBlue));
		
		GridBagLayout gbl=new GridBagLayout(); //实例化布局对象
		btnPane.setLayout(gbl);
		
		btnPane.add(btnLogIn);
		btnPane.add(btnSignIn);
		btnPane.add(btnClean);
		
		//为登录按钮添加监听器
		btnLogIn.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{	
				Login lg = new Login();
				UserInfo info = new UserInfo(txtField.getText(),pwdField.getText(),"admin");
				//判断用户名和密码
				if(lg.login(info))
				{
					serverWin = new ServerView();
					serverWin.Frame_Server.setVisible(true);
					closeLoginView();
				}
				else
					JOptionPane.showMessageDialog(null, "用户名或密码输入错误", "错误", JOptionPane.ERROR_MESSAGE);
			}		
		});

		//为注册按钮添加监听器
		btnSignIn.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{	
			    RegisterView registerWin = new RegisterView();
			}		
		});
		
		//为清除按钮添加监听器
		btnClean.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{				
				txtField.setText("");
				pwdField.setText("");
			}		
		});
		
		
		//组件1(gridx,gridy)组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数

		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(80, 120, 80, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=4;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1;            
		gbl.setConstraints(btnLogIn, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(80, 20, 80, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=4;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(btnSignIn, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(80, 20, 80, 120);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=3;
		gbs.gridy=4;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(btnClean, gbs);
		
		this.setLayout(gbl);
		
		JPanel TitlePanel = new JPanel();
		JLabel label = new JLabel("服务器端登录");  
		
		TitlePanel.add(label); 
		
		JPanel LogTxtPanel = getLogTxtPanel();
//		JPanel UserTypePanel = getUserTypePanel();
		
		this.add(TitlePanel);
		this.add(LogTxtPanel);
//		this.add(UserTypePanel);
		this.add(btnPane);
		
		//组件1(gridx,gridy)组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(2, 2, 2, 2);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=0;
		gbs.gridy=0;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1;            
		gbl.setConstraints(TitlePanel, gbs);		
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(2, 2, 2, 2);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=0;
		gbs.gridy=1;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(LogTxtPanel, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(2, 2, 2, 2);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=0;
		gbs.gridy=2;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
//		gbl.setConstraints(UserTypePanel, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(2, 2, 2, 2);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=0;
		gbs.gridy=3;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(btnPane, gbs);
		
		this.pack();
		// 
		
	}

	// 
	private JPanel getLogTxtPanel() {
		JPanel LogTxtPanel = new JPanel();
		
		JLabel lblName = new JLabel("用户名");
		LogTxtPanel.add(lblName);
		txtField = new JTextField(10);
		LogTxtPanel.add(txtField);	
		JLabel lblPwd = new JLabel("密码");
		LogTxtPanel.add(lblPwd);
		pwdField = new JPasswordField(10);
		LogTxtPanel.add(pwdField);
				
		GridBagLayout gbl=new GridBagLayout(); //实例化布局对象
		LogTxtPanel.setLayout(gbl);

		//组件1(gridx,gridy)组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数

		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(20, 80, 10, 0);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=1;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1;            
		gbl.setConstraints(lblName, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(20, 0, 10, 80);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=1;
		gbs.gridwidth=18;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(txtField, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 80, 20, 0);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=2;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(lblPwd, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 0, 20, 80);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=2;
		gbs.gridwidth=18;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(pwdField, gbs);
		
		return LogTxtPanel;
	}
	private void hideLoginView() {
		this.setVisible(false);
	}
	private void closeLoginView() {
		this.dispose();
	}
}
