package vc.view;

import javax.swing.*;
import javax.swing.table.TableColumn;

import vc.server.ServerThread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ServerView extends JFrame
{
	private JButton btnStart = new JButton("开启服务器");
	private JButton btnClose = new JButton("关闭服务器");
	private JLabel lblMsgTime;
	private JLabel lblShowTime;
	private JLabel lblMsgState;
	private JLabel lblShowState;
	private JLabel lblMsgNum;
	private JPanel Panel_Header;
	private static JLabel lblShowNum;
	public static JFrame Frame_Server;
	JScrollPane ScrollPane_ShowOnlineMessage;
	private static JPanel Panel_Main;
	private JLabel Label_MessageOnlineMessage;
	public static JTextArea TextArea_ShowOnlineMessage;
	public static int count;

	
	private ServerThread mainthread;
	GridBagConstraints gbs=new GridBagConstraints();//实例化这个对象用来对组件进行管理
	public ServerView()
	{
//		this.setBounds(360, 150, 640, 357);
//		this.setTitle("虚拟校园服务器端");
//		//this.setUndecorated(true);
//		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//		// 
//		this.setLocationRelativeTo(null);
		setMainPanel();
	}
	private void setMainPanel()
	{
		Frame_Server = new JFrame();
		Frame_Server.setBounds(360, 150, 640, 357);
		Frame_Server.setTitle("虚拟校园服务器端");
		//Frame_Server.setUndecorated(true);
		Frame_Server.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    
//	    this.Panel_Header = new JPanel();
//	    this.Panel_Header.setBackground(new Color(0, 120, 215));
//	    this.Panel_Header.setBounds(0, 0, 640, 41);
//	    Frame_Server.getContentPane().add(this.Panel_Header);
//	    this.Panel_Header.setLayout(null);
	    
	    Panel_Main = new JPanel();
	    Panel_Main.setToolTipText("");
	    Panel_Main.setFont(new Font("微软雅黑", 0, 36));
	    Panel_Main.setForeground(UIManager.getColor("Button.darkShadow"));
	    Panel_Main.setBackground(UIManager.getColor("Button.background"));
	    Panel_Main.setBounds(0, 40, 640, 317);
	    Panel_Main.setLayout(null);
	    this.Frame_Server.getContentPane().add(Panel_Main);
	    
	    this.lblMsgTime = new JLabel("当前时间：");
	    this.lblMsgTime.setForeground(Color.DARK_GRAY);
	    this.lblMsgTime.setFont(new Font("微软雅黑", 0, 16));
	    this.lblMsgTime.setBounds(35, 67, 80, 32);
	    Panel_Main.add(this.lblMsgTime);
	    
	    this.lblShowTime = new JLabel("2019年8月21日 12:00");
	    Timer timer = new Timer();
	    timer.schedule(new RemindTask(), 0L, 1000L);//
	    this.lblShowTime.setFont(new Font("微软雅黑", 0, 16));
	    this.lblShowTime.setBounds(133, 67, 188, 32);
	    Panel_Main.add(this.lblShowTime);
	    
	    this.lblMsgState = new JLabel("服务器状态");
	    this.lblMsgState.setForeground(Color.DARK_GRAY);
	    this.lblMsgState.setFont(new Font("微软雅黑", 0, 16));
	    this.lblMsgState.setBounds(35, 117, 100, 32);
	    Panel_Main.add(this.lblMsgState);
	    
	    this.lblShowState = new JLabel("关闭");
	    this.lblShowState.setFont(new Font("微软雅黑", 0, 16));
	    this.lblShowState.setBounds(133, 117, 188, 32);
	    Panel_Main.add(this.lblShowState);
	    
	    this.lblMsgNum = new JLabel("在线人数");
	    this.lblMsgNum.setForeground(Color.DARK_GRAY);
	    this.lblMsgNum.setFont(new Font("微软雅黑", 0, 16));
	    this.lblMsgNum.setBounds(35, 167, 80, 32);
	    Panel_Main.add(this.lblMsgNum);
	    
	    lblShowNum = new JLabel("");
	    lblShowNum.setFont(new Font("微软雅黑", 0, 16));
	    lblShowNum.setBounds(133, 167, 188, 32);
	    lblShowNum.setText("0 人");
	    Panel_Main.add(lblShowNum);
	    
	    this.btnStart = new JButton("开启服务器");
	    this.btnStart.setFont(new Font("微软雅黑", 1, 14));
	    this.btnStart.setBounds(35, 255, 188, 32);
	    Panel_Main.add(this.btnStart);
	    
	    this.btnClose = new JButton("关闭服务器");
	    this.btnClose.setEnabled(false);
	    this.btnClose.setFont(new Font("微软雅黑", 1, 14));
	    this.btnClose.setBounds(329, 255, 188, 32);
	    Panel_Main.add(this.btnClose);
	    
	    this.Label_MessageOnlineMessage = new JLabel("控制台信息");
	    this.Label_MessageOnlineMessage.setForeground(Color.DARK_GRAY);
	    this.Label_MessageOnlineMessage.setFont(new Font("微软雅黑", 0, 16));
	    this.Label_MessageOnlineMessage.setBounds(337, 47, 124, 32);
	    Panel_Main.add(this.Label_MessageOnlineMessage);
	    
	    this.ScrollPane_ShowOnlineMessage = new JScrollPane();
	    this.ScrollPane_ShowOnlineMessage.setBounds(337, 77, 253, 140);
	    Panel_Main.add(this.ScrollPane_ShowOnlineMessage);
	    
	    TextArea_ShowOnlineMessage = new JTextArea(50, 17);
	    TextArea_ShowOnlineMessage.setForeground(SystemColor.controlDkShadow);
	    TextArea_ShowOnlineMessage.setFont(new Font("微软雅黑", 0, 14));
	    TextArea_ShowOnlineMessage.setBounds(343, 83, 253, 99);
	    TextArea_ShowOnlineMessage.enable(false);
	    TextArea_ShowOnlineMessage.setDisabledTextColor(SystemColor.controlDkShadow);
	    this.ScrollPane_ShowOnlineMessage.setViewportView(TextArea_ShowOnlineMessage);
	    
		btnStart.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{				
				//
				ServerView.this.mainthread = new ServerThread();
		        ServerView.this.btnStart.setEnabled(false);
		        ServerView.this.btnClose.setEnabled(true);
		        ServerView.TextArea_ShowOnlineMessage.append("服务器已开启\n\n");
		        ServerView.this.Panel_Main.repaint();
			}		
		});
		
		btnClose.addActionListener(new ActionListener() 
		{			
			@Override			
			public void actionPerformed(ActionEvent e) 
			{				
				tchUiExit();	
				ServerView.this.mainthread.close();
		        ServerView.this.btnStart.setEnabled(true);
		        ServerView.this.btnClose.setEnabled(false);
		        ServerView.TextArea_ShowOnlineMessage.append("服务器已关闭\n\n");
		        ServerView.count = 0;
		        ServerView.lblShowNum.setText(Integer.toString(ServerView.count));
			}		
		});
	  }

	private class RemindTask
    extends TimerTask
	{
	  private RemindTask() {}
	    
	  public void run()
	  {
	    ServerView.this.lblShowTime.setText(ServerView.this.getTime());
	  }
	}
	
	public String getTime()
	{
		String time = null;
	    Calendar calendar = Calendar.getInstance();
	    Date date = calendar.getTime();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    time = format.format(date);
	    
	    return time;
	  }
	
	public static void setTextNumber(int count)
	{
	    lblShowNum.setText(count + "人");
	    Panel_Main.repaint();
	}
	
	public static void setTextArea(String content)
	{
	    TextArea_ShowOnlineMessage.append(content + "\n");
	    Panel_Main.repaint();
	}
	
	public void tchUiExit()
	{
		this.dispose();
	}


}
