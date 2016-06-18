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

public class DatabaseUtil {
	
	
	
	public static Connection getConnection(String filepath) 
			throws SQLException {

	    		Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + filepath);
	    		return con;
			}
	
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

	public static void setList(Connection con, ObservableList<Event> eventData)
		    throws SQLException {

				PreparedStatement prepStmt = con.prepareStatement(
						"insert into Zdarzenia values (NULL, ?, ?, ?, ?);");
		
				eventData.forEach(event -> {
    		
					String place = event.getPlace().toString();
					Date date = java.sql.Date.valueOf(event.getDateLD());
					Time time = java.sql.Time.valueOf(event.getTimeLT());
					String description = event.getDescS();
    	
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