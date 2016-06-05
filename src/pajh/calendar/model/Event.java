package pajh.calendar.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {
	
	private StringProperty place;
	private StringProperty desc;
	private ObjectProperty<LocalDate> time;
	
	public Event() {
		this(null,null);
	}

	public Event(String place, String desc) {
		this.place = new SimpleStringProperty(place);
		this.desc = new SimpleStringProperty(desc);
		
		this.time = new SimpleObjectProperty<LocalDate>(LocalDate.of(2000, 2, 14));
	}

	public StringProperty getPlace() {
		return place;
	}

	public void setPlace(StringProperty place) {
		this.place = place;
	}

	public StringProperty getDesc() {
		return desc;
	}

	public void setDesc(StringProperty desc) {
		this.desc = desc;
	}

	public ObjectProperty<LocalDate> getTime() {
		return time;
	}

	public void setTime(ObjectProperty<LocalDate> time) {
		this.time = time;
	}
}
