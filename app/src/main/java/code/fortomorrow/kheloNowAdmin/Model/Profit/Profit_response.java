package code.fortomorrow.kheloNowAdmin.Model.Profit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profit_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public Integer m;
    @SerializedName("total_winning_amount")
    @Expose
    public Integer totalWinningAmount;
    @SerializedName("total_refund_amount")
    @Expose
    public Integer totalRefundAmount;
    @SerializedName("net_income")
    @Expose
    public Integer netIncome;
}
