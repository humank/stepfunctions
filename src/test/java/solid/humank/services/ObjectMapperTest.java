package solid.humank.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import solid.humank.model.EC2Request;

public class ObjectMapperTest {

    @Test
    public void pojoToJsonTest() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(new EC2Request());
        System.out.println(result);
    }
}
