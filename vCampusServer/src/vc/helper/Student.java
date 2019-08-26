package vc.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import vc.common.StudentRollInfo;
import vc.db.StudentRollModel;

public class Student {

	private StudentRollModel model;
	  
	  public Student()
	  {
	    this.model = new StudentRollModel();
	  }
	  
	  public StudentRollInfo query(StudentRollInfo info)
	  {
	    try
	    {
	      ResultSet rs = (ResultSet)this.model.search(info);
	      if (rs.next()) {
	        return new StudentRollInfo(rs.getString("ID"), rs.getString("stuName"), rs.getString("age"), rs.getString("gender"), 
	          rs.getString("birthday"), rs.getString("birthPlace"), rs.getString("entranceTime"), rs.getString("photo"), rs.getString("nation"), 
	          rs.getString("department"), rs.getString("major"), rs.getString("dormitory"));
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
	      ResultSet rs = (ResultSet)this.model.search(null);
	      Vector<StudentRollInfo> v = new Vector();
	      while (rs.next())
	      {
	        StudentRollInfo temp = new StudentRollInfo(rs.getString("ID"), rs.getString("stuName"), rs.getString("age"), rs.getString("gender"), 
	          rs.getString("birthday"), rs.getString("birthPlace"), rs.getString("entranceTime"), rs.getString("photo"), rs.getString("nation"), 
	          rs.getString("department"), rs.getString("major"), rs.getString("dormitory"));
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
	  
	  public boolean addInfo(StudentRollInfo info)
	  {
	    return this.model.insert(info);
	  }
	  
	  public boolean modifyInfo(StudentRollInfo info)
	  {
	    return this.model.modify(info);
	  }
	  
	  public boolean deleteInfo(StudentRollInfo info)
	  {
	    return this.model.delete(info);
	  }
}
