package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

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

import vc.common.BookInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class BookModifyView extends JFrame {

	private int bookId;
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryAdminView myLibraryAdminView = null;
	
	////修改后的图书信息
	public String name;
	public String author;
	public int id;
	public String isbn;
	public String pub;
	public long pubDate;
	public String pos;	
	public boolean isBorrowed;

	private JPanel contentPane;
	private JTable tblBookInfo;
	/**
	 * Create the frame.
	 */
	public BookModifyView(int tmpBookId, LibraryAdminView tmpLibraryAdminView) {
		this.bookId = tmpBookId;
		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("图书信息修改");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblTitle.setBounds(209, 13, 194, 47);
		contentPane.add(lblTitle);
		
		////////////表格的创建与初始化////////////
        /* 
         * * 设置JTable的列默认的宽度和高度 
         */  
        tblBookInfo = new JTable(8,2);
        tblBookInfo.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		tblBookInfo.setColumnSelectionAllowed(true);
		tblBookInfo.setForeground(Color.DARK_GRAY);
		tblBookInfo.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
		tblBookInfo.setGridColor(Color.GRAY);                     // 网格颜色
		tblBookInfo.setBounds(105, 250, 250, 140);
		tblBookInfo.setRowHeight(30);

		// 第一列列宽设置为80，第二列列宽设置为320
		tblBookInfo.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblBookInfo.getColumnModel().getColumn(1).setPreferredWidth(320);
		tblBookInfo.setValueAt("书名", 0, 0);
		tblBookInfo.setValueAt("书号", 1, 0);
		tblBookInfo.setValueAt("ISBN", 2, 0);
		tblBookInfo.setValueAt("作者", 3, 0);
		tblBookInfo.setValueAt("出版社", 4, 0);
		tblBookInfo.setValueAt("出版日期", 5, 0);
		tblBookInfo.setValueAt("馆藏", 6, 0);
		tblBookInfo.setValueAt("是否借阅", 7, 0);
		//隐藏表头：为表头设置一个 CellRenderer, 这个 CellRenderer 的预选高度为 0
		tblBookInfo.getTableHeader().setVisible(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setPreferredSize(new Dimension(0, 0));
		tblBookInfo.getTableHeader().setDefaultRenderer(renderer);
		tblBookInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//接受信息进行显示
		List<BookInfo> booklist = new ILibraryAdminImpl(this.sockethelper).EnquiryABookById(bookId);
		System.out.println("booklist.size:"+booklist.size());
		if(booklist.size()==0)
		{
			JOptionPane.showMessageDialog(null, "没有找到这本书哟~");
			this.dispose();
		} 
		//将非String的数据类型转化为String进行显示
		tblBookInfo.setValueAt(booklist.get(0).getName(), 0, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(0).getId()) , 1, 1);
		tblBookInfo.setValueAt(booklist.get(0).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(0).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPub(), 4, 1);
		tblBookInfo.setValueAt( String.valueOf(booklist.get(0).getPubDate()), 5, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPos(), 6, 1);
		if(booklist.get(0).isBorrowed())
		{
			tblBookInfo.setValueAt("已借", 7, 1);
		}
		else
		{
			tblBookInfo.setValueAt("可借", 7, 1);
		}	
		
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);  
		scrollBookInfo.setBounds(106, 73, 399, 284);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //设置水平滚动条需要时可见
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);

		////////////按键的初始化////////////
		JButton btnModify = new JButton("修改");
		btnModify.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnModify.setBounds(410, 386, 84, 30);
		contentPane.add(btnModify);
		
		JButton btnReturn = new JButton("返回");
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturn.setBackground(SystemColor.inactiveCaption);
		btnReturn.setBounds(508, 386, 84, 30);
		contentPane.add(btnReturn);
		
		////////////按键监听////////////
		
		//修改
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tblBookInfo.getCellEditor()!=null)
					tblBookInfo.getCellEditor().stopCellEditing();//强制JTable结束编辑状态
				name = (String) tblBookInfo.getValueAt(0,1);
				id = Integer.parseInt((String) (tblBookInfo.getValueAt(1,1)));
				isbn = (String) tblBookInfo.getValueAt(2,1);
				author = (String) tblBookInfo.getValueAt(3,1);
				pub = (String) tblBookInfo.getValueAt(4,1);
				pubDate = Long.parseLong((String) (tblBookInfo.getValueAt(5,1)));
				pos = (String) tblBookInfo.getValueAt(6,1);
				if(((String) tblBookInfo.getValueAt(7,1)).equals("可借"))
					isBorrowed = false;
				else
					isBorrowed = true;
				System.out.println(pubDate);
				Object[] options ={ "确定", "取消" };  
				int isOk = JOptionPane.showOptionDialog(null, "确定修改吗", "标题",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
				if(isOk==0)
				{
					boolean isModifi = new ILibraryAdminImpl(sockethelper).modifyBook(id, isbn, name, author, pub, pubDate, pos, isBorrowed);
					if(isModifi)
						JOptionPane.showMessageDialog(null, "修改图书成功！");
					else
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
