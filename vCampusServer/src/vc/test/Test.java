package vc.test;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import vc.view.LoginView;
import vc.view.ServerView;

public class Test {

	public static void main(String[] args) 
	{
//		InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));
		try { 
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF(); 
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;  
			UIManager.put("RootPane.setupButtonVisible",false);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			

			} catch (Exception e) {                 
				e.printStackTrace();            
				}               

		try {   
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 
		catch (ClassNotFoundException e) { 
			e.printStackTrace();} 
		catch (InstantiationException e) { 
				e.printStackTrace();} 
		catch (IllegalAccessException e) { 
					e.printStackTrace();} 
		catch (UnsupportedLookAndFeelException e) { 
						e.printStackTrace();}

		
		LoginView LoginWin = new LoginView();
	}

}
