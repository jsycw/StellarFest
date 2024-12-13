package views.guest;

import controllers.InvitationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Invitation;
import utils.Response;
import utils.Auth;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GuestInvitationView {
    private VBox root;
    private Label titleLabel;
    private Button refreshButton, backButton;
    private TableView<Invitation> tableView;
    private Scene scene;

    public void init(String userEmail) {
        root = new VBox(10);
        titleLabel = new Label("Your Invitations");
        refreshButton = new Button("Refresh");
        backButton = new Button("Back");
        tableView = new TableView<>();
    }

    public void layout() {
        root.getChildren().addAll(titleLabel, refreshButton, backButton, tableView);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefWidth(600);
    }

    public void setEventHandlers(Stage stage, String userEmail) {
        refreshButton.setOnAction(e -> loadInvitations(userEmail));
        
        loadInvitations(userEmail);
        
        backButton.setOnAction(e -> GuestHomeView.display(stage));
    }

    private void loadInvitations(String userEmail) {
        Response<List<Invitation>> response = InvitationController.getInvitations(userEmail);
        
        if (response.isSuccess()) {
            List<Invitation> invitations = response.getData();
            ObservableList<Invitation> invitationList = FXCollections.observableArrayList(invitations);
            tableView.setItems(invitationList);
            
            TableColumn<Invitation, String> invitationIdColumn = new TableColumn<>("Invitation ID");
            invitationIdColumn.setCellValueFactory(new PropertyValueFactory<>("invitationId"));

            TableColumn<Invitation, String> eventIdColumn = new TableColumn<>("Event ID");
            eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
            
            TableColumn<Invitation, String> eventNameColumn = new TableColumn<>("Event Name");
            eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));

            TableColumn<Invitation, String> statusColumn = new TableColumn<>("Status");
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("invitationStatus"));

            TableColumn<Invitation, String> roleColumn = new TableColumn<>("Role");
            roleColumn.setCellValueFactory(new PropertyValueFactory<>("invitationRole"));

            TableColumn<Invitation, Void> actionColumn = new TableColumn<>("Action");
            actionColumn.setCellFactory(param -> new TableCell<Invitation, Void>() {
                private final Button acceptBtn = new Button("Accept");

                {
                    acceptBtn.setOnAction(e -> {
                        Invitation invitation = getTableView().getItems().get(getIndex());
                        String currentUserId = Auth.get().getUserId();
                        Response<Void> acceptResponse = InvitationController.acceptInvitation(currentUserId, invitation.getEventId());
                        
                        if (acceptResponse.isSuccess()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Invitation accepted successfully!");
                            alert.showAndWait();
                            loadInvitations(userEmail);
                        } 
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText(acceptResponse.getMessage());
                            alert.showAndWait();
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } 
                    else {
                        setGraphic(acceptBtn);
                    }
                }
            });

            
            tableView.getColumns().clear();
            tableView.getColumns().addAll(invitationIdColumn, eventIdColumn, eventNameColumn, statusColumn, roleColumn, actionColumn);
        } 
        else {
            Text errorMessage = new Text("Failed to fetch invitations: " + response.getMessage());
            root.getChildren().add(errorMessage);
        }
    }

    public static void display(Stage stage, String userEmail) {
        GuestInvitationView view = new GuestInvitationView();
        view.init(userEmail);
        view.layout();
        view.setEventHandlers(stage, userEmail);

        view.scene = new Scene(view.root, 600, 400);
        stage.setScene(view.scene);
        stage.setTitle("View Invitations");
        stage.show();
    }
}