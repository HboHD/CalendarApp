package pajh.calendar.view;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pajh.calendar.model.Alarm;
import pajh.calendar.model.Event;

public class SetAlarmDialogController {

    @FXML
    private Label dateLabel;
    @FXML
    private Label placeLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private TextField alarmTime;

    private Stage dialogStage;
    private Event event;
    private Alarm alarm;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	alarmTime.setText("10");
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

        placeLabel.setText(event.getPlaceString());
        descLabel.setText(event.getDescString());
        dateLabel.setText(event.getDateLD().toString());
        timeLabel.setText(event.getTimeLT().toString());
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
        	int intAlarmTime = Integer.parseInt(alarmTime.getText());
        	event.setAlarm(intAlarmTime);

        	alarm = new Alarm(event);
        	event.setAlarmW(alarm);
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

    @FXML
    private void handleErase() {
    	event.getAlarmW().cancelAlarm();
    	event.deleteAlarm();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if(!(event.getAlarmW() == null)) {
        	errorMessage += "Juz dodano przypomnienie dla tego wydarzenia. "
        			+ "Wykup wersje premium aby dodac wiecej niz jedno przypomnienie.";
        }

        if( !(alarmTime.getText().matches("[0-9]*")) || alarmTime.getText() == "" ) {
            errorMessage += "Brak wlasciwej daty!\n";
        }

        if( LocalDateTime.now().isAfter(LocalDateTime.of(event.getDateLD(), event.getTimeLT())
                .minusMinutes(Integer.parseInt(alarmTime.getText())))) {
        	errorMessage += "Przypomnienie nale¿y ustawic conajmniej minute od aktualnego czasu";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Zle przypomnienie!");
            alert.setHeaderText("Prosze wpisac poprawny czas przypomnienia.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}