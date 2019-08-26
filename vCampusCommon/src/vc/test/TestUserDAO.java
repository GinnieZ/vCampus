package vc.test;

import org.junit.Assert;
import org.junit.Test;

import vc.dao.IUserDAO;
import vc.dao.impl.IUserDAOImpl;
import vc.vo.User;


public class TestUserDAO {
	@Test
	public void testAdd() {
		IUserDAO iud = new IUserDAOImpl();
		User user = new User("ss", "dd", "fff");
		user = iud.add(user);
		Assert.assertEquals("ss", user.getUid());
	}
}
