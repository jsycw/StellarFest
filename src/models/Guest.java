package models;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User{
	private List<String> accepted_invitations; 
	
    public Guest(String user_id, String email, String name, String role) {
        super(user_id, email, name, role);
        this.accepted_invitations = new ArrayList<>();
    }

    public List<String> getAcceptedInvitations() {
        return accepted_invitations;
    }

    public void addAcceptedInvitation(String eventID) {
        this.accepted_invitations.add(eventID);
    }

    public void removeAcceptedInvitation(String eventID) {
        this.accepted_invitations.remove(eventID);
    }

}
