package code.fortomorrow.kheloNowAdmin.Model.EarnHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refer_income_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("income_from_refer")
        @Expose
        public Integer incomeFromRefer;

    }
}
