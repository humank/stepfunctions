package solid.humank.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EC2Request {

    private AutoScalingGroupParams autoScalingGroupParams;
    private LaunchConfigurationParams launchConfigurationParams;


}
