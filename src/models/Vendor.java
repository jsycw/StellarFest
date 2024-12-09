package models;

import utils.Connect;
import utils.Response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Vendor extends User {
    private final static Connect db = Connect.getInstance();

    public Vendor(String userId, String userEmail, String username, String userPassword) {
        super(userId, userEmail, username, userPassword, "Vendor");
    }

    public Response<Void> acceptEventInvitation(String eventId) {
        String query = "UPDATE invitations SET invitation_status = 1 WHERE user_id = ? AND event_id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, this.getUserId());
            ps.setString(2, eventId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return Response.success("Invitation accepted successfully.", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Failed to accept invitation: " + e.getMessage());
        }
        return Response.error("Failed to accept invitation. No matching record found.");
    }

    public static Response<List<Event>> fetchAcceptedEvents(String email) {
        String query = "SELECT e.event_id, e.event_name, e.event_date, e.event_location, e.event_description, e.organizer_id " +
                "FROM events e JOIN invitations i ON e.event_id = i.event_id " +
                "JOIN users u ON u.user_id = i.user_id " +
                "WHERE u.user_email = ? AND i.invitation_role = 'Vendor' AND i.invitation_status = 1";

        List<Event> events = new ArrayList<>();
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

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

        return Response.success("Fetched accepted events successfully", events);
    }

    public Response<Void> manageVendorProduct(String productDescription, String productName) {
        String query = "INSERT INTO vendors (vendor_id, product_name, product_description) " +
                "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE " +
                "product_name = VALUES(product_name), " +
                "product_description = VALUES(product_description)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, this.getUserId());
            ps.setString(2, productName);
            ps.setString(3, productDescription);
            ps.executeUpdate();
            return Response.success("Product management updated successfully.", null);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Failed to manage vendor product: " + e.getMessage());
        }
    }

    public static Response<VendorProduct> getVendorProductDetails(String vendorId) {
        String query = "SELECT product_name, product_description FROM vendors WHERE vendor_id = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setString(1, vendorId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String productName = rs.getString("product_name");
                String productDescription = rs.getString("product_description");
                return Response.success("Product details fetched successfully", new VendorProduct(productName, productDescription));
            }
            return Response.success("No product found for this vendor.", null);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.error("Error fetching product details: " + e.getMessage());
        }
    }

    public static Response<Void> validateVendorProductInput(String description, String productName) {
        if (description == null || description.isEmpty()) {
            return Response.error("Product description must be filled.");
        }
        if (productName == null || productName.isEmpty()) {
            return Response.error("Product name must be filled.");
        }
        if (description.length() > 200) {
            return Response.error("Product description must be 200 characters or less.");
        }
        return Response.success("Input validation successful.", null);
    }
}
