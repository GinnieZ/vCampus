package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vc.common.StudentRollInfo;

public class StudentRollModel 
implements Model
{
	private Connection con;
	  private String query;
	  private StudentRollInfo info;
	  
	  public StudentRollModel()
	  {
	    this.con = DBConnection.getConnection();
	    this.query = "";
	    this.info = null;
	  }
	  
	  public boolean insert(Object obj)
	  {
	    this.info = ((StudentRollInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	      
	        ("insert into tbStudentRoll values ('" + this.info.getId() + "','" + this.info.getName() + "','" + this.info.getAge() + "','" + this.info.getGender() + "','" + this.info.getBirthday() + "','" + this.info.getBirthPlace() + "','" + this.info.getDepartment() + "','" + this.info.getDormitory() + "');");
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
	    this.info = ((StudentRollInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	      
	        ("update tbStudentRoll set stuName='" + this.info.getName() + "',age='" + this.info.getAge() + "',gender='" + this.info.getGender() + "',birthday='" + this.info.getBirthday() + "',birthPlace='" + this.info.getBirthPlace() +  "',department='" + this.info.getDepartment() + "',dormitory='" + this.info.getDormitory() + "' where ID='" + this.info.getId() + "';");
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
	    this.info = ((StudentRollInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("delete from tbStudentRoll where ID='" + this.info.getId() + "';");
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
	    this.info = ((StudentRollInfo)obj);
	    if (this.info == null)
	    {
	      this.query = "select * from tbStudentRoll;";
	    }
	    else if (!this.info.getId().equals(""))
	    {
	      this.query = ("select * from tbStudentRoll where ID='" + this.info.getId() + "';");
	      if (!this.info.getName().equals("")) {
	        this.query = ("select * from tbStudentRoll where stuName='" + this.info.getName() + "' and ID='" + this.info.getId() + "';");
	      }
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
