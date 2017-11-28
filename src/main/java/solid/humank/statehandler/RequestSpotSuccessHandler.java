package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.NotifyInfo;
import solid.humank.model.ExecuteResult;

public class RequestSpotSuccessHandler implements RequestHandler<EC2RequestResult, NotifyInfo> {

    @Override
    public NotifyInfo handleRequest(EC2RequestResult input, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("check param result : " + input.getResult());

        return new NotifyInfo(ExecuteResult.SPOT_INSTANCT_REQUEST_SUCCESS.toString());
    }
}
