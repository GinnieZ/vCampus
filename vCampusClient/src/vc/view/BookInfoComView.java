package vc.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryComImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class BookInfoComView extends JFrame {

	
	private String userId;
	private String bookName;
	private int bookId;
	private int page = 0;
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryComView myLibraryComView = null;
	private List<BookInfo> booklist = null;


	private JPanel contentPane;
	private JTable tblBookInfo;
	private JLabel lblCurrentP;
	
	/**
	 * Create the frame.
	 */
	public BookInfoComView( String tmpUserId, String tmpBookName, LibraryComView tmpLibraryComView) {
		this.userId = tmpUserId;
		this.bookName = tmpBookName;
		this.myLibraryComView = tmpLibraryComView;
		sockethelper.getConnection();
		
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 840, 675);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("图书查询结果");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblTitle.setBounds(180, 13, 194, 47);
		contentPane.add(lblTitle);
		
		////////////表格的创建与初始化////////////       
        tblBookInfo = new JTable(8,2);
        tblBookInfo.setEnabled(false);//设置表格不可编辑
      
        tblBookInfo.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
		tblBookInfo.setColumnSelectionAllowed(true);
		tblBookInfo.setForeground(Color.DARK_GRAY);
		tblBookInfo.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
		tblBookInfo.setGridColor(Color.GRAY);                     // 网格颜色
		tblBookInfo.setBounds(105, 250, 250, 140);
		tblBookInfo.setRowHeight(30);

        // 第一列列宽设置为80，第二列列宽设置为320
		tblBookInfo.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblBookInfo.getColumnModel().getColumn(1).setPreferredWidth(400);
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
		booklist = new ILibraryComImpl(userId, this.sockethelper).EnquiryAllBook(bookName);
		System.out.println("booklist.size:"+booklist.size());
		if(booklist.size()==0)
		{
			JOptionPane.showMessageDialog(null, "没有找到这本书哟~");
			this.dispose();
			return;
		}
		System.out.println(booklist.size());
		lblCurrentP = new JLabel("目前 0/");
		lblCurrentP.setForeground(Color.DARK_GRAY);
		lblCurrentP.setFont(new Font("微软雅黑 Light", Font.PLAIN, 15));
		lblCurrentP.setBounds(412, 502, 84, 30);
		contentPane.add(lblCurrentP);
		
		String str = "目前  "+1+"/"+(booklist.size());	
		lblCurrentP.setText(str);
		
		//用第一条查询结果进行初始化
		tblBookInfo.setValueAt(booklist.get(0).getName(), 0, 1);
		tblBookInfo.setValueAt(booklist.get(0).getId(), 1, 1);
		tblBookInfo.setValueAt(booklist.get(0).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(0).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPub(), 4, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPubDate(), 5, 1);
		tblBookInfo.setValueAt(booklist.get(0).getPos(), 6, 1);
		if(booklist.get(0).isBorrowed())
			tblBookInfo.setValueAt("已借", 7, 1);
		else
			tblBookInfo.setValueAt("可借", 7, 1);		
										
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);
		scrollBookInfo.setBounds(102, 106, 413, 335);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //设置水平滚动条总是可见
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);

		////////////按键的初始化////////////
		JButton btnBorrow = new JButton("借阅");
		btnBorrow.setBackground(new Color(255, 182, 193));
		btnBorrow.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnBorrow.setBounds(377, 569, 84, 30);
		contentPane.add(btnBorrow);
		
		JButton btnReturn = new JButton("返回");
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturn.setBackground(new Color(95, 158, 160));
		btnReturn.setBounds(499, 569, 84, 30);
		contentPane.add(btnReturn);
		
		JButton btnFirstP = new JButton("首页");
		btnFirstP.setBackground(new Color(176, 196, 222));
		btnFirstP.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnFirstP.setBounds(118, 473, 84, 27);
		contentPane.add(btnFirstP);
		
		JButton btnPreviousP = new JButton("上一页");
		btnPreviousP.setBackground(new Color(176, 196, 222));
		btnPreviousP.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnPreviousP.setBounds(216, 473, 84, 27);
		contentPane.add(btnPreviousP);
		
		JButton btnNextP = new JButton("下一页");
		btnNextP.setBackground(new Color(176, 196, 222));
		btnNextP.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNextP.setBounds(314, 473, 84, 27);
		contentPane.add(btnNextP);
		
		JButton btnLastP = new JButton("尾页");
		btnLastP.setBackground(new Color(176, 196, 222));
		btnLastP.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnLastP.setBounds(412, 473, 84, 27);
		contentPane.add(btnLastP);
		
		////////////图片显示////////////		
		JLabel lblBookImg = new JLabel("New label");
		lblBookImg.setBounds(538, 116, 245, 325);

		ImageIcon bookImg = new ImageIcon("src\\vc\\images\\"+bookName+".jpg");	
		bookImg.setImage(bookImg.getImage().getScaledInstance(245, 325, Image.SCALE_DEFAULT ));		
		lblBookImg.setIcon(bookImg);
		contentPane.add(lblBookImg);
		////////////按键监听////////////	
		
		//借阅
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//判断是否可借，将用户id与书号传与服务器端，服务器端进行借阅，修改相关信息，客户端刷新结果
				bookId = (int)tblBookInfo.getValueAt( 1, 1);
				Date borrowDate=new Date();
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		        String borrowDateStr=dateFormat.format(borrowDate);
				long borrowDateLong =  Long.parseLong(borrowDateStr);
				//计算归还日期
				Calendar rightNow = Calendar.getInstance();		
				rightNow.add(Calendar.DAY_OF_YEAR,30);//日期加30天
				Date returnDate=rightNow.getTime();
				String returnDateStr=dateFormat.format(returnDate);
				long returnDateLong =  Long.parseLong(returnDateStr);
				System.out.println("计算的归还日期："+returnDateStr);
				boolean isBorrowed = new ILibraryComImpl(userId, sockethelper).BorrowBook(0,bookName,borrowDateLong,returnDateLong);
				if(!isBorrowed)
					JOptionPane.showMessageDialog(null, "这本书已经被借了哦~");
				else
				{
					//取消借阅成功弹窗
					//JOptionPane.showMessageDialog(null, "借阅成功~");
					//修改借阅信息
					tblBookInfo.setValueAt("已借",7,1);
				}
				
			}
		});
		
		//返回
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		//首页
		btnFirstP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page = 0;
				lblCurrentP.setText("目前  "+(page+1)+"/"+(booklist.size()));
				refresh();
			}
		});
		
		//上一页
		btnPreviousP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page==0)
					JOptionPane.showMessageDialog(null, "已经是第一页了哟~");
				else
				{
					page--;
					lblCurrentP.setText("目前  "+(page+1)+"/"+(booklist.size()));
					refresh();
				}
			}
		});

		//下一页
		btnNextP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(page==booklist.size()-1)
					JOptionPane.showMessageDialog(null, "已经是最后一页了哟~");
				else
				{
					page++;
					lblCurrentP.setText("目前  "+(page+1)+"/"+(booklist.size()));
					refresh();
				}
			}
		});

		//尾页		
		btnLastP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				page = booklist.size()-1;
				lblCurrentP.setText("目前  "+(page+1)+"/"+(booklist.size()));
				refresh();
			}
		});

		//关闭事件的监听
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
				myLibraryComView.refresh();
			 }
			}); 
	}
	//刷新表格
	public void refresh()
	{
		tblBookInfo.setValueAt(booklist.get(page).getName(), 0, 1);
		tblBookInfo.setValueAt(booklist.get(page).getId(), 1, 1);
		tblBookInfo.setValueAt(booklist.get(page).getIsbn(), 2, 1);
		tblBookInfo.setValueAt(booklist.get(page).getAuthor(), 3, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPub(), 4, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPubDate(), 5, 1);
		tblBookInfo.setValueAt(booklist.get(page).getPos(), 6, 1);
		if(booklist.get(page).isBorrowed())
			tblBookInfo.setValueAt("已借", 7, 1);
		else
			tblBookInfo.setValueAt("可借", 7, 1);
	}
	//关闭窗口
	public void close()
	{
		myLibraryComView.refresh();
		this.dispose();
	}
}
