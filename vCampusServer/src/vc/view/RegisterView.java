package vc.view;

import javax.swing.*;

import vc.common.UserInfo;
import vc.helper.Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class RegisterView extends JFrame
{
	GridBagConstraints gbs=new GridBagConstraints();//实例化这个对象用来对组件进行管理
	
	JTextField txtName;
	JTextField txtUserName;
    JPasswordField pswFirst;
    JPasswordField pswSecond;
	
	public RegisterView()
	{
		this.setSize(600,600);	
		this.setTitle("管理员注册");
 	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// 
		initComponents();
	}

	private void initComponents() 
	{
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		
		JLabel lblTitle = new JLabel("管理员注册界面");
		

		JLabel lblName = new JLabel("请输入您的真实姓名：");
		JLabel lblUserName = new JLabel("请输入用户名：");
		JLabel lblPsw = new JLabel("请输入您的密码：");
		JLabel lblPswAgain = new JLabel("请再次输入您的密码：");
		
		txtName = new JTextField(10);
		txtUserName = new JTextField(10);
		pswFirst = new JPasswordField(10);
		pswSecond = new JPasswordField(10);
		
		JButton btnOk = new JButton("确定");
		JButton btnClean = new JButton("清除");
		JButton btnReturn= new JButton("返回");
		btnOk.setSize(5,2);
		btnClean.setSize(5,2);
		btnReturn.setSize(5,2);
		
		pane.add(lblTitle);
		pane.add(lblName);
		pane.add(lblUserName);
		pane.add(lblPsw);
		pane.add(lblPswAgain);
		pane.add(txtName);
		pane.add(txtUserName);
		pane.add(pswFirst);
		pane.add(pswSecond);
		pane.add(btnOk);
		pane.add(btnClean);
		pane.add(btnReturn);
		
		GridBagLayout gbl=new GridBagLayout(); //实例化布局对象
		pane.setLayout(gbl);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=1;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(lblTitle, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=2;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(lblName, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=2;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(txtName, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=3;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(lblUserName, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=3;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(txtUserName, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=4;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(lblPsw, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=4;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(pswFirst, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=5;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(lblPswAgain, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=5;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(pswSecond, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=1;
		gbs.gridy=6;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(btnOk, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=2;
		gbs.gridy=6;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(btnReturn, gbs);
		
		gbs.fill=GridBagConstraints.BOTH;
		gbs.insets=new Insets(10, 20, 10, 20);
		gbs.weightx=1;gbs.weighty=1;
		gbs.gridx=3;
		gbs.gridy=6;
		gbs.gridwidth=1;                                             
		gbs.gridheight=1; 
		gbl.setConstraints(btnClean, gbs);
		
		this.add(pane);
		btnOk.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{	
				if(pswFirst.getText().equals(pswSecond.getText())) {
					Login lg = new Login();
					UserInfo info = new UserInfo(txtUserName.getText(),pswFirst.getText(),"admin",txtName.getText());//	
					if(lg.register(info))
					{
						JOptionPane.showMessageDialog(null, "注册成功", "提示",JOptionPane.INFORMATION_MESSAGE);
						registerExit();	
					}
					else
						JOptionPane.showMessageDialog(null, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
					
				}
				else
					JOptionPane.showMessageDialog(null, "两次输入的密码不相同", "错误", JOptionPane.ERROR_MESSAGE);
				
			}		
		});
		
		btnClean.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{	
				txtName.setText("");
				txtUserName.setText("");
				pswFirst.setText("");	
				pswSecond.setText("");	
			}		
		});
		
		btnReturn.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{				
				registerExit();						
			}		
		});
		this.pack();		
	}
	
	public void registerExit()
	{
		this.dispose();
	}
}

