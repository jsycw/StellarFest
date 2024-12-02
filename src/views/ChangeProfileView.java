package views;

public class ChangeProfileView {
//    private VBox root;
//    private GridPane grid;
//    private Label emailLabel, nameLabel, oldPasswordLabel, newPasswordLabel;
//    private TextField emailInput, nameInput;
//    private PasswordField oldPasswordInput, newPasswordInput;
//    private Button updateButton, backButton;
//    private Scene scene;
//
//    public void init() {
//        root = new VBox();
//        grid = new GridPane();
//
//        emailLabel = new Label("New Email:");
//        nameLabel = new Label("New Name:");
//        oldPasswordLabel = new Label("Current Password:");
//        newPasswordLabel = new Label("New Password:");
//
//        emailInput = new TextField();
//        nameInput = new TextField();
//        oldPasswordInput = new PasswordField();
//        newPasswordInput = new PasswordField();
//
//        updateButton = new Button("Update Profile");
//        backButton = new Button("Back");
//    }
//
//    public void layout() {
//        grid.add(emailLabel, 0, 0);
//        grid.add(emailInput, 1, 0);
//        grid.add(nameLabel, 0, 1);
//        grid.add(nameInput, 1, 1);
//        grid.add(oldPasswordLabel, 0, 2);
//        grid.add(oldPasswordInput, 1, 2);
//        grid.add(newPasswordLabel, 0, 3);
//        grid.add(newPasswordInput, 1, 3);
//        grid.add(updateButton, 1, 4);
//        grid.add(backButton, 1, 5);
//
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setAlignment(Pos.CENTER);
//
//        root.getChildren().add(grid);
//        root.setPadding(new Insets(20));
//        root.setAlignment(Pos.CENTER);
//    }
//
//    public void style() {
//        root.setSpacing(20);
//        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
//        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
//    }
//
//    public void setEventHandlers(Stage stage, String currentEmail) {
//    	updateButton.setOnAction(e -> {
//    	    String newEmail = emailInput.getText();
//    	    String newName = nameInput.getText();
//    	    String oldPassword = oldPasswordInput.getText();
//    	    String newPassword = newPasswordInput.getText();
//
//    	    if (oldPassword.isEmpty()) {
//    	        Alert alert = new Alert(Alert.AlertType.ERROR, "Current password is required.", ButtonType.OK);
//    	        alert.showAndWait();
//    	        return;
//    	    }
//
//    	    UserController userController = new UserController();
//    	    String result = userController.changeProfile(
//    	        currentEmail,
//    	        newEmail.isEmpty() ? null : newEmail,
//    	        newName.isEmpty() ? null : newName,
//    	        oldPassword,
//    	        newPassword.isEmpty() ? null : newPassword
//    	    );
//
//    	    Alert alert = new Alert(
//    	        result.startsWith("Profile updated") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
//    	        result,
//    	        ButtonType.OK
//    	    );
//    	    alert.showAndWait();
//    	});
//    }
//
//    public static void display(Stage stage, String currentEmail) {
//        ChangeProfileView changeProfileView = new ChangeProfileView();
//        changeProfileView.init();
//        changeProfileView.layout();
//        changeProfileView.style();
//        changeProfileView.setEventHandlers(stage, currentEmail);
//
//        changeProfileView.scene = new Scene(changeProfileView.root, 400, 300);
//        stage.setScene(changeProfileView.scene);
//        stage.setTitle("Change Profile");
//        stage.show();
//    }
}
