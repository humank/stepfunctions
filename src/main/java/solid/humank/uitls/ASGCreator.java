package solid.humank.uitls;

import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder;
import com.amazonaws.services.autoscaling.model.*;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ASGCreator {

    public static final Logger logger = LogManager.getLogger(ASGCreator.class);

    public void create() {

        //build client
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        //Get current region AZs
        List<String> azNames = getAZs(ec2);

        AmazonAutoScaling as = AmazonAutoScalingClientBuilder.defaultClient();

        CreateAutoScalingGroupRequest asgRequest = new CreateAutoScalingGroupRequest();

        //create launch configuration
        String launchConfigurationName = createLaunchConfiguration(as);

        //apply tags
        List<Tag> asgTags = applyTags();

        asgRequest.withAutoScalingGroupName("myAutoScalingGroup")
                .withLaunchConfigurationName(launchConfigurationName)
                .withAvailabilityZones(azNames)
                .withDesiredCapacity(5)
                .withMaxSize(10)
                .withMinSize(5)
                .withTargetGroupARNs("arn:aws:elasticloadbalancing:ap-northeast-1:584518143473:targetgroup/TG-lab-ALB-16NABNOLSNMWC/9f8c337c46e80d77")
                .withVPCZoneIdentifier("subnet-77f8703e, subnet-43a36218")
                .withTags(asgTags);

        CreateAutoScalingGroupResult result = as.createAutoScalingGroup(asgRequest);
        logger.info("Running Result : {}", result);

    }

    private List<Tag> applyTags() {
        List<Tag> asgTags = new ArrayList<Tag>();
        asgTags.add(
                new Tag().withKey("Name")
                    .withValue("InstanceFromASG")
                .withPropagateAtLaunch(true)
        );
        return asgTags;
    }

    private String createLaunchConfiguration(AmazonAutoScaling as) {
        CreateLaunchConfigurationRequest lcr = new CreateLaunchConfigurationRequest();
        lcr.withImageId("ami-da9e2cbc")
                .withInstanceType("t2.micro")
                .withKeyName("labuserkey")
                .withLaunchConfigurationName("myLC")
                .withSecurityGroups("lab-SG-PKDT24OQIGEE-EC2HostSecurityGroup-GQ9GPFW3WNZF")
                .withSpotPrice("0.02")
                .withLaunchConfigurationName("myLaunchConfigurationName")
                .withAssociatePublicIpAddress(true);

        String launchConfigurationName = lcr.getLaunchConfigurationName();
        CreateLaunchConfigurationResult result = as.createLaunchConfiguration(lcr);
        logger.info("Create LC result : {}", result.toString());
        return launchConfigurationName;
    }

    private List<String> getAZs(AmazonEC2 ec2) {
        DescribeAvailabilityZonesResult azsResult = ec2.describeAvailabilityZones();
        List<AvailabilityZone> azlist = azsResult.getAvailabilityZones();

        List<String> azNames = new ArrayList<String>();

        for (AvailabilityZone az : azlist) {
            azNames.add(az.getZoneName());
        }
        return azNames;
    }
}
