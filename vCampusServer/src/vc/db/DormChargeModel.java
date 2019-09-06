package vc.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormUtilityBillsInf;

public class DormChargeModel implements Model {
	private Connection con;
	private String query;
	private DormChargeInf info;
	public DormChargeModel() {
		// TODO Auto-generated constructor stub
		 this.info = null;
		 this.con = (Connection) DBConnection.getConnection();
		 this.query = "";
	}

	@Override
	public boolean insert(Object paramObject) {
	    this.info = ((DormChargeInf)paramObject);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      //获取前一个月水电费
	      //前一个月水电费bills
	      int bill = -100;
	      //当前欠款
	      String arrears = "";
	      //当前账户余额
	      String nowBalance = "";
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          //前一个月的最后一天
          //因为储存水电费的表格里以每月最后一天的时间来代表本月水电费
          String lastday = new String("");
          Calendar cale = null;  
          cale = Calendar.getInstance();  
          cale.add(Calendar.MONTH+1, 1); 
          cale.set(Calendar.DAY_OF_MONTH, 0);          
          lastday = format.format(cale.getTime()); 
          System.out.println("这是这个月的最后一天"+ lastday); 
	      String queryBills = ("select utilityBills from tbDormUtilityBills where studentID = '"+
	    		  this.info.getStudentID()+"' and billsTime = '"+lastday+"';");
	      System.out.println(queryBills);
	      ResultSet rs = stmt.executeQuery(queryBills);
		  while(rs.next()) {
			  bill = rs.getInt("utilityBills");
		  }
		  System.out.println("bill"+bill);
		  if(bill == -100) {
			  return false;
		  }

		  //计算余额与欠缴费
		  int tempResult = this.info.getChargeMoney() + this.info.getBalance()-bill;
		  if(tempResult > 0) {
			  arrears = "0";
			  nowBalance = Integer.toString(tempResult);
		  }
		  else {
			  arrears = Integer.toString(tempResult);
			  nowBalance = "0";
		  }
	      this.query = ("insert into tbDormChargeInf (chargeTime, studentID, "
	      		+ "chargeMoney, arrears, utilityBills,balance) values ('"+ this.info.getChargeTime()+"','" + 
	    		  this.info.getStudentID() +"','"+this.info.getChargeMoney()+"','"+
	      		arrears+"','"+bill+"','"+nowBalance+"');");
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
	public Object search(Object paramObject) {
		// TODO Auto-generated method stub
	    this.info = ((DormChargeInf)paramObject);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbDormChargeInf where studentID='" + this.info.getStudentID() + "';");
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
