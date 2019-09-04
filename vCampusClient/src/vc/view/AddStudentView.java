package vc.view;

import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vc.common.StudentRollInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IStudentImpl;

public class AddStudentView extends JFrame{

	public JFrame mainFrame;
	private JPanel AddStudentPanel;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private TextField newID = new TextField(20);
	private TextField newName = new TextField(20);
	private TextField newSex = new TextField(20);
	private TextField newAge= new TextField(20);
	private TextField newBirthDate = new TextField(20);
	private TextField newAddress = new TextField(20);
	private TextField newMajor = new TextField(20);
	private TextField newDorm = new TextField(20);
	private JButton addConfirmButton = new JButton("确定添加");

	JLabel IDLabel = new JLabel("学号");
	JLabel nameLabel = new JLabel("姓名 ");
	JLabel sexLabel = new JLabel("性别 ");
	JLabel ageLabel = new JLabel("年龄  ");
	JLabel birthLabel = new JLabel("出生日期 ");
	JLabel addressLabel = new JLabel("家庭住址 ");
	JLabel majorLabel = new JLabel("专业 ");
	JLabel dormLabel = new JLabel("宿舍 ");
	
	public AddStudentView(String id) {
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
       
                
		Box box = Box.createVerticalBox();
		Box box2 = Box.createVerticalBox();
		box2.add(IDLabel);
		box2.add(nameLabel);    
	    box2.add(sexLabel);
	    box2.add(ageLabel);
	    box2.add(birthLabel);
	    box2.add(addressLabel);
	    box2.add(majorLabel);
	    box2.add(dormLabel); 
	    
		box.add(newID);			
		box.add(newName);
		box.add(newSex);
		box.add(newAge);	
		box.add(newBirthDate);
		box.add(newAddress);
		box.add(newMajor);
		box.add(newDorm);             
		box.add(addConfirmButton); 
		AddStudentPanel.add(box2);
		AddStudentPanel.add(box);
		AddStudentPanel.add(addConfirmButton);
		mainFrame.add(AddStudentPanel);      		
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
		          StudentRollInfo s = new StudentRollInfo("", "", "", "", "", "", "", ""/*, "", "", "", ""*/);
		          s.setId(sId);
		          s.setName(sName);
		          s.setGender(sSex);
		          s.setAge(sAge);
		          s.setBirthday(sBirthdate);
		          s.setDepartment(sDepart);
		          s.setDormitory(sDormitory);
		          s.setBirthPlace(sPlace);
		          //s.setEntranceTime("");
		          //s.setMajor("");
		          //s.setNation("");
		          //s.setPhoto("");
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
			}

	protected void dialogClose() {
		// TODO Auto-generated method stub
		this.mainFrame.dispose();
	}
	
}
