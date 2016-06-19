package pajh.calendar.model;

import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alarm {

	private Timer timer;
	private Event event;

	
	/**
	 * Alarm class construct. Gets Event object which it is setting alarm for. 
	 * Firstly it subtract alarm time (in minutes) from event time and then
	 * schedule an alarm to fire up.
	 * 
	 * @param event
	 */
	public Alarm(Event event){
		this.event = event;

		Calendar calendar = Calendar.getInstance();
		calendar.set(event.getDate().getYear(), event.getDate().getMonthValue(),
				event.getDate().getDayOfMonth(), event.getTime().getHour(),
				event.getTime().getMinute(), 0);
		calendar.add(Calendar.MINUTE, -event.alarmProperty().get());
		calendar.add(Calendar.MONTH, -1);
		Date time = calendar.getTime();

		timer = new Timer();
		timer.schedule(new RemindTask(), time);
	}

    /**
     * Alarm class construct nulling all fields.
     */
	public Alarm() {
		this.timer = null;
		this.event = null;
	}

    /**
     * Cancels alarm.
     */
	public void cancelAlarm () {
		timer.cancel();
		timer.purge();
	}

    /**
     * Creates a runnable instance of an alarm which waits to be activated as long
     * as a thread of app is running. When time is up, it shows a dialog with
     * the alarm and makes a beep sound.
     */
    class RemindTask extends TimerTask {
        public void run() {
        	Platform.runLater(new Runnable() {
        		public void run() {
        			Toolkit.getDefaultToolkit().beep();
        			Alert alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle("Przypomnienie!");
        			alert.setHeaderText(null);
        			alert.setContentText("Dla wydarzenia: " + event.getDesc()
        			+ ", w miejscu: " + event.getPlace() + "\nData i czas: "
        			+ event.getDate().toString() + " " + event.getTime().toString());

        			alert.showAndWait();
        			timer.cancel();
        			timer.purge();
        		}
        	});
        }
    }
}
