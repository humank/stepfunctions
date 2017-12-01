package solid.humank.uitls;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.IpPermission;
import solid.humank.model.EC2Request;

import java.util.ArrayList;

public class OndemandCreator {

    public void createSingleEC2(EC2Request input) {

        EnvironmentVariableCredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
                .withCredentials(provider)
                .build();

        CreateSecurityGroupRequest securityGroupRequest =
                new CreateSecurityGroupRequest("GettingStartedGroup",
                        "Getting Started Security Group");

        ec2.createSecurityGroup(securityGroupRequest);

        String ipCIDR = "10.180.32.0/0";

        ArrayList<String> ipRanges = new ArrayList<String>();
        ipRanges.add(ipCIDR);

        // Open up port 22 for TCP traffic to the associated IP from above (e.g. ssh traffic).
        ArrayList<IpPermission> ipPermissions = new ArrayList<IpPermission>();
        IpPermission ipPermission = new IpPermission();
        ipPermission.setIpProtocol("tcp");
        ipPermission.setFromPort(new Integer(22));
        ipPermission.setToPort(new Integer(22));
        //ipPermission.setIpRanges(ipRanges);
        ipPermissions.add(ipPermission);

        AuthorizeSecurityGroupIngressRequest ingressRequest =
                new AuthorizeSecurityGroupIngressRequest(
                        "GettingStartedGroup", ipPermissions);
        ec2.authorizeSecurityGroupIngress(ingressRequest);
    }

}
