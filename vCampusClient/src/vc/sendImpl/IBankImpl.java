package vc.sendImpl;
/** “¯––
* @author 09017406
* ø‚ƒÀ°§∞¢’®Ã‚
*/

import vc.common.BankInfo;
import vc.common.MsgType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import vc.helper.SocketHelper;
import vc.send.IBank;

public class IBankImpl implements IBank, MsgType {
	ObjectInputStream is;
	ObjectOutputStream os;
	String Sid ;
	
	public IBankImpl(SocketHelper sockethelper,String STUid)
	  {
	    this.is = sockethelper.getIs();
	    this.os = sockethelper.getOs();
	    this.Sid=STUid;
	  }
	  
	 public double checkAccount(String bCad)
	  {
	    try
	    {
	      this.os.writeInt(201);
	      this.os.flush();
	      System.out.println("IBankImpl " + bCad);
	      BankInfo Banktemp = new BankInfo(bCad, 0.0D, "", "", 0.0D);
	      
	      this.os.writeObject(Banktemp);
	      this.os.flush();
	      try
	      {
	        if (this.is.readInt() == 2011) {
	          return this.is.readDouble();
	        }
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      return 0.0D;
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return 0.0D;
	  }
	  
	  public boolean transferAccount(String Bid,double money, String receiver, String pwd, double banlance)
	  {
	    try
	    {
	      this.os.writeInt(202);
	      this.os.flush();
	      //String id = Bid;
	      BankInfo Banktemp = new BankInfo(Bid, banlance, pwd, receiver, money);
	      Banktemp.setTransferAmount(money);
	      Banktemp.setTransferTo(receiver);
	      Banktemp.setPwd(pwd);
	      Banktemp.setBalance(banlance);
	      
	      this.os.writeObject(Banktemp);
	      this.os.flush();
	      if (this.is.readInt() == 2021) {
	        return true;
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }

}
