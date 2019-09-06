package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vc.common.DormLivingInf;
import vc.common.UserInfo;

public class DormInfModel implements Model {
	private Connection con;
	private String query;
	private DormLivingInf info;
	
	public DormInfModel() {
		// TODO Auto-generated constructor stub
		 this.info = null;
		 this.con = (Connection) DBConnection.getConnection();
		 this.query = "";
	}

	@Override
	public boolean insert(Object obj) {
		// TODO Auto-generated method stub
		this.info = ((DormLivingInf)obj);
		try {
			Statement stmt = this.con.createStatement();
			 this.query = ("insert into tbDormLivingInf values ('" + this.info.getStudentID() + "','"
			+ this.info.getregion() + "','" + this.info.getunit() + "','" + this.info.getbuilding() + "','"
			+this.info.getbed()+"','" +this.info.getphone()+"');'");
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
	public boolean modify(Object obj) {
		// TODO Auto-generated method stub
		this.info = ((DormLivingInf)obj);
		try {
			Statement stmt = this.con.createStatement();
			 this.query = ("update tbDormLivingInf set region='" + this.info.getregion() +
					 "' " +",unit='" + this.info.getunit() + "',building='" + this.info.getbuilding() + "',bed='"
			+this.info.getbed()+"',phone='" +this.info.getphone()+"'"+"where student_ID='"+this.info.getStudentID()+"';");
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
	public boolean delete(Object obj) {
		// TODO Auto-generated method stub
	    this.info = ((DormLivingInf)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("delete from tbDormLivingInf where student_ID='" + this.info.getStudentID() + "';");
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
	public Object search(Object obj) {
		// TODO Auto-generated method stub
	    this.info = ((DormLivingInf)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbDormLivingInf where student_ID='" + this.info.getStudentID() + "';");
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
