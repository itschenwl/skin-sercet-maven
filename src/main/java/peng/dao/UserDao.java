package peng.dao;

import peng.javabean.User;

public interface UserDao {
	 void insert(User user) throws Exception;
	public User validate(String username, String password) throws Exception; 
}
