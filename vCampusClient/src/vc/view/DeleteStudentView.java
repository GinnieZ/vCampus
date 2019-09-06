package vc.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import vc.common.CourseInfo;
import vc.common.StudentRollInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ISelectCourseImpl;
import vc.sendImpl.IStudentImpl;

public class DeleteStudentView extends JFrame{

	public JFrame mainFrame;
	private JPanel AddStudentPanel;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	
	private JButton deleteConfirmButton = new JButton("确认删除");
	private JTextField textField_StuId = new JTextField();
	//private Object btnDelete;
	private JTable table_StuInfo = new JTable();
	JScrollPane scrollPane_StuInfo = new JScrollPane();
	private JButton searchButton = new JButton("删除");
	

	public DeleteStudentView(String id) {
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
		AddStudentPanel = new JPanel();
		scrollPane_StuInfo = new JScrollPane();
		scrollPane_StuInfo.setViewportView(getStudentTable());		
		

		AddStudentPanel.add(deleteConfirmButton);
		
		
		//AddStudentPanel.add(deleteConfirmButton);
		AddStudentPanel.add(scrollPane_StuInfo);
		mainFrame.add(AddStudentPanel);     
       
		
	}
	private JTable getStudentTable() {

		table_StuInfo = new JTable();
		String[] columns = { "学生ID", "姓名", "年龄", "性别", "出生日期", "地址", "专业", "宿舍" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table_StuInfo.setModel(model);
		List<StudentRollInfo> list = new IStudentImpl(this.sockethelper).EnquiryAllStu(null);

		for (int i = 0; i < list.size(); i++)
	    {
	      StudentRollInfo courseList = (StudentRollInfo)list.get(i);
	      Object[] rowData = { courseList.getId(), courseList.getName(), courseList.getAge(), courseList.getGender(), courseList.getBirthday(), courseList.getBirthPlace(), courseList.getDepartment(), courseList.getDormitory() };
	      model.addRow(rowData);
	    }
	    return this.table_StuInfo;
	}
	private void run() {
		this.deleteConfirmButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	  
	        System.out.println("删除学生信息");
	       
	      
	        if (table_StuInfo.getSelectedRow() == -1) {
	          JOptionPane.showMessageDialog(null, "还未选中任何学生");
	          return;
	        }
	        StudentRollInfo stu = new StudentRollInfo("", "", "", "", "", "", "", ""/*, "", "", "", ""*/);
	        String str = (String)DeleteStudentView.this.table_StuInfo
	          .getValueAt(DeleteStudentView.this.table_StuInfo
	          .getSelectedRow(), 0);
	        stu.setId(str);
	        System.out.println(str);
	        boolean flag = new IStudentImpl(DeleteStudentView.this.sockethelper).DeleteStudentView(stu);
	        if (flag)
	        {
	          JOptionPane.showMessageDialog(null, "删除成功");
	          scrollPane_StuInfo.setViewportView(getStudentTable());
	          //DeleteStudentView.this.scrollPane_StuInfo.setViewportView(DeleteStudentView.this.getAllStuTable());
	        }
	        else
	        {
	          JOptionPane.showMessageDialog(null, "删除失败");
	        }
	      }
	    });
	}


}
