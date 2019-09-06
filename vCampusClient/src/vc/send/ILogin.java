package vc.send;

import vc.common.UserInfo;

public abstract interface ILogin
{
  public abstract boolean Login(UserInfo paramUserInfo);
  public abstract boolean LogOut(UserInfo paramUserInfo);
}
