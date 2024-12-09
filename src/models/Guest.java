package models;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User{
	private String acceptedInvitations;
	
	public Guest(String userId, String email, String username, String password, String role) {
		super(userId, email, username, password, "Guest");
		// TODO Auto-generated constructor stub
	}

	
    

}
