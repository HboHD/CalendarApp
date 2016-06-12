package pajh.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RootLayoutController {

	@FXML
	private MenuItem about;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
    }

	@FXML
    private void handleMenuAboutEvent() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("O programie");
		alert.setHeaderText("Informacje o organizerze");
		alert.setContentText("OrganizerApp v1.0 \n"
				+ "Autorzy: Piotr Adryjañczyk, Jakub Holys \n"
				+ "All rights reserved 2016 \u00ae");

		alert.showAndWait();
	}
}
