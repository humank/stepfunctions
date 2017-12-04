package solid.humank.model;

import lombok.Data;

@Data
public class LaunchConfigurationParams {

    private String launchConfigurationName;
    private String imageId;
    private String instanceType;
    private String keyName;
    private String ec2HostSecurityGroup;
    private boolean associatePublicIpAddress = true;
    private double spotPrice;
    private String targetCapacity;

}
