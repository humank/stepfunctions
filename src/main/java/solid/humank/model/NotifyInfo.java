package solid.humank.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotifyInfo {

    private String result;
    private String subject;
    private String mailFrom;
    private String mailTo;
    private String body;
    private EC2RequestResult ec2RequestResult;

    public NotifyInfo() {

    }

    public NotifyInfo(String subject) {
        this.subject = subject;
    }

}
