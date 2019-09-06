package vc.common;

import java.io.Serializable;

public class DormLivingInf 
implements Serializable
{

	private String studentID;
	private String region;
	private int unit;
	private String building;
	private int room;
	private int bed;
	private String phone;
	
	public DormLivingInf() {
		this.studentID="";
		this.region="";
		this.unit=0;
		this.building="";
		this.room=0;
		this.bed=0;
		this.phone="";
	}
	
	public DormLivingInf(String id,String myregion,int myunit,String mybuilding,int myroom,int mybed,String myphone) {
		// TODO Auto-generated constructor stub
		this.studentID=id;
		this.region=myregion;
		this.unit=myunit;
		this.building=mybuilding;
		this.room=myroom;
		this.bed=mybed;
		this.phone=myphone;
	}

	public void setStudentID(String myid) {
		this.studentID=myid;
	}
	
	public String getStudentID() {
		return this.studentID;
	}
	
	public void setregion(String myregion) {
		this.region=myregion;
	}
	
	public String getregion() {
		return this.region;
	}
	
	public void setunit(int myunit) {
		this.unit=myunit;
	}
	
	public int getunit() {
		return this.unit;
	}
	
	public void setbuilding(String mybuilding) {
		this.region=mybuilding;
	}
	
	public String getbuilding() {
		return this.building;
	}
	
	public void setroom(int myroom) {
		this.room=myroom;
	}
	
	public int getroom() {
		return this.room;
	}
	
	public void setbed(int mybed) {
		this.bed=mybed;
	}
	
	public int getbed() {
		return this.bed;
	}
	
	public void setphone(String myphone) {
		this.phone=myphone;
	}
	
	public String getphone() {
		return this.phone;
	}
}
