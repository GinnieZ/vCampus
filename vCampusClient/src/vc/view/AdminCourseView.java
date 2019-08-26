package vc.view;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vc.common.CourseInfo;
import vc.common.TextFieldHintListener;
import vc.helper.SocketHelper;
import vc.sendImpl.ISelectCourseImpl;
import vc.view.CourseView.JTabbedPaneDemo;

public class AdminCourseView extends JFrame  {
	private String AdminId;
	private SocketHelper sockethelper = new SocketHelper();
	public JFrame mainFrame;
	private JPanel mainPanel;
	private JScrollPane courseScrollPane;
	private JTable courseTbl;
	private JButton addButton = new JButton("添加");
	private JButton deleteButton = new JButton("删除");
	private JButton modifyButton = new JButton("修改");
	private JButton confirmButton = new JButton("完成");
	private JButton addConfirmButton = new JButton("确定添加");
	private JButton modifyConfirmButton = new JButton("确定修改");
	private JFrame addCourseFrame;
	private JPanel addCoursePanel;
	private JFrame modifyCourseFrame;
	private JPanel modifyCoursePanel;
	private String modifyID;
	private TextField newID = new TextField(20);
	private TextField newName = new TextField(20);
	private TextField newTeacher = new TextField(20);
	private TextField newPlace = new TextField(20);
	private TextField newTime = new TextField(20);
	private TextField newCredit = new TextField(20);
	private TextFieldHintListener listenerID = new TextFieldHintListener(newID, "课程代码");
	private TextFieldHintListener listenerName = new TextFieldHintListener(newName, "课程名称");
	private TextFieldHintListener listenerTeacher = new TextFieldHintListener(newTeacher, "授课教师");
	private TextFieldHintListener listenerPlace = new TextFieldHintListener(newPlace, "授课地点");
	private TextFieldHintListener listenerTime = new TextFieldHintListener(newTime, "授课时间");
	private TextFieldHintListener listenerCredit = new TextFieldHintListener(newCredit, "课程学分");

	private TextField modifyName = new TextField(20);
	private TextField modifyTeacher = new TextField(20);
	private TextField modifyPlace = new TextField(20);
	private TextField modifyTime = new TextField(20);
	private TextField modifyCredit = new TextField(20);
	private TextFieldHintListener listenerMName = new TextFieldHintListener(modifyName, "");
	private TextFieldHintListener listenerMTeacher = new TextFieldHintListener(modifyTeacher, "");
	private TextFieldHintListener listenerMPlace = new TextFieldHintListener(modifyPlace, "");
	private TextFieldHintListener listenerMTime = new TextFieldHintListener(modifyTime, "");
	private TextFieldHintListener listenerMCredit = new TextFieldHintListener(modifyCredit, "");
	
	public AdminCourseView(String id) {
		AdminId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		action();
	}
	
	private void setMainPanel() {
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setBounds(10, 20, 640, 357);
		mainFrame.setTitle("管理课程");
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		mainPanel = new JPanel();
		courseScrollPane = new JScrollPane();
		courseScrollPane.setViewportView(getCourseTable());
		mainPanel.add(addButton);
		mainPanel.add(deleteButton);
		mainPanel.add(modifyButton);
		confirmButton.setEnabled(false);
		mainPanel.add(confirmButton);
		mainPanel.add(courseScrollPane);
		mainFrame.add(mainPanel);
		
	}
	
