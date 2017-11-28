package solid.humank.model;

public class EC2RequestResult {

    public EC2RequestResult(){

    }

    public EC2RequestResult(String requestResult) {
        this.result = requestResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        result = result;
    }

    private String result;

}
