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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vc.common.DormChargeInf;
import vc.helper.SocketHelper;
import vc.sendImpl.IDormImpl;
import vc.view.DormModifyLivingInfView.GBC;

public class DormChargeView extends JFrame {

	private JLabel titleLabel;
	private JLabel label1;
	private JLabel label2;
	private JTextField text1;
	private JTextField text2;
	private JPanel titlePanel;
	private JPanel label1Panel;
	private JPanel label2Panel;
	private JPanel btnPanel;
	private JPanel black1Panel;
	private JPanel black2Panel;
	private JPanel black3Panel;
	private JButton btn;
	private int money;
	private String secret;
	private SocketHelper sockethelper;	
	private DormChargeInf theStudentChargeInfo;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					DormChargeView  mainWindow = new DormChargeView();
//					mainWindow.setTitle("Charge");
//					mainWindow.setSize(500,200);
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
//	public DormChargeView() {
//		initCompoent();
//		initUI();
//		this.theStudentChargeInfo = new DormChargeInf();
//	}
	
	/**
	 * Create the frame.
	 */
	public DormChargeView(SocketHelper mysockethelper, DormChargeInf info) {
		this.sockethelper = mysockethelper;
		this.theStudentChargeInfo =info;
		this.setTitle("Charge");
		this.setSize(500,200);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initCompoent();
		initUI();
		btnListener();
	}

	private void initCompoent() {
		// TODO Auto-generated method stub
		titleLabel = new JLabel("缴纳水电费");
		label1 = new JLabel("缴纳费用");
		label2 = new JLabel("一卡通密码");
		btn = new JButton("确认提交");
		titlePanel = new JPanel();
		btnPanel = new JPanel();
		label1Panel = new JPanel();
		label2Panel = new JPanel();
		black1Panel = new JPanel();
		black2Panel = new JPanel();
		black3Panel = new JPanel();
		text1 = new JTextField();
		text2 = new JTextField();
		money = 0;
		secret = "";
	}


	private void initUI() {
		//设置画布布局方式 
		GridBagLayout gridbaglayout = new GridBagLayout();
		this.setLayout(gridbaglayout);
		
		//标题
		Font font = new Font("微软雅黑", Font.PLAIN, 25);
		titleLabel.setFont(font);
		titlePanel.add(titleLabel);
//		titlePanel.setBackground(Color.GREEN);
        this.add(titlePanel, new GBC(0,0,4,1).  
                setFill(GBC.BOTH).setIpad(0,30).setWeight(100,0));
        //"缴费金额"标签的位置
        Font font1 = new Font("微软雅黑", Font.PLAIN, 15);
        label1.setFont(font1);
        label1Panel.add(label1);
//        label1Panel.setBackground(Color.PINK);
        getContentPane().add(label1Panel, new GBC(0,1,1,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));  
        //"缴纳金额"输入框的位置
        getContentPane().add(text1, new GBC(1,1,3,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //中间空一行
//        black1Panel.setBackground(Color.BLUE);
        getContentPane().add(black1Panel, new GBC(0,2,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //"一卡通输入密码"标签的位置
        label2.setFont(font1);
        label2Panel.add(label2);
//       label2Panel.setBackground(Color.YELLOW);
        getContentPane().add(label2Panel, new GBC(0,3,1,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100)); 
        //"一卡通输入密码"输入框的位置
        getContentPane().add(text2, new GBC(1,3,3,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //最后空一行访button
        btnPanel.add(btn);
        getContentPane().add(btnPanel, new GBC(0,4,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //最后再空一行
        getContentPane().add(black2Panel, new GBC(0,5,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));        
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
		money = Integer.parseInt(text1.getText().trim());
		System.out.println("money");
		System.out.println(money);
		secret = text2.getText().trim();
	}
	
	//btn的监听，确认后提料信息
	public void btnListener() {
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("点击了确认提交");
				// TODO Auto-generated method stub
				GetTextField();
				//获取当前系统时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String strDate = df.format(System.currentTimeMillis());
				System.out.println(strDate);
				DormChargeInf tempChargeInfo = new DormChargeInf(theStudentChargeInfo.getChargeID(),
						strDate,theStudentChargeInfo.getStudentID(),money,theStudentChargeInfo.getArreas(),
						0,theStudentChargeInfo.getBalance());
				System.out.println("ID:"+theStudentChargeInfo.getChargeID());
				if(new IDormImpl(sockethelper).AddDormChargeInf(tempChargeInfo)) {
					JOptionPane.showMessageDialog(null, "提示", "更改成功", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "error", "出错啦", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
