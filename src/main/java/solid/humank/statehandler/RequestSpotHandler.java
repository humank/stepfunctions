package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2Request;
import solid.humank.model.EC2RequestResult;

public class RequestSpotHandler implements RequestHandler<EC2Request, EC2RequestResult> {

    public static final String REQUEST_SUCCESS = "SUCCESS";

    @Override
    public EC2RequestResult handleRequest(EC2Request input, Context context) {

        return new EC2RequestResult(REQUEST_SUCCESS);
    }
}
