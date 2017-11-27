package solid.humank.model;

public class EC2Request {

    private double spotPrice;
    private String[] PublicSubnets;
    private String applicationLoadBalancerTargetGroup;
    private String[] ec2HostSecurityGroup;
    private String keyPairName;
    private String instanceType;
    private String targetCapacity;

    private String runner = "Kim";


    public double getSpotPrice() {
        return spotPrice;
    }

    public EC2Request(){

    }

    public void setSpotPrice(double spotPrice) {
        this.spotPrice = spotPrice;
    }

    public String[] getPublicSubnets() {
        return PublicSubnets;
    }

    public void setPublicSubnets(String[] publicSubnets) {
        PublicSubnets = publicSubnets;
    }

    public String getApplicationLoadBalancerTargetGroup() {
        return applicationLoadBalancerTargetGroup;
    }

    public void setApplicationLoadBalancerTargetGroup(String applicationLoadBalancerTargetGroup) {
        this.applicationLoadBalancerTargetGroup = applicationLoadBalancerTargetGroup;
    }

    public String[] getEc2HostSecurityGroup() {
        return ec2HostSecurityGroup;
    }

    public void setEc2HostSecurityGroup(String[] ec2HostSecurityGroup) {
        this.ec2HostSecurityGroup = ec2HostSecurityGroup;
    }

    public String getKeyPairName() {
        return keyPairName;
    }

    public void setKeyPairName(String keyPairName) {
        this.keyPairName = keyPairName;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getTargetCapacity() {
        return targetCapacity;
    }

    public void setTargetCapacity(String targetCapacity) {
        this.targetCapacity = targetCapacity;
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }


}
