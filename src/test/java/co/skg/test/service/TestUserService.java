package co.skg.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import co.skg.test.model.User;

public class TestUserService {
	
	@Test
	public void testCreateUser() {

		User user = new User(999, "skgUser");
		assertTrue(UserService.createUser(user));
	}

	@Test
	public void testSearchUser() {

		User user = new User(999, "skgUser");
		assertEquals(user, UserService.searchUser(user.getDocument()));
	}

}
