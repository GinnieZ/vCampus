	package vc.db;
	
	import vc.common.CourseSelectedInfo;
	import java.io.PrintStream;
	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	
	public class CourseSelectedModel
	  implements Model
	{
	  private Connection con;
	  private String query;
	  private CourseSelectedInfo info;
	  
	  public CourseSelectedModel()
	  {
	    this.con = DBConnection.getConnection();
	    this.query = "";
	    this.info = null;
	  }
	  
	  public boolean insert(Object obj)
	  {
	    this.info = ((CourseSelectedInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("insert into tbCourseSelected values ('" + this.info.getId() + "','" + this.info.getSelector() + "');");
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
	    this.info = ((CourseSelectedInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("update tbCourseSelected set selector='" + this.info.getSelector() + "' where ID='" + this.info.getId() + "';");
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
	    this.info = ((CourseSelectedInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("delete from tbCourseSelected where ID='" + this.info.getId() + "';");
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
	    this.info = ((CourseSelectedInfo)obj);
	    if (!this.info.getId().equals("")) {
	      this.query = ("select * from tbCourseSelected where ID='" + this.info.getId() + "';");
	    } else if (!this.info.getSelector().equals("")) {
	      this.query = ("select * from tbCourseSelected where selector='" + this.info.getSelector() + "';");
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
	
