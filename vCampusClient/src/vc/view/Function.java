package vc.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vc.common.UserInfo;

import vc.helper.SocketHelper;
import vc.send.ILogin;
import vc.sendImpl.ILoginImpl;

public class Function extends JFrame {
	private JPanel contentPane;
	private String id;
	private String type;
	private SocketHelper sockethelper;
	
	public Function(String myid, int t) {
		sockethelper = new SocketHelper();
        sockethelper.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 545);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		id = myid;
		if(t == 0)
			type = "student";
		else
		{
			if(t == 1)
				type = "admin";
			else
			{
				JOptionPane.showMessageDialog(null, "登录类型转换失败！");
		    	  type = "student";
			}
		}

		JButton btnSchoolRoll = new JButton("学籍");
		btnSchoolRoll.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnSchoolRoll.setForeground(Color.WHITE);
		btnSchoolRoll.setBackground(new Color(102, 153, 204));
		btnSchoolRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				if(type == "student")
				{
					StudentView myStudentView = new StudentView(id);
					myStudentView.setVisible(true);
				}
				else
				{
					if (type == "admin")
					{	
					  AdminFunctionView myAdminFunctionView = new AdminFunctionView(id);
					  myAdminFunctionView.setVisible(true);
					}	
				}
			}
		});
		btnSchoolRoll.setBounds(71, 250, 93, 23);
		contentPane.add(btnSchoolRoll);
		
		JButton btnCourseSelect = new JButton("选课");
		btnCourseSelect.setForeground(Color.WHITE);
		btnCourseSelect.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnCourseSelect.setBackground(new Color(102, 153, 204));
		btnCourseSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(type == "student")
				{
					CourseView myCourseView = new CourseView(id);
					myCourseView.setVisible(true);
				}
				else
				{
					if (type == "admin") {
						AdminCourseView AdminCourseView = new AdminCourseView(id);
						AdminCourseView.setVisible(true);
					}
				}
			}
		});
		btnCourseSelect.setBounds(235, 250, 93, 23);
		contentPane.add(btnCourseSelect);
		
		JButton btnDorm = new JButton("宿舍");
		btnDorm.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnDorm.setForeground(Color.WHITE);
		btnDorm.setBackground(new Color(102, 153, 204));
		btnDorm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if(type == "student") {
//					DormView myHospitalView = new DormView(sockethelper,id);
//					myHospitalView.setVisible(true);
//				}

			}
		});
		btnDorm.setBounds(71, 384, 93, 23);
		contentPane.add(btnDorm);
		
		JButton btnHospital = new JButton("医院");
		btnHospital.setForeground(Color.WHITE);
		btnHospital.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnHospital.setBackground(new Color(102, 153, 204));
		btnHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				if(type == "student") {
					HospitalView myHospitalView = null;
					myHospitalView = new HospitalView(id);
					myHospitalView.setVisible(true);
				}
				if(type == "admin") {
					System.out.print(true);
					HospitalView2 myHospitalView2 = null;
					myHospitalView2 = new HospitalView2(id);
					myHospitalView2.setVisible(true);
				}
			}
		});
		btnHospital.setBounds(235, 384, 93, 23);
		contentPane.add(btnHospital);
		
		JButton btnLibrary = new JButton("图书馆");
		btnLibrary.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnLibrary.setForeground(Color.WHITE);
		btnLibrary.setBackground(new Color(102, 153, 204));
		btnLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
				if(type.equals("student"))
				{
					LibraryComView myLibraryView = new LibraryComView(id);
					myLibraryView.setVisible(true);
				}
				else
				{
					LibraryAdminView myLibraryView = new LibraryAdminView(id);
					myLibraryView.setVisible(true);
				}
			}
		});
		btnLibrary.setBounds(563, 250, 93, 23);
		contentPane.add(btnLibrary);
		
		JButton btnStore = new JButton("商店");
		btnStore.setForeground(Color.WHITE);
		btnStore.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnStore.setBackground(new Color(102, 153, 204));
		btnStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
			}
		});
		btnStore.setBounds(399, 384, 93, 23);
		contentPane.add(btnStore);
		
		JButton btnOnlineClass = new JButton("在线课堂");
		btnOnlineClass.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnOnlineClass.setForeground(Color.WHITE);
		btnOnlineClass.setBackground(new Color(102, 153, 204));
		btnOnlineClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(type == "student")
				{
					OnlineClassView myOCView = new OnlineClassView(id);
					myOCView.setVisible(true);
				}
				else
				{
					if (type == "admin") {
						JOptionPane.showMessageDialog(null, "仅对学生用户开放！");
//						AdminCourseView AdminCourseView = new AdminCourseView(id);
//						AdminCourseView.setVisible(true);
					}
				}
			}
		});
		btnOnlineClass.setBounds(399, 250, 100, 23);
		contentPane.add(btnOnlineClass);
		
		JButton btnModify = new JButton("银行管理");
		btnModify.setForeground(Color.WHITE);
		btnModify.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnModify.setBackground(new Color(102, 153, 204));
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BankView bankView=new BankView(id);
				bankView.setVisible(true);
				//
			}
		});
		btnModify.setBounds(563, 384, 100, 23);
		contentPane.add(btnModify);
		
		
		JLabel lblStuStatus = new JLabel("New label");
		lblStuStatus.setBounds(74, 157, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon stuStatusIcon = new ImageIcon("src\\vc\\images\\学籍.png");	
		stuStatusIcon.setImage(stuStatusIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblStuStatus.setIcon(stuStatusIcon);
		contentPane.add(lblStuStatus);
		
		JLabel lblCourse = new JLabel("New label");
		lblCourse.setBounds(238, 157, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon courseIcon = new ImageIcon("src\\vc\\images\\课堂.png");	
		courseIcon.setImage(courseIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblCourse.setIcon(courseIcon);
		contentPane.add(lblCourse);
		
		JLabel lblOnlineClass = new JLabel("New label");
		lblOnlineClass.setBounds(402, 157, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon OnlineClassIcon = new ImageIcon("src\\vc\\images\\在线课堂.png");	
		OnlineClassIcon.setImage(OnlineClassIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblOnlineClass.setIcon(OnlineClassIcon);
		contentPane.add(lblOnlineClass);
		
		JLabel lblLibrary = new JLabel("New label");
		lblLibrary.setBounds(566, 157, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon LibraryIcon = new ImageIcon("src\\vc\\images\\图书馆.png");	
		LibraryIcon.setImage(LibraryIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblLibrary.setIcon(LibraryIcon);
		contentPane.add(lblLibrary);
		
		JLabel lbldorm = new JLabel("New label");
		lbldorm.setBounds(74, 281, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon dormIcon = new ImageIcon("src\\vc\\images\\宿舍.png");	
		dormIcon.setImage(dormIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lbldorm.setIcon(dormIcon);
		contentPane.add(lbldorm);
		
		JLabel lblHospital = new JLabel("New label");
		lblHospital.setBounds(238, 286, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon HospitalIcon = new ImageIcon("src\\vc\\images\\医院.png");	
		HospitalIcon.setImage(HospitalIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblHospital.setIcon(HospitalIcon);
		contentPane.add(lblHospital);
		
		JLabel lblShop = new JLabel("New label");
		lblShop.setBounds(402, 286, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon shopIcon = new ImageIcon("src\\vc\\images\\商店.png");	
		shopIcon.setImage(shopIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblShop.setIcon(shopIcon);
		contentPane.add(lblShop);
		
		JLabel lblBank = new JLabel("New label");
		lblBank.setBounds(566, 286, 90, 90);
		//实例化ImageIcon 对象
		ImageIcon bankIcon = new ImageIcon("src\\vc\\images\\银行.png");	
		bankIcon.setImage(bankIcon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT ));//可以用下面三句代码来代替			
		lblBank.setIcon(bankIcon);
		contentPane.add(lblBank);
		
		JLabel label = new JLabel("\u865A\u62DF\u6821\u56ED\u7CFB\u7EDF");
		label.setForeground(SystemColor.inactiveCaptionBorder);
		label.setBackground(SystemColor.text);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label.setBounds(222, 25, 400, 96);
		contentPane.add(label);
		
		//关闭事件的监听
		//关闭时退出登录
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
				ILogin myLogin = new ILoginImpl(sockethelper);
				UserInfo tmpUser = new UserInfo(id,"","","","");
				myLogin.LogOut(tmpUser);
				sockethelper.socketClose();
			}
			}); 

				
	}
}
