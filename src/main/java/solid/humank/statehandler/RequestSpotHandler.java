package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import solid.humank.model.EC2Request;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.ExecuteResult;

public class RequestSpotHandler implements RequestHandler<EC2Request, EC2RequestResult> {

    //public static final Logger log4j = LogManager.getRootLogger();

    //http://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/tutorial-spot-adv-java.html
    //create spot instance


    public EC2RequestResult handleRequest(EC2Request input, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Get Request spot price : " + Double.toString(input.getSpotPrice()));
        //log4j.info("log info from log4j");


        /**
         * simulate scenario, if spot price > 0.2 then fail, procees flow go to on-demand.
         */
        if(input.getSpotPrice()>0.2){
            return new EC2RequestResult(ExecuteResult.SPOT_INSTANCE_REQUEST_FAIL.toString());
        }
        return new EC2RequestResult(ExecuteResult.SPOT_INSTANCT_REQUEST_SUCCESS.toString());
    }

}
