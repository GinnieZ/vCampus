package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import vc.common.OnlineClassSelectedInfo;

public class OnlineClassSelectedModel implements Model
{
	  private Connection con;
	  private String query;
	  private OnlineClassSelectedInfo info;
	  
	  public OnlineClassSelectedModel()
	  {
	    this.con = DBConnection.getConnection();
	    this.query = "";
	    this.info = null;
	  }
	  
	  public boolean insert(Object obj)
	  {
	    this.info = ((OnlineClassSelectedInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("insert into tbOnlineClassSelected values ('" + this.info.getId() + "','" 
	      + this.info.getSelector() + "','" + 1 + "');");
	      System.out.println(this.query);
	      stmt.executeUpdate(this.query);
	      return true;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public boolean modify(Object obj)
	  {
	    this.info = ((OnlineClassSelectedInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("update tbOnlineClassSelected set selector='" 
	      + this.info.getSelector() + "', currentPeriod='" 
	    		  + this.info.getCurrentPeriod() +"' where ID='" + this.info.getId() + "';");
	      System.out.println(this.query);
	      stmt.executeUpdate(this.query);
	      return true;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public boolean delete(Object obj)
	  {
	    this.info = ((OnlineClassSelectedInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("delete from tbOnlineClassSelected where ID='" + this.info.getId() + "';");
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
	  
	  public Object search(Object obj)
	  {
	    this.info = ((OnlineClassSelectedInfo)obj);
	    if (!this.info.getId().equals("")) {
	      this.query = ("select * from tbOnlineClassSelected where ID='" + this.info.getId() + "';");
	    } else if (!this.info.getSelector().equals("")) {
	      this.query = ("select * from tbOnlineClassSelected where selector='" + this.info.getSelector() + "';");
	    }
	    try
	    {
	      Statement stmt = this.con.createStatement();
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
	

