package utils;

import models.User;

public class AuthUser {
	private static User user;
	
	private AuthUser() {};
	
	public static User get() {
		return user;
	}
	
	public static void set(User u) {
		user = u;
	}
	
	public static void clear() {
		user = null;
	}
}
