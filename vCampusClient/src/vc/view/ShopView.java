package vc.view;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;

import vc.helper.SocketHelper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ShopView extends JFrame {

	public JFrame mainFrame;
	private JPanel contentPane;
	private JPanel cards;
	private JPanel card1;
	private JPanel card2;
	private JPanel card3;
	private String StudentId;
	private SocketHelper sockethelper = new SocketHelper();
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JButton buttonMyAccount;
	private JButton shop;
	private JButton buttonAccountback;
	private JButton Cart;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShopView(String id) {
		StudentId = id;
		sockethelper.getConnection();
		init();
		this.setVisible(false);
		operation();
		
	}

	public void init() {
		mainFrame=new JFrame("天天商店");    //创建Frame窗口
	 
		

		cards = new JPanel (new CardLayout());    
		JPanel card1 = new JPanel();
		card1.setLayout(null);
		buttonMyAccount = new JButton ("我的账户");
		buttonMyAccount.setBounds(289, 0, 93, 27);
		card1.add(buttonMyAccount);
		JPanel card2 = new JPanel();
		card2.setLayout(null);
		JButton buttonAccountback = new JButton("返回");
		buttonAccountback.setBounds(0, 0, 63, 27);
		card2.add(buttonAccountback);
		JPanel card3 = new JPanel();
		card3.setLayout(null);
		
		JButton button = new JButton("结算");
		button.setBounds(319, 126, 63, 27);
		card3.add(button);
		cards.add(card1);
		
		JButton button_3 = new JButton("添加进购物车");
		button_3.setBounds(250, 113, 132, 27);
		card1.add(button_3);
		
		table = new JTable();
		table.setBounds(34, 40, 202, 100);
		card1.add(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(14, 1, 74, 26);
		comboBox.addItem("全部");
		
		comboBox.addItem("食物");
		comboBox.addItem("饮料");
		comboBox.addItem("衣服");
		comboBox.addItem("日用品");
		comboBox.addItem("其他");
		card1.add(comboBox);
		
		
	
		
		JButton Cart = new JButton("购物车");
		Cart.setBounds(289, 66, 93, 27);
		card1.add(Cart);
		cards.add(card2);
		
		JLabel labelBalance = new JLabel("余额：");
		labelBalance.setBounds(118, 13, 72, 18);
		card2.add(labelBalance);
		
		JLabel labelMyOrder = new JLabel("我的订单：");
		labelMyOrder.setBounds(10, 40, 90, 18);
		card2.add(labelMyOrder);
		
		JTable tableOrder = new JTable();
		tableOrder.setBounds(62, 60, 280, 80);
		card2.add(tableOrder);
		cards.add(card3);
		
		JTable tableCart = new JTable();
		tableCart.setBounds(14, 13, 251, 127);
		card3.add(tableCart);
		
		JButton deletegoods = new JButton("删除");
		deletegoods.setBounds(329, 58, 63, 27);
		card3.add(deletegoods);
		
		JButton shop = new JButton("商店");
		shop.setBounds(329, 0, 63, 27);
		card3.add(shop);
		
		CardLayout cl=(CardLayout)(cards.getLayout());
		cl.show(cards,"card1");    //调用show()方法显示面板2
		mainFrame.getContentPane().add(cards);
		mainFrame.setBounds(300,200,400,200);
		mainFrame.setVisible(true);

		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	public void operation() {
		buttonMyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					card2.setVisible(true);
					dispose();
				}
			});
		shop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					card1.setVisible(true);
					dispose();
				}
			});
		buttonAccountback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					card1.setVisible(true);
					dispose();
				}
			});
		Cart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					card3.setVisible(true);
					dispose();
				}
			});
		
		
		
		}
}
