package solid.humank.model;

import lombok.Data;

@Data
public class EC2RequestResult {

    private String metadata;
    private String result;
    private EC2Request originRqeust;

    public EC2RequestResult() {

    }

    public EC2RequestResult(String requestResult) {
        metadata = requestResult;
    }
}
