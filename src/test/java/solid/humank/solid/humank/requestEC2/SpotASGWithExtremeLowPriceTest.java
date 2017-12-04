package solid.humank.solid.humank.requestEC2;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import solid.humank.model.*;
import solid.humank.services.ASGUtil;
import solid.humank.statehandler.RequestSpotHandler;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SpotASGWithExtremeLowPriceTest {

    public static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(SpotASGWithExtremeLowPriceTest.class);

    String launchConfigurationName;
    String asgName;
    String imageId;
    String instanceType;
    String appTargetGroupArn;
    Map<String, String> tags;
    String vpcIdSubnets;
    String keyName;
    double spotPrice;
    String securityGroups;
    int targetCapacity;
    int desiredCapacity;
    int maxSize;
    int minSize;


    @Before
    public void init_vpc_environment() {
        launchConfigurationName = ASGUtil.defineLCName();
        asgName = ASGUtil.defineAsgName();
        imageId = "ami-da9e2cbc";
        instanceType = "t2.micro";
        appTargetGroupArn = "arn:aws:elasticloadbalancing:ap-northeast-1:584518143473:targetgroup/TG-lab-ALB-16NABNOLSNMWC/9f8c337c46e80d77";
        tags = new HashMap<String, String>();
        tags.put("Name", "myInstance");
        vpcIdSubnets = "subnet-f7a263ac,subnet-74f8703d";
        spotPrice = 0.001;
        securityGroups = "lab-SG-PKDT24OQIGEE-EC2HostSecurityGroup-GQ9GPFW3WNZF";
        targetCapacity = 1;
        desiredCapacity = 3;
        maxSize = 3;
        minSize = 1;

    }

    @Before
    public void check_aws_credential() {

        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        ec2.describeVpcs();
    }

    @Test
    public void request_for_extreme_low_spotPrice_ec2_with_asg() throws JsonProcessingException {

        EC2Request ec2Request = new EC2Request();

        LaunchConfigurationParams lcParams = new LaunchConfigurationParams();
        lcParams.setAssociatePublicIpAddress(true);
        lcParams.setEc2HostSecurityGroup(securityGroups);
        lcParams.setImageId(imageId);
        lcParams.setInstanceType(instanceType);
        lcParams.setKeyName(keyName);
        lcParams.setLaunchConfigurationName(launchConfigurationName);
        lcParams.setSpotPrice(spotPrice);
        lcParams.setTargetCapacity(Integer.toString(targetCapacity));

        AutoScalingGroupParams asgParams = new AutoScalingGroupParams();
        asgParams.setAppLoadBalancerTargetGroupArn(appTargetGroupArn);
        asgParams.setAutoScalingGroupName(asgName);
        asgParams.setDesiredCapacity(desiredCapacity);
        asgParams.setLaunchConfigurationName(launchConfigurationName);
        asgParams.setVipIdSubNets(vpcIdSubnets);
        asgParams.setMaxSize(maxSize);
        asgParams.setMinSize(minSize);
        asgParams.setTags(tags);

        ec2Request.setLaunchConfigurationParams(lcParams);
        ec2Request.setAutoScalingGroupParams(asgParams);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ec2Request);
        logger.info(json);

        EC2RequestResult result = new RequestSpotHandler().handleRequest(ec2Request, LambdaMock.createMockContext());

        assertEquals(ExecuteResult.SPOT_INSTANCT_REQUEST_SUCCESS.toString(), result.getResult());
    }

}
