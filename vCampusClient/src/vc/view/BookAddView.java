package vc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import vc.common.BookInfo;
import vc.helper.SocketHelper;
import vc.sendImpl.ILibraryAdminImpl;
import vc.sendImpl.ILibraryComImpl;
import java.awt.SystemColor;

public class BookAddView extends JFrame {

	private JPanel contentPane;
	private SocketHelper sockethelper = new SocketHelper();
	private LibraryAdminView myLibraryAdminView = new LibraryAdminView("");
	private  JTable tblBookInfo;

	////新增的图书信息
	protected String name;
	protected String isbn;
	protected String author;
	protected String pub;
	protected long pubDate;
	protected String pos;
	protected boolean isBorrowed;
	protected int bookNum;
	
	/**
	 * Create the frame.
	 */
	public BookAddView(LibraryAdminView tmpLibraryAdminView) {

		this.myLibraryAdminView = tmpLibraryAdminView;
		sockethelper.getConnection();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 625, 516);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("增加图书");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblTitle.setBounds(196, 13, 194, 47);
		contentPane.add(lblTitle);
		
		////////////表格的创建与初始化////////////       
        tblBookInfo = new JTable(7,2);
        
        tblBookInfo.setFont(new Font("微软雅黑 Light", Font.PLAIN, 18));
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
		tblBookInfo.setValueAt("ISBN", 1, 0);
		tblBookInfo.setValueAt("作者", 2, 0);
		tblBookInfo.setValueAt("出版社", 3, 0);
		tblBookInfo.setValueAt("出版日期", 4, 0);
		tblBookInfo.setValueAt("馆藏", 5, 0);
		tblBookInfo.setValueAt("书籍数量", 6, 0);
		tblBookInfo.setValueAt("", 0, 1);
		tblBookInfo.setValueAt("", 1, 1);
		tblBookInfo.setValueAt("", 2, 1);
		tblBookInfo.setValueAt("", 3, 1);
		tblBookInfo.setValueAt("", 4, 1);
		tblBookInfo.setValueAt("", 5, 1);
		tblBookInfo.setValueAt("", 6, 1);
		//隐藏表头：为表头设置一个 CellRenderer, 这个 CellRenderer 的预选高度为 0
		tblBookInfo.getTableHeader().setVisible(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setPreferredSize(new Dimension(0, 0));
		tblBookInfo.getTableHeader().setDefaultRenderer(renderer);
		tblBookInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);       
		
		
		JScrollPane scrollBookInfo = new JScrollPane(tblBookInfo);  
		scrollBookInfo.setBounds(113, 73, 368, 275);				
		
		scrollBookInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  //设置水平滚动条需要时可见
	    getContentPane().add(scrollBookInfo);
		contentPane.add(scrollBookInfo);
		
		////////////按键的初始化////////////		
		JButton btnSubmit = new JButton("提交");		
		btnSubmit.setBackground(new Color(255, 182, 193));
		btnSubmit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnSubmit.setBounds(385, 381, 84, 30);
		contentPane.add(btnSubmit);
		
		JButton btnReturn = new JButton("返回");		
		btnReturn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnReturn.setBackground(new Color(95, 158, 160));
		btnReturn.setBounds(496, 381, 84, 30);
		contentPane.add(btnReturn);
		
		////////////按键监听////////////
		
		//返回
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		//提交
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tblBookInfo.getCellEditor()!=null)
					tblBookInfo.getCellEditor().stopCellEditing();//强制JTable结束编辑状态
				
				if((tblBookInfo.getValueAt(0,1).equals(""))||(tblBookInfo.getValueAt(1,1).equals(""))||(tblBookInfo.getValueAt(2,1).equals(""))
						||(tblBookInfo.getValueAt(3,1).equals(""))||(tblBookInfo.getValueAt(4,1).equals(""))||(tblBookInfo.getValueAt(5,1).equals(""))
						||(tblBookInfo.getValueAt(6,1).equals("")))
				{
					JOptionPane.showMessageDialog(null, "有空白信息，请将信息填写完整！");
				}
				else
				{
					name = (String) tblBookInfo.getValueAt(0,1);
					isbn = (String) tblBookInfo.getValueAt(1,1);
					author = (String) tblBookInfo.getValueAt(2,1);
					pub = (String) tblBookInfo.getValueAt(3,1);
					pubDate = Long.parseLong(((String) tblBookInfo.getValueAt(4,1)));
					pos = (String) tblBookInfo.getValueAt(5,1);
					bookNum = Integer.parseInt(((String)tblBookInfo.getValueAt(6,1)));
					if((bookNum!=0))
					{
						Object[] options ={ "确定", "取消" };  
						int isOk = JOptionPane.showOptionDialog(null, "确定提交吗", "标题",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); 
						if(isOk==0)
						{
							boolean isAdd = new ILibraryAdminImpl(sockethelper).addBook(name, isbn, author, pub, pubDate, pos, bookNum);
							//取消成功提示
//							if(isAdd)
//								JOptionPane.showMessageDialog(null, "新增图书成功！");
							if(isAdd)
								JOptionPane.showMessageDialog(null, "新增图书失败！");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "图书数量不能为0！");
					}
				}
				
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
