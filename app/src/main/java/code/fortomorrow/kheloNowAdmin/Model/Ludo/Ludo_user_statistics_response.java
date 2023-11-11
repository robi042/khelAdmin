package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_user_statistics_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("match_id")
        @Expose
        public Integer matchId;
        @SerializedName("match_title")
        @Expose
        public String matchTitle;
        @SerializedName("paid")
        @Expose
        public Integer paid;
        @SerializedName("winning_money")
        @Expose
        public String winningMoney;
        @SerializedName("refund_amount")
        @Expose
        public String refundAmount;

    }
}
