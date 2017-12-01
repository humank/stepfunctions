package solid.humank.solid.humank.requestEC2;

import com.amazonaws.services.autoscaling.model.Tag;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import solid.humank.uitls.ASGCreator;

import java.util.ArrayList;
import java.util.List;

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

    @Before
    public void init_vpc_environment() {
        launchConfigurationName = defineLCName();
        asgName = defineAsgName();
        imageId = "ami-da9e2cbc";
        instanceType = "t2.micro";
        targetGroupArn = "arn:aws:elasticloadbalancing:ap-northeast-1:584518143473:targetgroup/TG-lab-ALB-16NABNOLSNMWC/9f8c337c46e80d77";
        tags = createTags();
        vpcIdSubnets = "subnet-77f8703e, subnet-43a36218";
    }

    private List<Tag> createTags() {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(
                new Tag().withKey("Name")
                        .withValue("InstanceFromASG")
                        .withPropagateAtLaunch(true)
        );
        return tags;
    }

    @Before
    public void check_aws_credential() {

        AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        ec2.describeVpcs();
    }

    @Test
    public void request_for_on_demand_ec2_with_asg() {

        ASGCreator asgCreator = new ASGCreator();
        asgCreator.requestASGOnDemandEC2(asgName, launchConfigurationName, imageId, instanceType, targetGroupArn, tags, vpcIdSubnets, keyName);

    }

    private String defineLCName() {
        return String.format("LC-%s", getDateTimeInYMDHMS());
    }

    private String defineAsgName() {
        return String.format("ASG-%s", getDateTimeInYMDHMS());
    }

    private String getDateTimeInYMDHMS() {
        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        return dt.toString(formatter);
    }


}
