package vc.db;

import vc.common.BookStatusInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookStatusModel
  implements Model
{
  private Connection con;
  private String query;
  private BookStatusInfo info;
  
  public BookStatusModel()
  {
    this.con = DBConnection.getConnection();
    this.query = "";
    this.info = null;
  }
  
  public boolean insert(Object obj)
  {
    this.info = ((BookStatusInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("insert into tbBookStatus values (" + this.info.getId() + ",'" + this.info.getName() + "','" + this.info.getBorrower() + "'," + this.info.getBorrowDate() + "," + this.info.getReturnDate() + "," + this.info.getActualReturnDate() + "," + this.info.isOvertime() + ");");
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
    this.info = ((BookStatusInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("update tbBookStatus set returnDate=" + this.info.getReturnDate() + ",actualReturnDate=" + this.info.getActualReturnDate() + ","
        		+ "isOvertime=" + this.info.isOvertime() + " ,borrowDate=" + this.info.getBorrowDate() + ", "
        				+ "borrower=" + this.info.getBorrower() + " where ID=" + this.info.getId() + " ;");
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
    this.info = ((BookStatusInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = ("delete from tbBookStatus where ID=" + this.info.getId() + ";");
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
    this.info = ((BookStatusInfo)obj);
    if (!this.info.getBorrower().equals("")) {
      this.query = ("select * from tbBookStatus where borrower='" + this.info.getBorrower() + "';");
    } else if (!this.info.getName().equals("")) {
      this.query = ("select * from tbBookStatus where bookName='" + this.info.getName() + "';");
    }else if (this.info.getId()!=0) {
        this.query = ("select * from tbBookStatus where ID='" + this.info.getId() + "';");
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
