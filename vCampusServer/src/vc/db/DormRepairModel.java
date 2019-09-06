package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vc.common.DormLivingInf;
import vc.common.DormRepairInf;

public class DormRepairModel implements Model {
	private Connection con;
	private String query;
	private DormRepairInf info;
	
	public DormRepairModel() {
		// TODO Auto-generated constructor stub
		 this.info = null;
		 this.con = (Connection) DBConnection.getConnection();
		 this.query = "";
	}

	@Override
	public boolean insert(Object paramObject) {
		  this.info = ((DormRepairInf)paramObject);
		    try
		    {
		      Statement stmt = this.con.createStatement();
		      this.query = ("insert into tbDormRepairInf (repairTime,student_ID,reason,state) values ("+"'" + this.info.getTime() +  "','"+
		    		  this.info.getStudentID()+ "','"+this.info.getReason() +"','"+ this.info.getState()+"');'");
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

	@Override
	public boolean modify(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object search(Object obj) {
		// TODO Auto-generated method stub
	    this.info = ((DormRepairInf)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbDormRepairInf where student_ID='" + this.info.getStudentID() + "';");
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
