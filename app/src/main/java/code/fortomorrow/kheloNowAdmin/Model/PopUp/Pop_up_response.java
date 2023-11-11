package code.fortomorrow.kheloNowAdmin.Model.PopUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pop_up_response {
    @SerializedName("e")
    @Expose
    public Integer e;
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

        @SerializedName("popup_status")
        @Expose
        public Boolean popupStatus;

        public Boolean getPopupStatus() {
            return popupStatus;
        }

        public void setPopupStatus(Boolean popupStatus) {
            this.popupStatus = popupStatus;
        }
    }

}
