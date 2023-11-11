package code.fortomorrow.kheloNowAdmin.Model.UserAbove;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User_above_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public Integer m;
    @SerializedName("list")
    @Expose
    public List<Data> data = null;

    public class Data {

        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("balance")
        @Expose
        public Integer balance;
        @SerializedName("deposit_balance")
        @Expose
        public Integer depositBalance;
        @SerializedName("winning_balance")
        @Expose
        public Integer winningBalance;

    }
}
