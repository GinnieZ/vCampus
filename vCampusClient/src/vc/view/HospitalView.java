package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import vc.common.PatientInfo;
import vc.common.UserInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;

public class HospitalView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	PatientInfo patient = null;
	UserInfo user = null;
	int register = -1;
	
	IHospitalimpl newIHospitalimpl;
	
	private JPanel contentPane;
	private JLabel textField;
	private JLabel textField_1;
	private JLabel textField_2;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HospitalView2 frame = new HospitalView2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 */
	public HospitalView(String id){
		StudentId = id;
		patient = new PatientInfo(id,null,0,0,null,null,null);
		user = new UserInfo(id,null,null,null,null);
		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
			System.out.println("尝试连接");
			this.connected();
			//System.out.println("个体连接服务器成功");
		} catch (ClassNotFoundException e1) {
			System.out.println("个体连接服务器失败");
			e1.printStackTrace();
		}
		
		//System.out.println("register"+register);
		
		textField = new JLabel();
		//System.out.println(patient.getName());
		textField.setText(patient.getName());
		textField.setBounds(82, 38, 66, 21);
		contentPane.add(textField);
		//textField.setColumns(10);
		
		textField_1 = new JLabel();
		//System.out.println("view"+patient.getGender());
		//textField_1.setText(patient.getGender());
		textField_1.setText(patient.getName());
		textField_1.setBounds(82, 63, 66, 21);
		contentPane.add(textField_1);
		textField_1.setText(String.valueOf(patient.getAge()));
		
		textField_2 = new JLabel();
		textField_2.setText((String)patient.getGender());
		textField_2.setBounds(272, 38, 66, 21);
		contentPane.add(textField_2);
		
		//挂号
		JButton Button_1 = new JButton("\u6302\u53F7");
		Button_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				register = newIHospitalimpl.register(patient);
				JOptionPane.showMessageDialog(null, "恭喜您挂号成功，您的号码为"+register);
			}
		});
		Button_1.setBounds(79, 124, 93, 23);
		contentPane.add(Button_1);
		
		//病史
		JButton Button_3 = new JButton("\u75C5\u53F2");
		Button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 //System.exit(0);
				MHistory ms;
				try {
					ms = new MHistory(StudentId);
					ms.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				dispose(); 
				
			}
		});
		Button_3.setBounds(256, 124, 93, 23);
		contentPane.add(Button_3);
		
		//药单
		JButton button_2 = new JButton("\u836F\u5355");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MedcineBillView myMedcineBillView = null;
				myMedcineBillView = new MedcineBillView(StudentId);
				myMedcineBillView.setVisible(true);

			}
		});
		button_2.setBounds(79, 186, 93, 23);
		contentPane.add(button_2);
		
		//返回 System.exit(0)
		JButton button_4 = new JButton("\u8FD4\u56DE");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 //System.exit(0);
				dispose(); 
				Function funtion = new Function(StudentId, 0);
		        funtion.setVisible(true);
		        dispose();
			}
		});
		button_4.setBounds(256, 186, 93, 23);
		contentPane.add(button_4);
		
		
	}

	public void connected() throws ClassNotFoundException {
		//user.setStuId(StudentId);
		patient = newIHospitalimpl.getUserInfo(user);
		//newIHospitalimpl.addPatient(patient);
		//register = patient.getRegister();
		//patient.setGender("女");
		//newIHospitalimpl.register(patient,register);
	}

}
