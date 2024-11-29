package views;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView {
    public static void display(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        TextField emailInput = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordInput = new PasswordField();

        Button loginButton = new Button("Login");
//        Button registerButton = new Button("Register");
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here.");

        loginButton.setOnAction(e -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();

            if (UserController.login(email, password)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login Successful!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials.", ButtonType.OK);
                alert.showAndWait();
            }
        });

//        registerButton.setOnAction(e -> {
//            RegisterView.display(stage); 
//        });
        registerLink.setOnAction(e -> {
            RegisterView.display(stage);
        });

        grid.add(emailLabel, 0, 0);
        grid.add(emailInput, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordInput, 1, 1);
        grid.add(loginButton, 1, 2);
//        grid.add(registerButton, 1, 3); 
        grid.add(registerLink, 1, 3);

        Scene scene = new Scene(grid, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}
