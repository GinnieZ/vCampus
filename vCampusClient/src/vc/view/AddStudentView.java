package vc.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vc.common.StudentRollInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IStudentImpl;

public class AddStudentView extends JFrame{

	private JPanel AddStudentPanel;
	private String StudentId;
	private TextField newID = new TextField("学号");
	private TextField newName = new TextField("姓名");
	private TextField newSex = new TextField("性别");
	private TextField newAge= new TextField("年龄");
	private TextField newBirthDate = new TextField("出生日期");
	private TextField newAddress = new TextField("地址");
	private TextField newMajor = new TextField("专业");
	private TextField newDorm = new TextField("宿舍");
	private JButton addConfirmButton = new JButton("确定添加");
	JScrollPane scrollPane_StuInfo = new JScrollPane();
	private SocketHelper sockethelper = new SocketHelper();
	private JTable table_StuInfo = new JTable();
	private JButton refreshButton = new JButton("  刷新  ");
	
	public AddStudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		run();
		
	}

	private Component getAllStuTable() {

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
	      StudentRollInfo studentList = (StudentRollInfo)list.get(i);
	      Object[] rowData = { studentList.getId(), studentList.getName(), studentList.getAge(), studentList.getGender(), studentList.getBirthday(), studentList.getBirthPlace(), studentList.getDepartment(), studentList.getDormitory() };
	      model.addRow(rowData);
	    }
	    return this.table_StuInfo;
	
	}

	private void setMainPanel() {
		
		new JFrame();
		setVisible(true);
		setBounds(10, 20, 640, 357);
		setTitle("增加学生学籍信息");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		AddStudentPanel = new JPanel();   
		scrollPane_StuInfo = new JScrollPane();
		scrollPane_StuInfo.setViewportView(getStudentTable());		
		AddStudentPanel.add(addConfirmButton);	
		AddStudentPanel.add(scrollPane_StuInfo);
		add(AddStudentPanel);  
                
		Box box = Box.createVerticalBox();
		 
	    //文本
		box.add(newID);			
		box.add(newName);
		box.add(newSex);
		box.add(newAge);	
		box.add(newBirthDate);
		box.add(newAddress);
		box.add(newMajor);
		box.add(newDorm);             
		box.add(addConfirmButton); 
		box.add(refreshButton);
		AddStudentPanel.add(box);
		add(AddStudentPanel);      		
	}
	
	private Component getStudentTable() {
		
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
	      StudentRollInfo studentList = (StudentRollInfo)list.get(i);
	      Object[] rowData = { studentList.getId(), studentList.getName(), studentList.getAge(), studentList.getGender(), studentList.getBirthday(), studentList.getBirthPlace(), studentList.getDepartment(), studentList.getDormitory() };
	      model.addRow(rowData);
	    }
	    return this.table_StuInfo;
	
	}

	private void run() {
		this.addConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sId = AddStudentView.this.newID.getText();
		        String sName = AddStudentView.this.newName.getText();
		        String sSex = AddStudentView.this.newSex.getText();
		        String sAge = AddStudentView.this.newAge.getText();
		        String sBirthdate = AddStudentView.this.newBirthDate.getText();
		        String sPlace = AddStudentView.this.newAddress.getText();
		        String sDepart = AddStudentView.this.newMajor.getText();
		        String sDormitory = AddStudentView.this.newDorm.getText();
		        if ((sId.length() != 0) && (sName.length() != 0) && 
		          (sAge.length() != 0) && (sBirthdate.length() != 0) && 
		          (sSex.length() != 0) && (sPlace.length() != 0) && (sDepart.length() != 0) && (sDormitory.length() != 0))
		        {
		          StudentRollInfo s = new StudentRollInfo("", "", "", "", "", "", "", "");
		          s.setId(sId);
		          s.setName(sName);
		          s.setGender(sSex);
		          s.setAge(sAge);
		          s.setBirthday(sBirthdate);
		          s.setDepartment(sDepart);
		          s.setDormitory(sDormitory);
		          s.setBirthPlace(sPlace);
		        
		          boolean isSave = new IStudentImpl(AddStudentView.this.sockethelper).AddStudentView(s);
		          if (isSave)
		          {
		            JOptionPane.showMessageDialog(null, "添加成功");
		            AddStudentView.this.dialogClose();
		          }
		          else
		          {
		            JOptionPane.showMessageDialog(null, "添加失败");
		          }
		        }
		        else
		        {
		          JOptionPane.showMessageDialog(null, "还有空项未输入");
		        }
		      }
			});
		this.refreshButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	        AddStudentView.this.scrollPane_StuInfo.setViewportView(AddStudentView.this.getAllStuTable());
	      }
	    });
		scrollPane_StuInfo.setViewportView(getAllStuTable());
	}

	protected void dialogClose() {
		//this.dispose();
	}
	
}
