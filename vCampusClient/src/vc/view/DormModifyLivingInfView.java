package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;

import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormVisitInf;
import vc.helper.SocketHelper;
import vc.sendImpl.IDormImpl;
import vc.view.DormView.GBC;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class DormModifyLivingInfView extends JFrame {

	private SocketHelper sockethelper;	
	private DormLivingInf originLivingInfo;
	private JPanel titlePanel;	
	private JPanel label1Panel;
	private JPanel label2Panel;
	private JPanel label3Panel;
	private JPanel label4Panel;
	private JPanel label5Panel;
	private JPanel label6Panel;
	private JPanel text1Panel;
	private JPanel text2Panel;
	private JPanel text3Panel;
	private JPanel text4Panel;
	private JPanel text5Panel;
	private JPanel text6Panel;
	private JPanel buttonPanel;
	private JPanel blackPanel;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel labelTitle;
	private JTextField text1;
	private JTextField text2;
	private JTextField text3;
	private JTextField text4;
	private JTextField text5;
	private JTextField text6;
	private JButton btn;
	private String strRegion;
	private int IntUnit;
	private String strBuilding;
	private int IntRoom;
	private int IntBed;
	private String strPhone;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DormModifyLivingInfView  mainWindow = new DormModifyLivingInfView();
//					mainWindow.setTitle("UpdateLivingInf");
//					mainWindow.setSize(600,500);
//					mainWindow.setResizable(true);
//					mainWindow.setLocationRelativeTo(null);
//					mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					mainWindow.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	
//	public DormModifyLivingInfView() {
//		super();
//		initCompoent();
//		initUI();
//	}


	/**
	 * Create the frame.
	 */
	public DormModifyLivingInfView(SocketHelper socket,DormLivingInf info) {
		super();
		this.setTitle("UpdateLivingInf");
		this.setSize(600,400);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.originLivingInfo = info;
		this.sockethelper = socket;
		initCompoent();
		initUI();
		btnListener();
	}

	private void initCompoent() {
		// TODO Auto-generated method stub
		titlePanel = new JPanel();
		label1Panel = new JPanel();
		label2Panel = new JPanel();
		label3Panel = new JPanel();
		label4Panel = new JPanel();
		label5Panel = new JPanel();
		label6Panel = new JPanel();
		text1Panel = new JPanel();
		text2Panel = new JPanel();
		text3Panel = new JPanel();
		text3Panel = new JPanel();
		text4Panel = new JPanel();
		text5Panel = new JPanel();
		text6Panel = new JPanel();
		buttonPanel = new JPanel();
		blackPanel = new JPanel();
		label1 = new JLabel("region");
		label2 = new JLabel("unit");
		label3 = new JLabel("building");	
		label4 = new JLabel("room");
		label5 = new JLabel("bed");	
		label6 = new JLabel("phone");
		labelTitle = new JLabel("更改个人住宿信息");
		text1 = new JTextField(originLivingInfo.getregion());
		text2 = new JTextField(String.valueOf(originLivingInfo.getunit()));
		text3 = new JTextField(originLivingInfo.getbuilding());
		text4 = new JTextField(String.valueOf(originLivingInfo.getroom()));
		text5 = new JTextField(String.valueOf(originLivingInfo.getbed()));
		text6 = new JTextField(originLivingInfo.getphone());
		btn = new JButton("确认提交");
		strRegion = "";
		IntUnit = 0;
		strBuilding = "";
		IntRoom = 0;
		IntBed = 0;
		strPhone = "";
	}

	private void initUI(){
		//设置画布布局方式 
		GridBagLayout gridbaglayout = new GridBagLayout();
		gridbaglayout.rowWeights = new double[]{};
		gridbaglayout.columnWeights = new double[]{};
		getContentPane().setLayout(gridbaglayout);
		Font font = new Font("微软雅黑", Font.PLAIN, 25);
		labelTitle.setFont(font);
		titlePanel.add(labelTitle);
        getContentPane().add(titlePanel, new GBC(0,0,3,1).  
                setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));  
        buttonPanel.add(btn);
        getContentPane().add(buttonPanel, new GBC(0,12,2,2).  
                setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));  
        //做一个空白填充
        getContentPane().add(blackPanel, new GBC(2,1,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100)); 
        label1Panel.add(label1);
        getContentPane().add(label1Panel,new GBC(0,1,1,2).
        		setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        label2Panel.add(label2);
        getContentPane().add(label2Panel,new GBC(0,3,1,2).  
        		setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        label3Panel.add(label3);
        getContentPane().add(label3Panel,new GBC(0,5,1,2).  
        		setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        label4Panel.add(label4);
        getContentPane().add(label4Panel,new GBC(0,7,1,2).  
        		setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        label5Panel.add(label5);
        getContentPane().add(label5Panel,new GBC(0,9,1,2).  
        		setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        label6Panel.add(label6);
        getContentPane().add(label6Panel,new GBC(0,11,1,2).  
             	setFill(GBC.BOTH).setIpad(70, 90).setWeight(0, 100));
        getContentPane().add(text1,new GBC(1,1,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text1Panel,new GBC(1,2,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text2,new GBC(1,3,1,1).  
        		setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text3Panel,new GBC(1,4,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text3,new GBC(1,5,1,1).  
              setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text4Panel,new GBC(1,6,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text4,new GBC(1,7,1,1).  
              setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text5Panel,new GBC(1,8,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text5,new GBC(1,9,1,1).  
              setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text6Panel,new GBC(1,10,1,1).  
                setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
        getContentPane().add(text6,new GBC(1,11,1,1).  
              setFill(GBC.BOTH).setIpad(70, 90).setWeight(50, 100));
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
	
	//获取输入框的内容
	public void GetTextField() {
		strRegion = text1.getText().trim();		
		IntUnit = Integer.parseInt(text2.getText().trim());
		strBuilding = text3.getText().trim();
		IntRoom = Integer.parseInt(text4.getText().trim());
		IntBed = Integer.parseInt(text5.getText().trim());
		strPhone = text6.getText().trim();
	}
	
	//btn的监听，确认后提料信息
	public void btnListener() {
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GetTextField();
				DormLivingInf tempInfo = new DormLivingInf(originLivingInfo.getStudentID(),
						strRegion,IntUnit,strBuilding,IntRoom,IntBed,strPhone);
				if(new IDormImpl(sockethelper).ModifyDormLivingInf(tempInfo)) {
					JOptionPane.showMessageDialog(null, "您已成功更改", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "error", "出错啦", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}


