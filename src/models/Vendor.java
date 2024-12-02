package models;

import java.util.ArrayList;
import java.util.List;

public class Vendor extends User{
	private List<String> accepted_invitations; 
	
    public Vendor(String user_id, String email, String name, String role) {
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
