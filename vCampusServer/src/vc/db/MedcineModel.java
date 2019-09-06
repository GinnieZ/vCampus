package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import vc.common.CourseInfo;
import vc.common.MedcineInfo;

public class MedcineModel implements Model{
	private Connection con;
	private String query;
	private MedcineInfo info;
	
	public MedcineModel(){
	    this.con = DBConnection.getConnection();
	    this.query = "";
	    this.info = null;
	}
	
	@Override
	//insert涓璼ql璇彞鏈�鍚庢槸"')"  ?
	public boolean insert(Object obj) {
		this.info = ((MedcineInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	    		  ("insert into tbMedcine (u_Name,u_Usage,u_Num,u_Instruction) values ('"  + this.info.getName() + "','" + this.info.getUsage() + "','" + this.info.getNum() + "','" + this.info.getInstruction() + "');");
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
	    this.info = ((MedcineInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	        ("update tbMedcine set u_Name='" + this.info.getName() + "',u_Usage='" + this.info.getUsage() + "',u_Num='" + this.info.getNum() + "',u_Instruction='" + this.info.getInstruction() + " where ID='" + this.info.getId() + "';");
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
	    this.info = ((MedcineInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      if(this.info.getId() != null) {
	    	  this.query = ("delete from tbMedcine where ID='" + this.info.getId() + "';");
	      }
	      if(this.info.getName() != null) {
	    	  this.query = ("delete from tbMedcine where u_Name='" + this.info.getName() + "';");
	      }
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
	public String[] search(Object obj) {
	    this.info = ((MedcineInfo)obj);
	    //System.out.println("this.info.getId():"+this.info.getId());
	    System.out.println("searchMedcine ing");
	    if (this.info == null) {
	      this.query = "select * from tbMedcine;";
	    } else if (this.info.getId()!=null) {
	      this.query = ("select * from tbMedcine where ID='" + this.info.getId() + "';");
	    } else if (this.info.getName()!=null) {
	      this.query = ("select * from tbMedcine where u_Name='" + this.info.getName() + "';");
	    }
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      System.out.println(this.query);
	      
	      ResultSet rs = stmt.executeQuery(this.query);
	      
	      Vector<String> v = new Vector<String>();
	      String temp = new String();
	      while(rs.next()) {
	    	  temp =rs.getString("u_Name");
	    	  //System.out.println(temp);
	    	  v.add(temp);
	    	  temp = rs.getString("u_Usage");
	    	  //System.out.println(temp);
	    	  v.add(temp);
	    	  temp = rs.getString("u_Instruction");
	    	  //System.out.println(temp);
	    	  v.add(temp);
	      }
	      
	      String[] x= (String[])v.toArray(new String[rs.getRow()]);
	      
	      if (x != null) {
	        return x;
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public boolean purchase(Object obj) {
	    this.info = ((MedcineInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	        ("update tbMedcine set u_Num=u_Num-1'" + " where ID='" + this.info.getId() + "';");
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

}
