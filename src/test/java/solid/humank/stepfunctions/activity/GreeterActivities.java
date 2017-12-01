package solid.humank.stepfunctions.activity;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskRequest;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskResult;
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class GreeterActivities {

    /**
     * create spot instance or ec2 instance
     * http://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/tutorial-spot-adv-java.html
     */

    public static final Logger logger = LogManager.getLogger();
    public static final String ACTIVITY_ARN = "arn:aws:states:ap-northeast-1:584518143473:activity:get-greeting";


    public String getGreeting(String who) throws Exception {
        return "{\"Hello\": \"" + who + "\"}";
    }

    public static void main(final String[] args) throws Exception {
        logger.info("start it ");

        GreeterActivities greeterActivities = new GreeterActivities();
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSocketTimeout((int) TimeUnit.SECONDS.toMillis(70));

        EnvironmentVariableCredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        AWSStepFunctions client = AWSStepFunctionsClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_1)
                .withCredentials(provider)
                .withClientConfiguration(clientConfiguration)
                .build();

        GetActivityTaskResult result = client.getActivityTask(new GetActivityTaskRequest().withActivityArn(ACTIVITY_ARN));

        logger.info("check something.");

        while (true) {
            GetActivityTaskResult getActivityTaskResult =
                    client.getActivityTask(
                            new GetActivityTaskRequest().withActivityArn(ACTIVITY_ARN));
            logger.info("The tasktoken is :{}", getActivityTaskResult.getTaskToken());

            if (getActivityTaskResult.getTaskToken() != null) {
                try {
                    JsonNode json = Jackson.jsonNodeOf(getActivityTaskResult.getInput());
                    String greetingResult =
                            greeterActivities.getGreeting(json.get("who").textValue());
                    logger.info("I get the param from console, who : {}", greetingResult);
                    client.sendTaskSuccess(
                            new SendTaskSuccessRequest()
                                    .withOutput(greetingResult + "hey .. response from my intellij...")
                                    .withTaskToken(getActivityTaskResult.getTaskToken()));
                } catch (Exception e) {
                    client.sendTaskFailure(new SendTaskFailureRequest().withTaskToken(
                            getActivityTaskResult.getTaskToken()));
                }
            } else {
                //Thread.sleep(1000);
                logger.info("can not get tasktoken");
            }
        }
    }

}
