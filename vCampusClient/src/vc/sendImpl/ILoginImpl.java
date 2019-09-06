package vc.sendImpl;

import vc.common.MsgType;
import vc.common.UserInfo;
import vc.send.ILogin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import vc.helper.SocketHelper;

public class ILoginImpl
  implements ILogin, MsgType
{
  ObjectInputStream is;
  ObjectOutputStream os;
  
  public ILoginImpl(SocketHelper sockethelper)
  {
    this.is = sockethelper.getIs();
    this.os = sockethelper.getOs();
  }
  
  public boolean Login(UserInfo user)
  {
    try
    {
      this.os.writeInt(101);
      this.os.flush();
      this.os.writeObject(user);
      this.os.flush();
      if (this.is.readInt() == 1011) {
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean register(UserInfo info)
  {
	 
    try
    {
    	this.os.writeInt(102);
        this.os.flush();
        this.os.writeObject(info);
        this.os.flush();
        if (this.is.readInt() == 1021) {
          return true;
        }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    return false;
	  
  }
  public boolean LogOut(UserInfo user)
  {
    try
    {
      this.os.writeInt(103);
      this.os.flush();
      this.os.writeObject(user);
      this.os.flush();
      if (this.is.readInt() == 1031) {
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
