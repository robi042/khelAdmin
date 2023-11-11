package code.fortomorrow.kheloNowAdmin.Model.AddNewMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewMatchResponse {

    @SerializedName("e")
    @Expose
    private Integer e;
    @SerializedName("m")
    @Expose
    private String m;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
