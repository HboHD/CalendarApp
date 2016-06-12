package pajh.calendar.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {
	
	private StringProperty place;
	private StringProperty desc;
	private ObjectProperty<LocalDate> time;
	
	public Event() {
	}

	public Event(String place, String desc, LocalDate date) {
		this.place = new SimpleStringProperty(place);
		this.desc = new SimpleStringProperty(desc);
		this.time = new SimpleObjectProperty<LocalDate>(date);
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
	
	public void setTimeLD(LocalDate time) {
		this.time.set(time);;
	}

	public void setDesc(String text) {
		this.desc.set(text);		
	}

	public void setPlace(String text) {
		this.place.set(text);		
	}
	
	public LocalDate getTimeLD(){
		return this.time.get();
	}

	public String getDescString() {
		return this.desc.get();
	}

	public String getPlaceString() {
		return this.place.get();
	}
}
