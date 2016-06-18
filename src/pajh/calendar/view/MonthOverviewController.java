package pajh.calendar.view;

import java.time.LocalDate;

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

public class MonthOverviewController {
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
    public MonthOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().getPlace());
        descColumn.setCellValueFactory(cellData -> cellData.getValue().getDesc());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getTime());
    }

    /**
     * Is called by the main application to give a reference back to itself.
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

                if (event.getPlaceString().toLowerCase().contains(lowerCaseFilter)) {
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

    @FXML
    private void handleFilterEvent() {
    }

    @FXML
    private void handleNewEvent() {
        Event tempEvent = new Event(eventPlace.getText(), eventDesc.getText(), eventDate.getValue());
        mainApp.getEventData().add(tempEvent);
    }

    @FXML
    private void handleEditEvent() {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
        else {
        	mainApp.showEventEditDialog(selectedEvent);
        }

    }

    @FXML
    private void handleDeleteEvent() {
        int selectedIndex = eventTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0 ) {
        mainApp.getEventData().remove(eventTable.getSelectionModel().getSelectedItem());
        }
        else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("B³¹d");
            alert.setHeaderText("Nie wybrano ¿adnego wydarzenia!");
            alert.setContentText("Prosze wybrac wydarzenie.");


            alert.showAndWait();
        }
    }
}
