package code.fortomorrow.kheloNowAdmin.Model.AddMoneyList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("add_money_id")
@Expose
private Integer addMoneyId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("amount")
@Expose
private Object amount;
@SerializedName("payment_method")
@Expose
private String paymentMethod;
@SerializedName("requested_time")
@Expose
private String requestedTime;
@SerializedName("phone_number")
@Expose
private String phoneNumber;

public Integer getAddMoneyId() {
return addMoneyId;
}

public void setAddMoneyId(Integer addMoneyId) {
this.addMoneyId = addMoneyId;
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

public Object getAmount() {
return amount;
}

public void setAmount(Object amount) {
this.amount = amount;
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

public String getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}

}