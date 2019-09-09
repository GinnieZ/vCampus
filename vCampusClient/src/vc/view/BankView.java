package vc.view;
/** 银行
* @author 09017406
* 库乃·阿炸题
*/
import vc.common.BankInfo;
import vc.common.StudentRollInfo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vc.helper.SocketHelper;
import vc.sendImpl.IBankImpl;

public class BankView extends JFrame {
	JFrame mainFrame;
	JFrame checkFrame;
	JFrame transferFrame;
	private JPanel mainPanel;
	
	private JPanel jpC1;
	private JPanel jpC2;
	private JPanel jpC3;
	private JPanel jpC4;
	
	private JPanel jpT1;
	private JPanel jpT2;
	private JPanel jpT3;
	private JPanel jpT4;
	private JPanel jpT5;
	
	
	private SocketHelper sockethelper = new SocketHelper();
	
	private JTextField cardIDField;//银行账号(余额查询)
	private JTextField cardIDField2;//银行账号(转账)
	private JTextField pwdFieldC;//密码(余额查询)
	private JTextField banlanceField;//余额
	private JTextField recieverIdField;//接受者账号
	private JTextField amountField;//转账金额
	private JTextField pwdFieldT;//密码(转账)
	
	private JButton btnCheck=new JButton("余额查询");
	private JButton btnTransfer=new JButton("转账");
	private JButton btnRecord=new JButton("转账记录");
	private JButton btnBack=new JButton("退出");
	
	String id = null;
	
	 public BankView(String stuId) {
		 sockethelper.getConnection();
		 this.id = stuId;
		 setMainPanel();
		 this.setVisible(false);
	 }
	 
