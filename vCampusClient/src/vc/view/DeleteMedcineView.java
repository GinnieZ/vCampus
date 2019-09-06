package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vc.common.MedcineInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class DeleteMedcineView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private String StudentId;
	private String MID;
	private String name;
	private SocketHelper sockethelper = new SocketHelper();
	private JLabel lblNewLabel;
	private MedcineInfo medcine;
	IHospitalimpl newIHospitalimpl;
	private JTextField textField;
	private JTextField textField_1;
	
	

	/**
	 * Create the frame.
	 */
	public DeleteMedcineView(String id) {
		StudentId = id;

		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u836F\u54C1\u540D\u79F0\uFF1A");
		label.setBounds(48, 111, 75, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(129, 105, 223, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		
		JLabel lblid = new JLabel("\u836F\u54C1ID\uFF1A");
		lblid.setBounds(48, 57, 75, 15);
		contentPane.add(lblid);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(129, 54, 83, 21);
		contentPane.add(textField_1);
		
		JLabel label_2 = new JLabel("\u6216");
		label_2.setBounds(16, 111, 22, 15);
		contentPane.add(label_2);
		
		JButton btnNewButton = new JButton("\u5B8C\u6210");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				name = textField.getText();
				MID = textField_1.getText();
				save();
				dispose();
			}
		});
		btnNewButton.setBounds(168, 193, 93, 23);
		contentPane.add(btnNewButton);
		
	}
	
	private void save() {
		medcine = new MedcineInfo(MID, name, null, null, null);
		newIHospitalimpl.deleteMedcine(medcine);
	}
}
