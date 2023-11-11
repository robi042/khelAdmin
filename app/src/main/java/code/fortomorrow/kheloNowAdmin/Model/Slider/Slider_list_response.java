package code.fortomorrow.kheloNowAdmin.Model.Slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Slider_list_response {

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

        @SerializedName("slider_id")
        @Expose
        public Integer sliderId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("image_link")
        @Expose
        public String imageLink;

        public Integer getSliderId() {
            return sliderId;
        }

        public void setSliderId(Integer sliderId) {
            this.sliderId = sliderId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }
    }
}
