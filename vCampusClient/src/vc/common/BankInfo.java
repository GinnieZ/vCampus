package vc.common;
/** “¯––
* @author 09017406
* ø‚ƒÀ°§∞¢’®Ã‚
*/

import java.io.Serializable;

public class BankInfo implements Serializable {
	private static final long serialVersionUID = 3L;
	  private String id;
	  private double balance;
	  private String pwd;
	  private String transferTo;
	  private double transferAmount;
	  
	  public BankInfo(String id, double b, String pwd, String to, double am)
	  {
	    setId(id);
	    setBalance(b);
	    setPwd(pwd);
	    setTransferTo(to);
	    setTransferAmount(am);
	  }
	  
	  public void setId(String param)
	  {
	    this.id = param;
	  }
	  
	  public String getId()
	  {
	    return this.id;
	  }
	  
	  public void setBalance(double param)
	  {
	    this.balance = param;
	  }
	  
	  public double getBalance()
	  {
	    return this.balance;
	  }
	  
	  public void setPwd(String param)
	  {
	    this.pwd = param;
	  }
	  
	  public String getPwd()
	  {
	    return this.pwd;
	  }
	  
	  public void setTransferTo(String param)
	  {
	    this.transferTo = param;
	  }
	  
	  public String getTransferTo()
	  {
	    return this.transferTo;
	  }
	  
	  public void setTransferAmount(double param)
	  {
	    this.transferAmount = param;
	  }
	  
	  public double getTransferAmount()
	  {
	    return this.transferAmount;
	  }

}
