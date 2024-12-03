package views;

import java.sql.SQLException;

import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Vendor;

public class LoginView {
    private VBox root;
    private GridPane grid;
    private Label titleLabel, emailLabel, passwordLabel;
    private TextField emailInput;
    private PasswordField passwordInput;
    private Button loginButton;
    private Hyperlink registerLink;
    private Scene scene;

    public void init() {
        root = new VBox();
        grid = new GridPane();
        titleLabel = new Label("Login to StellarFest");
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        emailInput = new TextField();
        emailInput.setPromptText("Enter your email");
        passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        loginButton = new Button("Login");
        registerLink = new Hyperlink("Don't have an account? Register here.");
    }

    public void layout() {
        grid.add(emailLabel, 0, 0);
        grid.add(emailInput, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordInput, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        root.getChildren().addAll(titleLabel, grid, registerLink);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox.setMargin(titleLabel, new Insets(0, 0, 20, 0));
    }

    public void style() {
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        registerLink.setStyle("-fx-text-fill: #4CAF50;");
    }

    public void setEventHandlers(Stage stage) {
        loginButton.setOnAction(e -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();

            if (email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.", ButtonType.OK);
                alert.showAndWait();
            } else {
                String role = new UserController().login(email, password);
                Alert alert;
                if (role != null && !role.isEmpty()) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "Login successful!", ButtonType.OK);
                    alert.showAndWait();

                    switch (role) {
                        case "admin":
                            AdminHomeView.display(stage);
                            break;
                        case "event organizer":
                            EventOrganizerHomeView.display(stage);
                            break;
                        case "vendor":
                        	Vendor vendor = null;
                        	try {
                        		vendor = new Vendor("user_id", "email", "name", "role");
                        		vendor = vendor.getVendorFromDatabase(email);
                        		if (vendor != null) {
                                    VendorHomeView.display(stage, vendor);
                                } else {
                                    alert = new Alert(Alert.AlertType.ERROR, "Vendor not found.", ButtonType.OK);
                                    alert.showAndWait();
                                }
                        	} catch (SQLException e1) {
                        		// TODO Auto-generated catch block
                        		e1.printStackTrace();
                        	}
                            break;
                        case "guest":
                            GuestHomeView.display(stage);
                            break;
                        default:
                            alert = new Alert(Alert.AlertType.ERROR, "Invalid role detected.", ButtonType.OK);
                            alert.showAndWait();
                            break;
                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password.", ButtonType.OK);
                    alert.showAndWait();
                }
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

        loginView.scene = new Scene(loginView.root, 400, 300);
        stage.setScene(loginView.scene);
        stage.setTitle("Login");
        stage.show();
    }
}
