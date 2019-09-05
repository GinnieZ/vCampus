package vc.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import vc.helper.SocketHelper;

public class Function extends JFrame {
	private JPanel contentPane;
	private String id;
	private String type;
	
	public Function(String myid, int t) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 256);
		contentPane = new JPanel();
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
		btnSchoolRoll.setBounds(20, 20, 93, 23);
		contentPane.add(btnSchoolRoll);
		
		JButton btnCourseSelect = new JButton("选课");
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
		btnCourseSelect.setBounds(180, 20, 93, 23);
		contentPane.add(btnCourseSelect);
		
		JButton btnDorm = new JButton("宿舍");
		btnDorm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
			}
		});
		btnDorm.setBounds(20, 50, 93, 23);
		contentPane.add(btnDorm);
		
		JButton btnHospital = new JButton("医院");
		btnHospital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
			}
		});
		btnHospital.setBounds(180, 50, 93, 23);
		contentPane.add(btnHospital);
		
		JButton btnLibrary = new JButton("图书馆");
		btnLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
			}
		});
		btnLibrary.setBounds(20, 80, 93, 23);
		contentPane.add(btnLibrary);
		
		JButton btnStore = new JButton("商店");
		btnStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
			}
		});
		btnStore.setBounds(180, 80, 93, 23);
		contentPane.add(btnStore);
		
		JButton btnOnlineClass = new JButton("在线课堂");
		btnOnlineClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(type == "student")
				{
					OnlineClassView myOCView = new OnlineClassView(id);
					myOCView.setVisible(true);
					dispose();
				}
				else
				{
					if (type == "admin") {
//						AdminCourseView AdminCourseView = new AdminCourseView(id);
//						AdminCourseView.setVisible(true);
//						dispose();
					}
				}
			}
		});
		btnOnlineClass.setBounds(20, 110, 93, 23);
		contentPane.add(btnOnlineClass);
		
		JButton btnModify = new JButton("银行管理");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SocketHelper sockethelper2 = new SocketHelper();
				sockethelper2.getConnection();
				BankView bankView=new BankView(sockethelper2, id);
				bankView.setVisible(true);
				dispose();
				//
			}
		});
		btnModify.setBounds(180, 110, 93, 23);
		contentPane.add(btnModify);
	}

}
