package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.ExecuteResult;
import solid.humank.model.NotifyInfo;
import solid.humank.uitls.SESMailer;

public class ResultNotifyHandler implements RequestHandler<EC2RequestResult, NotifyInfo> {
    @Override
    public NotifyInfo handleRequest(EC2RequestResult input, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("ready to send email");

        String mailFrom = "yikaikao@amazon.com";
        String mailTo = "yikaikao@gmail.com";

        SESMailer mailer = new SESMailer();
        NotifyInfo notyInfo = new NotifyInfo();
        mailer.send(notyInfo);

        return notyInfo;
    }

}
