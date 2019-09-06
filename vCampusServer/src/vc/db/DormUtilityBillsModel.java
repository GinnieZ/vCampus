package vc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vc.common.DormLivingInf;
import vc.common.DormUtilityBillsInf;

public class DormUtilityBillsModel implements Model {
	private Connection con;
	private String query;
	private DormUtilityBillsInf info;
	public DormUtilityBillsModel() {
		// TODO Auto-generated constructor stub
		 this.info = null;
		 this.con = (Connection) DBConnection.getConnection();
		 this.query = "";
	}

	@Override
	public boolean insert(Object paramObject) {
		// TODO Auto-generated method stub
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

	/**
	查询该生水电费信息
	 * @param DormUtilityBillsInf
	 * @return DormUtilityBillsInf[]
	 */
	@Override
	public Object search(Object paramObject) {
		// TODO Auto-generated method stub
	    this.info = ((DormUtilityBillsInf)paramObject);
	    try
	    {
	      Statement stmt = this.con.createStatement();
	      this.query = ("select * from tbDormUtilityBills where studentID='" + this.info.getStudentID() + "';");
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
