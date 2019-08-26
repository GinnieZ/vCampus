package vc.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vc.common.CourseInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ISelectCourseImpl;
import vc.view.CourseView.JTabbedPaneDemo;

public class StudentView extends JFrame {
	
	public JFrame mainFrame;
	private JPanel StudentRollPanel;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private JTextField NameText = new JTextField("姓名",15);
	private JTextField SexText = new JTextField("性别",15);
	private JTextField AgeText = new JTextField("年龄",15);
	private JTextField BirthdateText = new JTextField("出生日期",15);
	private JTextField AddressText = new JTextField("家庭地址",15);
	private JTextField MajorText = new JTextField("专业",15);
	private JTextField DormText = new JTextField("宿舍",15);
	private JButton confirmButton = new JButton("确认");
	private JButton returnButton = new JButton("返回");
	private JTable StudentTbl;
	
	
	public StudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		run();
	}

	
	private void setMainPanel() {
		// TODO Auto-generated method stub
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setBounds(10, 20, 640, 357);
		mainFrame.setTitle("学籍信息");
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        StudentRollPanel = new JPanel();
		

		StudentRollPanel.add(NameText);
		StudentRollPanel.add(SexText);
		StudentRollPanel.add(AgeText);
		StudentRollPanel.add(BirthdateText);
		StudentRollPanel.add(AddressText);
		StudentRollPanel.add(MajorText);
		StudentRollPanel.add(DormText);
		StudentRollPanel.add(confirmButton);
		StudentRollPanel.add(returnButton);
		
		((StudentView) mainFrame).setStudentRollPanel();
		mainFrame.getContentPane();
		
	}
	

	public void setStudentRollPanel() {
		// TODO Auto-generated method stub
		StudentRollPanel = new JPanel();		

		StudentRollPanel.add(NameText);
		StudentRollPanel.add(SexText);
		StudentRollPanel.add(AgeText);
		StudentRollPanel.add(BirthdateText);
		StudentRollPanel.add(AddressText);
		StudentRollPanel.add(MajorText);
		StudentRollPanel.add(DormText);
		StudentRollPanel.add(confirmButton);
		StudentRollPanel.add(returnButton);		
		
	}
	

	private void run() {}
}
