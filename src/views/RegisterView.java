package views;

import controllers.UserController;
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
    private Label titleLabel, emailLabel, usernameLabel, passwordLabel, roleLabel;
    private TextField emailInput, usernameInput;
    private PasswordField passwordInput;
    private ComboBox<String> roleSelect;
    private Button registerButton;
    private Hyperlink loginLink;
    private Scene scene;

    public void init() {
        root = new VBox();
        grid = new GridPane();
        titleLabel = new Label("Register for StellarFest");
        emailLabel = new Label("Email:");
        usernameLabel = new Label("Username:");
        passwordLabel = new Label("Password:");
        roleLabel = new Label("Role:");
        
        emailInput = new TextField();
        emailInput.setPromptText("Enter your email");
        usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");

        roleSelect = new ComboBox<>();
        roleSelect.getItems().addAll("Event Organizer", "Vendor", "Guest");
        roleSelect.setPromptText("Select Role");

        registerButton = new Button("Register");
        loginLink = new Hyperlink("Already have an account? Login here.");
    }

    public void layout() {
        grid.add(emailLabel, 0, 0);
        grid.add(emailInput, 1, 0);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameInput, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordInput, 1, 2);
        grid.add(roleLabel, 0, 3);
        grid.add(roleSelect, 1, 3);
        grid.add(registerButton, 1, 4);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        root.getChildren().addAll(titleLabel, grid, loginLink);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox.setMargin(titleLabel, new Insets(0, 0, 20, 0));
    }

    public void style() {
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        loginLink.setStyle("-fx-text-fill: #4CAF50;");
    }

    public void setEventHandlers(Stage stage) {
        registerButton.setOnAction(e -> {
            String email = emailInput.getText();
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String role = roleSelect.getValue();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.", ButtonType.OK);
                alert.showAndWait();
            } else {
                String message = new UserController().register(email, username, password, role);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
                alert.showAndWait();

                if (message.equals("Registration successful. Please log in.")) {
                    LoginView.display(stage);
                }
            }
        });

        loginLink.setOnAction(e -> LoginView.display(stage));
    }

    public static void display(Stage stage) {
        RegisterView registerView = new RegisterView();
        registerView.init();
        registerView.layout();
        registerView.style();
        registerView.setEventHandlers(stage);

        registerView.scene = new Scene(registerView.root, 400, 400);
        stage.setScene(registerView.scene);
        stage.setTitle("Register");
        stage.show();
    }
}
