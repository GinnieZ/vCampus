package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddMedcineView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private String name,usage,num,instruction;
	private JLabel lblNewLabel;
	private MedcineInfo medcine;
	IHospitalimpl newIHospitalimpl;
	private JButton btnNewButton;
	/**
	 * Create the frame.
	 */
	public AddMedcineView(String id) {
		StudentId = id;

		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbll = new JLabel("\u540D\u79F0\uFF1A");
		lbll.setBounds(48, 36, 53, 23);
		contentPane.add(lbll);
		
		JLabel label = new JLabel("\u7528\u9014\uFF1A");
		label.setBounds(48, 83, 53, 23);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u6570\u91CF\uFF1A");
		label_1.setBounds(236, 36, 53, 23);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u7528\u6CD5\uFF1A");
		label_2.setBounds(48, 136, 53, 23);
		contentPane.add(label_2);
		
		textField = new JTextField();
		textField.setBounds(103, 37, 123, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(280, 37, 66, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(103, 84, 278, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(103, 137, 278, 21);
		contentPane.add(textField_3);

		
		btnNewButton = new JButton("\u5B8C\u6210");
		/*
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		*/
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				name = textField.getText();
				usage = textField_2.getText();
				num = textField_1.getText();
				instruction = textField_3.getText();
				save(name,usage,num,instruction);
				dispose();
			}
		});
		btnNewButton.setBounds(172, 199, 93, 23);
		contentPane.add(btnNewButton);
	}
	
	private void save(String name,String usage,String num,String instruction) {
		medcine = new MedcineInfo(null, name, usage, num, instruction);
		newIHospitalimpl.addMedcine(medcine);
	}
}
