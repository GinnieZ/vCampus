package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.URL;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.main.PlayerMain;

import vc.common.CourseInfo;
import vc.common.OnlineClassInfo;
import vc.common.OnlineClassSelectedInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.IOnlineClassImpl;
import vc.sendImpl.ISelectCourseImpl;

/**
 * 在线课堂界面类。
 * @author 09017408
 *
 */

public class OnlineClassView extends JFrame {
	private JPanel myClassPanel = new JPanel();
	private JPanel allClassePanel = new JPanel();
	private JScrollPane allClasseScrollPanel = new JScrollPane();
	private JScrollPane courseScrollPane = new JScrollPane();
	private JScrollPane timetableScrollPane = new JScrollPane();
	private SocketHelper sockethelper = new SocketHelper();
	private GridBagConstraints gbs = new GridBagConstraints();
	private String StudentId;
	private JButton continueButton = new JButton("继续学习");
	private JButton deleteButton = new JButton("删除课程");
	private JButton reviewButton = new JButton("回顾复习");
	private JButton videoButton = new JButton("播放视频");
	private JButton notesButton = new JButton("下载讲义");
	private JButton selectButton = new JButton("选课");
	private JButton returnButton = new JButton("返回");
	private JButton reviewConfirmButton = new JButton("开始复习");
	private JButton preButton = new JButton("上一个");
	private JButton nextButton = new JButton("下一个");
	private JPanel buttonCombination = new JPanel();
	private JTable courseTbl;
	private JFrame learnFrame;
	private JPanel learnPanel;
	private JFrame reviewFrame;
	private JPanel reviewPanel;
	private String myCourseID;
	private int myCurrentPeriod;
	private JTextField periodAvailable = new JTextField(20);
	boolean reviewFlag = false;
	private  List<OnlineClassInfo> list;
	private int numClass;
	private int currentClass;
	private List<JLabel> labelList;
	OnlineClassInfo theClass = null;
	
