package solid.humank.statehandler;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.elasticmapreduce.model.CreateSecurityConfigurationRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solid.humank.model.EC2Request;
import solid.humank.model.EC2RequestResult;
import solid.humank.model.ExecuteResult;
import solid.humank.uitls.ASGCreator;

import java.util.ArrayList;

public class RequestSpotHandler implements RequestHandler<EC2Request, EC2RequestResult> {

    public static final Logger log4j = LogManager.getRootLogger();

    //http://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/tutorial-spot-adv-java.html
    //create spot instance


    public EC2RequestResult handleRequest(EC2Request input, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Get Request spot price : " + Double.toString(input.getSpotPrice()));
        log4j.info("log info from log4j");

        log4j.info("Print out the input variable toString(): {}", input.toString());

        /**
         * simulate scenario, if spot price > 0.2 then fail, procees flow go to on-demand.
         */
        if (input.getSpotPrice() > 0.2) {
            return new EC2RequestResult(ExecuteResult.SPOT_INSTANCE_REQUEST_FAIL.toString());
        }

            //TODO Create good Request PayLoad Object structure and gen json file.
            //new ASGCreator().requestASGEC2();

        return new EC2RequestResult(ExecuteResult.SPOT_INSTANCT_REQUEST_SUCCESS.toString());
    }

}
