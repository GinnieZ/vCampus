package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;

public class PayView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	PatientInfo patient = null;
	IHospitalimpl newIHospitalimpl;
	MedcineInfo medcine = null;

	/**
	 * Create the frame.
	 */
	public PayView(String id) {
		StudentId = id;
		patient = new PatientInfo(id,null,0,0,null,null,null);
		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel jl3=new JLabel(new ImageIcon("src/¸¶¿î¶þÎ¬Âë.jpg"));
		contentPane.add(jl3);
        jl3.setBounds(0, 150, 700, 500);
	}



}
