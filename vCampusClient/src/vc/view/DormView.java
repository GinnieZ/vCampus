package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.common.DormUtilityBillsInf;
import vc.common.DormVisitInf;
import vc.helper.SocketHelper;
import vc.sendImpl.IDormImpl;


public class DormView extends JFrame {

	private JPanel contentPane;
	private JLabel titleLable;
	private JLabel billsLabel;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JPanel titlePanel;
	private JPanel btn1Panel;
	private JPanel btn2Panel;
	private JPanel btn3Panel;
	private JPanel btn456Panel;
	private JPanel btn7Panel;
	private JPanel tablePanel;
	private JTable table;
	private MyTableModel mytablemodel;
	private SocketHelper sockethelper = new SocketHelper();
	private String stuId;
	private DormLivingInf livingInfo = new DormLivingInf();
	private DormChargeInf chargeInfo = new DormChargeInf();
	private DormRepairInf repairInfo = new DormRepairInf();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
////					DormView  mainWindow = new DormView();
////					mainWindow.setTitle("DormInfDisplay");
////					mainWindow.setSize(700,400);
////					mainWindow.setResizable(false);
////					mainWindow.setLocationRelativeTo(null);
////					mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////					mainWindow.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

//	public DormView() {
//		super();
//		initCompoent();
//		initUI();
//		addButtonListener();
//	}

