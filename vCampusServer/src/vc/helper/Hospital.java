package vc.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.common.UserInfo;
import vc.db.MedcineModel;
import vc.db.PatientModel;

public class Hospital {
	private PatientModel pModel;
	private MedcineModel mModel;
	
	public Hospital() {
		this.pModel = new PatientModel();
		this.mModel = new MedcineModel(); 
	}
	
	/*
	public PatientInfo[] queryPatient(){
		try
	    {
	      ResultSet rs = (ResultSet)this.pModel.search(null);
	      Vector<PatientInfo> v = new Vector();
	      while (rs.next())
	      {
	    	  //涓嬮潰杩欎釜閫昏緫杩樻病鏈夌悊娓�
	        PatientInfo temp = new PatientInfo(rs.getString("id"), ((PatientInfo) rs).getUserInfo(), rs.getInt("register"), rs.getInt("age"), rs.getString("gender"), 
	        		((PatientInfo)rs).getUnpaidMedcine(), rs.getString("Classify"));
	        v.add(temp);
	      }
	      return (PatientInfo[])v.toArray(new PatientInfo[rs.getRow()]);
	    }
	    catch (SQLException e)
	    {
	      System.out.println("Database exception");
	      e.printStackTrace();
	    }
	    return null;
	}
	*/
	
	  public boolean addPatient(PatientInfo info)
	  {
	    return this.pModel.insert(info);
	  }
	  
	  public boolean addPatient_2(PatientInfo info)
	  {
	    return this.pModel.insertPatient(info);
	  }
	  
	  public boolean addMedcine(MedcineInfo info)
	  {
	    return this.mModel.insert(info);
	  }
	  
	  public boolean deleteMedcine(MedcineInfo info)
	  {
	    return this.mModel.delete(info);
	  }
	  
	  public boolean deletePatient(PatientInfo info)
	  {
	    return this.pModel.delete(info);
	  }
	  
	  public boolean modifyPatient(PatientInfo info)
	  {
	    return this.pModel.modify(info);
	  }
	  
	  public boolean purchase(MedcineInfo info)
	  {
	    return this.mModel.purchase(info);
	  }
	  
	  //鍚庢湡瑕佹敼
	  public boolean pay(PatientInfo info)
	  {
		info.setUnpaidMedcine(null);
	    return this.pModel.pay(info);
	  }
	  
	  public String[] getUserInfo(UserInfo user) {
		  String[] rs = this.pModel.searchUser(user);
		  return rs;
	  }
	  
	  public Object getPatientInfo(PatientInfo info) {
		  String[] rs = this.pModel.search(info);
		  return rs;
	  }

	  public String[] getMedcineInfo(MedcineInfo info) {
		  String[] rs = this.mModel.search(info);
		  return rs;
	  }
	  
	  public String[] searchUnpaidMedcine(PatientInfo info) {
		  return this.pModel.searchUnpaidMedcine(info);
	  }
	  
	  public int register(PatientInfo info) {
		  return this.pModel.searchRegister(info);
	  }

	  public boolean prescribe(String id,String prescription)
	  {
		  return this.pModel.prescribe(id,prescription);
	  }
	  
	  public ResultSet readMHistory(String id)
	  {
		  return (ResultSet) this.pModel.readMHistory(id);
	  }
	  
	  public String[] readDay(String id)
	  {
		  return (String[]) this.pModel.readPrescription(id,1);
	  }
	  
	  public String[] readPrescription(String id)
	  {
		  return (String[]) this.pModel.readPrescription(id,2);
	  }
}
