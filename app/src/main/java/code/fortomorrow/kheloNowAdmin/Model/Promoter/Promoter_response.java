package code.fortomorrow.kheloNowAdmin.Model.Promoter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promoter_response {

    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;


    public class M {
        @SerializedName("promoter_id")
        @Expose
        public Integer promoter_id;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("status")
        @Expose
        public String status;

    }

}
