package views.eventorganizer;

import controllers.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Event;
import utils.Response;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class EventView {
    private VBox root;
    private Label titleLabel;
    private Button createEventButton, backButton;
    private TableView<Event> tableView;
    private Scene scene;

    public void init(String userId) {
        root = new VBox(10);
        titleLabel = new Label("Your Events");
        createEventButton = new Button("Create Event");
        backButton = new Button("Back");
        tableView = new TableView<>();
    }

    public void layout() {
        root.getChildren().addAll(titleLabel, createEventButton, backButton, tableView);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefWidth(600);
    }

    public void setEventHandlers(Stage stage, String userId) {
        createEventButton.setOnAction(e -> CreateEventView.display(stage, userId));

        Response<List<Event>> eventsResponse = EventOrganizerController.viewOrganizedEvents(userId);
        if (eventsResponse.isSuccess()) {
            List<Event> events = eventsResponse.getData();
            ObservableList<Event> eventList = FXCollections.observableArrayList(events);
            tableView.setItems(eventList);

            // Create columns and bind them using PropertyValueFactory
            TableColumn<Event, String> eventIdColumn = new TableColumn<>("Event ID");
            eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));

            TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
            eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));

            TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
            eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

            TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
            eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));

            TableColumn<Event, Void> eventDetailsColumn = new TableColumn<>("Details");
            eventDetailsColumn.setCellFactory(param -> new TableCell<Event, Void>() {
                private final Button btn = new Button("View Details");

                {
                    btn.setOnAction(e -> {
                        Event event = getTableView().getItems().get(getIndex());
                        String eventId = event.getEventId();
                        EventDetailsView.display(stage, eventId, userId);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            });

            // Add columns to the table
            tableView.getColumns().addAll(eventIdColumn, eventNameColumn, eventDateColumn, eventLocationColumn, eventDetailsColumn);
        } else {
            Text errorMessage = new Text("Failed to fetch events: " + eventsResponse.getMessage());
            root.getChildren().add(errorMessage);
        }
        
        backButton.setOnAction(e -> EventOrganizerHomeView.display(stage));
    }

    public static void display(Stage stage, String userId) {
        EventView view = new EventView();
        view.init(userId);
        view.layout();
        view.setEventHandlers(stage, userId);

        view.scene = new Scene(view.root, 600, 400);
        stage.setScene(view.scene);
        stage.setTitle("View Organized Events");
        stage.show();
    }
}
