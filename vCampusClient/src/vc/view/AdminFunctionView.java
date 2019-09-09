package vc.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AdminFunctionView extends JFrame{

	private JPanel contentPane;
	private String id;
	
	public AdminFunctionView(String myid)
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 120, 314, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		id = myid;
		
		JButton btnAdd = new JButton("增加学籍信息");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddStudentView myAddStudentView = new AddStudentView(id);
				myAddStudentView.setVisible(true);
				//dispose();
				
			}
		});
		btnAdd.setBounds(20, 20, 93, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("删除学籍信息");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DeleteStudentView myDeleteStudentView = new DeleteStudentView(id);
				myDeleteStudentView.setVisible(true);
				//dispose();
			}
		});
		btnDelete.setBounds(20, 50, 93, 23);
		contentPane.add(btnDelete);
		
		JButton btnModify = new JButton("修改学籍信息");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ModifyStudentView myModifyStudentView = new ModifyStudentView(id);
				myModifyStudentView.setVisible(true);
				//dispose();
			}
		});
		btnModify.setBounds(20, 80, 93, 23);
		contentPane.add(btnModify);
		
		
		
		JButton btnCheck = new JButton("查看学籍信息");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CheckStudentView myCheckStudentView = new CheckStudentView(id);
				myCheckStudentView.setVisible(true);
				//dispose();
			}
		});
		btnCheck.setBounds(20, 110, 93, 23);
		contentPane.add(btnCheck);
		
	}

	
}
