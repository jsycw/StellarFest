package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Response;

public class Guest extends User {
    
    public Guest(String userId, String userEmail, String username, String userPassword) {
        super(userId, userEmail, username, userPassword, "Guest");
    }

    public Response<Void> acceptInvitation(String eventId) {
        return Invitation.acceptInvitation(this.userId, eventId);
    }

    public static Response<List<Event>> viewAcceptedEvents(String email) {
        // SQL Query to get accepted events
        String query = String.format(
            "SELECT e.event_id, e.event_name, e.event_date, e.event_location, e.event_description, e.organizer_id " +
            "FROM events e " +
            "JOIN invitations i ON e.event_id = i.event_id " +
            "JOIN users u ON u.user_id = i.user_id " +
            "WHERE u.user_email = '%s' AND i.invitation_role = 'Guest' AND i.invitation_status = 1", email);

        ResultSet rs = db.execQuery(query);
        ArrayList<Event> events = new ArrayList<>();
        
        if (rs == null) {
            return Response.error("Error fetching accepted events data");
        }
        
        try {
            while (rs.next()) {
                String eventId = rs.getString("event_id");
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String eventLocation = rs.getString("event_location");
                String eventDescription = rs.getString("event_description");
                String organizerId = rs.getString("organizer_id");
                
                events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching accepted events: " + e.getMessage());
        }
        
        return Response.success("Fetch accepted events success", events);
    }
}
