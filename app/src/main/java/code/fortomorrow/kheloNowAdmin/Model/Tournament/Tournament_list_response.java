package code.fortomorrow.kheloNowAdmin.Model.Tournament;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tournament_list_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("start_date")
        @Expose
        public String startDate;
        @SerializedName("end_date")
        @Expose
        public String endDate;
        @SerializedName("entry_fee")
        @Expose
        public String entryFee;
        @SerializedName("total_prize")
        @Expose
        public String total_prize;
        @SerializedName("total_team")
        @Expose
        public String totalTeam;
        @SerializedName("hasFirstPrize")
        @Expose
        public Boolean hasFirstPrize;
        @SerializedName("first_prize")
        @Expose
        public String firstPrize;
        @SerializedName("hasThirdPrize")
        @Expose
        public Boolean hasThirdPrize;
        @SerializedName("third_prize")
        @Expose
        public String thirdPrize;
        @SerializedName("hasSeventhPrize")
        @Expose
        public Boolean hasSeventhPrize;
        @SerializedName("seventh_prize")
        @Expose
        public String seventhPrize;

    }
}
