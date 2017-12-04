package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.NotifyInfo;
import solid.humank.services.ASGCreator;
import solid.humank.solid.humank.exception.EC2RequestException;

public class RequestSpotFailHandler implements RequestHandler<EC2RequestResult, NotifyInfo> {

    @Override
    public NotifyInfo handleRequest(EC2RequestResult input, Context context) throws EC2RequestException {

        try {
            LambdaLogger logger = context.getLogger();

            requestSpotFailThenRequestOnDemand(input);

            EC2RequestResult ec2RequestResult = new ASGCreator().requestOndemandEC2(input.getOriginRqeust());

            NotifyInfo notifyInfo = new NotifyInfo();

            notifyInfo.setEc2RequestResult(ec2RequestResult);
            notifyInfo.setResult(ec2RequestResult.getResult());

            return notifyInfo;
        } catch (Exception ex) {
            throw new EC2RequestException(ex.getMessage());
        }
    }

    private void requestSpotFailThenRequestOnDemand(EC2RequestResult input) {
        input.getOriginRqeust().getLaunchConfigurationParams().setSpotPrice(0);
    }
}
