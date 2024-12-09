package controllers;

import java.util.List;

import models.Admin;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;
import utils.Response;

public class AdminController {

    // View all events
    public static Response<List<Event>> viewAllEvents() {
        return Admin.viewAllEvents();
    }

    // View event details by eventId
    public static Response<Event> viewEventDetails(String eventId) {
        return Admin.viewEventDetails(eventId);
    }

    // Delete a specific event by eventId
    public static Response<Void> deleteEvent(String eventId) {
        return Admin.deleteEvent(eventId);
    }

    // Delete a specific user by userId
    public static Response<Void> deleteUser(String userId) {
        return Admin.deleteUser(userId);
    }

    // Get all users
    public static Response<List<User>> getAllUsers() {
        return Admin.getAllUsers();
    }

    // Get all events
    public static Response<List<Event>> getAllEvents() {
        return Admin.getAllEvents();
    }

    // Get guests by eventId
    public static Response<List<Guest>> getGuestsByTransaction(String eventId) {
        return Admin.getGuestsByTransaction(eventId);
    }

    // Get vendors by eventId
    public static Response<List<Vendor>> getVendorsByTransaction(String eventId) {
        return Admin.getVendorsByTransaction(eventId);
    }
}
