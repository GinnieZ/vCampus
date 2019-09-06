package vc.common;

import java.io.Serializable;

public class DormChargeInf 
	implements Serializable
{
	private String chargeID;
	private String studentID;
	private String chargeTime;
	private int chargeMoney;
	private int arrears;
	private int utilityBills;
	private int balance;
	
	public DormChargeInf() {
		chargeID = "";
		chargeTime = "";
		studentID = "";
		chargeMoney = 0;
		arrears = 0;
		utilityBills = 0;
		balance = 0;
	}
	public DormChargeInf(String chargeid,String chargetime,String studentid,int money,int arr,int bill,int bal) {
		// TODO Auto-generated constructor stub
		setChargeID(chargeid);
		setChargeTime(chargetime);
		setStudentID(studentid);
		setChargeMoney(money);
		setArrears(arr);
		setUtilityBills(bill);
		setBalance(bal);
	}

	private void setBalance(int bal) {
		// TODO Auto-generated method stub
		this.balance = bal;
	}
	public void setChargeID(String id) {
		this.chargeID = id;
	}
	public void setStudentID(String stuid) {
		this.studentID = stuid;
	}
	
	public void setChargeTime(String time) {
		this.chargeTime = time;
	}
	
	public void setChargeMoney(int money) {
		this.chargeMoney = money;
	}
	
	public void setArrears(int ars) {
		this.arrears = ars;
	}
	
	public void setUtilityBills(int bill) {
		this.utilityBills = bill;
	}
	
	public String getChargeID() {
		return this.chargeID;
	}
	public String getStudentID() {
		return this.studentID;
	}
	
	public String getChargeTime() {
		return this.chargeTime;
	}
	
	public int getChargeMoney() {
		return this.chargeMoney;
	}
	
	public int getArreas() {
		return this.arrears;
	}
	
	public int getUtilityBills() {
		return this.utilityBills;
	}
	
	public int getBalance() {
		return this.balance;
	}
}
