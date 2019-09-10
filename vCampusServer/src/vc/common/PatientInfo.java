package vc.common;

import java.io.Serializable;
import java.util.Vector;

public class PatientInfo 
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	//private UserInfo user;
	private String name;
	private int age;
	private String gender;
	//private mHistory mhistory;
	private Vector<String> unpaidMedcine;
	private int register;
	private String classify;
	
	public PatientInfo(String id, String name, int register, int age, String gender, Vector<String> unpaidMedcine, String Classify) {
		this.id = id;
		this.name = name;
		this.register = register;
		this.age = age;
		this.gender = gender;
		this.unpaidMedcine = unpaidMedcine;
		this.classify = classify;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	public UserInfo getUserInfo() {
		return this.user;
	}

	public void setUserInfo(UserInfo user) {
		this.user = user;
	}
	*/
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Vector<String> getUnpaidMedcine() {
		return this.unpaidMedcine;
	}
	
	public String[] getUnpaidMedcine_2() {
		int i = 0;
		System.out.println("getUnpaidMedcine_2");
		String[] s = new String[unpaidMedcine.size()];
		for(i=0;i<unpaidMedcine.size();i++) {
			System.out.println(unpaidMedcine.get(i));
			s[i] = unpaidMedcine.get(i);
		}
		return s;
	}
	
	public void setUnpaidMedcine(Vector<String> unpaidMedcine) {
		this.unpaidMedcine = unpaidMedcine;
	}
	
	public void setUnpaidMedcine_2(String[] unpaidMedcine) {
		if(unpaidMedcine != null) {
			//this.unpaidMedcine.setSize(unpaidMedcine.length);
			this.unpaidMedcine = new Vector();
			for(int i=0;i<unpaidMedcine.length;i++) {
				this.unpaidMedcine.add(unpaidMedcine[i]);
			}
		}
	}
	
	public void addUnpaidMedcine(Vector<String> unpaidMedcine) {
		int i = 0;
		for(i=0;i<unpaidMedcine.size();i++) {
			this.unpaidMedcine.add(unpaidMedcine.get(i));
		}
	}
	
	public void addUnpaidMedcine_2(String[] unpaidMedcine) {
		int i = 0;
		for(i=0;i<unpaidMedcine.length;i++) {
			this.unpaidMedcine.add(unpaidMedcine[i]);
		}
	}
	
	public void addUnpaidMedcine_3(String unpaidMedcine) {
		this.unpaidMedcine.add(unpaidMedcine);
	}
	
	public int getRegister() {
		return this.register;
	}

	public void setRegister(int register) {
		this.register = register;
	}
	
	public String getClassify() {
		return this.classify;
	}
	
	public void setClassify(String classify) {
		this.classify = classify;
	}
}
