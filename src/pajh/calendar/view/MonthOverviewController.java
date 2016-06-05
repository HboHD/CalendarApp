package pajh.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        eventTable.setItems(mainApp.getPersonData());
    }
}