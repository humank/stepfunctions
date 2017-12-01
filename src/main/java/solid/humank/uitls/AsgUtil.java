package solid.humank.uitls;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AsgUtil {

    public static String defineLCName() {
        return String.format("LC-%s", getCurrentDateTimeInYMDHMS());
    }

    public static String defineAsgName() {
        return String.format("ASG-%s", getCurrentDateTimeInYMDHMS());
    }

    public static String getCurrentDateTimeInYMDHMS() {
        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        return dt.toString(formatter);
    }
}
