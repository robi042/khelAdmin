package code.fortomorrow.kheloNowAdmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Check_admin_status_response {
    @SerializedName("e")
    @Expose
    public Integer e ;
    @SerializedName("m")
    @Expose
    public M m;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public M getM() {
        return m;
    }

    public void setM(M m) {
        this.m = m;
    }

    public class M {

        @SerializedName("add_admin")
        @Expose
        public Boolean addAdmin;

        public Boolean getAddAdmin() {
            return addAdmin;
        }

        public void setAddAdmin(Boolean addAdmin) {
            this.addAdmin = addAdmin;
        }
    }
}
