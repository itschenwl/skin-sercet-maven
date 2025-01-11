package peng.dao;

import peng.javabean.User;

public interface UserDao<T>{
	void insert(User user) throws Exception;
	
	User validate(String username, String password) throws Exception;

	void update(User user) throws Exception;

	void IntroUpdate(User user) throws Exception;
	
}
