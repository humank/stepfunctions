package solid.humank.solid.humank.requestEC2;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.services.autoscaling.model.CreateAutoScalingGroupResult;
import com.amazonaws.services.autoscaling.model.Tag;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import solid.humank.uitls.ASGCreator;
import solid.humank.uitls.AsgUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OnDemandASGEC2Test {

    public static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(OnDemandASGEC2Test.class);

    String launchConfigurationName;
    String asgName;
    String imageId;
    String instanceType;
    String targetGroupArn;
    List<Tag> tags;
    String vpcIdSubnets;
    String keyName;
    double spotPrice;
    String securityGroups;

    @Before
    public void init_vpc_environment() {
        launchConfigurationName = AsgUtil.defineLCName();
        asgName = AsgUtil.defineAsgName();
        imageId = "ami-da9e2cbc";
        instanceType = "t2.micro";
        targetGroupArn = "arn:aws:elasticloadbalancing:ap-northeast-1:584518143473:targetgroup/TG-lab-ALB-16NABNOLSNMWC/9f8c337c46e80d77";
        tags = AsgUtil.defineInstanceTags();
        vpcIdSubnets = "subnet-77f8703e, subnet-43a36218";
        spotPrice = 0.0;
        securityGroups = "lab-SG-PKDT24OQIGEE-EC2HostSecurityGroup-GQ9GPFW3WNZF";
    }

    @Before
    public void check_aws_credential() {

        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        ec2.describeVpcs();
    }

    @Test
    public void request_for_on_demand_ec2_with_asg() {

        ASGCreator asgCreator = new ASGCreator();
        CreateAutoScalingGroupResult result = asgCreator.requestASGEC2(asgName, launchConfigurationName, imageId, instanceType, targetGroupArn, tags, vpcIdSubnets, keyName, spotPrice, securityGroups);

        ResponseMetadata metadata = result.getSdkResponseMetadata();
        assertEquals("{}", result.toString());


    }


}
