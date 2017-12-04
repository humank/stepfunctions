package solid.humank.model;

import lombok.Data;

import java.util.Map;

@Data
public class AutoScalingGroupParams {

    private String autoScalingGroupName;
    private String launchConfigurationName;
    private String availabilityZoneNames;
    private int desiredCapacity;
    private int maxSize;
    private int minSize;
    private String appLoadBalancerTargetGroupArn;
    private String vipIdSubNets;
    private Map<String, String> tags;

}
