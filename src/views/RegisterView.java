package views;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterView {

    public static void display(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        TextField emailInput = new TextField();
        Label usernameLabel = new Label("Username:");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordInput = new PasswordField();
        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Event Organizer", "Vendor", "Guest");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

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

        backButton.setOnAction(e -> {
            LoginView.display(stage); 
        });

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

        Scene scene = new Scene(grid, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.show();
    }
}
