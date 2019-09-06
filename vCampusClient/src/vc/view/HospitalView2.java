package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class HospitalView2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	PatientInfo admin = null;
	IHospitalimpl newIHospitalimpl;
	
	MedcineInfo medcine;
	
	private JPanel contentPane;
	private JLabel textField;
	private JLabel textField_1;
	private JLabel textField_2;
	/**
	 * Create the frame.
	 */
	public HospitalView2(String id) {
		StudentId = id;
		admin = new PatientInfo(id,null,0,0,null,null,null);
		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 351, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u59D3\u540D\uFF1A");
		lblNewLabel.setBounds(30, 41, 46, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u5E74\u9F84\uFF1A");
		lblNewLabel_1.setBounds(30, 66, 46, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6027\u522B\uFF1A");
		lblNewLabel_2.setBounds(230, 41, 46, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u4E2A\u4EBA\u4FE1\u606F\uFF1A");
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(10, 0, 92, 31);
		contentPane.add(lblNewLabel_3);
		
		try {
			this.connected();
			//System.out.println("个体连接服务器成功");
		} catch (ClassNotFoundException e1) {
			System.out.println("个体连接服务器失败");
			e1.printStackTrace();
		}
		
		textField = new JLabel();
		//System.out.println(patient.getName());
		textField.setText(admin.getName());
		textField.setBounds(82, 38, 66, 21);
		contentPane.add(textField);
		//textField.setColumns(10);
		
		textField_1 = new JLabel();
		//System.out.println("view"+admin.getGender());
		//textField_1.setText(patient.getGender());
		textField_1.setText(admin.getName());
		textField_1.setBounds(82, 63, 66, 21);
		contentPane.add(textField_1);
		textField_1.setText(String.valueOf(admin.getAge()));
		
		textField_2 = new JLabel();
		textField_2.setText((String)admin.getGender());
		textField_2.setBounds(272, 38, 66, 21);
		contentPane.add(textField_2);
		
		
		
		JButton btnNewButton = new JButton("\u6DFB\u52A0\u836F\u79CD");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddMedcineView addMedcineView = null;
				addMedcineView = new AddMedcineView(StudentId);
				addMedcineView.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(55, 91, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u5220\u9664\u836F\u79CD");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DeleteMedcineView deleteMedcineView = null;
				deleteMedcineView = new DeleteMedcineView(StudentId);
				deleteMedcineView.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(190, 91, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton button = new JButton("\u5F00\u836F");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MakeMedcineView makeMedcineView = null;
				makeMedcineView = new MakeMedcineView(StudentId);
				makeMedcineView.setVisible(true);
				dispose();
			}
		});
		button.setBounds(55, 125, 93, 23);
		contentPane.add(button);
		
		
	}
	
	public void connected() throws ClassNotFoundException {
		System.out.println("PInfo.getId()_-1："
				+ admin.getId());
		admin = newIHospitalimpl.getPatientInfo(admin);

	}
}
