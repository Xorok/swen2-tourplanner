package at.tiefenbrunner.swen2semesterprojekt.util;

import lombok.NonNull;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationFormat extends Format {

    /**
     * Formats a Duration into a human-readable string.
     *
     * @param obj        the Duration object to format
     * @param toAppendTo the StringBuffer to which the formatted text will be appended
     * @param pos        a FieldPosition identifying a field in the formatted text
     * @return the StringBuffer passed in as toAppendTo, with formatted text appended
     */
    @Override
    public StringBuffer format(Object obj, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
        if (!(obj instanceof Duration duration)) {
            throw new IllegalArgumentException("The object to format must be a non-null Duration.");
        }

        long days = duration.toDays();
        duration = duration.minus(days, ChronoUnit.DAYS);

        long hours = duration.toHours();
        duration = duration.minus(hours, ChronoUnit.HOURS);

        long minutes = duration.toMinutes();
        duration = duration.minus(minutes, ChronoUnit.MINUTES);

        long seconds = duration.getSeconds();
        long millis = duration.toMillisPart();

        if (days > 0) {
            toAppendTo.append(days).append("d ");
        }
        if (hours > 0) {
            toAppendTo.append(hours).append("h ");
        }
        if (minutes > 0) {
            toAppendTo.append(minutes).append("m ");
        }
        if (seconds > 0) {
            toAppendTo.append(seconds).append("s ");
        }
        if (millis > 0 || toAppendTo.isEmpty()) {  // Handle case when duration is less than a second
            toAppendTo.append(millis).append("ms ");
        }
        toAppendTo.setLength(toAppendTo.length() - 1); // remove extra space at the end

        return toAppendTo;
    }

    /**
     * Parses a string to produce a Duration. Not implemented in this example.
     *
     * @param source the string to parse
     * @param pos    a ParsePosition object with index and error index information
     * @return null (not implemented)
     */
    @Override
    public Object parseObject(String source, @NonNull ParsePosition pos) {
        throw new UnsupportedOperationException("Parsing a Duration from a String is not supported.");
    }
}