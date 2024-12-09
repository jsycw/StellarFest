package views;

import controllers.EventOrganizerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Event;
import models.User;
import utils.Response;

import java.util.List;

public class EventDetailsView {
    private VBox root;
    private Label titleLabel, eventNameLabel, eventDateLabel, eventLocationLabel, eventDescriptionLabel;
    private Button viewVendorsButton, viewGuestsButton, backButton;
    private Scene scene;

    public void init() {
        root = new VBox(20);

        titleLabel = new Label("Event Details");
        eventNameLabel = new Label();
        eventDateLabel = new Label();
        eventLocationLabel = new Label();
        eventDescriptionLabel = new Label();

        viewVendorsButton = new Button("View Vendors");
        viewGuestsButton = new Button("View Guests");
        backButton = new Button("Back");
    }

    public void layout() {
        root.getChildren().addAll(
                titleLabel,
                eventNameLabel,
                eventDateLabel,
                eventLocationLabel,
                eventDescriptionLabel,
                viewVendorsButton,
                viewGuestsButton,
                backButton
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(15);

        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    }

    public void setEventHandlers(Stage stage, String eventId, String userId) {
        Response<Event> eventResponse = EventOrganizerController.viewOrganizedEventDetails(eventId);

        if (eventResponse.isSuccess()) {
            Event event = eventResponse.getData();
            eventNameLabel.setText("Event Name: " + event.getEventName());
            eventDateLabel.setText("Event Date: " + event.getEventDate());
            eventLocationLabel.setText("Event Location: " + event.getEventLocation());
            eventDescriptionLabel.setText("Event Description: " + event.getEventDescription());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, eventResponse.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        viewVendorsButton.setOnAction(e -> {
            Response<List<User>> vendorsResponse = EventOrganizerController.getVendors(eventId);
            if (vendorsResponse.isSuccess()) {
                List<User> vendors = vendorsResponse.getData();
                VendorListView.display(stage, vendors, eventId, userId);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, vendorsResponse.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });

        viewGuestsButton.setOnAction(e -> {
            Response<List<User>> guestsResponse = EventOrganizerController.getGuests(eventId);
            if (guestsResponse.isSuccess()) {
                List<User> guests = guestsResponse.getData();
                GuestListView.display(stage, guests, eventId, userId);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, guestsResponse.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });

        backButton.setOnAction(e -> EventView.display(stage, userId));
    }

    public static void display(Stage stage, String eventId, String userId) {
        EventDetailsView view = new EventDetailsView();
        view.init();
        view.layout();
        view.setEventHandlers(stage, eventId, userId);

        view.scene = new Scene(view.root, 400, 400);
        stage.setScene(view.scene);
        stage.setTitle("Event Details");
        stage.show();
    }
}
