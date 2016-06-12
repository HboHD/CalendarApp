package pajh.calendar.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pajh.calendar.model.Event;

public class EventEditDialogController {

    @FXML
    private TextField eventPlace;
    @FXML
    private TextArea eventDesc;
    @FXML
    private DatePicker eventDate;

    private Stage dialogStage;
    private Event event;
    private boolean okClicked = false;

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

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setEvent(Event event) {
        this.event = event;

        eventPlace.setText(event.getPlaceString());
        eventDesc.setText(event.getDescString());
        eventDate.setValue(event.getTimeLD());
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
        if (isInputValid()) {
            event.setDesc(eventDesc.getText());
            event.setPlace(eventPlace.getText());
            event.setTimeLD(eventDate.getValue());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (eventDate.getValue() == null) {
            errorMessage += "Brak wlasciwej daty!\n";
        }
        if (eventPlace.getText() == null || eventPlace.getText().length() == 0) {
            errorMessage += "Brak wlasciwego miejsca!\n";
        }
        if (eventDesc.getText() == null || eventDesc.getText().length() == 0) {
            errorMessage += "Brak opisu!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}