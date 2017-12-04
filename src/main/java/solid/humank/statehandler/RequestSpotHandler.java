package solid.humank.statehandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solid.humank.model.EC2Request;
import solid.humank.model.EC2RequestResult;
import solid.humank.services.ASGCreator;

public class RequestSpotHandler implements RequestHandler<EC2Request, EC2RequestResult> {

    public static final Logger log4j = LogManager.getRootLogger();

    //http://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/tutorial-spot-adv-java.html
    //create spot instance

    @Override
    public EC2RequestResult handleRequest(EC2Request input, Context context) {

        LambdaLogger logger = context.getLogger();
        log4j.info("Print out the input variable toString(): {}", input.toString());
        return new ASGCreator().requestSpotEC2(input);

    }

}
