package solid.humank.solid.humank.requestEC2;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import solid.humank.model.AutoScalingGroupParams;
import solid.humank.model.EC2Request;
import solid.humank.model.LaunchConfigurationParams;
import solid.humank.uitls.ASGCreator;
import solid.humank.uitls.AsgUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SpotASGWithExpectedPriceTest {
    public static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(OnDemandASGEC2Test.class);

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
        launchConfigurationName = AsgUtil.defineLCName();
        asgName = AsgUtil.defineAsgName();
        imageId = "ami-da9e2cbc";
        instanceType = "t2.micro";
        appTargetGroupArn = "arn:aws:elasticloadbalancing:ap-northeast-1:584518143473:targetgroup/TG-lab-ALB-16NABNOLSNMWC/9f8c337c46e80d77";
        tags = new HashMap<String, String>();
        tags.put("Name", "myInstance");
        vpcIdSubnets = "subnet-77f8703e, subnet-43a36218";
        spotPrice = 0.02;
        securityGroups = "lab-SG-PKDT24OQIGEE-EC2HostSecurityGroup-GQ9GPFW3WNZF";
        targetCapacity = 2;
        desiredCapacity = 5;
        maxSize = 5;
        minSize = 2;

    }

    @Before
    public void check_aws_credential() {

        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        ec2.describeVpcs();
    }

    @Test
    public void request_for_on_demand_ec2_with_asg() {

        ASGCreator asgCreator = new ASGCreator();
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
        asgParams.setMaxSize(maxSize);
        asgParams.setMinSize(minSize);
        asgParams.setTags(tags);

        ec2Request.setLaunchConfigurationParams(lcParams);
        ec2Request.setAutoScalingGroupParams(asgParams);
        String result = asgCreator.requestSpotEC2(ec2Request);

        assertEquals("{}{}", result.toString());
    }


}