	public OnlineClassView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.dispose();
		action();
		selectAction();
		addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	        	File scFileDir = new File("cache");
	            File TrxFiles[] = scFileDir.listFiles();
	            for(File curFile:TrxFiles) {
	                curFile.delete();  
	            }
	        }
		});
	}

	public class JTabbedPaneDemo extends JPanel {
		private JTabbedPane jTabbedpane = new JTabbedPane();
		private String[] tabNames = { "正在学习", "课程一览" };

		public JTabbedPaneDemo() {
			layoutComponents();
		}

		private void layoutComponents() {
			int i = 0;

			setMyClassPanel();
			setAllClassePanelPanel();

			jTabbedpane.addTab(tabNames[i++], null, myClassPanel, "My Class");
			jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0); // 快捷键

			jTabbedpane.addTab(tabNames[i++], null, allClassePanel, "All Class");
			jTabbedpane.setMnemonicAt(1, KeyEvent.VK_1);

			setLayout(new GridLayout(1, 1));
			add(jTabbedpane);
		}
	}

	private void setMainPanel() {
		setVisible(true);
		setBounds(10, 20, 850, 500);
		setTitle("在线课堂");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTabbedPaneDemo tabbedPaneDemo = new JTabbedPaneDemo();
		setContentPane(tabbedPaneDemo);
		
		setLearnFrame();
		learnFrame.setVisible(false);
		setReviewFrame();
		reviewFrame.setVisible(false);
	}

	public void setMyClassPanel() {
		myClassPanel.setLayout(new BorderLayout(10,5));
		buttonCombination.add(continueButton);
		buttonCombination.add(deleteButton);
		buttonCombination.add(reviewButton);
		myClassPanel.add(buttonCombination, BorderLayout.NORTH);
		courseScrollPane.setViewportView(getCourseTable());
		courseScrollPane.setBounds(50, 60, 450, 500);
		myClassPanel.add(courseScrollPane);

	}

	private JTable getCourseTable() {
		courseTbl = new JTable();
		courseTbl.setPreferredSize(new Dimension(500, 500));
		String[] columns = { "代码", "课程名称", "授课教师", "总课时", "当前课时", "状态" };
		DefaultTableModel model = new DefaultTableModel(columns, 0)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		courseTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		courseTbl.getTableHeader().setReorderingAllowed(false);
		courseTbl.setModel(model);
		courseTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		List<OnlineClassInfo> courselist = new IOnlineClassImpl(this.sockethelper)
				.EnquirySelectClass(this.StudentId);

		for (int i = 0; i < courselist.size(); i++) {
			OnlineClassInfo temp = (OnlineClassInfo) courselist.get(i);
			int period = 0;
			List<OnlineClassSelectedInfo> courseSelectedlist = new IOnlineClassImpl(this.sockethelper)
					.EnquirySelectStudent(temp);
			for (int j = 0; j < courseSelectedlist.size(); j++) {
				period = courseSelectedlist.get(j).getCurrentPeriod();
				}
			String states;
			if(temp.getPeriod() == period)
			{
				states = "已学完";
			}
			else
			{
				states = "学习中";
			}
			Object[] rowData = { temp.getId(), temp.getName(), temp.getTeacher(),
						temp.getPeriod(), period, states};
				
			model.addRow(rowData);
		}
		return courseTbl;
	}

	public void setAllClassePanelPanel() {
		allClassePanel.removeAll();
		allClassePanel.repaint();
		
		list = new IOnlineClassImpl(this.sockethelper).EnquiryAllClass();
		if(list.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "没有任何课程！");
			return;
		}
		
		OnlineClassInfo tempClass;
		ImageIcon img;
		String path;
		numClass = list.size();
		labelList = new ArrayList<>();
		for(int i = 0; i < numClass; i++)
		{
			tempClass = (OnlineClassInfo) list.get(i);
			path =  "cache/" + (i+1) + ".jpg";
			if(new IOnlineClassImpl(this.sockethelper).getImg(tempClass.getId(), path))
			{
				img = new ImageIcon(path);
				Image image = img.getImage();         
				Image smallImage = image.getScaledInstance(140,200,image.SCALE_FAST);
				ImageIcon smallIcon = new ImageIcon(smallImage);
				JLabel imgLabel = new JLabel();
				imgLabel.setIcon(smallIcon);
				imgLabel.setName(String.valueOf(i + 1));
//				imgLabel.setPreferredSize(new Dimension(100, 80));
				allClassePanel.add(imgLabel);
				labelList.add(imgLabel);
			}
		}
		lableAction();

	}
	
	void resetAllCoursePanel(String theID)
	{
		allClassePanel.removeAll();
		String path =  "cache/" + theID + ".jpg";
		ImageIcon img = new ImageIcon(path);
		Image image = img.getImage();         
		Image smallImage = image.getScaledInstance(280,400,image.SCALE_FAST);
		ImageIcon smallIcon = new ImageIcon(smallImage);
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(smallIcon);
		allClassePanel.add(imgLabel);
		List<OnlineClassInfo> searchClass = new IOnlineClassImpl(this.sockethelper)
				.EnquiryClassById(theID);
		if(!searchClass.isEmpty())
		{
			theClass = searchClass.get(0);
		}
		JLabel theClassName = new JLabel("课程名称 " + theClass.getName());
		JLabel theClassTeacher = new JLabel("授课教师 " + theClass.getTeacher());
		JLabel theClassPeriod = new JLabel(String.valueOf("总课时数 " + theClass.getPeriod()));
		Box box = Box.createVerticalBox();
		box.add(theClassName);
		box.add(theClassTeacher);
		box.add(theClassPeriod);

		JLabel introText = new JLabel("课程简介");
		box.add(introText);
		
		JTextArea intro = new JTextArea(20, 20);
		path = "cache/" + theID + ".txt";
		if(!new IOnlineClassImpl(this.sockethelper).getIntro(theID, path))
		{
			JOptionPane.showMessageDialog(null, "该课程暂无简介！");
		}
		else {
			File selFile = new File(path);
			try {
				BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(selFile),"gbk"));
				String line=null;
				while ((line=reader.readLine())!=null) {
					intro.append(line+"\n");
					}
				} catch (Exception e1) {
				e1.printStackTrace();
				}
			
			intro.setEditable(false);
			intro.setBorder(null); 
			intro.setLineWrap(true);
			box.add(intro);
		}

		allClassePanel.add(box);
		
		this.returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAllClassePanelPanel();
			}
		});
		
		List<OnlineClassInfo> selectClass = new IOnlineClassImpl(this.sockethelper)
				.EnquirySelectClass(StudentId);
		boolean flag = false;
		for (int i = 0; i < selectClass.size(); i++) {
			OnlineClassInfo tempOCI = selectClass.get(i);
			System.out.println("the class id is: " + tempOCI.getId());
			if(tempOCI.getId().equals(theID))
			{
				flag = true;
				break;
			}
		}
		if(flag)
		{
			selectButton.setEnabled(false);
		}
		else
		{
			selectButton.setEnabled(true);
		}
		
		Box bottonBox = Box.createVerticalBox();
		bottonBox.add(selectButton);
		bottonBox.add(returnButton);
		allClassePanel.add(bottonBox);
		allClassePanel.validate();
		allClassePanel.repaint();
	}
	
	private void selectAction()
	{
		this.selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (new IOnlineClassImpl(sockethelper).selectClass(theClass.getId(), StudentId)) {
					JOptionPane.showMessageDialog(null, "已成功添加课程！");
				} else {
					JOptionPane.showMessageDialog(null, "添加课程失败！");
				}
				courseScrollPane.setViewportView(getCourseTable());
				setAllClassePanelPanel();
			}
		});
	}
	
	private void lableAction()
	{
		currentClass = 0;
		while(currentClass < labelList.size())
		{
			labelList.get(currentClass).addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {
					JLabel tempLabel = (JLabel) e.getSource();
					
					
					resetAllCoursePanel(tempLabel.getName());
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			currentClass++;
		}
	}
	private void action() {
		this.continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "未选中任何课程！");
					return;
				}
				int currentRow = courseTbl.getSelectedRow();

				String courseID = (String) courseTbl.getValueAt(currentRow, 0);
				int currentPeriod = (int) courseTbl.getValueAt(currentRow, 4);
				myCourseID = courseID;
				myCurrentPeriod = currentPeriod;
				resetLearnFrame();
				learnFrame.setVisible(true);
				// refresh
				setMyClassPanel();
				setAllClassePanelPanel();
			}
		});

		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (courseTbl.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "未选中任何课程！");
					return;
				}
				int currentRow = courseTbl.getSelectedRow();
				int n = JOptionPane.showConfirmDialog(null, "确认不再学习本课程吗?您的进度将会丢失", 
						"确认删除框", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					String courseID = (String) courseTbl.getValueAt(currentRow, 0);
					if (new IOnlineClassImpl(sockethelper).cancelClass(courseID, StudentId, 0)) {
						JOptionPane.showMessageDialog(null, "已成功删除课程！");
					} else {
						JOptionPane.showMessageDialog(null, "删除课程失败！");
					}
					// refresh
					setMyClassPanel();
					setAllClassePanelPanel();
				}
				else if (n == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});
		
		this.reviewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentRow = courseTbl.getSelectedRow();
				String courseID = (String) courseTbl.getValueAt(currentRow, 0);
				int currentPeriod = (int) courseTbl.getValueAt(currentRow, 4);
				myCourseID = courseID;
				myCurrentPeriod = currentPeriod;
				if((int)courseTbl.getValueAt(currentRow, 4) == 1)
				{
					JOptionPane.showMessageDialog(null, "您还没有已经完成的课时！");
					return;
				}
				reviewFrame.setVisible(true);
			}
		});
		
		
		this.videoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String path = "cache/currentPlay.mp4";
			       
		        if (new IOnlineClassImpl(sockethelper).downloadVideo(myCourseID, myCurrentPeriod, path)) {
					JOptionPane.showMessageDialog(null, "已成功加载视频！");
				} else {
					JOptionPane.showMessageDialog(null, "加载视频失败！");
				}
				
				PlayerMain pm = new PlayerMain();
				pm.setPaht(path);
				pm.main(null);
			}
		});
		
		this.notesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        FileDialog fd = new FileDialog(learnFrame, "另存为", FileDialog.SAVE);

		        fd.setVisible(true);

		        String path = fd.getDirectory() + fd.getFile() + ".txt";
		       
		        if (new IOnlineClassImpl(sockethelper).downloadNotes(myCourseID, myCurrentPeriod, path)) {
					JOptionPane.showMessageDialog(null, "已成功下载讲义！");
				} else {
					JOptionPane.showMessageDialog(null, "下载讲义失败！");
				}
			}
		});
		
		this.reviewConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chosedPeriod = (String) periodAvailable.getText();
				int chosedP = Integer.valueOf(chosedPeriod);
				if(chosedP >= 1 && chosedP < myCurrentPeriod)
				{
					myCurrentPeriod = chosedP;
					reviewFlag = true;
					reviewFrame.setVisible(false);
					resetLearnFrame();
					learnFrame.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "不合法的课时数！");
					return;
				}
			}
		});
	
	}
	void setLearnFrame()
	{
		learnFrame = new JFrame();
		learnPanel = new JPanel();
		learnFrame.setVisible(true);
		learnFrame.setBounds(500, 100, 500, 350);
		learnFrame.setTitle("学习课程");
		learnFrame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
//	        	File scFileDir = new File("cache");
//	            File TrxFiles[] = scFileDir.listFiles();
//	            for(File curFile:TrxFiles) {
//	                curFile.delete();  
//	            }
//		          File deleteCache = new File("cache/currentPlay.mp4");
//		          if(deleteCache.exists())
//		          {
//		        	  deleteCache.delete();
//		        	  System.out.println("缓存已清空");
//		          }
	        	if(!reviewFlag) {
	  	          int result = JOptionPane.showConfirmDialog(learnFrame,
	  		              "要标记本课时为已完成吗 ?", "提示 : ",
	  		              JOptionPane.YES_NO_OPTION);
	  		          if (result == JOptionPane.YES_OPTION)
	  		          {
	  		        	  if (new IOnlineClassImpl(sockethelper).forward(myCourseID, StudentId, myCurrentPeriod)) {
	  							JOptionPane.showMessageDialog(null, "已完成当前课时！");
	  							setMyClassPanel();
	  						} else {
	  							JOptionPane.showMessageDialog(null, "本课程已经全部学完！");
	  						}
	  		        	  learnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  		          }
	  		          else if (result == JOptionPane.NO_OPTION)
	  		        	  learnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        	}
	        	else
	        	{
	        		reviewFlag = false;
	        		learnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 		
	        	}

	        }
	      });
	}
	
	void resetLearnFrame()
	{
		JLabel text = new JLabel("课程简介");
		learnPanel.add(text);
//	       
//        if (new IOnlineClassImpl(sockethelper).getSummary(myCourseID, myCurrentPeriod, path)) {
//			JOptionPane.showMessageDialog(null, "已成功下载讲义！");
//		} else {
//			JOptionPane.showMessageDialog(null, "下载讲义失败！");
//		}

		learnPanel.add(videoButton);
		learnPanel.add(notesButton);
		learnFrame.getContentPane().add(learnPanel);

	}
	
	void setReviewFrame()
	{
		reviewFrame = new JFrame();
		reviewFrame.setVisible(false);
		reviewFrame.setBounds(500, 100, 500, 350);
		reviewFrame.setTitle("回顾课程");
		reviewPanel = new JPanel();
		reviewPanel.add(periodAvailable);
		reviewPanel.add(reviewConfirmButton);
		reviewFrame.getContentPane().add(reviewPanel);
	}

}
