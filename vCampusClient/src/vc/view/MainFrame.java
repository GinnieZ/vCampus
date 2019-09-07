package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;

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
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
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
		setType(Type.UTILITY);
		setTitle("虚拟校园系统");
		setForeground(new Color(0, 102, 153));
		setBackground(new Color(0, 102, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 389);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel("用户名");
		lblUserName.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblUserName.setBounds(118, 155, 54, 15);
		contentPane.add(lblUserName);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(182, 149, 163, 21);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("密码");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(118, 205, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(184, 199, 161, 21);
		contentPane.add(txtPassword);
		
//		////学生测试用！删除！
//		txtUserName.setText("09017401");
//		txtPassword.setText("123");
		////管理员测试用！删除！
		txtUserName.setText("09017408");
		txtPassword.setText("123");
		
		comboType = new JComboBox();
		comboType.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		comboType.addItem("--请选择--");
	    comboType.addItem("学生");
	    comboType.addItem("管理员");
	    comboType.setBounds(118, 241, 161, 21);
	    contentPane.add(comboType);
	    
		JButton btnNewButton = new JButton("登录");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(0, 153, 204));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		          SocketHelper sockethelper = new SocketHelper();
		          sockethelper.getConnection();
		          doLogin(sockethelper);
			}
		});
		btnNewButton.setBounds(118, 288, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("注册");
		btnNewButton2.setForeground(Color.WHITE);
		btnNewButton2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnNewButton2.setBackground(new Color(102, 153, 153));
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		          SocketHelper sockethelper2 = new SocketHelper();
		          sockethelper2.getConnection();
		          //
		          RegisterFrame register = new RegisterFrame();
			        register.setVisible(true);
			        dispose();
			}
		});
		btnNewButton2.setBounds(251, 288, 93, 23);
		contentPane.add(btnNewButton2);		

		JLabel lblTitle = new JLabel("vCampus");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
		lblTitle.setBounds(206, 33, 139, 79);
		contentPane.add(lblTitle);
		
		JLabel lblIcon = new JLabel();
		lblIcon.setVerticalAlignment(SwingConstants.CENTER);
		lblIcon.setBounds(81, 20, 120, 120);
	
		//实例化ImageIcon 对象
		ImageIcon schoolIcon = new ImageIcon("src\\vc\\images\\学校.png");

		/*下面这句意思是：得到此图标的 Image（image.getImage()）；	
		在此基础上创建它的缩放版本，缩放版本的宽度，高度与JLble一致（getScaledInstance(width, height,Image.SCALE_DEFAULT )）		
		最后该图像就设置为得到的缩放版本（image.setImage）		
		*/		
		schoolIcon.setImage(schoolIcon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替		
		//Image img = image.getImage();		
		//img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);		
		//image.setImage(img);		
		lblIcon.setIcon(schoolIcon);
		contentPane.add(lblIcon);
		
		JPanel panelImg = new JPanel();
		panelImg.setBounds(0, 0, 464, 142);

		JLabel lblImg = new JLabel(new ImageIcon("src\\vc\\images\\登录页.png"));
		lblImg.setBounds(0, 0, 464, 136);
		panelImg.add(lblImg);
		panelImg.setOpaque(false);		
		contentPane.add(panelImg);
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
