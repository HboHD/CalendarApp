package pajh.calendar.view;

import pajh.calendar.MainApp;
import pajh.calendar.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DeleteOlderThanDialogController {

	@FXML
	private DatePicker datePicker;

    private Stage dialogStage;
    private MainApp mainApp;
    private boolean okClicked = false;

    public DeleteOlderThanDialogController() {
    }

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
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {

		if (datePicker.getValue() != null) {

			ObservableList<Event> tmp = FXCollections.observableArrayList();

			mainApp.getEventData().forEach(event -> {
				if (event.getDate().isBefore(datePicker.getValue())) {
					tmp.add(event);
				}
			});
			if (tmp.size() != 0) {
				for (int i = 0; i < mainApp.getEventData().size(); i++) {
					for (int j = 0; j < tmp.size(); j++) {
						if (mainApp.getEventData().get(i) == tmp.get(j)) {
							mainApp.getEventData().remove(i);
						}
					}
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Brak elementów");
				alert.setHeaderText("Brak elementów do usuniêcia, wybierz inn¹ datê");

				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Brak wyboru");
			alert.setHeaderText("Nie wybra³eœ daty");
			alert.setContentText("Wybierz datê i spróbuj ponownie");

			alert.showAndWait();
		}

    	okClicked = true;
        dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
