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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.URL;
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
	public JFrame mainFrame;
	private JPanel myClassPanel = new JPanel();
	private JPanel allClassePanel = new JPanel();
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
	private JButton reviewConfirmButton = new JButton("开始复习");
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

	public OnlineClassView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		setMainPanel();
		this.dispose();
		action();
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
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setBounds(10, 20, 850, 500);
		mainFrame.setTitle("在线课堂");
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTabbedPaneDemo tabbedPaneDemo = new JTabbedPaneDemo();
		mainFrame.setContentPane(tabbedPaneDemo);
		
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
				myCourseID = courseID;
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
	        		learnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 		
	        	}
	          File deleteCache = new File("cache/currentPlay.mp4");
	          if(deleteCache.exists())
	          {
	        	  deleteCache.delete();
	        	  System.out.println("视频缓存已清空");
	          }
	        }
	      });
	}
	
	void resetLearnFrame()
	{

		JLabel text = new JLabel("课程简介");
		learnPanel.add(text);
		learnPanel.add(videoButton);
		learnPanel.add(notesButton);
		learnFrame.add(learnPanel);

	}
	
	void setReviewFrame()
	{
		reviewFrame = new JFrame();
		reviewPanel = new JPanel();
		reviewFrame.setVisible(false);
		reviewFrame.setBounds(500, 100, 500, 350);
		reviewFrame.setTitle("回顾课程");
		reviewPanel.add(periodAvailable);
		reviewPanel.add(reviewConfirmButton);
		reviewFrame.add(reviewPanel);
	}

}