	 private void setMainPanel() {
		 mainFrame = new JFrame();
		 mainFrame.setVisible(true);
		 mainFrame.setSize(300,400);
		 mainFrame.setTitle("银行管理");
		 mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		 
		 mainPanel = new JPanel();
		 mainFrame.add(mainPanel);
		 
		 btnCheck.setBounds(30, 26, 54, 15);
		 mainPanel.add(btnCheck);
		 btnCheck.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 setCheckPanel();
				 checkFrame.setVisible(true);
			 
			 }
		 });
		 
		 btnTransfer.setBounds(30, 66, 54, 15);
		 mainPanel.add(btnTransfer);
		 btnTransfer.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 setTransferPanel();
				 transferFrame.setVisible(true);
			 //
			 }
		 });
		 
		 btnBack.setBounds(30, 106, 54, 15);
		 mainPanel.add(btnBack);
		 btnBack.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 Function funtion = new Function(id, 0);
				 funtion.setVisible(true);
				 mainFrame.dispose();
			 }
		 });
		 
	 }
	 
	 private void setCheckPanel() {
		 checkFrame=new JFrame();
		 checkFrame.setResizable(false);
		 checkFrame.setSize(300,400);
		 checkFrame.setTitle("余额查询");
		 checkFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		 checkFrame.setLayout(new GridLayout(5,1));
		 
		 jpC1=new JPanel();
		 jpC2=new JPanel();
		 jpC3=new JPanel();
		 jpC4=new JPanel();
		 
		 JLabel lblCardID = new JLabel("银行账号");
		 cardIDField=new JTextField(10);
		 
		 jpC1.add(lblCardID);
		 jpC1.add(cardIDField);
		 
		 JLabel lblPwd = new JLabel("密码");
		 pwdFieldC=new JTextField(10);
		 
		 jpC2.add(lblPwd);
		 jpC2.add( pwdFieldC);
		 
		 JButton btnOK=new JButton("确定");
		 btnOK.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 String Bid = cardIDField.getText();
				 String pwd = pwdFieldC.getText();
				 System.out.println("id in bankView" + Bid);
			        if ((id.equals("")) || (pwd.equals("")))
			        {
			          JOptionPane.showMessageDialog(null, "请输入内容~");
			          return;
			        }
			        Double test = new IBankImpl(sockethelper,id).checkAccount(Bid);
			        
			        BankView.this.banlanceField.setText(String.valueOf(test));
			        BankView.this.banlanceField.setEditable(false);
			 }
		 });
		 
		 JButton btnBack1=new JButton("退出");
		 btnBack1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 setMainPanel();
				 mainFrame.setVisible(true);
				 checkFrame.dispose();
			 }
		 });
		 
		 JButton btnClean=new JButton("清除");
		 btnClean.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 cardIDField.setText("");
				 pwdFieldC.setText("");
			 }
		 });
		 
		 jpC3.add(btnOK);
		 jpC3.add(btnBack1);
		 jpC3.add(btnClean);
		 
		 JLabel lblBanlance=new JLabel("余额:");
		 banlanceField=new JTextField(10);
		 
		 jpC4.add(lblBanlance);
		 jpC4.add(banlanceField);
		 
		 checkFrame.add(jpC1);
		 checkFrame.add(jpC2);
		 checkFrame.add(jpC3);
		 checkFrame.add(jpC4);
		 checkFrame.setVisible(true);
	 }
	 
	 private void setTransferPanel() {
		 transferFrame=new JFrame();
		 transferFrame.setResizable(false);
		 transferFrame.setSize(300,400);
		 transferFrame.setTitle("快速转账");
		 transferFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		 transferFrame.setLayout(new GridLayout(5,1));
		 
		 jpT1=new JPanel();
		 jpT2=new JPanel();
		 jpT3=new JPanel();
		 jpT4=new JPanel();
		 jpT5=new JPanel();
		 
		 JLabel lblCardID = new JLabel("银行账号");
		 cardIDField2=new JTextField(10);
		 
		 jpT1.add(lblCardID);
		 jpT1.add(cardIDField2);

		 JLabel lblRecieverID = new JLabel("对方账号");		 
		 recieverIdField=new JTextField(10);
		 
		 jpT2.add(lblRecieverID);
		 jpT2.add(recieverIdField);
		 
		 JLabel lblAmount = new JLabel("金额");		 
		 amountField=new JTextField(10);
		 
		 jpT3.add(lblAmount);
		 jpT3.add(amountField);
		 
		 JLabel lblPwd1 = new JLabel("密码");
		 pwdFieldT=new JTextField(10);
		 
		 jpT4.add(lblPwd1);
		 jpT4.add(pwdFieldT);
		 
		 JButton btnOK2=new JButton("确定");
		 btnOK2.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 String password = BankView.this.pwdFieldT.getText();
			        
			        String receiver = BankView.this.recieverIdField.getText();
			        if ((amountField.getText().equals("")) || (recieverIdField.getText().equals("")) || (password.equals("")))
			        {
			          JOptionPane.showMessageDialog(null, "请输入内容~");
			          return;
			        }
			        String Bid = cardIDField2.getText();
			        double money = Double.valueOf(BankView.this.amountField.getText()).doubleValue();
			        double test = new IBankImpl(sockethelper,id).checkAccount(Bid);
			        if (test < money)
			        {
			          JOptionPane.showMessageDialog(null, "余额不足！");
			          return;
			        }
			        boolean flag = new IBankImpl(sockethelper,id).transferAccount(Bid,money, receiver, password, test);
			        System.out.println("flag is " + flag);
			        if (flag) {
			          JOptionPane.showMessageDialog(null, "转账成功！");
			        } 
			        else {
			          JOptionPane.showMessageDialog(null, "转账失败！");
			        }
			 }
		 });
		 
		 JButton btnBack2=new JButton("退出");
		 btnBack2.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 setMainPanel();
				 mainFrame.setVisible(true);
				 transferFrame.dispose();
			 }
		 });
		 
		 JButton btnClean1=new JButton("清除");
		 btnClean1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
				 sockethelper.getConnection();
				 amountField.setText("");
				 recieverIdField.setText("");
				 pwdFieldT.setText("");
			 }
		 });
		 
		 jpT5.add(btnOK2);
		 jpT5.add(btnBack2);
		 jpT5.add(btnClean1);
		 
		 transferFrame.add(jpT1);
		 transferFrame.add(jpT2);
		 transferFrame.add(jpT3);
		 transferFrame.add(jpT4);
		 transferFrame.add(jpT5);
		 transferFrame.setVisible(true);
	 }

}
