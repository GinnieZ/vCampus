package vc.common;

import java.io.Serializable;

public class DormVisitInf 
	implements Serializable{
	private String visitID;
	private String name;
	private String timeIn;
	private String timeOut;
	private String reason;
	public DormVisitInf() {
		// TODO Auto-generated constructor stub
		visitID = "";
		name = "";
		timeIn = "";
		timeOut ="";
		reason = "";
	}


	public DormVisitInf(String id,String myname,String timein,String timeout,String myreason) {
		setVisitID(id);
		setVisitName(myname);
		setVisitTimeIn(timein);
		setVisitTimeOut(timeout);
		setVisitReason(myreason);
	}
	public void setVisitTimeIn(String time) {
		this.timeIn = time;
	}
	
	public void setVisitTimeOut(String time) {
		this.timeOut = time;
	}
	
	public void setVisitName(String myname) {
		this.name = myname;
	}
	
	public void setVisitID(String visid) {
		this.visitID = visid;
	}
	
	public void setVisitReason(String myreason) {
		this.reason = myreason;
	}
	
	public String getVisitID() {
		return this.visitID;
	}
	
	public String getVisitName() {
		return this.name;
	}
	
	public String getTimeIn() {
		return this.timeIn;
	}
	
	public String getTimeOut() {
		return this.timeOut;
	}
	
	public String getVisitReason() {
		return this.reason;
	}
}
