package vc.db;

/**
 * @author 09017406
 */

import vc.common.BankInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankModel implements Model {

	  private Connection con;
	  private String query;
	  private BankInfo info;
	  
	  public BankModel()
	  {
	    this.con = DBConnection.getConnection();
	    this.query = "";
	    this.info = null;
	  }
	  
	  public boolean insert(Object obj)
	  {
	    this.info = ((BankInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	        ("insert into tbBank(userID,balance,pwd,transferTo,transferAmount) values ('" + this.info.getId() + "','" + this.info.getBalance() + "','" + this.info.getPwd() + "','" + this.info.getTransferTo() +"','"+ this.info.getTransferAmount()  + "');");
	      System.out.println(this.query);
	      if (stmt.executeUpdate(this.query) != 0) {
	        return true;
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public boolean modify(Object obj)
	  {
	    this.info = ((BankInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	        ("update tbBank set balance='" + this.info.getBalance() + "',transferTo='" + this.info.getTransferTo() + "',transferAmount='" + this.info.getTransferAmount() + "' where userID='" + this.info.getId() + "';");
	      System.out.println(this.query);
	      if (stmt.executeUpdate(this.query) != 0) {
	        return true;
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public boolean delete(Object obj)
	  {
	    this.info = ((BankInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("delete from tbBank where userID='" + this.info.getId() + "';");
	      System.out.println(this.query);
	      if (stmt.executeUpdate(this.query) != 0) {
	        return true;
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public Object search(Object obj)///////////////////////////
	  {
	    this.info = ((BankInfo)obj);
	    if (!this.info.getId().equals(""))
	    {
	      this.query = ("select * from tbBank where userID='" + this.info.getId() + "' order by transferDate;");
	      if (!this.info.getPwd().equals("")) {
	        this.query = ("select * from tbBank where userID='" + this.info.getId() + "' and pwd='" + this.info.getPwd() + "' order by transferDate;");
	      }
	    }
	    try
	    {
	      Statement stmt = this.con.createStatement(1004, 1007);
	      System.out.println(this.query);
	      
	      ResultSet rs = stmt.executeQuery(this.query);
	      if (rs != null) {
	        return rs;
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }

}
