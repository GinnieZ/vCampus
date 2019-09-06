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
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.helper.SocketHelper;
import vc.sendImpl.IDormImpl;
import vc.view.DormChargeView.GBC;

public class DormAddRepairView extends JFrame {

	private SocketHelper sockethelper;
	private DormRepairInf theStudentRepairInfo;
	private JLabel titleLabel;
	private JLabel label1;
	private JPanel titlePanel;
	private JPanel label1Panel;
	private JPanel black1Panel;
	
	private JPanel btnPanel;
	private JTextField text1;
	private JButton btn;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					System.out.println("123");
//					DormAddRepairView view = new DormAddRepairView ();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public DormAddRepairView(SocketHelper mysockethelper,DormRepairInf info) {
		super();
		this.sockethelper = mysockethelper;
		this.theStudentRepairInfo =info;
		this.setTitle("DormRepair");
		this.setSize(500,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initCompoent();
		initUI();
		btnListener();
	}
	
//	public DormAddRepairView() {
//		super();
//		this.setTitle("DormRepair");
//		this.setSize(500,200);
//		this.setResizable(false);
//		this.setLocationRelativeTo(null);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setVisible(true);
//		this.reason = "";
//		initCompoent();
//		initUI();
//		btnListener();
//	}

	private void initCompoent() {
		// TODO Auto-generated method stub
		titleLabel = new JLabel("宿舍报修");
		label1 = new JLabel("报修原因");
		btn = new JButton("确认提交");
		text1 = new JTextField();
		titlePanel = new JPanel();
		btnPanel = new JPanel();
		label1Panel = new JPanel();
		black1Panel = new JPanel();
	}

	private void initUI() {
		// TODO Auto-generated method stub
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
        //"报修原因"标签的位置
        Font font1 = new Font("微软雅黑", Font.PLAIN, 15);
        label1.setFont(font1);
        label1Panel.add(label1);
//        label1Panel.setBackground(Color.PINK);
        getContentPane().add(label1Panel, new GBC(0,1,1,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));  
        //"报修原因"输入框的位置
        getContentPane().add(text1, new GBC(1,1,3,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //插入一个空白间隔
//        black1Panel.setBackground(Color.blue);
        getContentPane().add(black1Panel, new GBC(0,2,4,1).  
                setFill(GBC.BOTH).setIpad(70,90).setWeight(0,100));
        //最后空一行访button
        btnPanel.add(btn);
        getContentPane().add(btnPanel, new GBC(0,3,4,1).  
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
	
	private void btnListener() {
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取报修原因
				String reason = text1.getText().trim();
				theStudentRepairInfo.setReason(reason);
				//获取当前系统时间
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String strDate = df.format(System.currentTimeMillis());
				System.out.println(strDate);
				theStudentRepairInfo.setTime(strDate);
				if(new IDormImpl(sockethelper).AddDormRepairInf(theStudentRepairInfo)) {
					JOptionPane.showMessageDialog(null, "您已成功更改", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "error", "出错啦", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
