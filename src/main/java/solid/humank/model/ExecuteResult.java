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
    SEND_MAIL_SUCCESS {
        public String toString() {
            return "SEND_MAIL_SUCCESS";
        }
    },
    SEND_MAIL_FAIL {
        public String toString() {
            return "SEND_MAIL_FAIL";
        }
    },
    ON_DEMAND_REQUEST_SUCCESS {
        public String toString() {
            return "SUCCESS";
        }
    },
    ON_DEMAND_REQUEST_FAIL {
        public String toString() {
            return "FAIL";
        }
    }
}
