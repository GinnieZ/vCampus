package vc.common;

import java.io.Serializable;

public class UserInfo
  implements Serializable
{
  private static final long serialVersionUID = 11L;
  private String stuId;
  private String pwd;
  private String type;
  private String card;
  private String name;
  
  public UserInfo(String id, String p, String t, String c, String n)
  {
    setStuId(id);
    setPwd(p);
    setType(t);
    setCard(c);
    setName(n);
  }
  
  public void setStuId(String param)
  {
    this.stuId = param;
  }
  
  public String getStuId()
  {
    return this.stuId;
  }
  
  public void setPwd(String param)
  {
    this.pwd = param;
  }
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setType(String param)
  {
    this.type = param;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setName(String param)
  {
    this.name = param;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setCard(String param)
  {
    this.card = param;
  }
  
  public String getCard()
  {
    return this.card;
  }
}
