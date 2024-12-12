package controllers;

import java.util.List;

import models.Admin;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;
import utils.Response;

public class AdminController {

    public static Response<List<Event>> viewAllEvents() {
        return Admin.viewAllEvents();
    }

    public static Response<Event> viewEventDetails(String eventId) {
        return Admin.viewEventDetails(eventId);
    }

    public static Response<Void> deleteEvent(String eventId) {
        return Admin.deleteEvent(eventId);
    }

    public static Response<Void> deleteUser(String userId) {
        return Admin.deleteUser(userId);
    }

    public static Response<List<User>> getAllUsers() {
        return Admin.getAllUsers();
    }

    public static Response<List<Event>> getAllEvents() {
        return Admin.getAllEvents();
    }

    public static Response<List<Guest>> getGuestsByTransaction(String eventId) {
        return Admin.getGuestsByTransaction(eventId);
    }

    public static Response<List<Vendor>> getVendorsByTransaction(String eventId) {
        return Admin.getVendorsByTransaction(eventId);
    }
}
