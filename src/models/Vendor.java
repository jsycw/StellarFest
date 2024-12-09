package models;

import utils.Connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vendor extends User {
    private List<String> acceptedInvitations;

    public Vendor(String userId, String email, String username, String password, String role) {
        super(userId, email, username, password, role);
        this.acceptedInvitations = new ArrayList<>();
    }

    // Accept an invitation for an event
    public boolean acceptInvitation(String eventId) throws SQLException {
        String query = "UPDATE invitations SET invitation_status = 1 WHERE user_id = ? AND event_id = ?";
        PreparedStatement ps = Connect.getInstance().preparedStatement(query);
        ps.setString(1, this.getUserId());
        ps.setString(2, eventId);

        int rowsUpdated = ps.executeUpdate();
        return rowsUpdated > 0; // Returns true if the invitation was successfully accepted
    }

    // View all events that the vendor has accepted
    public List<Event> viewAcceptedEvents() throws SQLException {
        String query = String.format(
            "SELECT e.event_id, e.event_name, e.event_date, e.event_location, e.event_description, e.organizer_id " +
            "FROM events e " +
            "JOIN invitations i ON e.event_id = i.event_id " +
            "WHERE i.user_id = '%s' AND i.invitation_role = 'Vendor' AND i.invitation_status = 1",
            this.getUserId()
        );

        PreparedStatement ps = Connect.getInstance().preparedStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Event> events = new ArrayList<>();
        while (rs.next()) {
            String eventId = rs.getString("event_id");
            String eventName = rs.getString("event_name");
            String eventDate = rs.getString("event_date");
            String eventLocation = rs.getString("event_location");
            String eventDescription = rs.getString("event_description");
            String organizerId = rs.getString("organizer_id");

            events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
        }
        return events;
    }

    // Manage vendor products (Add or Update)
    public boolean manageVendor(String productName, String productDescription) throws SQLException {
        if (productName.isEmpty() || productDescription.isEmpty() || productDescription.length() > 200) {
            return false; // Validation failed
        }

        String query = String.format(
            "INSERT INTO vendors (vendor_id, product_name, product_description) " +
            "VALUES ('%s', '%s', '%s') " +
            "ON DUPLICATE KEY UPDATE " +
            "product_name = VALUES(product_name), " +
            "product_description = VALUES(product_description)",
            this.getUserId(), productName, productDescription
        );

        PreparedStatement ps = Connect.getInstance().preparedStatement(query);
        ps.executeUpdate();
        return true; // Successfully managed vendor product
    }
}