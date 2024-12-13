package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.Response;

public class EventOrganizer extends User {
    
    public EventOrganizer(String userId, String userEmail, String userName, String userPassword, String userRole) {
        super(userId, userEmail, userName, userPassword, userRole);
    }
    
    public static Response<Void> createEvent(String eventName, String eventDate, String eventLocation, String eventDescription, String organizerId) {
		return Event.createEvent(eventName, eventDate, eventLocation, eventDescription, organizerId);
	} 

    public static Response<List<Event>> viewOrganizedEvents(String userId) {
        String query = "SELECT * FROM events WHERE organizer_id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, userId);
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

            return Response.success("Events fetched successfully", events);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching events: " + e.getMessage());
        }
    }

    public static Response<Event> viewOrganizedEventDetails(String eventId) {
        String query = "SELECT * FROM events WHERE event_id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String eventLocation = rs.getString("event_location");
                String eventDescription = rs.getString("event_description");
                String organizerId = rs.getString("organizer_id");

                Event event = new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId);
                return Response.success("Event details fetched successfully", event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching event details: " + e.getMessage());
        }
        return Response.error("Event not found");
    }

    public static Response<List<User>> getInvitedGuests(String eventId) {
        String query = "SELECT u.user_id, u.user_email, u.user_name FROM users u " +
                       "JOIN invitations i ON u.user_id = i.user_id " +
                       "WHERE i.event_id = ? AND i.invitation_role = 'Guest' AND i.invitation_status = 1";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventId);
            ResultSet rs = ps.executeQuery();

            List<User> guests = new ArrayList<>();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userEmail = rs.getString("user_email");
                String userName = rs.getString("user_name");

                guests.add(new User(userId, userEmail, userName, "", "Guest"));
            }

            return Response.success("Guests fetched successfully", guests);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching guests: " + e.getMessage());
        }
    }

    public static Response<List<User>> getInvitedVendors(String eventId) {
        String query = "SELECT u.user_id, u.user_email, u.user_name FROM users u " +
                       "JOIN invitations i ON u.user_id = i.user_id " +
                       "WHERE i.event_id = ? AND i.invitation_role = 'Vendor' AND i.invitation_status = 1";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventId);
            ResultSet rs = ps.executeQuery();

            List<User> vendors = new ArrayList<>();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userEmail = rs.getString("user_email");
                String userName = rs.getString("user_name");

                vendors.add(new User(userId, userEmail, userName, "", "Vendor"));
            }

            return Response.success("Vendors fetched successfully", vendors);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching vendors: " + e.getMessage());
        }
    }
    
    public static Response<List<User>> getUninvitedGuests(String eventId) {
        String query = "SELECT u.user_id, u.user_email, u.user_name " +
                       "FROM users u " +
                       "WHERE u.user_role = 'Guest' " +
                       "AND NOT EXISTS (" +
                       "    SELECT 1 FROM invitations i " +
                       "    WHERE i.user_id = u.user_id " +
                       "    AND i.event_id = ? " +
                       ")";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventId);
            ResultSet rs = ps.executeQuery();

            List<User> guests = new ArrayList<>();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userEmail = rs.getString("user_email");
                String userName = rs.getString("user_name");

                guests.add(new User(userId, userEmail, userName, "", "Guest"));
            }

            return Response.success("Uninvited guests fetched successfully", guests);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching uninvited guests: " + e.getMessage());
        }
    }

    public static Response<List<User>> getUninvitedVendors(String eventId) {
        String query = "SELECT u.user_id, u.user_email, u.user_name " +
                       "FROM users u " +
                       "WHERE u.user_role = 'Vendor' " +
                       "AND NOT EXISTS (" +
                       "    SELECT 1 FROM invitations i " +
                       "    WHERE i.user_id = u.user_id " +
                       "    AND i.event_id = ? " +
                       ")";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventId);
            ResultSet rs = ps.executeQuery();

            List<User> vendors = new ArrayList<>();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String userEmail = rs.getString("user_email");
                String userName = rs.getString("user_name");

                vendors.add(new User(userId, userEmail, userName, "", "Vendor"));
            }

            return Response.success("Uninvited vendors fetched successfully", vendors);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching uninvited vendors: " + e.getMessage());
        }
    }

    

    public static Response<Void> checkCreateEventInput(String eventName, LocalDate eventDate, String eventLocation, String eventDescription) {
        if (eventName.isEmpty()) {
            return Response.error("Event name must be filled");
        }
        if (eventDate == null) {
            return Response.error("Event date must be filled");
        }
        if (eventLocation.isEmpty()) {
            return Response.error("Event location must be filled");
        }
        if (eventDescription.isEmpty()) {
            return Response.error("Event description must be filled");
        }
        if (eventLocation.length() < 5) {
            return Response.error("Event location must be at least 5 characters");
        }
        if (eventDescription.length() > 200) { 
            return Response.error("Event description cannot be more than 200 characters");
        }
        if (eventDate.isBefore(LocalDate.now()) || eventDate.isEqual(LocalDate.now())) {
            return Response.error("Event date must be in the future");
        }

        return Response.success("Event input validated", null);
    }

    public static Response<Void> editEventName(String eventId, String eventName) {
        String query = "UPDATE events SET event_name = ? WHERE event_id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, eventName);
            ps.setString(2, eventId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return Response.success("Event name updated successfully", null);
            } else {
                return Response.error("Event not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error updating event name: " + e.getMessage());
        }
    }
}
