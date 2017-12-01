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

    public CreateAutoScalingGroupResult requestASGEC2(String asgName, String launchConfigurationName, String imageId, String instanceType, String targetGroupArn, List<Tag> tags, String vpcIdSubnets, String keyName, double spotPrice) {
        //build client
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        //Get current region AZs
        List<String> azNames = getAZs(ec2);

        AmazonAutoScaling autoScaling = AmazonAutoScalingClientBuilder.defaultClient();

        CreateAutoScalingGroupRequest asgRequest = new CreateAutoScalingGroupRequest();

        //create launch configuration
        createLaunchConfiguration(launchConfigurationName, autoScaling, imageId, instanceType, keyName, spotPrice);

        asgRequest.withAutoScalingGroupName(asgName)
                .withLaunchConfigurationName(launchConfigurationName)
                .withAvailabilityZones(azNames)
                .withDesiredCapacity(3)
                .withMaxSize(6)
                .withMinSize(3)
                .withTargetGroupARNs(targetGroupArn)
                .withVPCZoneIdentifier(vpcIdSubnets)
                .withTags(tags);

        CreateAutoScalingGroupResult result = autoScaling.createAutoScalingGroup(asgRequest);
        logger.info("Running Result : {}", result);
        return result;
    }

    private String createLaunchConfiguration(String launchConfigurationName, AmazonAutoScaling as, String imageId, String instanceType, String keyName, double spotPrice) {
        CreateLaunchConfigurationRequest lcr = new CreateLaunchConfigurationRequest();
        lcr.withImageId(imageId)
                .withInstanceType(instanceType)
                .withKeyName(keyName)
                //.withSecurityGroups("lab-SG-PKDT24OQIGEE-EC2HostSecurityGroup-GQ9GPFW3WNZF")
                .withLaunchConfigurationName(launchConfigurationName)
                .withAssociatePublicIpAddress(true);
        if (spotPrice > 0) {
            lcr.withSpotPrice(Double.toString(spotPrice));
        }

        CreateLaunchConfigurationResult result = as.createLaunchConfiguration(lcr);
        logger.info("Create LC result : {}", result.toString());
        return result.getSdkResponseMetadata().toString();
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
