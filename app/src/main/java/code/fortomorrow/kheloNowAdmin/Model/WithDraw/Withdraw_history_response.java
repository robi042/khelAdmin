package code.fortomorrow.kheloNowAdmin.Model.WithDraw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Withdraw_history_response {
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

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("phone_no")
        @Expose
        public String phoneNo;
        @SerializedName("payment_method")
        @Expose
        public String paymentMethod;
        @SerializedName("requested_time")
        @Expose
        public String requestedTime;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getRequestedTime() {
            return requestedTime;
        }

        public void setRequestedTime(String requestedTime) {
            this.requestedTime = requestedTime;
        }
    }
}
