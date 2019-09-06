package vc.helper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHelper
{
  private String StuId;
  private Socket socket;
  private ObjectOutputStream os;
  private ObjectInputStream is;
  private DataOutputStream dos;
  private DataInputStream dis;
  
  public void socketClose()
  {
    try
    {
      this.socket.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void getConnection()
  {
    try
    {
      this.socket = new Socket("localhost", 8081);
      
      this.os = new ObjectOutputStream(this.socket.getOutputStream());
      this.is = new ObjectInputStream(this.socket.getInputStream());
      this.dos = new DataOutputStream(this.socket.getOutputStream());
      this.dis = new DataInputStream(this.socket.getInputStream());
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public ObjectOutputStream getOs()
  {
    return this.os;
  }
  
  public void setOs(ObjectOutputStream os)
  {
    this.os = os;
  }
  
  public DataInputStream getDis()
  {
    return this.dis;
  }
  
  public void setDis(DataInputStream dis)
  {
    this.dis = dis;
  }
  
  public DataOutputStream getDos()
  {
    return this.dos;
  }
  
  public void setOs(DataOutputStream dos)
  {
    this.dos = dos;
  }
  
  public ObjectInputStream getIs()
  {
    return this.is;
  }
  
  public void setIs(ObjectInputStream is)
  {
    this.is = is;
  }
  
  public Socket getSocket()
  {
    return this.socket;
  }
  
  public void setSocket(Socket socket)
  {
    this.socket = socket;
  }
  
  public String getStuId()
  {
    return this.StuId;
  }
  
  public void setStuId(String stuId)
  {
    this.StuId = stuId;
  }
}
