package models;

import utils.Connect;
import utils.Response;
import java.sql.*;

public class Event {
    private String eventId;
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private String eventDescription;
    private String organizerId;

    private static Connect db = Connect.getInstance();
    
    public Event(String eventId, String eventName, String eventDate, String eventLocation, String eventDescription,
			String organizerId) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
		this.eventDescription = eventDescription;
		this.organizerId = organizerId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(String organizerId) {
		this.organizerId = organizerId;
	}

	public static Connect getDb() {
		return db;
	}

	public static void setDb(Connect db) {
		Event.db = db;
	}
    

}