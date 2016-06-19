package pajh.calendar.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class TimeUtil {

 /** The time pattern that is used for conversion. */
 private static final String TIME_PATTERN = "HH:mm";

 	/** The time formatter. */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Returns the given time as a well formatted String. The above defined
     * {@link TimeUtil#TIME_PATTERN} is used.
     *
     * @param time the time to be returned as a string
     * @return formatted string
     */
    public static String format(LocalTime time) {
        if (time == null) {
            return null;
        }
        return TIME_FORMATTER.format(time);
    }

    /**
     * Converts a String in the format of the defined {@link TimeUtil#TIME_PATTERN}
     * to a {@link LocalTime} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param timeString the time as String
     * @return the time object or null if it could not be converted
     */
    public static LocalTime parse(String timeString) {
        try {
            return TIME_FORMATTER.parse(timeString, LocalTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid time.
     *
     * @param timeString
     * @return true if the String is a valid time
     */
    public static boolean validTime(String timeString) {
        return TimeUtil.parse(timeString) != null;
    }

}