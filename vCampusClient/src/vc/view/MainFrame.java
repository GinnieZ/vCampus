package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vc.common.UserInfo;
import vc.helper.SocketHelper;
import vc.send.ILogin;
import vc.sendImpl.ILoginImpl;
import vc.srv.IClientUserSrv;
import vc.srv.impl.IClientUserSrvImpl;
import vc.vo.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JComboBox comboType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel("用户名");
		lblUserName.setBounds(30, 26, 54, 15);
		contentPane.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(94, 23, 163, 21);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("密码");
		lblNewLabel_1.setBounds(30, 72, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(96, 69, 161, 21);
		contentPane.add(txtPassword);
		
		comboType = new JComboBox();
		comboType.addItem("--请选择--");
	    comboType.addItem("学生");
	    comboType.addItem("管理员");
	    comboType.setBounds(30, 100, 161, 21);
	    contentPane.add(comboType);
	    
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		          SocketHelper sockethelper = new SocketHelper();
		          sockethelper.getConnection();
		          doLogin(sockethelper);
			}
		});
		btnNewButton.setBounds(108, 159, 93, 23);
		contentPane.add(btnNewButton);
	}
	
	protected  void doLogin(SocketHelper sockethelper) {
//		String username = this.txtUserName.getText();
//		if ( username.trim().isEmpty() ) {
//			JOptionPane.showMessageDialog(this, "Cannot");
//			return;
//		}
//		System.out.println("User name : " + username);
//		String password = this.txtPassword.getText();
//		System.out.println("Password : " + password);
//		//
//		IClientUserSrv icus = new IClientUserSrvImpl();
//		//
//		User user = icus.login(username, password);
//		
//		System.out.println(user);
//	}
		
	    ILogin ius = new ILoginImpl(sockethelper);
	    String id = this.txtUserName.getText();
	    String pwd = String.valueOf(this.txtPassword.getPassword());
	    String type = (String)this.comboType.getSelectedItem();
	    String typeE = null;
//	    String str1;
//	    switch ((str1 = type).hashCode())
//	    {
//	    case 1370355237: 
//	      if (str1.equals("学生")) {
//	    	typeE = "student";
//	    	System.out.println(typeE);
//	        break;
//	      }
//	      break;
//	    case 31357043: 
//	      if (!str1.equals("管理员"))
//	      {
//	    	JOptionPane.showMessageDialog(null, "登录类型转换失败！");
//	        typeE = "student";
//	        break;
//	      }
//	      else
//	      {
//	        typeE = "admin";
//	      }
//	      break;
//	    }
	    
	    if (type == "学生")
	      {
	    	typeE = "student";
	      }
	      else if (type == "管理员")
	      {
	    	  typeE = "admin";
	      }
	      else
	      {
	    	  JOptionPane.showMessageDialog(null, "登录类型转换失败！");
	    	  typeE = "student";
	      }
	    
	    UserInfo user = new UserInfo(id, pwd, typeE, "", "");
	    if (ius.Login(user))
	    {
	      System.out.println("登录成功");
	      if (type == "学生")
	      {
	        JOptionPane.showMessageDialog(null, "学生界面成功登录！");
	        Function funtion = new Function(id, 0);
	        funtion.setVisible(true);
	        dispose();
//	        MainUIView_MY.setStudentId(id);
//	        MainUIView_MY.setIsLogin(true);
//	        MainUIView_MY.setType("student");
//	        MainUIView_MY.UpdateButtonShowOnline();
//	        MainUIView_MY.setMinimizeRestore();
//	        this.Frame_Login.dispose();
	      }
	      else if (type == "管理员")
	      {
	        JOptionPane.showMessageDialog(null, "管理员界面成功登录！");
	        Function funtion = new Function(id, 1);
	        funtion.setVisible(true);
	        dispose();
//	        MainUIView_MY.setStudentId(id);
//	        MainUIView_MY.setIsLogin(true);
//	        MainUIView_MY.setType("admin");
//	        MainUIView_MY.UpdateButtonShowOnline();
//	        MainUIView_MY.setMinimizeRestore();
//	        this.Frame_Login.dispose();
	      }
	    }
	    else
	    {
	      JOptionPane.showMessageDialog(null, "登录失败！");
	    }
	}
}
