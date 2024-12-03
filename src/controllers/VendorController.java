package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import models.Product;
import models.Vendor;
import utils.Connect;

public class VendorController {
	private Vendor vendor;
	private Connect db;
	
	public VendorController(Vendor vendor) {
		this.vendor = vendor;
	    this.db = Connect.getInstance();
	}
	
	public void manageProduct(String productName, String productDescription) {
		if (productName.isEmpty() || productDescription.isEmpty() || productDescription.length() > 200) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid product details!");
            alert.show();
            return;
        }

        String query = "INSERT INTO products (product_name, product_description) VALUES (?, ?)";
        try {
            var ps = db.preparedStatement(query);
            ps.setString(1, productName);
            ps.setString(2, productDescription);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Product added successfully!");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public List<Product> viewAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try {
            var ps = db.preparedStatement(query);
            var rs = ps.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("product_name");
                String productDescription = rs.getString("product_description");
                products.add(new Product(productName, productDescription));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
	
}
