package solid.humank.model;

import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class EC2Request {

    private double spotPrice;
    private String[] PublicSubnets;
    private String applicationLoadBalancerTargetGroup;
    private String[] ec2HostSecurityGroup;
    private String keyPairName;
    private String instanceType;
    private String targetCapacity;

    private String runner = "Kim";

    public EC2Request(){

    }

}
