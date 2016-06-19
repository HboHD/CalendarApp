package pajh.calendar.view;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pajh.calendar.MainApp;
import pajh.calendar.model.Event;

public class EventsOverviewController {
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> placeColumn;
    @FXML
    private TableColumn<Event, String> descColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateColumn;

    @FXML
    private TextField filterField;
    @FXML
    private TextField eventPlace;
    @FXML
    private TextArea eventDesc;
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField eventTime;

    @FXML
    private Label eventAlarmLabel;
    @FXML
    private Label eventDescLabel;
    @FXML
    private Label eventTimeLabel;

    @FXML
    private Label placeLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label dateLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public EventsOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        descColumn.setCellValueFactory(cellData -> cellData.getValue().descProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        eventTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEventDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * Also wrap the ObservableList of events into FilteredList and then
     * into SortedList to have dynamic filtering possibility with a Listener.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Event> filteredData = new FilteredList<>(mainApp.getEventData(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(event -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (event.getPlace().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Event> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(eventTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        eventTable.setItems(sortedData);
    }

    /**
     * Sets labels content to actual selected event's fields.
     * 
     * @param event Selected event
     */
    private void showEventDetails(Event event) {
		eventDescLabel.setText(event.getDesc());
		eventTimeLabel.setText(event.getTime().toString());
		if (event.getAlarmObject() == null) {
			eventAlarmLabel.setText("Brak");
		} else {
			eventAlarmLabel.setText(Integer.toString(event.alarmProperty().get()) + " minut przed wydarzeniem");
		}
    }

    /**
     * Handler for adding a new event to eventData list.
     */
    @FXML
    private void handleNewEvent() {
        Event tempEvent = new Event(eventPlace.getText(), eventDesc.getText(), eventDate.getValue(), LocalTime.parse(eventTime.getText()));
        mainApp.getEventData().add(tempEvent);
    }

    /**
     * Handler for editing an event. Shows error if there is no selected event.
     * Runs {@link MainApp#showEventEditDialog}.
     */
    @FXML
    private void handleEditEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wybranego wydarzenia");
            alert.setHeaderText(null);
            alert.setContentText("Prosze wybrac jakies wydarzenie.");

            alert.showAndWait();
        }
        else {
        	mainApp.showEventEditDialog(selectedEvent);
        }
    }
    
    /**
     * Handler for setting alarm to an event. Shows error if there is no
     * selected event. Runs {@link MainApp#showSetAlarmDialog}.
     */
    @FXML
    private void handleSetAlarmEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wybranego wydarzenia");
            alert.setHeaderText(null);
            alert.setContentText("Prosze wybrac jakies wydarzenie.");

            alert.showAndWait();
        }
        else {
        	mainApp.showSetAlarmDialog(selectedEvent);
        	showEventDetails(selectedEvent);
        }
    }

    /**
     * Handler for deleting event from eventData list. Shows error if there is no
     * selected event.
     */
    @FXML
    private void handleDeleteEvent() {
        int selectedIndex = eventTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0 ) {
        mainApp.getEventData().remove(eventTable.getSelectionModel().getSelectedItem());
        }
        else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wybranego wydarzenia");
            alert.setHeaderText(null);
            alert.setContentText("Prosze wybrac jakies wydarzenie.");


            alert.showAndWait();
        }
    }
}
