package vc.common;

import java.io.Serializable;

public class CourseSelectedInfo
  implements Serializable
{
  private static final long serialVersionUID = 2L;
  private String id;
  private String selector;
  
  public CourseSelectedInfo(String i, String selector)
  {
    this.id = i;
    this.selector = selector;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getSelector()
  {
    return this.selector;
  }
  
  public void setSelector(String selector)
  {
    this.selector = selector;
  }
}
