package vc.sendImpl;

import vc.common.StudentRollInfo;
import vc.common.MsgType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import vc.helper.SocketHelper;
import vc.send.IStudent;

public class IStudentImpl implements IStudent, MsgType
{
  SocketHelper socketHelper = new SocketHelper();
  ObjectInputStream is;
  ObjectOutputStream os;
  
  public IStudentImpl(SocketHelper sockethelper)
  {
    this.is = sockethelper.getIs();
    this.os = sockethelper.getOs();
  }
  
  public List EnquiryStuById (StudentRollInfo stu)
  {
    try
    {
      this.os.writeInt(301);
      this.os.flush();
      this.os.writeObject(stu);
      this.os.flush();
      try
      {
        if (this.is.readInt() == 3011) {
          return Arrays.asList(new StudentRollInfo[] { (StudentRollInfo)this.is.readObject() });
        }
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      return null;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
	return null;
  }
  
  public List EnquiryAllStu(StudentRollInfo stu)
  {
    try
    {
      this.os.writeInt(305);
      this.os.flush();
      try
      {
        if (this.is.readInt() == 3051) {
          return Arrays.asList((StudentRollInfo[])this.is.readObject());
        }
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      return null;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
	return null;
  }
  
  public boolean AddStudentView(StudentRollInfo stu)
  {
    try
    {
      this.os.writeInt(302);
      this.os.flush();
      this.os.writeObject(stu);
      this.os.flush();
      if (this.is.readInt() == 3021) {
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean DeleteStudentView(StudentRollInfo stu)
  {
    try
    {
      this.os.writeInt(303);
      this.os.flush();
      this.os.writeObject(stu);
      this.os.flush();
      if (this.is.readInt() == 3031) {
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean ModifyStudentView(StudentRollInfo stu)
  {
    try
    {
      this.os.writeInt(304);
      this.os.flush();
      this.os.writeObject(stu);
      this.os.flush();
      if (this.is.readInt() == 3041) {
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
