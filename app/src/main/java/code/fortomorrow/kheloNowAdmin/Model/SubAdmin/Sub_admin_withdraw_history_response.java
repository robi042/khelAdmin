package code.fortomorrow.kheloNowAdmin.Model.SubAdmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sub_admin_withdraw_history_response {
    public Integer e;
    @SerializedName("m")
    @Expose
    public Integer m;
    @SerializedName("withdraw_done_list")
    @Expose
    public List<WithdrawDone> withdrawDoneList = null;

    public class WithdrawDone {

        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("time")
        @Expose
        public String cashOutTime;


        @SerializedName("tranjection_id")
        @Expose
        public String transactionCode;

        @SerializedName("phone_number")
        @Expose
        public String phone_number;



    }
}
