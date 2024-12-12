package views.vendor;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import views.ChangeProfileView;
import views.LoginView;
import views.VendorAndGuest.AccEventView;

public class VendorHomeView {
    private VBox root;
    private Label welcomeLabel;
    private Button changeProfileButton, accEventViewButton, viewInvitationButton, manageVendorButton, logoutButton;
    private HBox navbar;
    private Scene scene;

    public void init(String name, String userId) {
        root = new VBox(20);

        welcomeLabel = new Label("Welcome, " + name + " (ID: " + userId + ")!");

        changeProfileButton = new Button("Profile");
        accEventViewButton = new Button("Event");
        viewInvitationButton = new Button("Invitation");
        manageVendorButton = new Button("Manage Products");
        logoutButton = new Button("Logout");

        navbar = new HBox(10);
        navbar.getChildren().addAll(changeProfileButton, accEventViewButton, viewInvitationButton, manageVendorButton);
        navbar.setAlignment(Pos.CENTER);
        navbar.setPadding(new Insets(10));
    }

    public void layout() {
        root.getChildren().addAll(navbar, welcomeLabel, logoutButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    }

    public void setEventHandlers(Stage stage, String userId, String userRole) {
        changeProfileButton.setOnAction(e -> ChangeProfileView.display(stage));

        accEventViewButton.setOnAction(e -> AccEventView.display(stage, userId, userRole));

        manageVendorButton.setOnAction(e -> ManageVendorView.display(stage));

        logoutButton.setOnAction(e -> {
            UserController.logout();
            LoginView.display(stage);
        });
    }

    public static void display(Stage stage) {
        User user = UserController.getAuthenticatedUser();
        if (user == null) {
            LoginView.display(stage);
            return;
        }

        String userRole = user.getRole();
        VendorHomeView view = new VendorHomeView();
        view.init(user.getUsername(), user.getUserId());
        view.layout();
        view.setEventHandlers(stage, user.getUserId(), userRole);

        view.scene = new Scene(view.root, 400, 300);
        stage.setScene(view.scene);
        stage.setTitle("Vendor Home");
        stage.show();
    }
}
