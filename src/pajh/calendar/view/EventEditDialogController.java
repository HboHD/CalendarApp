package pajh.calendar.view;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pajh.calendar.model.Event;
import pajh.calendar.util.TimeUtil;

public class EventEditDialogController {

    @FXML
    private TextField eventPlace;
    @FXML
    private TextArea eventDesc;
    @FXML
    private DatePicker eventDate;
    @FXML
    private TextField eventTime;

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
     * @param event Selected event
     */
    public void setEvent(Event event) {
        this.event = event;

        eventPlace.setText(event.getPlace());
        eventDesc.setText(event.getDesc());
        eventDate.setValue(event.getDate());
        eventTime.setText(event.getTime().toString());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
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
            event.setDate(eventDate.getValue());
            event.setTime(LocalTime.parse(eventTime.getText()));

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
        if (!(TimeUtil.validTime(eventTime.getText())) || eventTime.getText() == null ||
        		eventTime.getText().length() == 0) {
            errorMessage += "Zly format czasu (hh:mm)!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Niewlasciwe pola");
            alert.setHeaderText("Prosze poprawic blednie wypelnione pole.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}