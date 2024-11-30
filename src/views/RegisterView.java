package views;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
    private VBox root;
    private GridPane grid;
    private Label emailLabel, usernameLabel, passwordLabel, roleLabel;
    private TextField emailInput, usernameInput;
    private PasswordField passwordInput;
    private ComboBox<String> roleComboBox;
    private Button registerButton;
    private Button backButton;
    private Scene scene;

    public void init() {
        root = new VBox();
        grid = new GridPane();
        emailLabel = new Label("Email:");
        usernameLabel = new Label("Username:");
        passwordLabel = new Label("Password:");
        roleLabel = new Label("Role:");
        emailInput = new TextField();
        usernameInput = new TextField();
        passwordInput = new PasswordField();
        roleComboBox = new ComboBox<>();
        registerButton = new Button("Register");
        backButton = new Button("Back to Login");

        roleComboBox.getItems().addAll("Event Organizer", "Vendor", "Guest");
    }

    public void layout() {
        grid.add(emailLabel, 0, 0);
        grid.add(emailInput, 1, 0);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameInput, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordInput, 1, 2);
        grid.add(roleLabel, 0, 3);
        grid.add(roleComboBox, 1, 3);
        grid.add(registerButton, 1, 4);
        grid.add(backButton, 1, 5);

        root.getChildren().add(grid);
    }

    public void style() {
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
    }

    public void setEventHandlers(Stage stage) {
        registerButton.setOnAction(e -> {
            String email = emailInput.getText();
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String role = roleComboBox.getValue();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.", ButtonType.OK);
                alert.showAndWait();
            } else {
                boolean success = UserController.register(email, username, password, role);
                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Successful!", ButtonType.OK);
                    alert.showAndWait();
                    LoginView.display(stage);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Registration Failed. Try again.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        backButton.setOnAction(e -> LoginView.display(stage));
    }

    public static void display(Stage stage) {
        RegisterView registerView = new RegisterView();
        registerView.init();
        registerView.layout();
        registerView.style();
        registerView.setEventHandlers(stage);

        registerView.scene = new Scene(registerView.root, 300, 300);
        stage.setScene(registerView.scene);
        stage.setTitle("Register");
        stage.show();
    }
}