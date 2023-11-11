package code.fortomorrow.kheloNowAdmin.Model.AddMoneyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Add_money_accepted_list_response {
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

        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("phone_number")
        @Expose
        public String phoneNumber;
        @SerializedName("payment_method")
        @Expose
        public String paymentMethod;
        @SerializedName("requested_time")
        @Expose
        public String requestedTime;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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
