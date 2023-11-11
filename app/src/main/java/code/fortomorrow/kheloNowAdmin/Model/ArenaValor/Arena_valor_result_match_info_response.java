package code.fortomorrow.kheloNowAdmin.Model.ArenaValor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Arena_valor_result_match_info_response {
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
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("first_player")
        @Expose
        public String firstPlayer;
        @SerializedName("second_player")
        @Expose
        public String secondPlayer;
        @SerializedName("third_player")
        @Expose
        public String thirdPlayer;
        @SerializedName("forth_player")
        @Expose
        public String forthPlayer;
        @SerializedName("fifth_player")
        @Expose
        public String fifthPlayer;
        @SerializedName("extra_one")
        @Expose
        public String extraOne;
        @SerializedName("extra_two")
        @Expose
        public String extraTwo;
        @SerializedName("rank")
        @Expose
        public Integer rank;
        @SerializedName("kill")
        @Expose
        public Integer kill;
        @SerializedName("winning_money")
        @Expose
        public Integer winningMoney;
        @SerializedName("refund_amount")
        @Expose
        public Integer refundAmount;
        @SerializedName("hasResult")
        @Expose
        public Boolean hasResult;

    }
}
