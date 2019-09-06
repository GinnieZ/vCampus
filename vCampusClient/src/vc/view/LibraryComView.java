package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryComImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

/*
 * 打开此页面时即根据用户id查询借阅信息，进行显示
 */
public class LibraryComView extends JFrame {

	private JPanel contentPane;
	private JTextField txtBookName;
	private JTable tblBorrow;
	private JScrollPane scrollBorrow;
	private DefaultTableModel model;
	
	private String userId;
	private SocketHelper sockethelper = new SocketHelper();

	/**
	 * Create the frame.
	 */
	public LibraryComView(String id) {
		userId = id;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 666);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel(" 图书馆");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblTitle.setBounds(176, 24, 233, 48);
		contentPane.add(lblTitle);
		
		JLabel lblQuery = new JLabel("图书查询");
		lblQuery.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		lblQuery.setBounds(67, 83, 84, 24);
		contentPane.add(lblQuery);
		
		txtBookName = new JTextField();
		txtBookName.setText("请输入图书名");
		txtBookName.setForeground(SystemColor.activeCaptionBorder);
		txtBookName.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		txtBookName.setColumns(10);
		txtBookName.setBounds(106, 137, 412, 26);
		contentPane.add(txtBookName);
		
		
		JLabel lblBorrow = new JLabel("我的借阅");
		lblBorrow.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		lblBorrow.setBounds(67, 193, 84, 24);
		contentPane.add(lblBorrow);
		
		
		
			
		////////////表格的创建与初始化////////////    
		/* 
         * * 设置JTable的列名 
         */  
        String[] columnNames =  
        { "书号", "书名", "时间","应还时间","实际归还时间"};  
  
        
        tblBorrow = new JTable();
		tblBorrow.setColumnSelectionAllowed(true);
		tblBorrow.setForeground(SystemColor.activeCaption);
		tblBorrow.setBounds(105, 250, 250, 140);
		
		model = new DefaultTableModel(columnNames, 0){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // 无条件返回 false，任何单元格都不让编辑。
		        return false;
		    }
		};
		List<BookStatusInfo> borrowlist = new ILibraryComImpl(userId, this.sockethelper).EnquiryRecord(this.userId);
		
		for(int i = 0; i < borrowlist.size(); i++)
		{
			System.out.println(borrowlist.size());
			BookStatusInfo bookStatusTemp = borrowlist.get(i);
			System.out.println(bookStatusTemp.getName());
			if(bookStatusTemp.getActualReturnDate()==0)
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
						bookStatusTemp.getReturnDate(), "未归还"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
					bookStatusTemp.getReturnDate(), bookStatusTemp.getActualReturnDate()};
			    model.addRow(rowData);
			}
		}
		tblBorrow.setModel(model);
		scrollBorrow = new JScrollPane(tblBorrow);  
		scrollBorrow.setBounds(106, 245, 412, 282);
	    getContentPane().add(scrollBorrow);
		contentPane.add(scrollBorrow);
		
		////////////按键的初始化////////////	
		JButton btnQuery = new JButton("搜索");
		btnQuery.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnQuery.setBounds(548, 138, 119, 24);
		contentPane.add(btnQuery);
		
		JButton btnReturnBook = new JButton("归还");
		btnReturnBook.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturnBook.setBounds(548, 315, 119, 24);
		contentPane.add(btnReturnBook);
	
		JButton btnRefresh = new JButton("刷新");
		btnRefresh.setBackground(SystemColor.inactiveCaption);
		btnRefresh.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnRefresh.setBounds(421, 561, 84, 30);
		contentPane.add(btnRefresh);
		
		JButton btnReturn = new JButton("返回");
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturn.setBounds(583, 561, 84, 30);
		contentPane.add(btnReturn);
		
		////////////按键监听////////////
		
		//搜索
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBookInfoView();				
			}			
		});

		//归还
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//点击归还按钮，将归还信息（书号与借阅者学号）传与服务器端，服务器端进行归还判断，删除此条借阅信息，客户端删除借阅信息，刷新页面
				
				if (tblBorrow.getSelectedRow() == -1)
				{
				     JOptionPane.showMessageDialog(null, "请选中需要归还的图书！");
				     return;
				 }
				if (!tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 4).equals("未归还"))
				{
				     JOptionPane.showMessageDialog(null, "该图书已归还！");
				     return;
				}
				
				int bookId = (int)tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 0);				
				String bookName = (String) tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 1);
				long borrowDate = (long)tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 2);
				long returnDate = (long)tblBorrow.getValueAt(tblBorrow.getSelectedRow(), 3);
				Date actualReturnDate=new Date();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		        String actualReturnDateStr=dateFormat.format(actualReturnDate);
				long actualReturnDateLong =  Long.parseLong(actualReturnDateStr);
				boolean flag = new ILibraryComImpl(userId, sockethelper).ReturnBook(bookId,bookName,borrowDate,returnDate,actualReturnDateLong);
				if(flag)
				{
					JOptionPane.showMessageDialog(null, "归还成功！");					
					tblBorrow.setValueAt(actualReturnDateStr,tblBorrow.getSelectedRow(),4);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "归还失败！");
				}
			}
		});

		//刷新
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		//返回	
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});


        
	}
	
	//将搜索信息（图书名）传递与图书界面，打开图书信息界面，由图书界面将信息传与服务器端，接受服务器端回传的信息，进行显示
	public void openBookInfoView()
	{
		String bookName = txtBookName.getText();
		BookInfoComView	myBookInfoView = new BookInfoComView(userId, bookName,this);
		myBookInfoView.setVisible(true);
		myBookInfoView.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//refresh();
	}
	
	//刷新表格
	public void refresh()
	{
		List<BookStatusInfo> borrowlist = new ILibraryComImpl(userId, this.sockethelper).EnquiryRecord(this.userId);
		//清空表格内容
		while(model.getRowCount()>0)
		{
			model.removeRow(model.getRowCount()-1);
		}
		for(int i = 0; i < borrowlist.size(); i++)
		{
			BookStatusInfo bookStatusTemp = borrowlist.get(i);
			if(bookStatusTemp.getActualReturnDate()==0)
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
						bookStatusTemp.getReturnDate(), "未归还"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookStatusTemp.getId(), bookStatusTemp.getName(),  bookStatusTemp.getBorrowDate(), 
					bookStatusTemp.getReturnDate(), bookStatusTemp.getActualReturnDate()};
			    model.addRow(rowData);
			}
		}
		tblBorrow.setModel(model);
	}
	
	//关闭窗口
	public void close()
	{
		this.dispose();
	}

}
