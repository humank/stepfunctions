package solid.humank.uitls;

import com.amazonaws.services.autoscaling.model.Tag;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Tag> defineInstanceTags() {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(
                new Tag().withKey("Name")
                        .withValue(String.format("ASG-%s", getCurrentDateTimeInYMDHMS()))
                        .withPropagateAtLaunch(true)
        );
        return tags;
    }
}
