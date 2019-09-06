package vc.db;

import vc.common.BookInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookModel
  implements Model
{
  private Connection con;
  private String query;
  private BookInfo info;
  
  public BookModel()
  {
    this.con = DBConnection.getConnection();
    this.query = "";
    this.info = null;
  }
  
  public boolean insert(Object obj)
  {
    this.info = ((BookInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
    		  ("insert into tbBook (ISBN, bookName, author, pub, pubDate, pos, isBorrowed) values ('" + this.info.getIsbn() + "','" + this.info.getName() + "','" + this.info.getAuthor() + "','" + this.info.getPub() + "'," + this.info.getPubDate() + ",'" + this.info.getPos() + "','" + this.info.isBorrowed() + "');");
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
    this.info = ((BookInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("update tbBook set ISBN='" + this.info.getIsbn() + "',bookName='" + this.info.getName() + "',author='" + this.info.getAuthor() + "',pub='" + this.info.getPub() + "',pubDate=" + this.info.getPubDate() + ",pos='" + this.info.getPos() + "',isBorrowed=" + this.info.isBorrowed() + " where ID=" + this.info.getId() + ";");
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
    this.info = ((BookInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = ("delete from tbBook where ID=" + this.info.getId() + ";");
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
    this.info = ((BookInfo)obj);
    if (this.info.getId() != 0) {
      this.query = ("select * from tbBook where ID='" + this.info.getId() + "';");
    } else if (!this.info.getName().equals("")) {
      this.query = ("select * from tbBook where bookName='" + this.info.getName() + "';");
    } else if (!this.info.getAuthor().equals("")) {
      this.query = ("select * from tbBook where author='" + this.info.getAuthor() + "';");
    } else {
      this.query = "select * from tbBook;";
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