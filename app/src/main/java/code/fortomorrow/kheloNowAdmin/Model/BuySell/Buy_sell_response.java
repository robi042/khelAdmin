package code.fortomorrow.kheloNowAdmin.Model.BuySell;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Buy_sell_response {

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

        @SerializedName("product_id")
        @Expose
        public Integer productId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("hasDiscount")
        @Expose
        public Boolean hasDiscount;
        @SerializedName("discount")
        @Expose
        public Integer discount;
        @SerializedName("discounted_price")
        @Expose
        public Float discountedPrice;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Boolean getHasDiscount() {
            return hasDiscount;
        }

        public void setHasDiscount(Boolean hasDiscount) {
            this.hasDiscount = hasDiscount;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public Float getDiscountedPrice() {
            return discountedPrice;
        }

        public void setDiscountedPrice(Float discountedPrice) {
            this.discountedPrice = discountedPrice;
        }
    }
}
