package vc.common;

import java.io.Serializable;

public class OnlineClassInfo implements Serializable
{
	  private static final long serialVersionUID = 1L;
	  private String id;
	  private String name;
	  private String teacher;
	  private int period;
	  
	  public OnlineClassInfo(String id, String name, String teacher, int period)
	  {
	    this.id = id;
	    this.name = name;
	    this.teacher = teacher;
	    this.period = period;
	  }
	  
	  public String getId()
	  {
	    return this.id;
	  }
	  
	  public void setId(String id)
	  {
	    this.id = id;
	  }
	  
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public void setName(String name)
	  {
	    this.name = name;
	  }
	  
	  public String getTeacher()
	  {
	    return this.teacher;
	  }
	  
	  public void setTeacher(String teacher)
	  {
	    this.teacher = teacher;
	  }
	  
	  public int getPeriod()
	  {
	    return this.period;
	  }
	  
	  public void setPeriod(int period)
	  {
	    this.period = period;
	  }

}
