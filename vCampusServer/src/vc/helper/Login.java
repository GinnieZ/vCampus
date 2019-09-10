package vc.helper;

import vc.common.StudentRollInfo;
import vc.common.UserInfo;
import vc.db.LoginModel;
//import vc.db.StudentRollModel;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import vc.db.LoginModel;
public class Login
{
  private LoginModel model;
  
  public Login()
  {
    this.model = new LoginModel();
 //   this.srm = new StudentRollModel();
  }
  
  public boolean login(UserInfo info)
  {
    try
    {
      ResultSet rs = (ResultSet)this.model.search(info);
      if (rs.next()) {
        return (info.getPwd().equals(rs.getString("u_Pwd"))) && (info.getType().equals(rs.getString("u_Type")));
      }
      return false;
    }
    catch (SQLException e)
    {
      System.out.println("Database exception");
      e.printStackTrace();
    }
    return false;
  }

  public boolean register(UserInfo info)
  {
	  // 注册功能尚未开发（19/08/21）
//    StudentRollInfo temp = new StudentRollInfo(info.getStuId(), info.getName(), null, null, null, null, null, null, null, null, null, null);
//    try
//    {
//      ResultSet rs = (ResultSet)this.srm.search(temp);
//      if (rs.next()) {
//        return this.model.insert(info);
//      }
//    }
//    catch (SQLException e)
//    {
//      e.printStackTrace();
//    }
//	  StudentRollInfo temp = new StudentRollInfo(info.getStuId(), info.getName(), null, null, null, null, null, null, null, null, null, null);
	  return true;
	  
  }
}
