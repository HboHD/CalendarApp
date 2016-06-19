package pajh.calendar.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import pajh.calendar.util.LocalDateAdapter;
import pajh.calendar.util.LocalTimeAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {

	private StringProperty place;
	private StringProperty desc;
	private ObjectProperty<LocalDate> date;
	private ObjectProperty<LocalTime> time;
	private IntegerProperty alarm;
	private Alarm alarmW;

	public Event() {
	}

	public Event(String place, String desc, LocalDate date, LocalTime time) {
		this.place = new SimpleStringProperty(place);
		this.desc = new SimpleStringProperty(desc);
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.time = new SimpleObjectProperty<LocalTime>(time);
		this.alarm = new SimpleIntegerProperty();
		this.alarmW = null;
	}

	public void setAlarm(Alarm alarm) {
		this.alarmW = alarm;
	}

	public void deleteAlarm() {
		this.alarmW = null;
	}

	public Alarm getAlarmObject() {
		return this.alarmW;
	}
	
	public int getAlarm() {
		return this.alarm.get();
	}

	public StringProperty placeProperty() {
		return place;
	}

	public void setPlace(StringProperty place) {
		this.place = place;
	}

	public StringProperty descProperty() {
		return desc;
	}

	public void setDesc(StringProperty desc) {
		this.desc = desc;
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}

	public void setDate(ObjectProperty<LocalDate> date) {
		this.date = date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	public void setDesc(String text) {
		this.desc.set(text);
	}

	public void setPlace(String text) {
		this.place.set(text);
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return this.date.get();
	}

	public String getPlace() {
		return this.place.get();
	}

	public IntegerProperty alarmProperty() {
		return alarm;
	}

	public void setAlarm(IntegerProperty alarm) {
		this.alarm = alarm;
	}

	public void setAlarm(int alarm) {
		this.alarm.set(alarm);
	}

	public ObjectProperty<LocalTime> timeProperty() {
		return time;
	}

	public void setTime(ObjectProperty<LocalTime> time) {
		this.time = time;
	}

	public void setTime(LocalTime time) {
		this.time.set(time);
	}

	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	public LocalTime getTime() {
		return this.time.get();
	}

	public String getDesc() {
		return this.desc.get();
	}

}
