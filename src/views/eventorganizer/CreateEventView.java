package views.eventorganizer;

import controllers.EventOrganizerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CreateEventView {
    private VBox root;
    private Label titleLabel;
    private VBox eventNameBox, eventDateBox, eventLocationBox, eventDescriptionBox;
    private TextField eventNameField, eventLocationField;
    private DatePicker eventDatePicker;
    private TextArea eventDescriptionField;
    private Button createEventButton, backButton;
    private Scene scene;

    public void init() {
        root = new VBox(20);
        titleLabel = new Label("Create New Event");

        eventNameBox = new VBox(5);
        eventDateBox = new VBox(5);
        eventLocationBox = new VBox(5);
        eventDescriptionBox = new VBox(5);

        eventNameField = new TextField();
        eventDatePicker = new DatePicker();
        eventLocationField = new TextField();
        eventDescriptionField = new TextArea();

        createEventButton = new Button("Create Event");
        backButton = new Button("Back");

        eventNameBox.getChildren().addAll(new Label("Event Name:"), eventNameField);
        eventDateBox.getChildren().addAll(new Label("Event Date:"), eventDatePicker);
        eventLocationBox.getChildren().addAll(new Label("Event Location:"), eventLocationField);
        eventDescriptionBox.getChildren().addAll(new Label("Event Description:"), eventDescriptionField);
    }

    public void layout() {
        root.getChildren().addAll(titleLabel, eventNameBox, eventDateBox, eventLocationBox, eventDescriptionBox,
                createEventButton, backButton);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(15);

        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        eventDescriptionField.setPrefRowCount(5);
        eventDescriptionField.setWrapText(true);
    }

    public void setEventHandlers(Stage stage, String userId) {
        createEventButton.setOnAction(e -> {
            String eventName = eventNameField.getText();
            LocalDate eventDate = eventDatePicker.getValue();
            String eventLocation = eventLocationField.getText();
            String eventDescription = eventDescriptionField.getText();

            if (eventName.isEmpty() || eventLocation.isEmpty() || eventDescription.isEmpty() || eventDate == null) {
                showAlert(Alert.AlertType.ERROR, "All fields must be filled!", "Error");
            } else {
                var response = EventOrganizerController.createEvent(eventName, eventDate, eventLocation, eventDescription, userId);

                if (response.isSuccess()) {
                    showAlert(Alert.AlertType.INFORMATION, "Event created successfully!", "Success");
                    EventView.display(stage, userId);
                } else {
                    showAlert(Alert.AlertType.ERROR, response.getMessage(), "Error");
                }
            }
        });

        backButton.setOnAction(e -> EventView.display(stage, userId));
    }

    private void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void display(Stage stage, String userId) {
        CreateEventView view = new CreateEventView();
        view.init();
        view.layout();
        view.setEventHandlers(stage, userId);

        view.scene = new Scene(view.root, 400, 500);
        stage.setScene(view.scene);
        stage.setTitle("Create Event");
        stage.show();
    }
}
