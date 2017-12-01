package solid.humank.uitls;

import org.junit.Test;

public class CreateAutoScalingGroupTest {

    @Test
    public void createAutoScalingGroupTest(){

        ASGCreator creator = new ASGCreator();
        creator.create();
    }
}
