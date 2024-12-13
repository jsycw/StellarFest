package views.guest;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Event;

public class GuestEventDetailsView {

    private VBox root;
    private Label nameLabel, dateLabel, locationLabel, descriptionLabel;
    private Button backButton;
    private Scene scene;

    public void init(Event event) {
        root = new VBox(20);

        nameLabel = new Label("Event Name: " + event.getEventName());
        dateLabel = new Label("Event Date: " + event.getEventDate());
        locationLabel = new Label("Location: " + event.getEventLocation());
        descriptionLabel = new Label("Description: " + event.getEventDescription());

        backButton = new Button("Back");

        root.getChildren().addAll(nameLabel, dateLabel, locationLabel, descriptionLabel, backButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
    }

    public void layout() {
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        dateLabel.setStyle("-fx-font-size: 14px;");
        locationLabel.setStyle("-fx-font-size: 14px;");
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        root.setSpacing(15);
    }

    public void setEventHandlers(Stage stage, Event event) {
        backButton.setOnAction(e -> GuestEventView.display(stage));
    }

    public static void display(Event event) {
        Stage stage = new Stage();
        GuestEventDetailsView view = new GuestEventDetailsView();
        view.init(event);
        view.layout();
        view.setEventHandlers(stage, event);

        view.scene = new Scene(view.root, 400, 300);
        stage.setScene(view.scene);
        stage.setTitle("Event Details");
        stage.show();
    }
}
