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

public class MakeMedcineView extends JFrame {

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
	private JTextField textField;
	private JTextField textField_1;
	
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private String mid,mname;
	private String pid,pname;
	private int n = 0;
	private JTextField textField_2;
	private JTextField textField_3;
	/**
	 * Create the frame.
	 */
	public MakeMedcineView(String id) {
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
		label.setBounds(48, 82, 75, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(129, 79, 223, 21);
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
		label_2.setBounds(10, 82, 22, 15);
		contentPane.add(label_2);
		
		
		JLabel lblid_1 = new JLabel("\u75C5\u4EBAID\uFF1A");
		lblid_1.setBounds(48, 120, 75, 15);
		contentPane.add(lblid_1);
		
		JLabel label_4 = new JLabel("\u75C5\u4EBA\u540D\u79F0\uFF1A");
		label_4.setBounds(48, 145, 75, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u6216");
		label_5.setBounds(10, 145, 22, 15);
		contentPane.add(label_5);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(129, 117, 83, 21);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(129, 142, 223, 21);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("\u6DFB\u52A0");
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mname = textField.getText();
				mid = textField_1.getText();
				pname = textField_3.getText();
				pid = textField_2.getText();
				v.add(new MedcineInfo(mid,mname,null,null,null));
				pv.add(new PatientInfo(pid,pname,0,0,null,null,null));
				n++;
			}
		});
		btnNewButton.setBounds(80, 193, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u5B8C\u6210");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = 0;
				for(i=0;i<n;i++) {
					medcine = v.get(i);
					patient = pv.get(i);
					try {
						patient = newIHospitalimpl.getPatientInfo(patient);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						medcine = newIHospitalimpl.getMedcineInfo(medcine);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					patient.addUnpaidMedcine_3(medcine.getId());
					//medcineName[i] = medcine.getName();
					try {
						newIHospitalimpl.modifyPatientInfo(patient);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				v.clear();
				pv.clear();
			}
		});
		btnNewButton_1.setBounds(232, 193, 93, 23);
		contentPane.add(btnNewButton_1);

	}
}
