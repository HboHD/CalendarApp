package pajh.calendar.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import pajh.calendar.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.lang.String;


/**
 * Helper class for database's usage. Allows to create a connection with database,
 * creating SQL query for adding event's fields to database, getting list
 * of events from database and inserting it into eventData.
 */
public class DatabaseUtil {


	/**
	 * Creates and establish connection with database at given URL and then returns it.
	 *
	 * @param filepath
	 * @return con Connection to database
	 * @throws SQLException
	 */
	public static Connection getConnection(String filepath)
			throws SQLException {

	    		Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + filepath);
	    		return con;
			}


	/**
	 * Executes SQL query to read event's field from database,
	 * then saves it to proper fields for every event in database.
	 * Finally returns ObservableList of events.
	 *
	 * @param con Connection to database
	 * @return eventData List of type Event
	 * @throws SQLException
	 */
	public static ObservableList<Event> getList(Connection con)
		    throws SQLException {

		    	Statement stmt = null;
		    	ObservableList<Event> eventData = FXCollections.observableArrayList();

		    	stmt = con.createStatement();
		    	ResultSet rs = stmt.executeQuery("SELECT Miejsce, Data, Godzina, Opis FROM Zdarzenia");
		    	while (rs.next()) {

		    		Event event = new Event();

		    		String place= rs.getString("Miejsce");
		    		LocalDate date= rs.getDate("Data").toLocalDate();
		    		LocalTime time= rs.getTime("Godzina").toLocalTime();
		    		String description= rs.getString("Opis");

		    		event.setPlace(place);
		    		event.setDate(date);
		    		event.setTime(time);
		    		event.setDesc(description);

		    		eventData.add(event);
		    	}
		    	return eventData;
			}

	/**
	 * Gets connection to database and list of events. Converts all fields
	 * of event to proper types and executes query to insert prepared event to database.
	 *
	 * @param con Connection to database
	 * @param eventData ObservableList of events
	 * @throws SQLException
	 */
	public static void setList(Connection con, ObservableList<Event> eventData)
		    throws SQLException {

				PreparedStatement prepStmt = con.prepareStatement(
						"insert into Zdarzenia values (NULL, ?, ?, ?, ?);");

				eventData.forEach(event -> {

					String place = event.placeProperty().toString();
					Date date = java.sql.Date.valueOf(event.getDate());
					Time time = java.sql.Time.valueOf(event.getTime());
					String description = event.getDesc();

					try {
						prepStmt.setString(1, place);
						prepStmt.setDate(2, date);
						prepStmt.setTime(3, time);
						prepStmt.setString(4, description);
						prepStmt.execute();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
}