package views;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private VBox root;
    private GridPane grid;
    private Label emailLabel, passwordLabel;
    private TextField emailInput;
    private PasswordField passwordInput;
    private Button loginButton;
    private Hyperlink registerLink;
    private Scene scene;

    public void init() {
        root = new VBox();
        grid = new GridPane();
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        emailInput = new TextField();
        passwordInput = new PasswordField();
        loginButton = new Button("Login");
        registerLink = new Hyperlink("Don't have an account? Register here.");
    }

    public void layout() {
        grid.add(emailLabel, 0, 0);
        grid.add(emailInput, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordInput, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(registerLink, 1, 3);

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

        registerLink.setOnAction(e -> RegisterView.display(stage));
    }

    public static void display(Stage stage) {
        LoginView loginView = new LoginView();
        loginView.init();
        loginView.layout();
        loginView.style();
        loginView.setEventHandlers(stage);

        loginView.scene = new Scene(loginView.root, 300, 200);
        stage.setScene(loginView.scene);
        stage.setTitle("Login");
        stage.show();
    }
}
