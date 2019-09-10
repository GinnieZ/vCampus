package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Prescribe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//private JLabel lblNewLabel;
	Vector<MedcineInfo> v = new Vector<MedcineInfo>();
	Vector<PatientInfo> pv = new Vector<PatientInfo>();
	private MedcineInfo medcine;
	//private String[] medcineName = new String[100];
	private PatientInfo patient;
	IHospitalimpl newIHospitalimpl;
	private JTextField textField_1;
	
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private String mid,mname;
	private String pid,pname;
	private String prescription;
	private int n = 1;
	private JTextField textField_2;
	/**
	 * Create the frame.
	 */
	public Prescribe(String id) {
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
		
		
		JLabel lblid = new JLabel("\u6302\u53F7ID\uFF1A");
		lblid.setBounds(48, 57, 75, 15);
		contentPane.add(lblid);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(129, 54, 83, 21);
		contentPane.add(textField_1);
		
		
		JLabel lblid_1 = new JLabel("\u75C5\u60C5\uFF1A");
		lblid_1.setBounds(48, 88, 75, 15);
		contentPane.add(lblid_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(129, 85, 266, 53);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("\u6DFB\u52A0");

		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//mname = textField.getText();
				mid = textField_1.getText();
				//pname = textField_3.getText();
				prescription = textField_2.getText();
				if(prescription.equals("")) {
					prescription = new String(" ");
				}
				newIHospitalimpl.addMHistory(mid, prescription);
				
				HospitalView2 hsv = new HospitalView2(StudentId);
				hsv.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(174, 189, 93, 23);
		contentPane.add(btnNewButton);

	}
}
