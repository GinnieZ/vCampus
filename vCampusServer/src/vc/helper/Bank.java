package vc.helper;
/** 银行
* @author 09017406
* 库乃·阿炸题
*/

import vc.common.BankInfo;
import vc.db.BankModel;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Bank {
	private BankModel model;
	  
	  public Bank()
	  {
	    this.model = new BankModel();
	  }
	  
	  public double queryBalance(BankInfo info)
	  {
	    try
	    {
	      ResultSet rs = (ResultSet)this.model.search(info);
	      
	      if(rs != null)
	      {
	    	  while(rs.next())
	    	  {   	      
	    		  String temp = rs.getString("balance");
	    		  System.out.println("Bank: " + temp);
	    	      return Double.parseDouble(temp);
	    	  }
	      }
	    }
	    catch (SQLException e)
	    {
	      System.out.println("Database exception");
	      e.printStackTrace();
	    }
	    return 0.0D;
	  }
	  
	  public boolean transfer(BankInfo info)
	  {
	    info.setBalance(info.getBalance() - info.getTransferAmount());
	    if (this.model.modify(info))
	    {
	      BankInfo temp = new BankInfo(info.getTransferTo(), 0.0D, info.getPwd(), info.getId(), -info.getTransferAmount());
	      
	      ResultSet rs = (ResultSet)this.model.search(temp);
	      try
	      {
	        if (rs.last()) {
	          temp.setBalance(rs.getDouble("balance") - temp.getTransferAmount());
	        }
	      }
	      catch (SQLException e)
	      {
	        e.printStackTrace();
	      }
	      return true;

	    }
	    return false;
	  }

}
