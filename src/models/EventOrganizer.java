package models;

import java.util.ArrayList;
import java.util.List;

public class EventOrganizer extends User {
    private List<String> events_created; // or could be List<Event> if you want full event details

    public EventOrganizer(String user_id, String email, String name, String role) {
        super(user_id, email, name, role);
        this.events_created = new ArrayList<>();
    }

    public List<String> getEventsCreated() {
        return events_created;
    }

    public void addEventCreated(String eventID) {
        this.events_created.add(eventID);
    }

    public void removeEventCreated(String eventID) {
        this.events_created.remove(eventID);
    }
}
