package pajh.calendar.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of events. This is used for saving the
 * list of events to XML.
 */
@XmlRootElement(name = "events")
public class EventListWrapper {

    private List<Event> events;

    /**
     * Returns List of type Event.
     * 
     * @return events List<Event>
     */
    @XmlElement(name = "event")
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets EventListWrapper's List of type Event.
     * 
     * @param events List<Event> of events
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }
}