package code.fortomorrow.kheloNowAdmin.Model.EarnHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Earn_history_response {


    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public M m;

    public class M {

        @SerializedName("total_paid_amount")
        @Expose
        public Integer totalPaidAmount;
        @SerializedName("total_winning_amount")
        @Expose
        public Integer totalWinningAmount;
        @SerializedName("total_refund_amount")
        @Expose
        public Integer totalRefundAmount;
        @SerializedName("total_add_money")
        @Expose
        public Integer totalAddMoney;
        @SerializedName("total_withdraw_money")
        @Expose
        public Integer totalWithdrawMoney;

    }
}
