package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IHospitalimpl;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JTable;

public class MedcineBillView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private PatientInfo patient = null;
	private IHospitalimpl newIHospitalimpl;
	private MedcineInfo medcine = null;
	private MedcineInfo[] medcineinfolist;
	private String[] unpaidMedcine;
	private int num=0;
	private int[] Num = new int[10];
	private String[] medcineList = new String[10];
	private int ishere;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public MedcineBillView(String id) {
		StudentId = id;
		patient = new PatientInfo(id,null,0,0,null,null,null);
		sockethelper.getConnection();
		newIHospitalimpl = new IHospitalimpl(StudentId, this.sockethelper);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 604, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u836F\u540D");
		lblNewLabel.setBounds(87, 54, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u7528\u9014");
		lblNewLabel_1.setBounds(225, 54, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u4F7F\u7528\u8BF4\u660E");
		lblNewLabel_2.setBounds(346, 54, 65, 15);
		contentPane.add(lblNewLabel_2);
		
		try {
			this.connected();
			//System.out.println("个体连接服务器成功");
		} catch (ClassNotFoundException e1) {
			System.out.println("个体连接服务器失败");
			e1.printStackTrace();
		}
		//所有未付钱的药；
		unpaidMedcine = patient.getUnpaidMedcine_2();
		//System.out.println(unpaidMedcine.length);
		num = 0;
		for(int i=0;i<unpaidMedcine.length;i++) {
			if(num!=0) {
				ishere = -1;
				//System.out.println("unpaidMedcine["+i+"]:"+unpaidMedcine[i]);
				for(int j=0;j<num;j++) {
					//System.out.println("medcineList["+j+"]:"+medcineList[j]);
					if(medcineList[j].equals(unpaidMedcine[i])) {
						ishere = j;
					}
				}
				if(ishere != -1) {
					Num[ishere]++;
					//System.out.println("ishere:"+ishere);
					//System.out.println(Num[ishere]);
				}
				else {
					num++;
					medcineList[num-1] = unpaidMedcine[i];
					Num[num-1] = 1;
				}
			}
			else {
				num++;
				medcineList[0] = unpaidMedcine[0];
				Num[0] = 1;
			}
		}
		//System.out.println("长度："+medcineList.length+"first:"+medcineList[0]+"second"+medcineList[1]);
		medcineinfolist = new MedcineInfo[num];
		for(int i=0;i<num;i++) {
			medcine = new MedcineInfo(medcineList[i],null,null,null,null);
			try {
				medcine = newIHospitalimpl.getMedcineInfo(medcine);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			medcineinfolist[i] = medcine;
		}


		
		JButton btnNewButton = new JButton("\u4ED8\u6B3E");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					newIHospitalimpl.getPatientInfo(patient);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//药数量减少TODO
				//int n = Integer.parseInt(medcine.getNum());
				//medcine.setNum(String.valueOf(n-1));
				newIHospitalimpl.pay(patient);
				PayView payView = null;
				payView = new PayView(StudentId);
				payView.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(266, 305, 93, 23);
		contentPane.add(btnNewButton);
		
		Object[] columns={"药名","用途","使用说明","数量"};//字段
		String[][] data=new String[8][4];
		for(int i=0;i<num;i++) {
			data[i][0] = medcineinfolist[i].getName();
			data[i][1] = medcineinfolist[i].getUsage();
			data[i][2] = medcineinfolist[i].getInstruction();
			data[i][3] = String.valueOf(Num[i]);
		}
		
		//data[0][0] = "药名";data[0][1] = "用途";data[0][2] = "使用说明";data[0][3] = "数量";
		DefaultTableModel model=new DefaultTableModel(data, columns);
		JTable table=new JTable(model);		
		table.setBounds(37, 79, 536, 200);
		table.setRowHeight(25);
		//居中
		DefaultTableCellRenderer r =new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,   r);

		//FitTableColumns(table);
		contentPane.add(table);
		
		JLabel label = new JLabel("\u6570\u91CF");
		label.setBounds(492, 54, 54, 15);
		contentPane.add(label);

		
	}
	
	public void connected() throws ClassNotFoundException {
		patient = newIHospitalimpl.getPatientInfo(patient);
	}
	
	//设置列宽：适应单元格内容
	public void FitTableColumns(JTable myTable)
		{
			JTableHeader header = myTable.getTableHeader();
			int rowCount = myTable.getRowCount();
			Enumeration<TableColumn> columns = myTable.getColumnModel().getColumns();
			while (columns.hasMoreElements())
			{
				TableColumn column = (TableColumn) columns.nextElement();
				int col = header.getColumnModel().getColumnIndex(
						column.getIdentifier());
				int width = (int) myTable
						.getTableHeader()
						.getDefaultRenderer()
						.getTableCellRendererComponent(myTable,
								column.getIdentifier(), false, false, -1, col)
						.getPreferredSize().getWidth();
				for (int row = 0; row < rowCount; row++)
				{
					int preferedWidth = (int) myTable
							.getCellRenderer(row, col)
							.getTableCellRendererComponent(myTable,
									myTable.getValueAt(row, col), false, false,
									row, col).getPreferredSize().getWidth();
					width = Math.max(width, preferedWidth);
				}
				header.setResizingColumn(column); // 此行很重要
				column.setWidth(width + myTable.getIntercellSpacing().width + 10);
			}
		}
}
