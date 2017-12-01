package solid.humank.uitls;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.autoscaling.*;
import com.amazonaws.services.autoscaling.model.AutoScalingGroup;
import com.amazonaws.services.autoscaling.model.CreateLaunchConfigurationRequest;
import com.amazonaws.services.autoscaling.model.LaunchConfiguration;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import solid.humank.model.EC2Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EC2Utility {

    //http://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/tutorial-spot-adv-java.html


    public void spotInASG(EC2Request ec2Request) {


        // Retrieves the credentials from a AWSCrentials.properties file.
        AWSCredentials credentials = null;
//        try {
//            credentials = new PropertiesCredentials(
//                    GettingStartedApp.class.getResourceAsStream("AwsCredentials.properties"));
//        } catch (IOException e1) {
//            System.out.println("Credentials were not properly entered into AwsCredentials.properties.");
//            System.out.println(e1.getMessage());
//            System.exit(-1);
//        }

        EnvironmentVariableCredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        // Create the AmazonEC2 client so we can call various APIs.
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                .withCredentials(provider)
                .build();

        // Initializes a Spot Instance Request
        RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();

        // Request 1 x t1.micro instance with a bid price of $0.03.
        requestRequest.setSpotPrice("0.03");
        requestRequest.setInstanceCount(Integer.valueOf(1));

        // Setup the specifications of the launch. This includes the
        // instance type (e.g. t1.micro) and the latest Amazon Linux
        // AMI id available. Note, you should always use the latest
        // Amazon Linux AMI id or another of your choosing.

        LaunchSpecification launchSpecification = new LaunchSpecification();
        launchSpecification.setImageId("ami-8c1fece5");
        launchSpecification.setInstanceType("t1.micro");

        // Add the security group to the request.
        ArrayList<String> securityGroups = new ArrayList<String>();
        securityGroups.add("GettingStartedGroup");
        launchSpecification.setSecurityGroups(securityGroups);

        // Add the launch specifications to the request.
        requestRequest.setLaunchSpecification(launchSpecification);

        // Call the RequestSpotInstance API.
        RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(requestRequest);

        List<SpotInstanceRequest> requestResponses = requestResult.getSpotInstanceRequests();

// Setup an arraylist to collect all of the request ids we want to
// watch hit the running state.
        ArrayList<String> spotInstanceRequestIds = new ArrayList<String>();

// Add all of the request ids to the hashset, so we can determine when they hit the
// active state.
        for (SpotInstanceRequest requestResponse : requestResponses) {
            System.out.println("Created Spot Request: " + requestResponse.getSpotInstanceRequestId());
            spotInstanceRequestIds.add(requestResponse.getSpotInstanceRequestId());
        }

    }

    public void createASG(LaunchSpecification lcs) {

        AmazonAutoScaling asg = AmazonAutoScalingClientBuilder.defaultClient();
        //asg.setDesiredCapacity(4);

//        CreateLaunchConfigurationRequest lcreq = CreateLaunchConfigurationRequest.


        //asg.attachLoadBalancerTargetGroups();
        asg.attachLoadBalancers();


    }


}
