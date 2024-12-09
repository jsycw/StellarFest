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

public class VendorListView {
    private VBox root;
    private Label titleLabel;
    private ListView<String> vendorListView;
    private TextField vendorEmailField;
    private Button addVendorButton, backButton;
    private Scene scene;

    public void init() {
        root = new VBox(20);
        titleLabel = new Label("Vendors");
        vendorListView = new ListView<>();
        vendorEmailField = new TextField();
        vendorEmailField.setPromptText("Enter vendor email");
        addVendorButton = new Button("Add Vendor");
        backButton = new Button("Back");
    }

    public void layout() {
        root.getChildren().addAll(titleLabel, vendorListView, vendorEmailField, addVendorButton, backButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(15);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        vendorListView.setPrefHeight(200);
    }

    public void populateVendors(List<User> vendors) {
        vendorListView.getItems().clear();
        for (User vendor : vendors) {
            vendorListView.getItems().add(vendor.getUsername() + " (" + vendor.getEmail() + ")");
        }
    }

    public void setEventHandlers(Stage stage, String eventId, String userId) {
        backButton.setOnAction(e -> EventDetailsView.display(stage, eventId, userId));

        addVendorButton.setOnAction(e -> {
            String vendorEmail = vendorEmailField.getText().trim();
            if (vendorEmail.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vendor email must not be empty!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            Response<Void> response = InvitationController.sendInvitation(vendorEmail, eventId);
            if (response.isSuccess()) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Invitation sent successfully!", ButtonType.OK);
                successAlert.showAndWait();

                Response<List<User>> updatedVendorsResponse = EventOrganizerController.getVendors(eventId);
                if (updatedVendorsResponse.isSuccess()) {
                    populateVendors(updatedVendorsResponse.getData());
                } else {
                    Alert refreshErrorAlert = new Alert(Alert.AlertType.ERROR, "Failed to refresh vendor list.", ButtonType.OK);
                    refreshErrorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, response.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }

            vendorEmailField.clear();
        });
    }

    public static void display(Stage stage, List<User> vendors, String eventId, String userId) {
        VendorListView view = new VendorListView();
        view.init();
        view.layout();
        view.populateVendors(vendors);
        view.setEventHandlers(stage, eventId, userId);

        view.scene = new Scene(view.root, 400, 400);
        stage.setScene(view.scene);
        stage.setTitle("Vendor List");
        stage.show();
    }
}
