package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;

public class BookStatusView extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable tblStatus;
	private JScrollPane scrollStatus;
	private JButton btnReturn;
	
	private LibraryAdminView myLibraryAdminView = null;
	private SocketHelper sockethelper = new SocketHelper();
	private List<BookStatusInfo> statusList;
	
	////修改后的图书信息
	public int bookId;
	public String name;
	public String borrower;
	public long borrowDate;
	public long returnDate;
	public long actualReturnDate;
	public boolean isOvertime;
	
	/**
	 * Create the frame.
	 */
	public BookStatusView(String bookName, LibraryAdminView tmpLibraryAdminView) {
		
		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 712, 524);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("图书状态查询结果");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblTitle.setBounds(201, 13, 232, 47);
		contentPane.add(lblTitle);
				                
		////////////表格的创建与初始化////////////
		////////
		/* 
         * * 设置JTable的列名 
         */  
        String[] columnNames =  
        { "书号", "书名", "借阅者ID","时间","应还时间","实际归还时间"};  
  
        
        tblStatus = new JTable();
        tblStatus.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		tblStatus.setColumnSelectionAllowed(true);
		tblStatus.setForeground(new Color(0, 51, 102));
		tblStatus.setBounds(105, 250, 250, 140);
		
		model = new DefaultTableModel(columnNames, 0);
		statusList = new ILibraryAdminImpl(this.sockethelper).EnquiryBookStatus(bookName);
		
		for(int i = 0; i < statusList.size(); i++)
		{
			System.out.println(statusList.size());
			BookStatusInfo bookStatusTemp = statusList.get(i);
			System.out.println(bookStatusTemp.getName());
			if(bookStatusTemp.getActualReturnDate()==0)
			{
				Object[] rowData = { String.valueOf(bookStatusTemp.getId()), bookStatusTemp.getName(),  bookStatusTemp.getBorrower(), String.valueOf(bookStatusTemp.getBorrowDate()), 
						String.valueOf(bookStatusTemp.getReturnDate()), "未归还"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { String.valueOf(bookStatusTemp.getId()), bookStatusTemp.getName(),  bookStatusTemp.getBorrower(), String.valueOf(bookStatusTemp.getBorrowDate()), 
						String.valueOf(bookStatusTemp.getReturnDate()), String.valueOf(bookStatusTemp.getActualReturnDate())};
			    model.addRow(rowData);
			}
		}
		tblStatus.setModel(model);
		tblStatus.setRowHeight(25);
		tblStatus.getColumnModel().getColumn(0).setPreferredWidth(60);
		tblStatus.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblStatus.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblStatus.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblStatus.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblStatus.getColumnModel().getColumn(5).setPreferredWidth(100);
		tblStatus.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		scrollStatus = new JScrollPane(tblStatus);  
		scrollStatus.setBounds(94, 73, 484, 303);
		scrollStatus.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //设置水平滚动条需要时可见
	    getContentPane().add(scrollStatus);
		contentPane.add(scrollStatus);
		
		////////////按键的初始化////////////
		JButton btnModify = new JButton("修改");
		btnModify.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnModify.setBounds(475, 389, 84, 30);
		contentPane.add(btnModify);
		
		btnReturn = new JButton("返回");
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(596, 389, 84, 30);
		contentPane.add(btnReturn);
		
		////////////按键监听////////////
		
		//修改
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//选中某条记录，开始修改
				int checkedRow = tblStatus.getSelectedRow();
				if(tblStatus.getCellEditor()!=null)
					tblStatus.getCellEditor().stopCellEditing();//强制JTable结束编辑状态
				if (checkedRow == -1)
				{
				     JOptionPane.showMessageDialog(null, "请选中需要修改的图书！");
				     return;
				 }
				bookId = Integer.parseInt(((String)tblStatus.getValueAt(checkedRow,0)));
				name = (String) tblStatus.getValueAt(checkedRow,1);				
				borrower = (String) tblStatus.getValueAt(checkedRow,2);
				borrowDate = Long.parseLong(((String) tblStatus.getValueAt(checkedRow,3)));
				returnDate = Long.parseLong(((String) tblStatus.getValueAt(checkedRow,4)));
				if(((String) tblStatus.getValueAt(checkedRow,5)).equals("未归还"))
				{
					actualReturnDate = 0;
				}
				else
				{
					actualReturnDate = Long.parseLong(((String) tblStatus.getValueAt(checkedRow,5)));					
				}
				
				if(actualReturnDate>returnDate)
				{
					isOvertime = true;
				}
				else
				{
					isOvertime = false;
				}
				Object[] options ={ "确定", "取消" };  
				int isOk = JOptionPane.showOptionDialog(null, "确定修改吗", "标题",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
				if(isOk==0)
				{
					boolean isModify = new ILibraryAdminImpl(sockethelper).modifyBookStatus(bookId, name, borrower, borrowDate, returnDate, actualReturnDate, isOvertime);
					//取消成功提示
//					if(isModify)
//						JOptionPane.showMessageDialog(null, "修改图书成功！");
					if(!isModify)
						JOptionPane.showMessageDialog(null, "修改图书失败！");
				}
			}
		});

		//返回
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		
		//关闭事件的监听
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
				myLibraryAdminView.refresh();
			 }
			}); 		
	}
	//关闭窗口
	public void close()
	{
		myLibraryAdminView.refresh();
		this.dispose();
	}

}
