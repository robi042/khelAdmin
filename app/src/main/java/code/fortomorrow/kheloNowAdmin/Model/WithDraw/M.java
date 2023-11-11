package code.fortomorrow.kheloNowAdmin.Model.WithDraw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("withdraw_money_id")
@Expose
private Integer withdrawMoneyId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("amount")
@Expose
private Integer amount;
@SerializedName("payment_method")
@Expose
private String paymentMethod;
@SerializedName("phone_number")
@Expose
private String phoneNumber;
@SerializedName("requested_time")
@Expose
private String requestedTime;

public Integer getWithdrawMoneyId() {
return withdrawMoneyId;
}

public void setWithdrawMoneyId(Integer withdrawMoneyId) {
this.withdrawMoneyId = withdrawMoneyId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

public String getPaymentMethod() {
return paymentMethod;
}

public void setPaymentMethod(String paymentMethod) {
this.paymentMethod = paymentMethod;
}

public String getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}

public String getRequestedTime() {
return requestedTime;
}

public void setRequestedTime(String requestedTime) {
this.requestedTime = requestedTime;
}

}