	/**
	 * Create the frame.
	 */
	public DormView(SocketHelper sockethelper, String id) {
		super();
		this.setTitle("DormInfDisplay");
		this.setSize(700,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		initCompoent();
		initUI();
		addButtonListener();
		this.stuId = id;
		System.out.println("DormView:"+id);
		this.sockethelper = sockethelper;
	}

	public SocketHelper getsockethelper() {
		return this.sockethelper;
	}
	
	public void initCompoent() {
		btn1 = new JButton("住宿信息查询");
		btn2 = new JButton("水电费缴纳详情查询");
		btn3 = new JButton("宿舍维修详情查询");
		btn4 = new JButton("修改住宿信息");
		btn5 = new JButton("报修");
		btn6 = new JButton("缴费");
		btn7 = new JButton("每月水电费查询");
		titleLable = new JLabel("宿舍信息管理");
		mytablemodel = new MyTableModel();
		table = new JTable(mytablemodel);
		titlePanel = new JPanel();
		btn1Panel = new JPanel();
		btn2Panel = new JPanel();
		btn3Panel = new JPanel();
		btn456Panel = new JPanel();
		btn7Panel = new JPanel();
		tablePanel = new JPanel();
	}
	
	public void initUI() {
		//设置画布布局方式 
		GridBagLayout gridbaglayout = new GridBagLayout();
		this.setLayout(gridbaglayout);
		Font font = new Font("微软雅黑", Font.PLAIN, 25);
		titleLable.setFont(font);
		titlePanel.add(titleLable);
        this.add(titlePanel, new GBC(0,0,3,1).  
                setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));  
        //左侧中间的具体工具面板  
        btn1Panel.add(btn1);
        this.add(btn1Panel,new GBC(0,1).  
                     setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        //左侧下方的具体工具面板  
        btn2Panel.add(btn2); 
        this.add(btn2Panel,new GBC(0,2).  
                     setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        //左侧下方的具体工具面板  
        btn3Panel.add(btn3);
        this.add(btn3Panel,new GBC(0,3).  
                     setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        btn7Panel.add(btn7);
        this.add(btn7Panel,new GBC(0,4).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        //中间的表格
        setTableFont(table);
        JScrollPane scrollPane = new JScrollPane (table);
        tablePanel.add(scrollPane);
        this.add(tablePanel,new GBC(1,1,3,3).setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        
        //表格下面的一个面板
        btn456Panel.add(btn4);
        btn456Panel.add(btn5);
        btn456Panel.add(btn6);
        btn4.setVisible(false);
        btn5.setVisible(false);
        btn6.setVisible(false);
        this.add(btn456Panel,new GBC(2,4,1,1).setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
	}
	
	public void  setTableFont(JTable table) {
		// 设置表头文字居中显示,表格内容居中显示
        DefaultTableCellRenderer  renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(renderer.CENTER);
        DefaultTableCellRenderer r	= new DefaultTableCellRenderer();   
        r.setHorizontalAlignment(JLabel.CENTER);   
        table.setDefaultRenderer(Object.class, r);
		table.setShowHorizontalLines(true);
		table.setRowSelectionAllowed(true);
		table.setVisible(true);
	}
	
	public class GBC extends GridBagConstraints  
	{  
	   //初始化左上角位置  
	   public GBC(int gridx, int gridy)  
	   {  
	      this.gridx = gridx;  
	      this.gridy = gridy;  
	   }  
	  
	   //初始化左上角位置和所占行数和列数  
	   public GBC(int gridx, int gridy, int gridwidth, int gridheight)  
	   {  
	      this.gridx = gridx;  
	      this.gridy = gridy;  
	      this.gridwidth = gridwidth;  
	      this.gridheight = gridheight;  
	   }  
	  
	   //对齐方式  
	   public GBC setAnchor(int anchor)  
	   {  
	      this.anchor = anchor;  
	      return this;  
	   }  
	  
	   //是否拉伸及拉伸方向  
	   public GBC setFill(int fill)  
	   {  
	      this.fill = fill;  
	      return this;  
	   }  
	  
	   //x和y方向上的增量  
	   public GBC setWeight(double weightx, double weighty)  
	   {  
	      this.weightx = weightx;  
	      this.weighty = weighty;  
	      return this;  
	   }  
	  
	   //外部填充  
	   public GBC setInsets(int distance)  
	   {  
	      this.insets = new Insets(distance, distance, distance, distance);  
	      return this;  
	   }  
	  
	   //外填充  
	   public GBC setInsets(int top, int left, int bottom, int right)  
	   {  
	      this.insets = new Insets(top, left, bottom, right);  
	      return this;  
	   }  
	  
	   //内填充  
	   public GBC setIpad(int ipadx, int ipady)  
	   {  
	      this.ipadx = ipadx;  
	      this.ipady = ipady;  
	      return this;  
	   }  
	}  
	
	/**
  	表格模型实现，表格显示数据时将调用模型中的相应方法获取数据进行表格内容的显示
    */
	public class MyTableModel extends AbstractTableModel {
		private Vector<String> tableTitle;//表格的 列标题
		private Vector<Object[]> tableData;//用来存放表格数据的线性表

		public MyTableModel() {
	        tableTitle= new Vector<String>();
	    	tableData = new Vector<Object[]>();
	    	String [] titleData =new String[] {"学号","区号","单元号","楼号","房间号","床号","联系方式"};
//	    	Object[] rowData = new Object[]{"梅园",8,"D",401,4,119};
	    	setTitleModel(titleData);
//			tableData.add(rowData);
		}
	    //修改标题
	    public void setTitleModel(String[] title) {
	    	tableTitle.clear();
	    	for(String str: title) {
	    		tableTitle.add(str);
	    	}
	    }
	    
	    //修改表格数据
	    public void setDataModel(Object[] data,boolean clear) {
	    	if(clear) {
	    		tableData.clear();
	    	}
	    	tableData.add(data);
	    }
	    
	    @Override
	    public String getColumnName(int columnIndex) {
	    	return tableTitle.elementAt(columnIndex);
	    }
	    
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return tableData.size();
		}
	
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return tableTitle.size();
		}
	
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object temp[]=tableData.elementAt(rowIndex);
			return temp[columnIndex];
		}
		
		//设置表格允许编辑
		@Override
	    public boolean isCellEditable(int rowIndex, int columnIndex){
			return true;
		}
		
	    @Override
	    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
	    	//当单元格的数据发生改变的时候掉用该函数重设单元格的数据
	    	((String[])this.tableData.get(rowIndex))[columnIndex]=(String)aValue;
	    	super.setValueAt(aValue, rowIndex, columnIndex);
	    }
	    
	    public void setTableDataClear() {
	    	tableData.clear();
	    }
	}
	//获取当前学生水电费用信息
	public void getDormUtilityBillsInf() {
		DormUtilityBillsInf myInfo = new DormUtilityBillsInf();
		System.out.println("getDormChargeInf()");
		System.out.println(stuId);
		myInfo.setStudentID(stuId);
		System.out.println(myInfo.getStudentID());
		java.util.List<DormUtilityBillsInf> DormUtilityBillsInflist =  
				new IDormImpl(sockethelper).QueryDormUtilityBillsInf(myInfo);
		mytablemodel.setTableDataClear();
		System.out.println("DormUtilityBillsInflist.size():");
		System.out.println(DormUtilityBillsInflist.size());
		 for (int i = 0; i < DormUtilityBillsInflist.size()-1; i++)
		    {
			 DormUtilityBillsInf bList = (DormUtilityBillsInf)DormUtilityBillsInflist.get(i); 
				Object[] tableData = new Object[]{bList.getBillsID(),bList.getBillsTime(),bList.getStudentID(),
						bList.getUtilityBills()};
				mytablemodel.setDataModel(tableData, false);	
		    }
			String tableHead[] = new String[]{"记录单号","时间","学号","本月水电费"};
			mytablemodel.setTitleModel(tableHead);
			mytablemodel.fireTableStructureChanged();
	}
	
	//获取当前登录学生的水电缴纳信息并使表格界面改变
	public void getDormChargeInf() {
		DormChargeInf myInfo = new DormChargeInf();
		myInfo.setStudentID(stuId);
		java.util.List<DormChargeInf> DormChargeInflist =  new IDormImpl(sockethelper).QueryDormChargeInf(myInfo);
		mytablemodel.setTableDataClear();
		System.out.println("DormChargeInflist.size():");
		System.out.println(myInfo.getStudentID());
	    for (int i = 0; i < DormChargeInflist.size(); i++)
	    {
	    	DormChargeInf bList = (DormChargeInf)DormChargeInflist.get(i); 
			Object[] tableData = new Object[]{bList.getChargeID(),bList.getChargeTime(),bList.getStudentID(),
					bList.getChargeMoney(),bList.getArreas(),bList.getUtilityBills(),bList.getBalance()};
			chargeInfo = new DormChargeInf(bList.getChargeID(),bList.getChargeTime(),bList.getStudentID(),
					bList.getChargeMoney(),bList.getArreas(),bList.getUtilityBills(),bList.getBalance());
			mytablemodel.setDataModel(tableData, false);	
	    }
		String tableHead[] = new String[]{"缴费单号","缴费日期","缴纳人","缴纳费用","当前欠缴费","前月水电费","余额"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
	}
	
	//获取当前登录学生的个人住宿信息并使表格界面改变
	public void getDormLivingInf() {
		DormLivingInf myInfo = new DormLivingInf();
		myInfo.setStudentID(stuId);
		java.util.List<DormLivingInf> DormLivingInflist =  new IDormImpl(sockethelper).QueryDormLivingInf(myInfo);
		mytablemodel.setTableDataClear();
		System.out.println(DormLivingInflist.size());
	    for (int i = 0; i < DormLivingInflist.size(); i++)
	    {
	    	DormLivingInf bList = (DormLivingInf)DormLivingInflist.get(i); 
			Object[] tableData = new Object[]{bList.getStudentID(),bList.getregion(),bList.getunit(),
					bList.getbuilding(),bList.getroom(),bList.getbed(),bList.getphone()};
			livingInfo = new DormLivingInf(bList.getStudentID(),bList.getregion(),bList.getunit(),
					bList.getbuilding(),bList.getroom(),bList.getbed(),bList.getphone());
			mytablemodel.setDataModel(tableData, false);	
			System.out.println("获取当前登录学生的个人住宿信息并使表格界面改变:");
			System.out.println(bList.getphone());
	    }
		String tableHead[] = new String[]{"学号","区号","单元号","楼号","房间号","床号","联系方式"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
	}

	//获取当前登录学生的个人宿舍维修信息并使表格界面改变
	public void getDormRepairInf() {
		DormRepairInf myInfo = new DormRepairInf();
		myInfo.setStudentID(stuId);
		java.util.List<DormRepairInf> DormRepairInflist =  new IDormImpl(sockethelper).QueryDormRepairInf(myInfo);
		mytablemodel.setTableDataClear();
	    for (int i = 0; i < DormRepairInflist.size(); i++)
	    {
	    	DormRepairInf bList = (DormRepairInf)DormRepairInflist.get(i); 
			Object[] tableData = new Object[]{bList.getRepairID(),bList.getTime(),bList.getStudentID(),
					bList.getReason(),bList.getState()};
			repairInfo = new DormRepairInf(bList.getRepairID(),bList.getTime(),bList.getStudentID(),
					bList.getReason(),bList.getState());
			System.out.println("DormView里"+bList.getReason());
			mytablemodel.setDataModel(tableData, false);	
	    }
		String tableHead[] = new String[]{"报修记录编号","报修日期","报修人","报修原因","受理状况"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
	}

	public void getDormVisitInf() {
		DormVisitInf myInfo = new DormVisitInf();
		myInfo.setVisitName("夏之光");
		java.util.List<DormVisitInf> DormVisitInflist =  new IDormImpl(sockethelper).QueryDormVisitInf(myInfo);
		mytablemodel.setTableDataClear();
	    for (int i = 0; i < DormVisitInflist.size(); i++)
	    {
	    	DormVisitInf bList = (DormVisitInf)DormVisitInflist.get(i); 
			Object[] tableData = new Object[]{bList.getVisitID(),bList.getVisitName(),bList.getTimeIn(),
					bList.getTimeOut(),bList.getVisitReason()};
			mytablemodel.setDataModel(tableData, false);	
	    }
		String tableHead[] = new String[]{"来访记录单号","来访人","来访时间","离开时间","理由"};
		mytablemodel.setTitleModel(tableHead);
		mytablemodel.fireTableStructureChanged();
		
	}
	
	public void addButtonListener() {
		//查询住宿信息的监听
		btn1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("点击了btn1");
				btn4.setVisible(true);
				btn5.setVisible(false);
				btn6.setVisible(false);
				getDormLivingInf();
//				getDormVisitInf();
			}
		});
		
		//水电费缴纳情况查询的监听
		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("点击了btn2");
				btn4.setVisible(false);
				btn5.setVisible(false);
				btn6.setVisible(true);
				getDormChargeInf();
			}
		});
	/*
	 *来访人员
	 * */		
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("点击了btn2");
//				btn4.setVisible(true);
//				btn5.setVisible(false);
//				DormVisitInf myInfo = new DormVisitInf();
//				myInfo.setVisitName("夏之光");
//				java.util.List<DormVisitInf> DormVisitInflist =  new IDormImpl(sockethelper).QueryDormVisitInf(myInfo);
//				mytablemodel.setTableDataClear();
//			    for (int i = 0; i < DormVisitInflist.size()-1; i++)
//			    {
//			    	DormVisitInf bList = (DormVisitInf)DormVisitInflist.get(i); 
//					Object[] tableData = new Object[]{bList.getVisitID(),bList.getVisitName(),bList.getTimeIn(),
//							bList.getTimeOut(),bList.getVisitReason()};
//					mytablemodel.setDataModel(tableData, false);	
//			    }
//				String tableHead[] = new String[]{"来访记录单号","来访人","来访时间","离开时间","理由"};
//				mytablemodel.setTitleModel(tableHead);
//				mytablemodel.fireTableStructureChanged();	
//			}

		
		//宿舍维修信息查询
		btn3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("点击了btn3");
				btn4.setVisible(false);
				btn5.setVisible(true);
				btn6.setVisible(false);
				getDormRepairInf();
			}	
		});
		
		//修改住宿信息
		btn4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DormModifyLivingInfView modifyView = new DormModifyLivingInfView(sockethelper,livingInfo);
				modifyView.setVisible(true);
			}
		});
		
		btn5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DormAddRepairView addview = new DormAddRepairView(sockethelper,repairInfo);
				addview.setVisible(true);
			}
			
		});
		
		//新增缴费的监听
		btn6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("点击了btn6");
				DormChargeView dormchargeview = new DormChargeView(sockethelper,chargeInfo);
				dormchargeview.setVisible(true);
			}
			
		});
		
		//"查询水电费"的监听
		btn7.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("点击了btn7");
				btn4.setVisible(false);
				btn5.setVisible(false);
				btn6.setVisible(false);
				getDormUtilityBillsInf();
			}
		});
	}
}
