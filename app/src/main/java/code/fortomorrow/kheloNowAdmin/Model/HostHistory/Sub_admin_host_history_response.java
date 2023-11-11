package code.fortomorrow.kheloNowAdmin.Model.HostHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sub_admin_host_history_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public class M {

        @SerializedName("sub_admin_id")
        @Expose
        public Integer subAdminId;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("total_host")
        @Expose
        public Integer totalHost;

    }
}
