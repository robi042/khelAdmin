package code.fortomorrow.kheloNowAdmin.Model.AddMoneyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accept_money_history_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public M m;


    public class M {

        @SerializedName("total")
        @Expose
        public Integer total;
        @SerializedName("Bkash_add_amount")
        @Expose
        public Integer bkashAddAmount;
        @SerializedName("Nagad_add_amount")
        @Expose
        public Integer nagadAddAmount;
        @SerializedName("Rocket_add_amount")
        @Expose
        public Integer rocketAddAmount;

    }
}
