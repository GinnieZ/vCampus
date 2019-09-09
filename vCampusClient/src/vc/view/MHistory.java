package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vc.common.PatientInfo;
import vc.common.UserInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;

public class MHistory extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	PatientInfo patient = null;
	UserInfo user = null;
	
    String[] day=new String[100];
    String[] mhis=new String[100];
    int n=0;
    
    private JTable table;
	
	IHospitalimpl newIHospitalimpl;
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public MHistory(String id) throws SQLException {
		StudentId = id;
		patient = new PatientInfo(id,null,0,0,null,null,null);
		user = new UserInfo(id,null,null,null,null);
		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] day = newIHospitalimpl.readMDay(StudentId);
		String[] mhis = newIHospitalimpl.readMHistory(StudentId);

		n = day.length;
		//DefaultTableModel model=new DefaultTableModel(data, columns);
		String[][] content = new String[n][2];
		for(int i=0;i<n;i++) {
			content[i][0] = day[i];
			content[i][1] = mhis[i];
		}
		
		table=new JTable(new DefaultTableModel(
				content,
				new String[] {
					"\u65E5\u671F", "\u75C5\u60C5"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		//table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.setBounds(37, 79, 536, 200);
		table.setRowHeight(25);
		//����
		DefaultTableCellRenderer r =new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);

		JScrollPane scrollBookInfo = new JScrollPane(table);
		scrollBookInfo.setBounds(113, 73, 388, 275);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //����ˮƽ���������ǿɼ�
	    	getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);


		//contentPane.add(table);
	}

}
