package vc.db;

import vc.common.UserInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginModel
  implements Model
{
  private Connection con;
  private String query;
  private UserInfo info;
  
  public LoginModel()
  {
    this.info = null;
    this.con = (Connection) DBConnection.getConnection();
    this.query = "";
  }
  
  public boolean insert(Object obj)
  {
    this.info = ((UserInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("insert into tbUser values ('" + this.info.getStuId() + "','" + this.info.getPwd() + "','" + this.info.getType() + "','" + this.info.getName() + "');");
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
    this.info = ((UserInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("update tbUser set u_Pwd='" + this.info.getPwd() + "',u_Type='" + this.info.getType() + "',u_Name=" + this.info.getName() + "' where u_ID='" + this.info.getStuId() + "';");
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
    this.info = ((UserInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = ("delete from tbUser where u_ID='" + this.info.getStuId() + "';");
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
    this.info = ((UserInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = ("select * from tbUser where u_ID='" + this.info.getStuId() + "';");
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
