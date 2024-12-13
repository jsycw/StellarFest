package views.guest;

import controllers.GuestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Event;
import utils.Response;
import views.ChangeProfileView;
import utils.Auth;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GuestEventView {
	private Button changeProfileButton, accEventViewButton, viewInvitationButton, logoutButton;
    private HBox navbar;
    
    private VBox root;
    private Label titleLabel;
    private Button backButton;
    private TableView<Event> tableView;
    private Scene scene;

    public void init() {
//    	changeProfileButton = new Button("Profile");
//        accEventViewButton = new Button("Event");
//        viewInvitationButton = new Button("Invitation");
//        navbar = new HBox(10);
//        navbar.getChildren().addAll(changeProfileButton, accEventViewButton, viewInvitationButton);
//        navbar.setAlignment(Pos.CENTER);
//        navbar.setPadding(new Insets(10));
        
        root = new VBox(10);
        titleLabel = new Label("Your Accepted Events");
        backButton = new Button("Back");
        tableView = new TableView<>();
    }

    public void layout() {
        root.getChildren().addAll(navbar, titleLabel, backButton, tableView);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefWidth(600);
    }

    public void setEventHandlers(Stage stage, String userId) {

        backButton.setOnAction(e -> GuestHomeView.display(stage));
        loadAcceptedEvents(userId);
    }

    private void loadAcceptedEvents(String userId) {
        String userEmail = Auth.get().getEmail();
        Response<List<Event>> response = GuestController.viewAcceptedEvents(userEmail);
        
        if (response.isSuccess()) {
            List<Event> events = response.getData();
            ObservableList<Event> eventList = FXCollections.observableArrayList(events);
            tableView.setItems(eventList);

            TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
            eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));

            TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
            eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

            TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
            eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));

            TableColumn<Event, Void> detailsColumn = new TableColumn<>("Details");
            detailsColumn.setCellFactory(param -> new TableCell<Event, Void>() {
                private final Button detailBtn = new Button("View Details");

                {
                    detailBtn.setOnAction(e -> {
                        Event event = getTableView().getItems().get(getIndex());
                        showEventDetails(event);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(detailBtn);
                    }
                }
            });

            tableView.getColumns().clear();
            tableView.getColumns().addAll(eventNameColumn, eventDateColumn, eventLocationColumn, detailsColumn);
        } else {
            Text errorMessage = new Text("Failed to fetch events: " + response.getMessage());
            root.getChildren().add(errorMessage);
        }
    }

    private void showEventDetails(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Details");
        alert.setHeaderText(event.getEventName());
        
        String content = String.format(
            "Event ID: %s\n\n" +
            "Date: %s\n\n" +
            "Location: %s\n\n" +
            "Description: %s",
            event.getEventId(),
            event.getEventDate(),
            event.getEventLocation(),
            event.getEventDescription()
        );
        
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void display(Stage stage, String userId, String userRole) {
        GuestEventView view = new GuestEventView();
        view.init();
        view.layout();
        view.setEventHandlers(stage, userId);

        view.scene = new Scene(view.root, 600, 400);
        stage.setScene(view.scene);
        stage.setTitle("View Accepted Events");
        stage.show();
    }
}