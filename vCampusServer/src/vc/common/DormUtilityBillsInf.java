package vc.common;

import java.io.Serializable;

public class DormUtilityBillsInf 
	implements Serializable{
	
	private String billsID;
	private String studentID;
	private String billsTime;
	private int utilityBills;
	public DormUtilityBillsInf() {
		// TODO Auto-generated constructor stub
		billsID = "";
		studentID = "";
		billsTime = "";
		utilityBills = 0;
	}

	public DormUtilityBillsInf(String billsid,String id,String mytime,int bills) {
		setBillsID(billsid);
		setStudentID(id);
		setBillsTime(mytime);
		setUtilityBills(bills);
	}
	
	public void setBillsID(String id) {
		this.billsID = id;
	}
	
	public void setStudentID(String id) {
		this.studentID = id;
	}
	
	public void setBillsTime(String mytime) {
		this.billsTime = mytime;
	}
	
	public void setUtilityBills(int bills) {
		this.utilityBills = bills;
	}
	
	public String getBillsID() {
		return this.billsID;
	}
	public String getStudentID() {
		return this.studentID;
	}
	
	public String getBillsTime() {
		return this.billsTime;
	}
	
	public int getUtilityBills() {
		return this.utilityBills;
	}
}
