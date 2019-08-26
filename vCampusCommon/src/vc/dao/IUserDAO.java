package vc.dao;

import java.util.List;

import vc.vo.User;

/**
 * 实现对于数据库的访问：CRUD.
 * @author lenovo
 *
 */
public interface IUserDAO {

	public User add(User user);
	public User delete(User user);
	public User update(User user);
	public User findByID(String uid);
	public List<User> findAll();
}
