package vc.common;

import java.io.Serializable;

public class MedcineInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String usage;
	private String num;
	private String instruction;
	
	public MedcineInfo(String id, String name, String usage, String num, String instruction) {
		this.id = id;
		this.name = name;
		this.usage = usage;
		this.num = num;
		this.instruction = instruction;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNum() {
		return this.num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public void SetUsage(String usage) {
		this.usage = usage;
	}
	
	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
}
