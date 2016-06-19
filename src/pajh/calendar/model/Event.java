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

/**
 * Event class that allow to create an event with: place, description,
 * date, time and alarm. Also allows to delete a created alarm for event.
 */
public class Event {
	
	/**
	 * String property for place.
	 */
	private StringProperty place;
	/**
	 * String property for description.
	 */
	private StringProperty desc;
	/**
	 * Object property of type LocalDate for date.
	 */
	private ObjectProperty<LocalDate> date;
	/**
	 * Object property of type LocalTime for time.
	 */
	private ObjectProperty<LocalTime> time;
	/**
	 * Integer property for alarm
	 */
	private IntegerProperty alarm;
	/**
	 * Alarm object for appending alarm to an event.
	 */
	private Alarm alarmW;

	/**
	 * Event class contruct.
	 */
	public Event() {
	}

	/**
	 * Event class construct for creating an event with parameters.
	 * Alarm is set to null as we not set alarm in default.
	 * 
	 * @param place Place
	 * @param desc Description
	 * @param date Date
	 * @param time Time
	 */
	public Event(String place, String desc, LocalDate date, LocalTime time) {
		this.place = new SimpleStringProperty(place);
		this.desc = new SimpleStringProperty(desc);
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.time = new SimpleObjectProperty<LocalTime>(time);
		this.alarm = new SimpleIntegerProperty();
		this.alarmW = null;
	}

	/**
	 * Appends Alarm object to an event's alarm.
	 * 
	 * @param alarm Alarm object
	 */
	public void setAlarm(Alarm alarm) {
		this.alarmW = alarm;
	}
	/**
	 * Deletes an alarm for an event.
	 */
	public void deleteAlarm() {
		this.alarmW = null;
	}
	/**
	 * Returns Alarm object.
	 * 
	 * @return Alarm object
	 */
	public Alarm getAlarmObject() {
		return this.alarmW;
	}
	/**
	 * Returns alarm's value.
	 * 
	 * @return Alarm as integer
	 */
	public int getAlarm() {
		return this.alarm.get();
	}

	/**
	 * Returns place property.
	 * 
	 * @return Place property
	 */
	public StringProperty placeProperty() {
		return place;
	}

	/**
	 * Sets place property.
	 * 
	 * @param place Place property
	 */
	public void setPlace(StringProperty place) {
		this.place = place;
	}
	/**
	 * Returns description property.
	 * 
	 * @return desc Description property
	 */
	public StringProperty descProperty() {
		return desc;
	}
	/**
	 * Sets description property.
	 * 
	 * @param desc Description property
	 */
	public void setDesc(StringProperty desc) {
		this.desc = desc;
	}
	/**
	 * Returns date property as ObjectProperty of type LocalDate
	 * 
	 * @return date Date property
	 */
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}
	/**
	 * Sets date property.
	 * 
	 * @param date ObjectProperty of type LocalDate
	 */
	public void setDate(ObjectProperty<LocalDate> date) {
		this.date = date;
	}
	
	/**
	 * Sets date as LocalDate.
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date.set(date);
	}

	/**
	 * Sets description with a String.
	 * @param text String with description
	 */
	public void setDesc(String text) {
		this.desc.set(text);
	}

	/**
	 * Sets place with a String.
	 * @param text String with place
	 */
	public void setPlace(String text) {
		this.place.set(text);
	}

	/**
	 * Returns date as a LocalDate.
	 * 
	 * @return date
	 */
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return this.date.get();
	}

	/**
	 * Returns place as a String.
	 * @return place Place as a String
	 */
	public String getPlace() {
		return this.place.get();
	}

	/**
	 * Returns alarm as an Integer Property.
	 * 
	 * @return alarm Alarm as IntegerProperty
	 */
	public IntegerProperty alarmProperty() {
		return alarm;
	}

	/**
	 * Sets Alarm with IntegerProperty.
	 * @param alarm
	 */
	public void setAlarm(IntegerProperty alarm) {
		this.alarm = alarm;
	}

	/**
	 * Sets Alarm with integer.
	 * @param alarm
	 */
	public void setAlarm(int alarm) {
		this.alarm.set(alarm);
	}

	/**
	 * Returns time as an ObjectProperty of type LocalTime
	 * 
	 * @return time
	 */
	public ObjectProperty<LocalTime> timeProperty() {
		return time;
	}

	/**
	 * Sets time with ObjectProperty of type LocalTime
	 * 
	 * @param time
	 */
	public void setTime(ObjectProperty<LocalTime> time) {
		this.time = time;
	}

	/**
	 * Sets time with LocalTime.
	 * @param time
	 */
	public void setTime(LocalTime time) {
		this.time.set(time);
	}

	/**
	 * Returns time as a LocalTime
	 * 
	 * @return time
	 */
	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	public LocalTime getTime() {
		return this.time.get();
	}

	/**
	 * Returns description as a String
	 * 
	 * @return description
	 */
	public String getDesc() {
		return this.desc.get();
	}

}
