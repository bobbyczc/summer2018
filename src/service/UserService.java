package service;

import dao.UserDao;
import model.User;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		this.userDao = new UserDao();
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return -1表示数据库没数据 0表示密码错误 1表示正确
	 */
	public int login(String username, String password) {
		User user = userDao.getUserByname(username);
		if(user==null) {
			return -1;
		}else if(!user.getPassword().equals(password)) {
			return 0;
		}else {
			return user.getId();
		}
	}
	
	public int register(User user) {
		return userDao.register(user);
	}
	
	public User getUserById(int id) {
		return userDao.getUserById(id);
	}
	
	public boolean checkPsw(int userid, String password) {
		return userDao.checkPsw(userid, password);
	}
	
	public int changePsw(int userid, String newpsw) {
		return userDao.changePsw(userid, newpsw);
	}
}
