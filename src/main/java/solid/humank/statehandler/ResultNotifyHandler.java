package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.NotifyInfo;
import solid.humank.services.SESMailer;

public class ResultNotifyHandler implements RequestHandler<NotifyInfo, String> {
    @Override
    public String handleRequest(NotifyInfo notifyInfo, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("ready to send email");

        SESMailer mailer = new SESMailer();
        return mailer.send(notifyInfo);
    }

}
