package vc.srv;

import vc.vo.User;

/**
 * 客户端用户服务类。
 * @author lenovo
 *
 */
public interface IClientUserSrv {

	public User login(String username, String password);
}
