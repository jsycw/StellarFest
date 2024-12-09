package views;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Response;

public class ChangeProfileView {

    private VBox root;
    private Label titleLabel;
    private TextField emailField, nameField;
    private PasswordField oldPasswordField, newPasswordField;
    private Button updateButton, backButton;
    private Scene scene;

    public void init() {
        root = new VBox(15);
        titleLabel = new Label("Change Profile");

        emailField = new TextField();
        nameField = new TextField();
        oldPasswordField = new PasswordField();
        newPasswordField = new PasswordField();

        updateButton = new Button("Update Profile");
        backButton = new Button("Back");

        emailField.setPromptText("Enter new email");
        nameField.setPromptText("Enter new name");
        oldPasswordField.setPromptText("Enter old password");
        newPasswordField.setPromptText("Enter new password");
    }

    public void layout() {
        root.getChildren().addAll(
                titleLabel,
                new Label("Email:"), emailField,
                new Label("Name:"), nameField,
                new Label("Old Password:"), oldPasswordField,
                new Label("New Password:"), newPasswordField,
                updateButton, backButton
        );
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    }

    public void setEventHandlers(Stage stage) {
        updateButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String name = nameField.getText().trim();
            String oldPassword = oldPasswordField.getText();
            String newPassword = newPasswordField.getText();

            Response<Void> response = UserController.changeProfile(email, name, oldPassword, newPassword);

            if (response.isSuccess()) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!", ButtonType.OK);
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, response.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }
        });

        backButton.setOnAction(e -> {
            String userRole = UserController.getAuthenticatedUserRole();

            if (userRole == null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Unable to determine user role. Please log in again.", ButtonType.OK);
                errorAlert.showAndWait();
                LoginView.display(stage); 
                return;
            }

            switch (userRole) {
            	case "Admin":
            		AdminHomeView.display(stage, UserController.getAuthenticatedUserId());
            		break;
                case "Event Organizer":
                    EventOrganizerHomeView.display(stage);
                    break;
                case "Vendor":
                    VendorHomeView.display(stage, UserController.getAuthenticatedUserId());
                    break;
                case "Guest":
                    GuestHomeView.display(stage, UserController.getAuthenticatedUserId());
                    break;
                default:
                    Alert unknownRoleAlert = new Alert(Alert.AlertType.ERROR, "Unknown role: " + userRole, ButtonType.OK);
                    unknownRoleAlert.showAndWait();
                    LoginView.display(stage); 
                    break;
            }
        });

    }

    public static void display(Stage stage) {
        ChangeProfileView view = new ChangeProfileView();
        view.init();
        view.layout();
        view.setEventHandlers(stage);

        view.scene = new Scene(view.root, 400, 400);
        stage.setScene(view.scene);
        stage.setTitle("Change Profile");
        stage.show();
    }
}
