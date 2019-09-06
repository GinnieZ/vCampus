package vc.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DormRepairInf 
	implements Serializable{

	private String repairID;
	private String studentID;
	private String repairTime;
	private String reason;
	private String state;
	public DormRepairInf(){
		// TODO Auto-generated constructor stub
		repairID = "";
		studentID = "";
//		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String t = "2019-08-30";
//		repairTime = dateFormat.parse(t);
		reason = "";
		state = "δ����";
	}
	
	public DormRepairInf(String repairid,String mytime,String id,String myreason,String mystate){
		setRepairID(repairid);
		setStudentID(id);
		setTime(mytime);
		setReason(myreason);
		setState(mystate);
	}
	public void setRepairID(String id) {
		this.repairID = id;
	}
	
	public void setStudentID(String myid){
		this.studentID = myid;
	}
	
	public void setTime(String mytime){
//		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//		this.repairTime = dateFormat.parse(mytime);
		this.repairTime = mytime;
	}
	
	public void setReason(String myreason) {
		this.reason = myreason;
	}
	
	public void setState(String mystate) {
		this.state = mystate;
	}
	public String getRepairID() {
		return this.repairID;
	}
	public String getStudentID() {
		return this.studentID;
	}
	
	public String getTime() {
//		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
//		String date = formatter.format(this.repairTime);//格式化数据，取当前时间结果为 2014-10-30
		return this.repairTime;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public String getState() {
		return this.state;
	}
}
