package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Color;

public class LibraryAdminView extends JFrame {

	private String userId;
	private SocketHelper sockethelper = new SocketHelper();
	
	private JPanel contentPane;
	private JTextField txtBookName;
	private JTable tblAllBook;
	private DefaultTableModel model;
	private JScrollPane scrollAllBook;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LibraryAdminView frame = new LibraryAdminView("");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	

	/**
	 * Create the frame.
	 */
	public LibraryAdminView(String id) {
		userId = id;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 876, 674);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel(" 图书馆管理系统");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblTitle.setBounds(312, 13, 233, 48);
		contentPane.add(lblTitle);
		
		JLabel lblQuery = new JLabel("图书查询");
		lblQuery.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblQuery.setBounds(47, 72, 84, 24);
		contentPane.add(lblQuery);
		
		txtBookName = new JTextField();
		txtBookName.setForeground(new Color(0, 51, 102));
		txtBookName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		txtBookName.setText("请输入图书名");
		txtBookName.setBounds(86, 109, 346, 37);
		contentPane.add(txtBookName);
		txtBookName.setColumns(10);
		
		
		
		JLabel lblManage = new JLabel("图书信息管理");
		lblManage.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblManage.setBounds(47, 196, 119, 24);
		contentPane.add(lblManage);
		
		
		////////////表格的创建与初始化////////////
		/////
		/* 
         * * 设置JTable的列名 
         */  
        String[] columnNames =  
        { "书号", "ISBN","书名", "作者","出版社","出版日期","馆藏位置","是否借阅"};  
  
        
        tblAllBook = new JTable();
        tblAllBook.setBackground(new Color(240, 255, 255));
        tblAllBook.setFont(new Font("微软雅黑 Light", Font.PLAIN, 19));
		tblAllBook.setColumnSelectionAllowed(true);
		tblAllBook.setForeground(new Color(0, 51, 102));
		tblAllBook.setBounds(86, 257, 620, 212);
		
		
		model = new DefaultTableModel(columnNames, 0){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // 无条件返回 false，任何单元格都不让编辑。
		        return false;
		    }
		};
		List<BookInfo> bookList = new ILibraryAdminImpl(this.sockethelper).EnquiryAllBook();
		
		for(int i = 0; i < bookList.size(); i++)
		{
			System.out.println(bookList.size());
			BookInfo bookTemp = bookList.get(i);
			System.out.println(bookTemp.getName());
			if(bookTemp.isBorrowed())
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"已借"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"可借"};
			    model.addRow(rowData);
			}
		}
		tblAllBook.setModel(model);
		
		tblAllBook.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblAllBook.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(3).setPreferredWidth(200);
		tblAllBook.getColumnModel().getColumn(4).setPreferredWidth(280);
		tblAllBook.getColumnModel().getColumn(5).setPreferredWidth(80);
		tblAllBook.getColumnModel().getColumn(6).setPreferredWidth(280);
		tblAllBook.getColumnModel().getColumn(7).setPreferredWidth(80);
		tblAllBook.setRowHeight(25);
		tblAllBook.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
		
		scrollAllBook = new JScrollPane(tblAllBook);  
		scrollAllBook.setBounds(80, 250, 635, 295);
		scrollAllBook.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //设置水平滚动条需要时可见
	    getContentPane().add(scrollAllBook);
		contentPane.add(scrollAllBook);

		////////////按键的初始化////////////
		JButton btnInfoQuery = new JButton("信息查询");
		btnInfoQuery.setBackground(new Color(173, 216, 230));
		btnInfoQuery.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnInfoQuery.setBounds(458, 109, 119, 37);
		contentPane.add(btnInfoQuery);
		
		JButton btnStatusQuery = new JButton("状态查询");
		btnStatusQuery.setBackground(new Color(173, 216, 230));
		btnStatusQuery.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnStatusQuery.setBounds(596, 109, 119, 37);
		contentPane.add(btnStatusQuery);
		
		JButton btnRefesh = new JButton("刷新");
		btnRefesh.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnRefesh.setBackground(SystemColor.inactiveCaption);
		btnRefesh.setBounds(559, 558, 84, 30);
		contentPane.add(btnRefesh);
		
		JButton btnReturn = new JButton("返回");
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(721, 558, 84, 30);
		contentPane.add(btnReturn);
		
		JButton btnAdd = new JButton("增加");
		btnAdd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnAdd.setBounds(729, 318, 76, 24);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("删减");
		btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnDelete.setBounds(729, 377, 76, 24);
		contentPane.add(btnDelete);
		
		JButton btnModify = new JButton("修改");
		btnModify.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnModify.setBounds(729, 439, 76, 24);
		contentPane.add(btnModify);
		
		////////////按键监听////////////
		
		//信息查询
		btnInfoQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开图书信息界面
				openBookInfoView();
			}
		});

		//状态查询
		btnStatusQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开图书状态信息界面
				openBookStatusView();
			}
		});

		//增加图书
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开增加图书界面
				openAddBookView();
			}
		});

		//删减图书
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblAllBook.getSelectedRow() == -1)
				{
				     JOptionPane.showMessageDialog(null, "请选中需要删除的图书！");
				     return;
				 }
				Object[] options ={ "确定", "取消" };  
				int isOk = JOptionPane.showOptionDialog(null, "确定删除这本书吗", "标题",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
				if(isOk==0)
				{
					if(tblAllBook.getValueAt(tblAllBook.getSelectedRow(), 7).equals("已借"))
					{
						JOptionPane.showMessageDialog(null, "该图书处于借阅中，不能删除！");
					}
					else
					{
						int bookId = (int)tblAllBook.getValueAt(tblAllBook.getSelectedRow(), 0);
						boolean isDelete = new ILibraryAdminImpl(sockethelper).deleteBook(bookId);
						//取消成功删除弹窗
//						if(isDelete)
//							JOptionPane.showMessageDialog(null, "删除图书成功！");						
						
						if(!isDelete)
							JOptionPane.showMessageDialog(null, "删除图书失败！");
						refresh();
					}

				}
				
			}
		});
		
		//修改图书
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblAllBook.getSelectedRow() == -1)
				{
				     JOptionPane.showMessageDialog(null, "请选中需要修改的图书！");
				     return;
				 }			
				//打开图书修改界面
				openModifyBookView();
			}
		});
		
		//刷新
		btnRefesh.addActionListener(new ActionListener() {
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
		System.out.println("kaishiye："+bookName);
		BookInfoAdminView	myBookInfoView = new BookInfoAdminView(userId, bookName,this);
		myBookInfoView.setVisible(true);
	}
	
	//打开图书状态界面，将搜索信息（图书名）传递与服务器端，接受服务器端回传的信息，进行显示
	public void openBookStatusView()
	{
		String bookName = txtBookName.getText();
		BookStatusView	myBookStatusView = new BookStatusView(bookName,this);
		myBookStatusView.setVisible(true);
	}
	
	//打开增加图书界面
	public void openAddBookView()
	{
		BookAddView	myBookAddView = new BookAddView(this);
		myBookAddView.setVisible(true);
	}
	
	//打开图书修改界面
	public void openModifyBookView()
	{
		int bookId = (int)tblAllBook.getValueAt(tblAllBook.getSelectedRow(), 0);
		BookModifyView	myBookModifyView = new BookModifyView(bookId, this);
		myBookModifyView.setVisible(true);
		myBookModifyView.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	//关闭窗口
	public void close()
	{
		this.dispose();
	}
	
	//刷新表格
	public void refresh()
	{
		//清空表格内容
		while(model.getRowCount()>0)
		{
			model.removeRow(model.getRowCount()-1);
		}
		List<BookInfo> bookList = new ILibraryAdminImpl(this.sockethelper).EnquiryAllBook();		
		for(int i = 0; i < bookList.size(); i++)
		{
			System.out.println(bookList.size());
			BookInfo bookTemp = bookList.get(i);
			System.out.println(bookTemp.getName());
			if(bookTemp.isBorrowed())
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"已借"};
				model.addRow(rowData);
			}
			else
			{
				Object[] rowData = { bookTemp.getId(), bookTemp.getIsbn(), bookTemp.getName(),  bookTemp.getAuthor(), 
						bookTemp.getPub(),  bookTemp.getPubDate(), bookTemp.getPos(),"可借"};
			    model.addRow(rowData);
			}
		}
		tblAllBook.setModel(model);
	}
}
