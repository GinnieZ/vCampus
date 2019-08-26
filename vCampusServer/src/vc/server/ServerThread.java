package vc.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import vc.view.ServerView;

public class ServerThread
  extends Thread
{
  private ServerSocket server;
  private Vector<ClientThread> clients;
  public ServerThread()
  {
    try
    {
      this.server = new ServerSocket(8081);
      ServerView.setTextArea("服务器主线程启动\n监听8081端口");
      System.out.println("Server main thread start.\nListen on port 8081");
      this.clients = new Vector();
      
      start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void run()
  {
    while (!this.server.isClosed()) {
      try
      {
        Socket client = this.server.accept();
        
        ClientThread current = new ClientThread(client, this);
        current.start();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void close()
  {
    if (this.server != null) {
      try
      {
        this.server.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public int getSize()
  {
    return this.clients.size();
  }
  
  public int addClientConnection(ClientThread ct)
  {
    this.clients.add(ct);
    
    return this.clients.size();
  }
  
  public boolean closeClientConnection(ClientThread ct)
  {
    if (this.clients.contains(ct))
    {
      this.clients.remove(ct);
      
      return true;
    }
    return false;
  }
  
  public boolean searchClientConnection(String curUser)
  {
    for (ClientThread ct : this.clients) {
      if ((ct.curUser != null) && (ct.curUser.equals(curUser))) {
        return true;
      }
    }
    return false;
  }
}
