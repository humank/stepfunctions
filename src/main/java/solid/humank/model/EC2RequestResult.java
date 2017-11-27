package solid.humank.model;

public class EC2RequestResult {

    public EC2RequestResult(){

    }

    public EC2RequestResult(String requestSuccess) {
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    private String Result;

}
