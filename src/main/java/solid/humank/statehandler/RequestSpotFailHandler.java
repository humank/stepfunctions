package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.NotifyInfo;
import solid.humank.services.ASGCreator;

public class RequestSpotFailHandler implements RequestHandler<EC2RequestResult, NotifyInfo> {

    @Override
    public NotifyInfo handleRequest(EC2RequestResult input, Context context) {

        LambdaLogger logger = context.getLogger();

        requestSpotFailThenRequestOnDemand(input);

        EC2RequestResult ec2RequestResult = new ASGCreator().requestOndemandEC2(input.getOriginRqeust());

        NotifyInfo notifyInfo = new NotifyInfo();

        notifyInfo.setEc2RequestResult(ec2RequestResult);

        return notifyInfo;
    }

    private void requestSpotFailThenRequestOnDemand(EC2RequestResult input) {
        input.getOriginRqeust().getLaunchConfigurationParams().setSpotPrice(0);
    }
}
