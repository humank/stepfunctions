package solid.humank.services;

import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClientBuilder;
import com.amazonaws.services.autoscaling.model.CreateAutoScalingGroupRequest;
import com.amazonaws.services.autoscaling.model.CreateAutoScalingGroupResult;
import com.amazonaws.services.autoscaling.model.CreateLaunchConfigurationRequest;
import com.amazonaws.services.autoscaling.model.CreateLaunchConfigurationResult;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import solid.humank.model.*;

import java.util.ArrayList;
import java.util.List;

public class ASGCreator {

    public static final Logger logger = LogManager.getRootLogger();


    public EC2RequestResult requestOndemandEC2(EC2Request originRqeust) {
        return requestEC2(originRqeust);
    }

    public EC2RequestResult requestSpotEC2(EC2Request originReqeust) {
        return requestEC2(originReqeust);
    }

    /**
     * @param ec2Request
     * @return Combination result of launch configuration creation and auto scaling group creation.
     */
    protected EC2RequestResult requestEC2(EC2Request ec2Request) {

        AmazonAutoScaling autoScaling = AmazonAutoScalingClientBuilder.defaultClient();

        String lcResult = createLaunchConfiguration(autoScaling, ec2Request.getLaunchConfigurationParams());
        String asgResult = createAutoScalingGroup(autoScaling, ec2Request.getAutoScalingGroupParams());

        EC2RequestResult ec2RequestResult = new EC2RequestResult();
        if (lcResult.equals("{}") && asgResult.equals("{}")) {
            ec2RequestResult.setResult(ExecuteResult.SPOT_INSTANCT_REQUEST_SUCCESS.toString());
        } else {
            ec2RequestResult.setResult(ExecuteResult.SPOT_INSTANCE_REQUEST_FAIL.toString());
            ec2RequestResult.setMetadata(lcResult + asgResult);
        }

        return ec2RequestResult;
    }

    private String createLaunchConfiguration(AmazonAutoScaling autoScaling, LaunchConfigurationParams launchConfigurationParams) {

        CreateLaunchConfigurationRequest launchConfigurationRequest = new CreateLaunchConfigurationRequest();
        launchConfigurationRequest.withImageId(launchConfigurationParams.getImageId())
                .withInstanceType(launchConfigurationParams.getInstanceType())
                .withKeyName(launchConfigurationParams.getKeyName())
                //.withSecurityGroups(securityGroups)
                .withLaunchConfigurationName(launchConfigurationParams.getLaunchConfigurationName())
                .withAssociatePublicIpAddress(true);

//        List<String> collectionSG = new ArrayList<String>();
//        collectionSG.add(securityGroups);
//        launchConfigurationRequest.withSecurityGroups(collectionSG);

        if (launchConfigurationParams.getSpotPrice() > 0) {
            launchConfigurationRequest.withSpotPrice(Double.toString(launchConfigurationParams.getSpotPrice()));

        }

        CreateLaunchConfigurationResult result = autoScaling.createLaunchConfiguration(launchConfigurationRequest);
        logger.info("Create LC result : {}", result.toString());
        return result.toString();

    }

    private String createAutoScalingGroup(AmazonAutoScaling autoScaling, AutoScalingGroupParams autoScalingGroupParams) {

        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        List<String> azNames = getAZs(ec2);

        //create auto scaling group request
        CreateAutoScalingGroupRequest autoScalingGroupRequest = new CreateAutoScalingGroupRequest();
        autoScalingGroupRequest.withAutoScalingGroupName(autoScalingGroupParams.getAutoScalingGroupName())
                .withLaunchConfigurationName(autoScalingGroupParams.getLaunchConfigurationName())
                .withAvailabilityZones(azNames)
                .withDesiredCapacity(autoScalingGroupParams.getDesiredCapacity())
                .withMaxSize(autoScalingGroupParams.getMaxSize())
                .withMinSize(autoScalingGroupParams.getMinSize())
                .withTargetGroupARNs(autoScalingGroupParams.getAppLoadBalancerTargetGroupArn())
                .withVPCZoneIdentifier(autoScalingGroupParams.getVipIdSubNets())
                .withTags(ASGUtil.defineInstanceTags(autoScalingGroupParams.getTags()));


        CreateAutoScalingGroupResult result = autoScaling.createAutoScalingGroup(autoScalingGroupRequest);
        logger.info("Running Result : {}", result);
        return result.toString();
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
