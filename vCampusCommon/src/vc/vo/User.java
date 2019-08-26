package vc.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private String uid;
	private String userName;
	private String password;
	private String address;
	
	public 	User() {
		
	}
	
	
	public User(String uid, String userName, String password) {
		this.uid = uid;
		this.userName = userName;
		this.password = password;
	}


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
