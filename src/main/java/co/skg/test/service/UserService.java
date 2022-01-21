package co.skg.test.service;

import co.skg.test.dao.UserDao;
import co.skg.test.model.User;

public class UserService {

	public static User searchUser(Integer document) {

		return UserDao.searchUser(document);
	}

	public static boolean createUser(User user) {

		return UserDao.createUser(user);
	}
}
