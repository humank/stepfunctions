package solid.humank.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotifyInfo {

    private String subject;

    private String mailFrom;
    private String mailTo;
    private String body;

    public NotifyInfo() {

    }

    public NotifyInfo(String subject) {
        this.subject = subject;
    }

}
