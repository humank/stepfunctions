package solid.humank.uitls;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.util.EC2MetadataUtils.getSecurityGroups;

public class SpotCreator {

    public static final Logger log4j = LogManager.getRootLogger();

    public void simple() {
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        try {
            CreateSecurityGroupRequest securityGroupRequest =
                    new CreateSecurityGroupRequest("GettingStartedGroup",
                            "Getting Started Security Group");
            ec2.createSecurityGroup(securityGroupRequest);
        } catch (AmazonServiceException ase) {
            System.out.println(ase.getMessage());
        }

        String ipAddr = "0.0.0.0/0";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ipAddr = addr.getHostAddress() + "/10";
        } catch (UnknownHostException e) {
            // Fail here...
        }
        ArrayList<String> ipRanges = new ArrayList<String>();
        ipRanges.add(ipAddr);
        ArrayList<IpPermission> ipPermissions = new ArrayList<IpPermission>();
        IpPermission ipPermission = new IpPermission();
        ipPermission.setIpProtocol("tcp");
        ipPermission.setFromPort(new Integer(22));
        ipPermission.setToPort(new Integer(22));
        //ipPermission.setIpRanges(ipRanges);
        ipPermissions.add(ipPermission);

        try {
            AuthorizeSecurityGroupIngressRequest ingressRequest =
                    new AuthorizeSecurityGroupIngressRequest(
                            "GettingStartedGroup", ipPermissions);
            ec2.authorizeSecurityGroupIngress(ingressRequest);
        } catch (AmazonServiceException ase) {
            System.out.println(ase.getMessage());
        }
        RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();
        requestRequest.setSpotPrice("0.03");
        requestRequest.setInstanceCount(Integer.valueOf(1));

        LaunchSpecification launchSpecification = new LaunchSpecification();
        launchSpecification.setImageId("ami-da9e2cbc");
        launchSpecification.setInstanceType("t2.micro");

        ArrayList<String> securityGroups = new ArrayList<String>();
        securityGroups.add("GettingStartedGroup");
        launchSpecification.setSecurityGroups(securityGroups);

        requestRequest.setLaunchSpecification(launchSpecification);

        RequestSpotInstancesResult requestResult =
                ec2.requestSpotInstances(requestRequest);
    }

    public void showvpcs() {
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        DescribeVpcsResult vpcresults = ec2.describeVpcs();
        List<Vpc> vpcs = vpcresults.getVpcs();
        for (Vpc target : vpcs) {
            target.getVpcId();
        }
    }

    public void showsgs() {
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        DescribeSecurityGroupsResult sgs = ec2.describeSecurityGroups();
        List<SecurityGroup> sgList = sgs.getSecurityGroups();
        for (SecurityGroup sg : sgList) {
            log4j.info("sg name : {}", sg.getGroupName());
            log4j.info("sg id : {}", sg.getGroupId());
        }
    }

    public void create() {

        EnvironmentVariableCredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                .withCredentials(provider)
                .withRegion(Regions.AP_NORTHEAST_1.getName())
                .build();

        RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();
        requestRequest.setSpotPrice("0.03");
        requestRequest.setInstanceCount(Integer.valueOf(5));

        requestRequest.setType(SpotInstanceType.Persistent);

        LaunchSpecification launchSpecification = new LaunchSpecification();
        launchSpecification.setImageId("ami-da9e2cbc");
        launchSpecification.setInstanceType("t2.micro");
        //launchSpecification.setSubnetId("subnet-77f8703e,subnet-43a36218");
        launchSpecification.setSubnetId("subnet-77f8703e, subnet-43a36218");

        ArrayList<String> securityGroups = new ArrayList<String>();
        //securityGroups.add("lab-SG-PKDT24OQIGEE-EC2HostSecurityGroup-GQ9GPFW3WNZF");
        //securityGroups.add("sg-facb3583");
        launchSpecification.setSecurityGroups(securityGroups);
        launchSpecification.setKeyName("labuserkey");


        requestRequest.setLaunchSpecification(launchSpecification);

        RequestSpotInstancesResult requestResult =
                ec2.requestSpotInstances(requestRequest);
    }
}

