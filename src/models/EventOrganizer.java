package models;

import java.util.ArrayList;
import java.util.List;

public class EventOrganizer extends User {

	public EventOrganizer(String userId, String email, String username, String password, String role) {
		super(userId, email, username, password, role);
		// TODO Auto-generated constructor stub
	}

	private String eventsCreated;
}
