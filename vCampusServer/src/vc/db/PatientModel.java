package vc.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import vc.common.CourseInfo;
import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.common.UserInfo;

public class PatientModel implements Model{
	  private Connection con;
	  private String query;
	  private PatientInfo info;
	  private UserInfo user;
	  private long time = System.currentTimeMillis();
	  private Date date = new Date(time);
	  
	  public PatientModel()
	  {
	    this.con = DBConnection.getConnection();
	    this.query = "";
	    this.info = null;
	  }
	  
	@Override
	public boolean insert(Object obj) {
		this.info = ((PatientInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	        ("insert into tbRegister (registerDate, register, u_ID, u_Name, u_Gender, prescription) values ('" + date.toString() + " 00:00:00.000000' ,'" + this.info.getRegister() + "','" + this.info.getId() + "','" + this.info.getName() + "','" + this.info.getGender() + "','y" + "');");
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
	
	public boolean insertPatient(Object obj) {
		this.info = ((PatientInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
		  	        ("delete from tbPatient where u_ID ='" + this.info.getId() + "';");
	      stmt.executeUpdate(this.query);
	      this.query = 
	        ("insert into tbPatient values ('" + this.info.getId() + "','" + "','" + "','" + this.info.getName() + "','" + this.info.getGender() + "','" + this.info.getAge() + "','" + "');");
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
		this.info = ((PatientInfo)obj);
	    try
	    {
	      //鐩墠鍙兘淇敼u_UnpaidMedcine
	      Statement stmt = this.con.createStatement();
	      this.query = 
	  	        ("delete from tbMedcineSelected where selector ='" + this.info.getId() + "';");
	      System.out.println(this.query);
	      stmt.executeUpdate(this.query);
	      String[] medcine =  this.info.getUnpaidMedcine_2();

	      //MedcineInfo[] med = new MedcineInfo[medcine.length];
	      for(int i=0;i<medcine.length;i++) {
	    	  //med[i] = new MedcineInfo("",medcine[i],null,null,null);
	    	  this.query = 
	    		        ("insert into tbMedcineSelected values ('" + medcine[i] + "','" + this.info.getId() + "');");
	    	  System.out.println(this.query);
	    	  stmt.executeUpdate(this.query);
	      }
	      return true;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
		return false;
	}
	@Override
	public boolean delete(Object obj) {
		this.info = ((PatientInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
	        ("delete from tbRegister where register ='" + this.info.getRegister() + "',u_ID='" + this.info.getId() + "';");
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
	
	public boolean pay(Object obj) {
		this.info = ((PatientInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = 
		  	        ("delete from tbMedcineSelected where selector ='" + this.info.getId() + "';");
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
	    this.info = ((PatientInfo)obj);
	    if (!this.info.getId().equals("")) {
	      this.query = ("select * from tbPatient where u_ID ='" + this.info.getId() + "';");
	    } else if (!this.info.getName().equals("")) {
	      this.query = ("select * from tbPatient where u_Name ='" + this.info.getName() + "';");
	    }
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      System.out.println(this.query);
	      
	      ResultSet rs = stmt.executeQuery(this.query);
	      Vector<String> v = new Vector();
	      while(rs.next()) {
	    	  String temp = new String(rs.getString("u_Name"));
		      v.add(temp);
		      temp = rs.getString("u_Gender");
		      //System.out.println(temp);
		      v.add(temp);
		      temp = rs.getString("u_Age");
		      v.add(temp);
		      temp = rs.getString("u_ID");
		      v.add(temp);
	      };
	      String[] x= (String[])v.toArray(new String[rs.getRow()]);
	        return x;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public String[] searchUnpaidMedcine(Object obj) {
	    this.info = ((PatientInfo)obj);
	    if (this.info == null) {
	      this.query = "select * from tbMedcineSelected;";
	    } else if (!this.info.getId().equals("")) {
	      this.query = ("select * from tbMedcineSelected where selector ='" + this.info.getId() + "';");
	    } else if (!this.info.getName().equals("")) {
	      this.query = ("select * from tbMedcineSelected where u_Name ='" + this.info.getName() + "';");
	      System.out.println("searchUnpaidMedcine閿欏暒锛侊紒锛�");
	    }
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      System.out.println(this.query);
	      
	      ResultSet rs = stmt.executeQuery(this.query);
	      if(rs != null) {
	    	  Vector<String> v = new Vector();
		      while(rs.next()) {
		    	  String temp = new String(rs.getString("ID"));
			      v.add(temp);
			      
			      /*
			      temp = rs.getString("u_Gender");
			      v.add(temp);
			      temp = rs.getString("u_Age");
			      v.add(temp);
			      temp = rs.getString("u_ID");
			      v.add(temp);
			      */
		      };
		      String[] x= (String[])v.toArray(new String[rs.getRow()]);
		        return x;
	      }
	      else return null;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
		return null;
	}
	
	public int searchRegister(Object obj) {
		this.info = ((PatientInfo)obj);
		int nrow = 1;
		System.out.println(date);
		/*
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbRegister where u_ID ='09017408" + "';");	    
	      System.out.println(this.query);
	      ResultSet rs = stmt.executeQuery(this.query);
	      while(rs.next()){
	    	  System.out.println(rs.getString("registerDate"));
	      }
	      return nrow;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    */
		
		
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      
	      this.query = ("select * from tbRegister where registerDate ='" + date + " 00:00:00.000000';");	      
	      System.out.println(this.query);
	      ResultSet rs = stmt.executeQuery(this.query);
	      if (rs != null) {
	    	
	    	while(rs.next()){
	    	   nrow++;
	    	}
	      }
	      //System.out.println(nrow);
	      this.info.setRegister(nrow);
	      this.insert(info);
	      return nrow;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return nrow;
	}

	public String[] searchUser(Object obj)
	  {
	    this.user = ((UserInfo)obj);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbStudentRoll where ID ='" + this.user.getStuId() + "';");
	      System.out.println(this.query);
	      
	      ResultSet rs = stmt.executeQuery(this.query);
	      Vector<String> v = new Vector();
	      while(rs.next()) {
	    	  String temp = new String(rs.getString("stuName"));
		      v.add(temp);
		      temp = rs.getString("Gender");
		      //System.out.println(temp);
		      v.add(temp);
		      temp = rs.getString("Age");
		      v.add(temp);
		      temp = rs.getString("ID");
		      v.add(temp);
	      };
	      String[] x= (String[])v.toArray(new String[rs.getRow()]);
	    
	      return x;
	      
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
	
	public boolean prescribe(String id,String prescription) {
		try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("update tbRegister set prescription ='" + prescription + "' where register='" + id + "' and registerDate='" + date + " 00:00:00.000000';");
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
	
	public ResultSet readMHistory(String id) {
		//System.out.println("鍒版暟鎹簱浜�");
		try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbRegister where u_ID ='" + id + "';");
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
	
	public String[] readPrescription(String id, int i) {
		System.out.println("readPrescription");
		try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbRegister where u_ID ='" + id + "';");
	      System.out.println(this.query); 
	      ResultSet rs = stmt.executeQuery(this.query);
	      Vector<String> v = new Vector();
	      if(i == 1) {
		      while(rs.next()) {
		    	  String temp = new String(rs.getString("registerDate"));
		    	  //System.out.println(temp); 
		    	  temp = temp.substring(0,temp.length()-9); 
		    	  //System.out.println(temp);
			      v.add(temp);
		      };
	      }
	      if(i == 2) {
		      while(rs.next()) {
		    	  /*
		    	  String temp =  new String(" ");
		    	  if(rs.getString("prescription").equals("")) {
		    		  temp =  new String(" ");
		    	  }
		    	  else{
		    		  temp = new String(rs.getString("prescription"));
		    	  }
		    	  */
		    	  String temp = new String(rs.getString("prescription"));
			      v.add(temp);
		      };
	      }
	      String[] x= (String[])v.toArray(new String[rs.getRow()]);
	      return x;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
		return null;
	}
}
