package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.NotifyInfo;

public class ResultNotifyHandler implements RequestHandler<EC2RequestResult,NotifyInfo> {
    @Override
    public NotifyInfo handleRequest(EC2RequestResult input, Context context) {
        return null;
    }


    public void sendEmail(){

    }

}
