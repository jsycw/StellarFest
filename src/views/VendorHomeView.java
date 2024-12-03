package views;

import java.util.List;

import controllers.VendorController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Product;
import models.Vendor;

public class VendorHomeView {
	public static void display(Stage stage, Vendor vendor) {
		if (vendor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vendor data is missing!");
            alert.showAndWait();
            return;
        }
		VendorController controller = new VendorController(vendor);

        VBox root = new VBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        Label label = new Label("Welcome, " + vendor.getUsername());
        Button viewInvitations = new Button("View Invitations");
        Button manageProducts = new Button("Manage Products");

        manageProducts.setOnAction(e -> viewAllProducts(controller));

        root.getChildren().addAll(label, viewInvitations, manageProducts);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Vendor Home");
        stage.show();
    }

	private static void viewAllProducts(VendorController controller) {
		Stage stage = new Stage();
        VBox root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        List<Product> products = controller.viewAllProducts();

        TableView<Product> table = new TableView<>();
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(nameColumn, descriptionColumn);

        table.getItems().addAll(products);

        Button addProductButton = new Button("Add Product");
        addProductButton.setOnAction(e -> manageProducts(controller));  // Open "Add Product" form

        root.getChildren().addAll(new Label("All Products"), table, addProductButton);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.setTitle("View Products");
        stage.show();
	}

    private static void manageProducts(VendorController controller) {
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        TextField productName = new TextField();
        productName.setPromptText("Product Name");

        TextArea productDescription = new TextArea();
        productDescription.setPromptText("Product Description");
        productDescription.setWrapText(true);

        Button saveButton = new Button("Save Product");
        saveButton.setOnAction(e -> controller.manageProduct(productName.getText(), productDescription.getText()));

        root.getChildren().addAll(new Label("Add Product"), productName, productDescription, saveButton);
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Manage Products");
        stage.show();
    }
    
}
