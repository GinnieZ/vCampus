package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vc.common.CourseInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ISelectCourseImpl;

/**
 * 学生选课界面类。
 * @author 09017408
 *
 */

public class CourseView extends JFrame {
	private JPanel selectCoursePanel = new JPanel();
	private JPanel timetablePanel = new JPanel();
	private JScrollPane courseScrollPane = new JScrollPane();
	private JScrollPane timetableScrollPane = new JScrollPane();
	private SocketHelper sockethelper = new SocketHelper();
	private GridBagConstraints gbs = new GridBagConstraints();
	private String StudentId;
	private JButton selectButton = new JButton("选择");
	private JButton cancelButton = new JButton("退选");
	private JButton searchButton = new JButton("搜索");
	private JButton returnButton = new JButton("返回");
	private JPanel buttonCombination = new JPanel();
	private JTable courseTbl;
	private JTable timeTbl;
	private String[][] timeBlock = new String[5][5];
	private JFrame searchCourseFrame;
	private JPanel searchCoursePanel;
	private JButton searchConfirmButton = new JButton("确定搜索");
	private TextField searchField = new TextField(20);
	private JComboBox searchType = new JComboBox();

	public CourseView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		action();
	}

	public class JTabbedPaneDemo extends JPanel {
		private JTabbedPane jTabbedpane = new JTabbedPane();
		private String[] tabNames = { "选课界面", "当前课表" };

		public JTabbedPaneDemo() {
			layoutComponents();
		}

		private void layoutComponents() {
			int i = 0;

			setSelectCoursePanel();
			setTimetablePanel();

			jTabbedpane.addTab(tabNames[i++], null, selectCoursePanel, "first");
			jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0); // 快捷键

			jTabbedpane.addTab(tabNames[i++], null, timetablePanel, "second");
			jTabbedpane.setMnemonicAt(1, KeyEvent.VK_1);

			setLayout(new GridLayout(1, 1));
			add(jTabbedpane);
		}
	}

	private void setMainPanel() {
		setVisible(true);
		setBounds(10, 20, 850, 500);
		setTitle("学生选课");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JTabbedPaneDemo tabbedPaneDemo = new JTabbedPaneDemo();
		setContentPane(tabbedPaneDemo);
		setSearchCourseFrame();
		searchCourseFrame.setVisible(false);
	}

	public void setSelectCoursePanel() {
		selectCoursePanel.setLayout(new BorderLayout(10,5));
		buttonCombination.add(selectButton);
		buttonCombination.add(cancelButton);
		buttonCombination.add(searchButton);
		buttonCombination.add(returnButton);
		searchButton.setEnabled(true);
		returnButton.setEnabled(false);
		selectCoursePanel.add(buttonCombination, BorderLayout.NORTH);
		courseScrollPane.setViewportView(getCourseTable());
		courseScrollPane.setBounds(50, 60, 450, 500);
		selectCoursePanel.add(courseScrollPane);

	}

	private JTable getCourseTable() {
		courseTbl = new JTable();
		courseTbl.setPreferredSize(new Dimension(500, 500));
		String[] columns = { "代码", "课程名称", "授课教师", "授课地点", "授课时间", "学分", "状态" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		courseTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		courseTbl.getTableHeader().setReorderingAllowed(false);
		courseTbl.setModel(model);
		courseTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		List<CourseInfo> courselist = new ISelectCourseImpl(this.sockethelper)
				.EnquirySelectCourse(this.StudentId);
		List<CourseInfo> list = new ISelectCourseImpl(this.sockethelper).EnquiryAllCourse();

		for (int i = 0; i < list.size(); i++) {
			CourseInfo courseList = (CourseInfo) list.get(i);
			boolean flag = false;
			for (int j = 0; j < courselist.size(); j++) {
				CourseInfo tmpList = (CourseInfo) courselist.get(j);
				if (tmpList.getId().equals(courseList.getId())) {
					flag = true;
				}
			}
			if (flag) {
				Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
						courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "已选" };
				model.addRow(rowData);
			} else {
				Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
						courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "未选" };
				model.addRow(rowData);
			}
		}
		courseTbl.getColumnModel().getColumn(0).setPreferredWidth(40);
		courseTbl.getColumnModel().getColumn(1).setPreferredWidth(100);
		courseTbl.getColumnModel().getColumn(2).setPreferredWidth(40);
		courseTbl.getColumnModel().getColumn(3).setPreferredWidth(60);
		courseTbl.getColumnModel().getColumn(4).setPreferredWidth(80);
		courseTbl.getColumnModel().getColumn(5).setPreferredWidth(40);
		courseTbl.getColumnModel().getColumn(6).setPreferredWidth(40);
		return courseTbl;
	}

	public void setTimetablePanel() {
		timetableScrollPane.setViewportView(getTimetable());
		timetablePanel.add(this.timetableScrollPane);
	}

	private JTable getTimetable() {
		timeTbl = new JTable();
		String[] columns = { "周一", "周二", "周三", "周四", "周五" };
		DefaultTableModel model = new DefaultTableModel(columns, 0);
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				timeBlock[i][j] = null; // initialize
			}
		}
		
		List<CourseInfo> courselist = new ISelectCourseImpl(this.sockethelper)
				.EnquirySelectCourse(this.StudentId);
		for (int index = 0; index < courselist.size(); index++) {
			CourseInfo currentCourse = (CourseInfo) courselist.get(index);
			String originTime = currentCourse.getTime();
			List<String> time = Arrays.asList(originTime.split(",")); // Split by comma
			char cDay, cStart, cEnd;
			int day, start, end;
			Iterator<String> iter = time.iterator();

			while (iter.hasNext()) {
				String tempTime = (String) iter.next();
				cDay = tempTime.charAt(1);
				cStart = tempTime.charAt(2);
				cEnd = tempTime.charAt(3);
				
				// turn char into int
				start = (int)cStart - (int)'0';
				end = (int)cEnd - (int)'0';
				switch ((int) cDay) {
				case ((int) '一'):
					day = 1;
					break;
				case ((int) '二'):
					day = 2;
					break;
				case ((int) '三'):
					day = 3;
					break;
				case ((int) '四'):
					day = 4;
					break;
				case ((int) '五'):
					day = 5;
					break;
				default:
					day = 0;
					break;
				}
				timeBlock[day - 1][start / 2] = currentCourse.getName();
			}
		}
		for(int row = 0; row < 5; row++)
		{
			Object[] rowData = { timeBlock[0][row],  timeBlock[1][row], 
					timeBlock[2][row], timeBlock[3][row], timeBlock[4][row]};
			model.addRow(rowData);
		}
		
		timeTbl.setModel(model);
		timeTbl.setEnabled(false);
		timeTbl.getColumnModel().getColumn(0).setPreferredWidth(100);
		timeTbl.getColumnModel().getColumn(1).setPreferredWidth(100);
		timeTbl.getColumnModel().getColumn(2).setPreferredWidth(100);
		timeTbl.getColumnModel().getColumn(3).setPreferredWidth(100);
		timeTbl.getColumnModel().getColumn(4).setPreferredWidth(100);
		return timeTbl;
	}

	private void action() {
		this.selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "未选中任何课程！");
					return;
				}
				searchButton.setEnabled(true);
				returnButton.setEnabled(false);
				int currentRow = courseTbl.getSelectedRow();
				String courseState = (String) courseTbl.getValueAt(currentRow, 6);
				if (courseState == "已选") {
					JOptionPane.showMessageDialog(null, "已经选择过该课程！");
					return;
				}
				if(checkConflict(courseTbl.getValueAt(currentRow, 4)))
				{
					JOptionPane.showMessageDialog(null, "课程时间冲突！");
					return;
				}
				String courseID = (String) courseTbl.getValueAt(currentRow, 0);
				if (new ISelectCourseImpl(sockethelper).selectCourse(courseID, StudentId)) {
					JOptionPane.showMessageDialog(null, "已成功选择课程！");
					courseTbl.setValueAt("已选", currentRow, 6);
				} else {
					JOptionPane.showMessageDialog(null, "选课失败！");
				}
				// refresh
				setSelectCoursePanel();
				setTimetablePanel();
			}
		});

		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "未选中任何课程！");
					return;
				}
				searchButton.setEnabled(true);
				returnButton.setEnabled(false);
				int currentRow = courseTbl.getSelectedRow();
				String courseState = (String) courseTbl.getValueAt(currentRow, 6);
				if (courseState == "未选") {
					JOptionPane.showMessageDialog(null, "尚未选择该课程！");
					return;
				}
				String courseID = (String) courseTbl.getValueAt(currentRow, 0);
				if (new ISelectCourseImpl(sockethelper).cancelCourse(courseID, StudentId)) {
					JOptionPane.showMessageDialog(null, "已成功退选课程！");
					courseTbl.setValueAt("未选", currentRow, 6);
				} else {
					JOptionPane.showMessageDialog(null, "退课失败！");
				}
				// refresh
				setSelectCoursePanel();
				setTimetablePanel();
			}
		});
		
		this.searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetSearchCourseFrame();
				searchCourseFrame.setVisible(true);
			}
		});
		
		this.returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchButton.setEnabled(true);
				returnButton.setEnabled(false);
				courseScrollPane.setViewportView(getCourseTable());
			}
		});
		
		this.searchConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchAction();
			}
		});
	}
	
	void setSearchCourseFrame()
	{
		searchCourseFrame = new JFrame();
		searchCoursePanel = new JPanel();
		searchCourseFrame.setVisible(true);
		searchCourseFrame.setBounds(500, 100, 500, 350);
		searchCourseFrame.setTitle("搜索课程");
		searchCourseFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		searchType.addItem("课程代码");
		searchType.addItem("授课教师");
		searchType.addItem("课程名称");
		searchType.setBounds(30, 100, 161, 21);

	}
	
	void resetSearchCourseFrame()
	{
		searchField.setText("");
		searchCoursePanel.add(searchType);
		searchCoursePanel.add(searchField);
		searchCoursePanel.add(searchConfirmButton);
		searchCourseFrame.add(searchCoursePanel);
	}
	
	boolean checkConflict(Object obj)
	{
		String str = (String) obj;
		List<String> time = Arrays.asList(str.split(",")); // Split by comma
		Iterator<String> iter = time.iterator();

		while (iter.hasNext()) {
			String tempTime = (String) iter.next();
			char cDay = tempTime.charAt(1);
			int day;
			int start = (int)(tempTime.charAt(2)) - (int)'0';
			switch ((int) cDay) {
			case ((int) '一'):
				day = 1;
				break;
			case ((int) '二'):
				day = 2;
				break;
			case ((int) '三'):
				day = 3;
				break;
			case ((int) '四'):
				day = 4;
				break;
			case ((int) '五'):
				day = 5;
				break;
			default:
				day = 0;
				break;
			}
			
			if(timeBlock[day - 1][start / 2] != null)
			{
				System.out.println("冲突：" + timeBlock[day - 1][start / 2]);
				return true;
			}
		}
		return false;
	}
	
	void searchAction()
	{
		String searchContent = searchField.getText();
		String searchTypes = (String) searchType.getSelectedItem();
		List<CourseInfo> courselist = new ISelectCourseImpl(this.sockethelper)
				.EnquirySelectCourse(this.StudentId);
		if(searchTypes == "课程代码")
		{
			List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseById(searchContent);
			searchCourseFrame.setVisible(false);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "搜索成功！");
				searchButton.setEnabled(false);
				returnButton.setEnabled(true);
				String[] columns = { "代码", "课程名称", "授课教师", "授课地点", "授课时间", "学分", "状态" };
				DefaultTableModel model = new DefaultTableModel(columns, 0);
				model.setRowCount(0);
				for (int i = 0; i < list.size(); i++) {
					CourseInfo courseList = (CourseInfo) list.get(i);
					boolean flag = false;
					for (int j = 0; j < courselist.size(); j++) {
						CourseInfo tmpList = (CourseInfo) courselist.get(j);
						if (tmpList.getId().equals(courseList.getId())) {
							flag = true;
						}
					}
					if (flag) {
						Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
								courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "已选" };
						model.addRow(rowData);
					} else {
						Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
								courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "未选" };
						model.addRow(rowData);
					}
				}
				courseTbl.setModel(model);
			} else {
				JOptionPane.showMessageDialog(null, "没有匹配的课程！");
			}
			return;
		}

		if(searchTypes == "课程名称")
		{
			List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseByName(searchContent);
			searchCourseFrame.setVisible(false);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "搜索成功！");
				System.out.println("Show message Dialog.");
				searchButton.setEnabled(false);
				returnButton.setEnabled(true);
				String[] columns = { "代码", "课程名称", "授课教师", "授课地点", "授课时间", "学分", "状态" };
				DefaultTableModel model = new DefaultTableModel(columns, 0);
				model.setRowCount(0);
				for (int i = 0; i < list.size(); i++) {
					CourseInfo courseList = (CourseInfo) list.get(i);
					boolean flag = false;
					for (int j = 0; j < courselist.size(); j++) {
						CourseInfo tmpList = (CourseInfo) courselist.get(j);
						if (tmpList.getId().equals(courseList.getId())) {
							flag = true;
						}
					}
					if (flag) {
						Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
								courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "已选" };
						model.addRow(rowData);
					} else {
						Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
								courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "未选" };
						model.addRow(rowData);
					}
				}
				courseTbl.setModel(model);
			} else {
				JOptionPane.showMessageDialog(null, "没有匹配的课程！");
			}
			return;
		}
		
		if(searchTypes == "授课教师")
		{
			List<CourseInfo> list = new ISelectCourseImpl(sockethelper).EnquiryCourseByTeacher(searchContent);
			searchCourseFrame.setVisible(false);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "搜索成功！");
				searchButton.setEnabled(false);
				returnButton.setEnabled(true);
				String[] columns = { "代码", "课程名称", "授课教师", "授课地点", "授课时间", "学分", "状态" };
				DefaultTableModel model = new DefaultTableModel(columns, 0);
				model.setRowCount(0);
				for (int i = 0; i < list.size(); i++) {
					CourseInfo courseList = (CourseInfo) list.get(i);
					boolean flag = false;
					for (int j = 0; j < courselist.size(); j++) {
						CourseInfo tmpList = (CourseInfo) courselist.get(j);
						if (tmpList.getId().equals(courseList.getId())) {
							flag = true;
						}
					}
					if (flag) {
						Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
								courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "已选" };
						model.addRow(rowData);
					} else {
						Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getTeacher(),
								courseList.getPlace(), courseList.getTime(), Double.valueOf(courseList.getCredit()), "未选" };
						model.addRow(rowData);
					}
				}
				courseTbl.setModel(model);
			} else {
				JOptionPane.showMessageDialog(null, "没有匹配的课程！");
			}
			return;
		}
	}
}
