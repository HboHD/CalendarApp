package pajh.calendar.util;

import pajh.calendar.model.Event;
import javafx.collections.ObservableList;

public class iCalendarFormatUtil {
	
	public static String ConvertToiCalendarFormat(ObservableList<Event> eventList)
	{
		String documentPrefix = "BEGIN:VCALENDAR\n";
		String documentSufix = "END:VCALENDAR\n";
		
		String eventPrefix = "BEGIN:VEVENT\n";
		String eventSufix = "END:VEVENT\n";
		
		StringBuffer returnStatement = new StringBuffer();
		
		returnStatement.append(documentPrefix);
		for(Event event : eventList)
		{
			returnStatement.append(eventPrefix);
			returnStatement.append("DESCRIPTION:" + event.descProperty()+ "\n");
			returnStatement.append("DTSTART:" + event.dateProperty().toString().replace("-", "") + "T" +
					event.timeProperty().toString().replace(":", "").substring(0, 4) + "00\n");
			returnStatement.append("LOCATION:" + event.placeProperty() + "\n");
			returnStatement.append("SEQUENCE:0\n");
			returnStatement.append(eventSufix);
		}
		returnStatement.append(documentSufix);
		
		return returnStatement.toString();
	}

}