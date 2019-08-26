package vc.helper;

import vc.common.CourseInfo;
import vc.common.CourseSelectedInfo;
import vc.db.CourseModel;
import vc.db.CourseSelectedModel;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Course
{
  private CourseModel cModel;
  private CourseSelectedModel csModel;
  
  public Course()
  {
    this.cModel = new CourseModel();
    this.csModel = new CourseSelectedModel();
  }
  
  public CourseInfo[] queryCourse()
  {
    try
    {
      ResultSet rs = (ResultSet)this.cModel.search(null);
      Vector<CourseInfo> v = new Vector();
      while (rs.next())
      {
        CourseInfo temp = new CourseInfo(rs.getString("ID"), rs.getString("courseName"), rs.getString("teacher"), rs.getString("place"), 
          rs.getString("time"), rs.getDouble("credit"));
        v.add(temp);
      }
      return (CourseInfo[])v.toArray(new CourseInfo[rs.getRow()]);
    }
    catch (SQLException e)
    {
      System.out.println("Database exception");
      e.printStackTrace();
    }
    return null;
  }
  
public String[] queryStudent(CourseInfo info)
{
	List<String> l = new ArrayList<>();
	try 
	{
		CourseSelectedInfo temp = new CourseSelectedInfo(info.getId(), "");
		ResultSet rs = (ResultSet)this.csModel.search(temp);
		if(rs != null) {
			while (rs.next())
			{
				l.add(rs.getString("selector"));
			}

			return (String[])l.toArray(new String[l.size()]);
		}
	}
	catch (SQLException e) {
		System.out.println("Database exception");
		e.printStackTrace();
	}
	return null;
  }
  
  public boolean addCourse(CourseInfo info)
  {
    return this.cModel.insert(info);
  }
  
  public boolean deleteCourse(CourseInfo info)
  {
    return this.cModel.delete(info);
  }
  
  public boolean modifyCourse(CourseInfo info)
  {
    return this.cModel.modify(info);
  }
  
  public boolean selectCourse(CourseSelectedInfo info)
  {
    return this.csModel.insert(info);
  }
  
  public boolean deselectCourse(CourseSelectedInfo info)
  {
    return this.csModel.delete(info);
  }
  
  public CourseInfo[] queryCurriculum(CourseSelectedInfo info)
  {
    try
    {
      ResultSet rs = (ResultSet)this.csModel.search(info);
      Vector<CourseInfo> v = new Vector();
      Vector<CourseInfo> v1 = new Vector();
      while (rs.next())
      {
        CourseInfo temp = new CourseInfo(rs.getString("ID"), "", "", "", "", 0.0D);
        
        v1.add(temp);
      }
      for (int i = 0; i < v1.size(); i++)
      {
        rs = (ResultSet)this.cModel.search(v1.get(i));
        if (rs.next())
        {
          CourseInfo temp = new CourseInfo(rs.getString("ID"), rs.getString("courseName"), rs.getString("teacher"), rs.getString("place"), 
            rs.getString("time"), rs.getDouble("credit"));
          v.add(temp);
        }
      }
      return (CourseInfo[])v.toArray(new CourseInfo[rs.getRow()]);
    }
    catch (SQLException e)
    {
      System.out.println("Database exception");
      e.printStackTrace();
    }
    return null;
  }
  
  public CourseSelectedInfo[] queryStatus(CourseInfo info)
  {
    try
    {
      ResultSet rs = (ResultSet)this.cModel.search(info);
      Vector<CourseSelectedInfo> v = new Vector();
      if (rs.next())
      {
    	  CourseSelectedInfo temp = new CourseSelectedInfo(rs.getString("ID"), null);
        
        rs = (ResultSet)this.csModel.search(temp);
        while (rs.next())
        {
        	CourseSelectedInfo temp1 = new CourseSelectedInfo(rs.getString("ID"), rs.getString("selector"));
          v.add(temp1);
        }
      }
      return (CourseSelectedInfo[])v.toArray(new CourseSelectedInfo[rs.getRow()]);
    }
    catch (SQLException e)
    {
      System.out.println("Database exception");
      e.printStackTrace();
    }
    return null;
  }
}
