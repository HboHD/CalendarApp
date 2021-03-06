package pajh.calendar.view;

import java.io.File;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import pajh.calendar.MainApp;
import pajh.calendar.util.DatabaseUtil;

/**
 * @author Piotrek
 *
 */
public class RootLayoutController {

	private MainApp mainApp;

	@FXML
	private MenuItem about;

	@FXML
	private MenuItem settings;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    /**
     * Opens a FileChooser to let the user select XML to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Pliki XML (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadEventDataFromFileXML(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File eventFile = mainApp.getEventFilePath();
        if (eventFile != null) {
            mainApp.saveEventDataToFileXML(eventFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to. 
     * XML or ICS formats may be chosen.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Pliki XML (*.xml)", "*.xml");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter(
                "Pliki ICS (*.ics)", "*.ics");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilter2);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null && fileChooser.getSelectedExtensionFilter()==extFilter) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveEventDataToFileXML(file);
        }
        else if (file != null && fileChooser.getSelectedExtensionFilter()==extFilter2) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".ics")) {
                file = new File(file.getPath() + ".ics");
            }
            mainApp.saveEventDataToFileICS(file);
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to export to.
     * Creates connection with database and runs {@link DatabaseUtil#setList}.
     * 
     * @throws SQLException
     */
    @FXML
    private void handleExportDatabase() throws SQLException {

    	FileChooser fileChooser = new FileChooser();
    	// Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Pliki bazy danych Microsoft Access (*.accdb)", "*.accdb");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            try {
				DatabaseUtil.setList(DatabaseUtil.getConnection(file.getAbsolutePath()), mainApp.getEventData());
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Sukces");
	            alert.setHeaderText("Zapisano dane");
	            alert.setContentText("Zapisano dane do pliku:\n" + file.getPath());

	            alert.showAndWait();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("B��d");
	            alert.setHeaderText("Nie mo�na zapisa� danych");
	            alert.setContentText("Nie mo�na zapisa� danych do pliku:\n" + file.getPath());

	            alert.showAndWait();
			}
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to export to.
     * Creates connection with database and runs {@link DatabaseUtil#getList}
     * to save database's content to eventData.
     * 
     * @throws SQLException
     */
    @FXML
    private void handleImportDatabase() throws SQLException {

    	FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Plik bazy danych Microsoft Access (*.accdb)", "*.accdb");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            try {
				mainApp.getEventData().setAll(DatabaseUtil.getList(DatabaseUtil.getConnection(file.getAbsolutePath())));
				Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Sukces");
	            alert.setHeaderText("Odczytano dane");
	            alert.setContentText("Odczytano dane z pliku:\n" + file.getPath());

	            alert.showAndWait();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("B��d");
	            alert.setHeaderText("Nie mo�na odczyta� danych");
	            alert.setContentText("Nie mo�na odczyta� danych z pliku:\n" + file.getPath());

	            alert.showAndWait();
			}
        }
    }

    
    /**
     * Handler running {@link MainApp#showSettingsDialog}
     */
    @FXML
    private void handleSettingEvent() {
    	mainApp.showSettingsDialog();
    }
    
    /**
     * Handler showing AboutDialog with application information.
     */
	@FXML
    private void handleMenuAboutEvent() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("O programie");
		alert.setHeaderText("Informacje o organizerze");
		alert.setContentText("OrganizerApp v1.0 \n"
				+ "Autorzy: Piotr Adryja�czyk, Jakub Holys \n"
				+ "All rights reserved 2016 \u00ae");

		alert.showAndWait();
	}

    /**
     * Handler running {@link MainApp#showDeleteOlderThanDialog}
     */
    @FXML
    private void handleDeleteOlderThan (){
    	mainApp.showDeleteOlderThanDialog();
    }
}
