package vc.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.common.DormUtilityBillsInf;
import vc.common.DormVisitInf;
import vc.db.DormChargeModel;
import vc.db.DormInfModel;
import vc.db.DormRepairModel;
import vc.db.DormUtilityBillsModel;
import vc.db.DormVisitModel;

public class Dorm {
	private DormInfModel model;
	private DormRepairModel repairmodel;
	private DormChargeModel chargemodel;
	private DormVisitModel visitmodel;
	private DormUtilityBillsModel utilitybillsmodel;
	
	public Dorm() {
		// TODO Auto-generated constructor stub
		this.model = new DormInfModel();
		this.repairmodel = new DormRepairModel();
		this.chargemodel = new DormChargeModel();
		this.visitmodel = new DormVisitModel();
		this.utilitybillsmodel = new DormUtilityBillsModel();
	}
	/**
	 个人住宿信息查询
	 * @param modle
	 * @return DormLivingInf[]
	 */
	public DormLivingInf[] queryDormLivingInf(DormLivingInf info) {
		try {
			ResultSet rs = (ResultSet)this.model.search(info);
			Vector<DormLivingInf> v = new Vector();
			while(rs.next()) {
				 DormLivingInf temp = new DormLivingInf(rs.getString("student_ID"), rs.getString("region"),
		    			  rs.getInt("unit"), rs.getString("building"), rs.getInt("room"), rs.getInt("bed"),rs.getString("phone")); 
				 v.add(temp);
			}
			 return (DormLivingInf[])v.toArray(new DormLivingInf[rs.getRow()]);
		}
	    catch (SQLException e)
	    {
	      System.out.println("Database exception");
	      e.printStackTrace();
	    }
		return null;	
	}
	
	public DormLivingInf[] queryDormLivingInf() {
		 try
		    {
		      ResultSet rs = (ResultSet)this.model.search(null);
		      Vector<DormLivingInf> v = new Vector();
		      while (rs.next())
		      {
		    	  DormLivingInf temp = new DormLivingInf(rs.getString("student_ID"), rs.getString("region"),
		    			  rs.getInt("unit"), rs.getString("building"), rs.getInt("room"), rs.getInt("bed"),rs.getString("phone")); 
		    	  System.out.println("Dorm: " + rs.getString("student_ID"));
		    	  v.add(temp);
		      }
		      return (DormLivingInf[])v.toArray(new DormLivingInf[rs.getRow()]);
		    }
		    catch (SQLException e)
		    {
		      System.out.println("Database exception");
		      e.printStackTrace();
		    }
		return null;	 
	}
	
	public boolean addDormLivingInf(DormLivingInf info) {
		return this.model.insert(info);
	}
	
	public boolean deleteDormLivingInf(DormLivingInf info) {
		return this.model.delete(info);
	}
	
	public boolean modifyDormLivingInf(DormLivingInf info) {
		return this.model.modify(info);
	}
	
	/**
	查询宿舍维修信息
	 * @param repairmodle
	 * @return DormRepairInf[]
	 */
	public DormRepairInf[] queryDormRepairInf(DormRepairInf info) {
		 try
		    {
		      ResultSet rs = (ResultSet)this.repairmodel.search(info);
		      Vector<DormRepairInf> v = new Vector();
		      while (rs.next())
		      {
		    	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    	  Date d = (Date)rs.getObject("repairTime");
		    	  String strd= format.format(d);
		    	  DormRepairInf temp = new DormRepairInf(rs.getString("repair_ID"),
		    			  strd,rs.getString("Student_ID"),rs.getString("reason"),rs.getString("state")); 
		    	  v.add(temp);
		      }
		      return (DormRepairInf[])v.toArray(new DormRepairInf[rs.getRow()]);
		    }
		    catch (SQLException e)
		    {
		      System.out.println("Database exception");
		      e.printStackTrace();
		    }
		return null;	 
	}

	/**
	 *新增宿舍维修信息
	 * @param DormVisitInf
	 * @return boolean
	 */
	public boolean insertDormRepairInf(DormRepairInf info) {
		return this.repairmodel.insert(info);
	}
	
	/**
	 水电费缴纳账单查询
	 * @param chargermodle
	 * @return DormChargeInf[]
	 */
	public DormChargeInf[] queryDormChargeInf(DormChargeInf info) {
		 try
		    {
		      ResultSet rs = (ResultSet)this.chargemodel.search(info);
		      Vector<DormChargeInf> v = new Vector();
		      while (rs.next())
		      {
		    	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    	  Date d = (Date)rs.getObject("chargeTime");
		    	  String Strd = format.format(d);
		    	  DormChargeInf temp = new DormChargeInf(rs.getString("chargeID"),
		    			  d.toString(),rs.getString("studentID"),rs.getInt("chargeMoney"),
		    			  rs.getInt("arrears"),rs.getInt("utilityBills"),rs.getInt("balance")); 
		    	  v.add(temp);
		      }
		      return (DormChargeInf[])v.toArray(new DormChargeInf[rs.getRow()]);
		    }
		    catch (SQLException e)
		    {
		      System.out.println("Database exception");
		      e.printStackTrace();
		    }
		return null;	
	}
	
	
	/**
	 新增水电缴费
	 * @param DormChargermodle
	 * @return boolean
	 */
	public boolean insertDormChargeInf(DormChargeInf info) {
		return this.chargemodel.insert(info);
	}
	
	/**
	 *来访记录信息查询
	 * @param DormVisitInf
	 * @return DormVisitInf[]
	 */
	public DormVisitInf[] queryDormVisitInf(DormVisitInf info) {
		 try
		    {
		      ResultSet rs = (ResultSet)this.visitmodel.search(info);
		      Vector<DormVisitInf> v = new Vector();
		      while (rs.next())
		      {
		    	  Date in = (Date)rs.getObject("timeIn");
		    	  Date out = (Date)rs.getObject("timeOut");
		    	  DormVisitInf temp = new DormVisitInf(rs.getString("visitID"),rs.getString("name"),
		    			  in.toString(),out.toString(),rs.getString("reason")); 
		    	  v.add(temp);
		      }
		      return (DormVisitInf[])v.toArray(new DormVisitInf[rs.getRow()]);
		    }
		    catch (SQLException e)
		    {
		      System.out.println("Database exception");
		      e.printStackTrace();
		    }
		return null;	
	}

	
	/**
	 * 每月水电费信息查询
	 * @param DormUtilityBillsInf 
	 * @return DormUtilityBillsInf[]
	 */
	public DormUtilityBillsInf[] queryDormUtilityBillsInf(DormUtilityBillsInf info) {
		 try
		    {
			 
			 ResultSet rs = (ResultSet)this.utilitybillsmodel.search(info);
		      Vector<DormUtilityBillsInf> v = new Vector();
		      while (rs.next())
		      {
		    	  Date in = (Date)rs.getObject("billsTime");
		    	  System.out.println("billsTIme:");
		    	  System.out.println(in.toString());
		    	  DormUtilityBillsInf temp = new DormUtilityBillsInf(rs.getString("billsID"),rs.getString("studentID"),
		    			  in.toString(),rs.getInt("utilityBills")); 
		    	  v.add(temp);
		      }
		      return (DormUtilityBillsInf[])v.toArray(new DormUtilityBillsInf[rs.getRow()]);
		    }
		    catch (SQLException e)
		    {
		      System.out.println("Database exception");
		      e.printStackTrace();
		    }
		return null;	
	}
}
