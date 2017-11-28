package solid.humank.model;

public enum ExecuteResult {

    SPOT_INSTANCT_REQUEST_SUCCESS {
        public String toString() {
            return "SUCCESS";
        }
    },
    SPOT_INSTANCE_REQUEST_FAIL {
        public String toString() {
            return "FAIL";
        }
    },
    SEND_MAIL_SUCCESS{
        public String toString(){
            return "SEND_MAIL_SUCCESS";
        }
    }
}
