package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.Connect;

public class Vendor extends User{
	private List<String> accepted_invitations; 
	
	public Vendor(String user_id, String email, String name, String role) {
        super(user_id, email, name, role);
        this.accepted_invitations = new ArrayList<>();
    }
	
	public Vendor getVendorFromDatabase(String email) throws SQLException {
        
        String query = "SELECT * FROM users WHERE user_email = ?";
        var ps = Connect.getInstance().preparedStatement(query);
        ps.setString(1, email);

        var rs = ps.executeQuery();
        if (rs.next()) {
            String userId = rs.getString("user_id");
            String name = rs.getString("user_name");
            String role = rs.getString("user_role");

            return new Vendor(userId, email, name, role);
        }

        return null; 
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