	private JTable getCourseTable() {

		courseTbl = new JTable();
		String[] columns = { "代码", "课程名称", "授课教师", "授课地点", "授课时间", "学分", "已选人数" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		courseTbl.setModel(model);
		List<CourseInfo> list = new ISelectCourseImpl(this.sockethelper).EnquiryAllCourse();

		for (int i = 0; i < list.size(); i++) {
			CourseInfo courseList = (CourseInfo) list.get(i);
			String[] studentList = new ISelectCourseImpl(this.sockethelper).EnquiryStudent(courseList);
			int count = 0; // number of students who select certain course
			for (int j = 0; j < studentList.length; j++) {
				count++;
			}
			Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
					courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), count };
			model.addRow(rowData);
		}
		return courseTbl;
	}
	private void action() {
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAddCourseFrame();
				addCourseFrame.setVisible(true);
			}
		});
		
		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "未选中任何课程！");
					return;
				}
				int currentRow = courseTbl.getSelectedRow();
				int n = JOptionPane.showConfirmDialog(null, "确认删除吗?", "确认删除框", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					String courseID = (String) courseTbl.getValueAt(currentRow, 0);
					String courseName = (String) courseTbl.getValueAt(currentRow, 1);
					String courseTeacher = (String) courseTbl.getValueAt(currentRow, 2);
					String coursePlace = (String) courseTbl.getValueAt(currentRow, 3);
					String courseTime = (String) courseTbl.getValueAt(currentRow, 4);
					double courseCredit = (double) courseTbl.getValueAt(currentRow, 5);
					CourseInfo courseInfo = new CourseInfo(courseID, courseName, 
							courseTeacher, coursePlace, courseTime, courseCredit);
					if (new ISelectCourseImpl(sockethelper).deleteCourse(courseInfo)) {
						JOptionPane.showMessageDialog(null, "已成功删除课程！");
					} else {
						JOptionPane.showMessageDialog(null, "删除课程失败！");
					}
					// refresh
					courseScrollPane.setViewportView(getCourseTable());
				}
				else if (n == JOptionPane.NO_OPTION) {
					return;
				}	
			}
		});
		
		this.modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "未选中任何课程！");
					return;
				}

				int currentRow = courseTbl.getSelectedRow();
				setModifyCourseFrame(currentRow);
				modifyCourseFrame.setVisible(true);
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
		
		this.confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
	}
	
	void setAddCourseFrame()
	{
		addCourseFrame = new JFrame();
		addCoursePanel = new JPanel();
		addCourseFrame.setVisible(true);
		addCourseFrame.setBounds(500, 100, 500, 350);
		addCourseFrame.setTitle("添加课程");
		addCourseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		// empty
		newID.setText("");
		newName.setText("");
		newTeacher.setText("");
		newPlace.setText("");
		newTime.setText("");
		newCredit.setText("");
		
		newID.addFocusListener(listenerID);
		newName.addFocusListener(listenerName);
		newTeacher.addFocusListener(listenerTeacher);
		newPlace.addFocusListener(listenerPlace);
		newTime.addFocusListener(listenerTime);
		newCredit.addFocusListener(listenerCredit);
		
		Box box = Box.createVerticalBox();
		box.add(newID);
		box.add(newName);
		box.add(newTeacher);
		box.add(newPlace);
		box.add(newTime);
		box.add(newCredit);
		addCoursePanel.add(box);
		addCoursePanel.add(addConfirmButton);
		addCourseFrame.add(addCoursePanel);
		
		addAction();
		
	}
	
	void addAction()
	{
		this.addConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String courseID;
				String courseName;
				String courseTeacher;
				String coursePlace;
				String courseTime;
				double courseCredit;
				
				// examine whether the textfield is empty
				if(newID.getText() != "课程代码" && newID.getText().length() != 0)
				{
					courseID = newID.getText();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "课程代码为空！");
					return;
				}
				
				if(newName.getText() != "课程名称")
				{
					courseName = newName.getText();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "课程名称为空！");
					return;
				}
				
				if(newTeacher.getText() != "授课教师")
				{
					courseTeacher = newTeacher.getText();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "授课教师为空！");
					return;
				}
				
				if(newPlace.getText() != "授课地点")
				{
					coursePlace = newPlace.getText();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "授课地点为空！");
					return;
				}
				
				if(newTime.getText() != "授课时间")
				{
					courseTime = newTime.getText();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "授课时间为空！");
					return;
				}
				
				if(newCredit.getText() != "课程学分")
				{
					courseCredit = Double.parseDouble(newCredit.getText());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "课程学分为空！");
					return;
				}
				CourseInfo courseInfo = new CourseInfo(courseID, courseName, 
						courseTeacher, coursePlace, courseTime, courseCredit);
				if (new ISelectCourseImpl(sockethelper).addCourse(courseInfo)) {
					JOptionPane.showMessageDialog(null, "已成功添加课程！");
				} else {
					JOptionPane.showMessageDialog(null, "添加课程失败！");
				}
				addCourseFrame.dispose();
				// refresh
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
	}
	
	void setModifyCourseFrame(int currentRow)
	{
		modifyCourseFrame = new JFrame();
		modifyCoursePanel = new JPanel();
		modifyCourseFrame.setVisible(true);
		modifyCourseFrame.setBounds(500, 100, 500, 350);
		modifyCourseFrame.setTitle("修改课程");
		modifyCourseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		modifyID = (String) courseTbl.getValueAt(currentRow, 0);
//		listenerMName.setHintText((String) courseTbl.getValueAt(currentRow, 1));
//		listenerMTeacher.setHintText((String) courseTbl.getValueAt(currentRow, 2));
//		listenerMPlace.setHintText((String) courseTbl.getValueAt(currentRow, 3));
//		listenerMTime.setHintText((String) courseTbl.getValueAt(currentRow, 4));
//		listenerMCredit.setHintText(String.valueOf(courseTbl.getValueAt(currentRow, 5)));
		listenerMName = new TextFieldHintListener(modifyName, (String) courseTbl.getValueAt(currentRow, 1));
		listenerMTeacher = new TextFieldHintListener(modifyTeacher, (String) courseTbl.getValueAt(currentRow, 2));
		listenerMPlace = new TextFieldHintListener(modifyPlace, (String) courseTbl.getValueAt(currentRow, 3));
		listenerMTime = new TextFieldHintListener(modifyTime, (String) courseTbl.getValueAt(currentRow, 4));
		listenerMCredit = new TextFieldHintListener(modifyCredit, String.valueOf(courseTbl.getValueAt(currentRow, 5)));
		
		Box box = Box.createVerticalBox();
		box.add(modifyName);
		box.add(modifyTeacher);
		box.add(modifyPlace);
		box.add(modifyTime);
		box.add(modifyCredit);
		modifyCoursePanel.add(box);
		modifyCoursePanel.add(modifyConfirmButton);
		modifyCourseFrame.add(modifyCoursePanel);
		
		modifyAction();
		
	}
	
	void modifyAction()
	{
		this.modifyConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String courseName = modifyName.getText();
				String courseTeacher = modifyTeacher.getText();
				String coursePlace = modifyPlace.getText();
				String courseTime = modifyTime.getText();
				double courseCredit = Double.parseDouble(modifyCredit.getText());
				
				CourseInfo courseInfo = new CourseInfo(modifyID, courseName, 
						courseTeacher, coursePlace, courseTime, courseCredit);
				if (new ISelectCourseImpl(sockethelper).modifyCourse(courseInfo)) {
					JOptionPane.showMessageDialog(null, "已成功修改课程！");
				} else {
					JOptionPane.showMessageDialog(null, "修改课程失败！");
				}
				modifyCourseFrame.dispose();
				// refresh
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
	}
}
