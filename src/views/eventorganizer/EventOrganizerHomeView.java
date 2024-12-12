package views.eventorganizer;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;
import views.ChangeProfileView;
import views.LoginView;

public class EventOrganizerHomeView {
    private VBox root;
    private Label welcomeLabel;
    private Button changeProfileButton, eventViewButton, logoutButton;
    private Scene scene;

    public void init(String name, String userId) {
        root = new VBox(20);
        welcomeLabel = new Label("Welcome, " + name + " (ID: " + userId + ")!");
        changeProfileButton = new Button("Change Profile");
        eventViewButton = new Button("View Event");
        logoutButton = new Button("Logout");
    }

    public void layout() {
        root.getChildren().addAll(welcomeLabel, changeProfileButton, eventViewButton, logoutButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(15);

        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    }

    public void setEventHandlers(Stage stage, String userId) {
        changeProfileButton.setOnAction(e -> ChangeProfileView.display(stage));

        eventViewButton.setOnAction(e -> EventView.display(stage, userId));

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

        EventOrganizerHomeView view = new EventOrganizerHomeView();
        view.init(user.getUsername(), user.getUserId());
        view.layout();
        view.setEventHandlers(stage, user.getUserId());

        view.scene = new Scene(view.root, 400, 300);
        stage.setScene(view.scene);
        stage.setTitle("Event Organizer Home");
        stage.show();
    }
}
