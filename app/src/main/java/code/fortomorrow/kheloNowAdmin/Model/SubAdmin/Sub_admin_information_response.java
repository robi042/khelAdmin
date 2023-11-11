package code.fortomorrow.kheloNowAdmin.Model.SubAdmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sub_admin_information_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("accept_amount")
        @Expose
        public Integer acceptAmount;
        @SerializedName("requested_by")
        @Expose
        public String requestedBy;
        @SerializedName("requested_time")
        @Expose
        public String requestedTime;
        @SerializedName("accepted_by")
        @Expose
        public String acceptedBy;
        @SerializedName("accepted_time")
        @Expose
        public String acceptedTime;

        @SerializedName("tranjection_id")
        @Expose
        public String transactionCode;

    }
}
