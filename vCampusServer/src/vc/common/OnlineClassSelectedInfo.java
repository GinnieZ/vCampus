package vc.common;

import java.io.Serializable;

public class OnlineClassSelectedInfo 
implements Serializable
{
private static final long serialVersionUID = 2L;
private String id;
private String selector;
private int currentPeriod;

public OnlineClassSelectedInfo(String i, String selector,int currentPeriod)
{
  this.id = i;
  this.selector = selector;
  this.currentPeriod = currentPeriod;
}

public String getId()
{
  return this.id;
}

public void setId(String id)
{
  this.id = id;
}

public int getCurrentPeriod()
{
  return this.currentPeriod;
}

public void setCurrentPeriod(int currentPeriod)
{
  this.currentPeriod = currentPeriod;
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
