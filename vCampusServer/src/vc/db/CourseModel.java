package vc.db;

import vc.common.CourseInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseModel
  implements Model
{
  private Connection con;
  private String query;
  private CourseInfo info;
  
  public CourseModel()
  {
    this.con = DBConnection.getConnection();
    this.query = "";
    this.info = null;
  }
  
  public boolean insert(Object obj)
  {
    this.info = ((CourseInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("insert into tbCourse values ('" + this.info.getId() + "','" + this.info.getName() + "','" + this.info.getTeacher() + "','" + this.info.getPlace() + "','" + this.info.getTime() + "'," + this.info.getCredit() + ");");
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
    this.info = ((CourseInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = 
        ("update tbCourse set courseName='" + this.info.getName() + "',teacher='" + this.info.getTeacher() + "',place='" + this.info.getPlace() + "',time='" + this.info.getTime() + "',credit=" + this.info.getCredit() + " where ID='" + this.info.getId() + "';");
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
    this.info = ((CourseInfo)obj);
    try
    {
      Statement stmt = this.con.createStatement();
      this.query = ("delete from tbCourse where ID='" + this.info.getId() + "';");
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
    this.info = ((CourseInfo)obj);
    if (this.info == null) {
      this.query = "select * from tbCourse;";
    } else if (!this.info.getId().equals("")) {
      this.query = ("select * from tbCourse where ID='" + this.info.getId() + "';");
    } else if (!this.info.getName().equals("")) {
      this.query = ("select * from tbCourse where courseName like'%" + this.info.getName() + "%';");
    } else if (!this.info.getTeacher().equals("")) {
        this.query = ("select * from tbCourse where teacher like'%" + this.info.getTeacher() + "%';");
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
