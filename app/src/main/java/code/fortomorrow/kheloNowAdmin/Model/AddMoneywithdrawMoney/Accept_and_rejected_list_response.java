package code.fortomorrow.kheloNowAdmin.Model.AddMoneywithdrawMoney;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Add_money_accepted_list_response;

public class Accept_and_rejected_list_response {
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
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("payment_method")
        @Expose
        public String paymentMethod;
        @SerializedName("amount")
        @Expose
        public Integer amount;
        @SerializedName("requested_date")
        @Expose
        public String requestedDate;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getRequestedDate() {
            return requestedDate;
        }

        public void setRequestedDate(String requestedDate) {
            this.requestedDate = requestedDate;
        }
    }
}
