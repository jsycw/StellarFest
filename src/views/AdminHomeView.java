package views;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminHomeView {
    public static void display(Stage stage, String userId) {
        VBox root = new VBox();
        Text title = new Text("Welcome, Admin!");
        root.getChildren().add(title);
        
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Admin Home - StellarFest");
        stage.show();
    }
}
