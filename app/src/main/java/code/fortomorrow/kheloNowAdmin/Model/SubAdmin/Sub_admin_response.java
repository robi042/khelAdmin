package code.fortomorrow.kheloNowAdmin.Model.SubAdmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sub_admin_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public List<M> getM() {
        return m;
    }

    public void setM(List<M> m) {
        this.m = m;
    }

    public class M {

        @SerializedName("sub_admin_id")
        @Expose
        public Integer subAdminId;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("status")
        @Expose
        public String status;

        public Integer getSubAdminId() {
            return subAdminId;
        }

        public void setSubAdminId(Integer subAdminId) {
            this.subAdminId = subAdminId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
