package views;

import controllers.EventOrganizerController;
import controllers.InvitationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import utils.Response;

import java.util.List;

public class GuestListView {
    private VBox root;
    private Label titleLabel;
    private ListView<String> guestListView;
    private TextField guestEmailField;
    private Button addGuestButton, backButton;
    private Scene scene;

    public void init() {
        root = new VBox(20);
        titleLabel = new Label("Guests");
        guestListView = new ListView<>();
        guestEmailField = new TextField();
        guestEmailField.setPromptText("Enter guest email");
        addGuestButton = new Button("Add Guest");
        backButton = new Button("Back");
    }

    public void layout() {
        root.getChildren().addAll(titleLabel, guestListView, guestEmailField, addGuestButton, backButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(15);

        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        guestListView.setPrefHeight(200);
    }

    public void populateGuests(List<User> guests) {
        guestListView.getItems().clear();
        for (User guest : guests) {
            guestListView.getItems().add(guest.getUsername() + " (" + guest.getEmail() + ")");
        }
    }

    public void setEventHandlers(Stage stage, String eventId, String userId) {
        backButton.setOnAction(e -> EventDetailsView.display(stage, eventId, userId));

        addGuestButton.setOnAction(e -> {
            String guestEmail = guestEmailField.getText().trim();
            if (guestEmail.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Guest email must not be empty!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            Response<Void> response = InvitationController.sendInvitation(guestEmail, eventId);
            if (response.isSuccess()) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Invitation sent successfully!", ButtonType.OK);
                successAlert.showAndWait();

                Response<List<User>> updatedGuestsResponse = EventOrganizerController.getGuests(eventId);
                if (updatedGuestsResponse.isSuccess()) {
                    populateGuests(updatedGuestsResponse.getData());
                } else {
                    Alert refreshErrorAlert = new Alert(Alert.AlertType.ERROR, "Failed to refresh guest list.", ButtonType.OK);
                    refreshErrorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, response.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }

            guestEmailField.clear();
        });
    }

    public static void display(Stage stage, List<User> guests, String eventId, String userId) {
        GuestListView view = new GuestListView();
        view.init();
        view.layout();
        view.populateGuests(guests);
        view.setEventHandlers(stage, eventId, userId);

        view.scene = new Scene(view.root, 400, 400);
        stage.setScene(view.scene);
        stage.setTitle("Guest List");
        stage.show();
    }
}
