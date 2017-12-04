package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.NotifyInfo;
import solid.humank.solid.humank.exception.EC2RequestException;

public class RequestSpotSuccessHandler implements RequestHandler<EC2RequestResult, NotifyInfo> {

    @Override
    public NotifyInfo handleRequest(EC2RequestResult input, Context context) throws EC2RequestException {

        try {
            LambdaLogger logger = context.getLogger();

            NotifyInfo notifyInfo = new NotifyInfo();
            notifyInfo.setEc2RequestResult(input);
            notifyInfo.setResult(input.getResult());
            notifyInfo.setMailFrom("yikaikao@amazon.com");
            notifyInfo.setMailTo("yikaikao@gmail.com");
            notifyInfo.setBody("Your request to have ec2 resources are successfully created.");
            notifyInfo.setSubject("Step Function with Lambda serve EC2 Request");

            return notifyInfo;
        } catch (Exception ex) {
            throw new EC2RequestException(ex.getMessage());
        }

    }
}
