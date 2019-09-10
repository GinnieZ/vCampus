package vc.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vc.common.StudentRollInfo;
import vc.sendImpl.IStudentImpl;
import vc.helper.SocketHelper;

public class AdminFunctionView extends JFrame{

	private JPanel contentPane;
	private String id;
	private SocketHelper sockethelper = new SocketHelper();
	
	public AdminFunctionView(String myid)
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 120, 314, 256);
		sockethelper.getConnection();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		id = myid;
		
		JButton btnAdd = new JButton("增加学籍");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AddStudentView myAddStudentView = new AddStudentView(id);
				myAddStudentView.setVisible(true);
			   
				//dispose();
				
			}
		});
		btnAdd.setBounds(20, 20, 93, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("删除学籍");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DeleteStudentView myDeleteStudentView = new DeleteStudentView(id);
				myDeleteStudentView.setVisible(true);
				//dispose();
			}
		});
		btnDelete.setBounds(20, 60, 93, 23);
		contentPane.add(btnDelete);
		
		JButton btnModify = new JButton("修改学籍");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ModifyStudentView myModifyStudentView = new ModifyStudentView(id);
				myModifyStudentView.setVisible(true);
				//dispose();
			}
		});
		btnModify.setBounds(160, 20, 93, 23);
		contentPane.add(btnModify);
		
		
		
		JButton btnCheck = new JButton("查看学籍");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CheckStudentView myCheckStudentView = new CheckStudentView(id);
				myCheckStudentView.setVisible(true);
				
				//dispose();
			}
		});
		btnCheck.setBounds(160, 60, 93, 23);
		contentPane.add(btnCheck);
		
	}


	
}
