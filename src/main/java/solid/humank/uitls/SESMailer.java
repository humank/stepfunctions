package solid.humank.uitls;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solid.humank.model.ExecuteResult;
import solid.humank.model.NotifyInfo;

public class SESMailer {

    public static final Logger logger = LogManager.getLogger(SESMailer.class);

    public String send(NotifyInfo notyInfo) {
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.US_WEST_2).build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(notyInfo.getMailTo()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(notyInfo.getBody())))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(notyInfo.getSubject())))
                    .withSource(notyInfo.getMailFrom());

            client.sendEmail(request);
            logger.info("Email Sent ! From : {} , to : {}", notyInfo.getMailFrom(), notyInfo.getMailTo());

            return ExecuteResult.SEND_MAIL_SUCCESS.toString();
        } catch (Exception ex) {
            logger.error("The Mail was not sent. Error Message : {}", ex.getMessage());

        }

        return ExecuteResult.SEND_MAIL_FAIL.toString();
    }
}
