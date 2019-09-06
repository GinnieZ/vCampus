package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vc.common.CourseInfo;
import vc.common.OnlineClassInfo;

public class OnlineClassModel
implements Model
{
private Connection con;
private String query;
private OnlineClassInfo info;

public OnlineClassModel()
{
  this.con = DBConnection.getConnection();
  this.query = "";
  this.info = null;
}

public boolean insert(Object obj)
{
  this.info = ((OnlineClassInfo)obj);
  try
  {
    Statement stmt = this.con.createStatement();
    this.query = 
      ("insert into tbOnlineClass values ('" + this.info.getId() + "','" + this.info.getName() + "','" + this.info.getTeacher() + this.info.getPeriod() + ");");
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
  this.info = ((OnlineClassInfo)obj);
  try
  {
    Statement stmt = this.con.createStatement();
    this.query = 
      ("update tbCourse set tbOnlineClass='" + this.info.getName() + "',teacher='" + this.info.getTeacher() + "',period=" + this.info.getPeriod() + " where ID='" + this.info.getId() + "';");
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
  this.info = ((OnlineClassInfo)obj);
  try
  {
    Statement stmt = this.con.createStatement();
    this.query = ("delete from tbOnlineClass where ID='" + this.info.getId() + "';");
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
  this.info = ((OnlineClassInfo)obj);
  if (this.info == null) {
    this.query = "select * from tbOnlineClass;";
  } else if (!this.info.getId().equals("")) {
    this.query = ("select * from tbOnlineClass where ID='" + this.info.getId() + "';");
  } else if (!this.info.getName().equals("")) {
    this.query = ("select * from tbOnlineClass where courseName like'%" + this.info.getName() + "%';");
  } else if (!this.info.getTeacher().equals("")) {
      this.query = ("select * from tbOnlineClass where teacher like'%" + this.info.getTeacher() + "%';");
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
