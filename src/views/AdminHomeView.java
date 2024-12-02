package views;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminHomeView {
    public static void display(Stage stage) {
        VBox root = new VBox();
        Label label = new Label("Welcome to the Admin Home Page");
        root.getChildren().add(label);
        root.setSpacing(20);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Admin Home");
        stage.show();
    }
}
