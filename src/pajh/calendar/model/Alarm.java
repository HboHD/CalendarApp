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

	public Alarm(Event event){
		this.event = event;

		Calendar calendar = Calendar.getInstance();
		calendar.set(event.getDateLD().getYear(), event.getDateLD().getMonthValue(),
				event.getDateLD().getDayOfMonth(), event.getTimeLT().getHour(),
				event.getTimeLT().getMinute(), 0);
		calendar.add(Calendar.MINUTE, -event.getAlarm().get());
		calendar.add(Calendar.MONTH, -1);
		Date time = calendar.getTime();

		timer = new Timer();
		timer.schedule(new RemindTask(), time);
	}

	public Alarm() {
		this.timer = null;
		this.event = null;
	}

	public void cancelAlarm () {
		timer.cancel();
		timer.purge();
	}

    class RemindTask extends TimerTask {
        public void run() {
        	Platform.runLater(new Runnable() {
        		public void run() {
        			Toolkit.getDefaultToolkit().beep();
        			Alert alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle("Przypomnienie!");
        			alert.setHeaderText(null);
        			alert.setContentText("Dla wydarzenia: " + event.getDescString()
        			+ ", w miejscu: " + event.getPlaceString() + "\nData i czas: "
        			+ event.getDateLD().toString() + " " + event.getTimeLT().toString());

        			alert.showAndWait();
        			timer.cancel();
        			timer.purge();
        		}
        	});
        }
    }
}
