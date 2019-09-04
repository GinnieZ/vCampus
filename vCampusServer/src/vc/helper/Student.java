package vc.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import vc.common.StudentRollInfo;
import vc.db.StudentRollModel;

public class Student {

	private StudentRollModel smodel;
	  
	  public Student()
	  {
	    this.smodel = new StudentRollModel();
	  }
	  
	  public StudentRollInfo query(StudentRollInfo info)
	  {
	    try
	    {
	      ResultSet rs = (ResultSet)this.smodel.search(info);
	      if (rs.next()) {
	        return new StudentRollInfo(rs.getString("ID"), rs.getString("stuName"), rs.getString("age"), rs.getString("gender"), 
	          rs.getString("birthday"), rs.getString("birthPlace"), 
	          rs.getString("department"), rs.getString("dormitory"));
	      }
	      return null;
	    }
	    catch (SQLException e)
	    {
	      System.out.println("Database exception");
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  public StudentRollInfo[] queryAll()
	  {
	    try
	    {
	      ResultSet rs = (ResultSet)this.smodel.search(null);
	      Vector<StudentRollInfo> v = new Vector();
	      while (rs.next())
	      {
	        StudentRollInfo temp = new StudentRollInfo(rs.getString("ID"), rs.getString("stuName"), rs.getString("age"), rs.getString("gender"), 
	          rs.getString("birthday"), rs.getString("birthPlace"), 
	          rs.getString("department"), rs.getString("dormitory"));
	        v.add(temp);
	      }
	      return (StudentRollInfo[])v.toArray(new StudentRollInfo[rs.getRow()]);
	    }
	    catch (SQLException e)
	    {
	      System.out.println("Database exception");
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  public boolean AddStudentView(StudentRollInfo info)
	  {
	    return this.smodel.insert(info);
	  }
	  
	  public boolean ModifyStudentView(StudentRollInfo info)
	  {
	    return this.smodel.modify(info);
	  }
	  
	  public boolean DeleteStudentView(StudentRollInfo info)
	  {
	    return this.smodel.delete(info);
	  }
}
