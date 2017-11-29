import org.apache.logging.log4j.Logger;
import org.junit.Test;
import solid.humank.model.EC2Request;

import static org.apache.logging.log4j.LogManager.getLogger;

public class LombokTest {

    @Test
    public void testObjectString(){
        EC2Request req = new EC2Request();
        Logger logger = getLogger();
        logger.info(req.toString());
    }
}
