package vc.view;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import vc.common.StudentRollInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IStudentImpl;

public class StudentView extends JFrame {
	
	private JPanel StudentRollPanel;
	
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	
	private JTextField NameText = new JTextField(20);
	private JTextField SexText = new JTextField(20);
	private JTextField AgeText = new JTextField(20);
	private JTextField BirthdateText = new JTextField(20);
	private JTextField AddressText = new JTextField(20);
	private JTextField MajorText = new JTextField(20);
	private JTextField DormText = new JTextField(20);
	private JTable StudentTbl;
	
	JLabel nameLabel = new JLabel("姓名 ");
	JLabel sexLabel = new JLabel("性别 ");
	JLabel ageLabel = new JLabel("年龄  ");
	JLabel birthLabel = new JLabel("出生日期 ");
	JLabel addressLabel = new JLabel("家庭住址 ");
	JLabel majorLabel = new JLabel("专业 ");
	JLabel dormLabel = new JLabel("宿舍 ");
	
	public StudentView(String id) {
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
		setTitle("学生学籍信息");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        StudentRollPanel = new JPanel();
       
        Box box = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        //盒式布局标签
        box2.add(nameLabel);
        box2.add(sexLabel);
        box2.add(ageLabel);
        box2.add(birthLabel);
        box2.add(addressLabel);
        box2.add(majorLabel);
        box2.add(dormLabel);
        //盒式布局文本框
        box.add(NameText);
        box.add(SexText);
        box.add(AgeText);
        box.add(BirthdateText);
        box.add(AddressText);
        box.add(MajorText);
        box.add(DormText);
            
        StudentRollPanel.add(box2);
        StudentRollPanel.add(box);
		add(StudentRollPanel);
		
	}
	
	private void run() {
		StudentRollInfo stu = new StudentRollInfo(this.StudentId, "", "", "", "", "", "", "");
	    System.out.println(stu.getId());
	    List<StudentRollInfo> stuList = new IStudentImpl(this.sockethelper).EnquiryStuById(stu);
	    int i;
	    for ( i = 0; i < stuList.size(); i++)
	    {
	      StudentRollInfo tmp = (StudentRollInfo)stuList.get(i);
	      if (tmp.getId().equals(this.StudentId)) {
	        break;
	      }
	    }
	    StudentRollInfo tmpStu = (StudentRollInfo)stuList.get(i);
	    this.NameText.setText(tmpStu.getName());
	    this.NameText.setEditable(false);
	    this.AgeText.setText(tmpStu.getAge());
	    this.AgeText.setEditable(false);
	    this.SexText.setText(tmpStu.getGender());
	    this.SexText.setEditable(false);
	    this.BirthdateText.setText(tmpStu.getBirthday());
	    this.BirthdateText.setEditable(false);
	    this.AddressText.setText(tmpStu.getBirthPlace());
	    this.AddressText.setEditable(false);
	    this.MajorText.setText(tmpStu.getDepartment());
	    this.MajorText.setEditable(false);
	    this.DormText.setText(tmpStu.getDormitory());
	    this.DormText.setEditable(false);
	}
}
