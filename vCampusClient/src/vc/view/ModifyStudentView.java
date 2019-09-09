package vc.view;

import java.awt.Component;
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

public class ModifyStudentView extends JFrame{

	private JPanel MOdifyStudentPanel;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private TextField modifyID = new TextField("学号");
	private TextField modifyName = new TextField("姓名 ");
	private TextField modifySex = new TextField("性别 ");
	private TextField modifyAge = new TextField("年龄  ");
	private TextField modifyBirthDate = new TextField("出生日期 ");
	private TextField modifyAddress = new TextField("家庭住址 ");
	private TextField modifyMajor = new TextField("专业");
	private TextField modifyDorm = new TextField("宿舍 ");
	private JButton modifyConfirmButton = new JButton("确认修改");
	JScrollPane scrollPane_StuInfo = new JScrollPane();
	private JTable table_StuInfo = new JTable();
	private JButton refreshButton = new JButton("  刷新  ");
	
	
	public ModifyStudentView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.setVisible(false);
		run();
	}

	private void setMainPanel() {
	
		new JFrame();
		setVisible(true);
		setBounds(10, 20, 640, 357);
		setTitle("修改学生学籍信息");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		MOdifyStudentPanel = new JPanel();
		scrollPane_StuInfo = new JScrollPane();
		scrollPane_StuInfo.setViewportView(getStudentTable());		
		MOdifyStudentPanel.add(modifyConfirmButton);	
		MOdifyStudentPanel.add(scrollPane_StuInfo);
		add(MOdifyStudentPanel);  
        
		Box box = Box.createVerticalBox();
		       
		box.add(modifyID);
		box.add(modifyName);
		box.add(modifySex);
		box.add(modifyAge);
		box.add(modifyBirthDate);
		box.add(modifyAddress);
		box.add(modifyMajor);
		box.add(modifyDorm);           
		box.add(modifyConfirmButton);
		box.add(refreshButton);
		MOdifyStudentPanel.add(box);
	
		add(MOdifyStudentPanel);    		
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
		this.modifyConfirmButton.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      {
	        String sId = ModifyStudentView.this.modifyID.getText();
	        String sAge = ModifyStudentView.this.modifyAge.getText();
	        String sBirthdate = ModifyStudentView.this.modifyBirthDate.getText();
	        String sName = ModifyStudentView.this.modifyName.getText();
	        String sSex = ModifyStudentView.this.modifySex.getText();
	        String sPlace = ModifyStudentView.this.modifyAddress.getText();
	        String sDepart = ModifyStudentView.this.modifyMajor.getText();
	        String sDormitory = ModifyStudentView.this.modifyDorm.getText();
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
	          
	          boolean isSave = new IStudentImpl(ModifyStudentView.this.sockethelper).ModifyStudentView(s);
	          if (isSave)
	          {
	            JOptionPane.showMessageDialog(null, "修改成功");
	            ModifyStudentView.this.dialogClose();
	          }
	          else
	          {
	            JOptionPane.showMessageDialog(null, "修改失败");
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
	        ModifyStudentView.this.scrollPane_StuInfo.setViewportView(ModifyStudentView.this.getStudentTable());
	      }
	    });
		scrollPane_StuInfo.setViewportView(getStudentTable());
	}

	protected void dialogClose() {
		//this.dispose();
	}

}
