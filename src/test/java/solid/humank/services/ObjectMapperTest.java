package solid.humank.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import solid.humank.model.EC2Request;

import java.io.IOException;

public class ObjectMapperTest {

    @Test
    public void pojoToJsonTest() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        EC2Request request = new EC2Request();


        String result = mapper.writeValueAsString(new EC2Request());

        System.out.println(result);

    }
}
