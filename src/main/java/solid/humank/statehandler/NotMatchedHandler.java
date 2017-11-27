package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2Request;
import solid.humank.model.EC2RequestResult;

public class NotMatchedHandler implements RequestHandler {


    @Override
    public Object handleRequest(Object input, Context context) {

        EC2RequestResult result = (EC2RequestResult) input;
        return result;
    }
}
