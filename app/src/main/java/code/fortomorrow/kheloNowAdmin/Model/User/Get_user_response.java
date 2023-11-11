package code.fortomorrow.kheloNowAdmin.Model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_user_response {

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

        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("total_balance")
        @Expose
        public Integer totalBalance;
        @SerializedName("deposit_balance")
        @Expose
        public Integer depositBalance;
        @SerializedName("winning_balance")
        @Expose
        public Integer winningBalance;
        @SerializedName("total_match_play")
        @Expose
        public Integer totalMatchPlay;
        @SerializedName("match_win")
        @Expose
        public Integer matchWin;

        @SerializedName("total_refer")
        @Expose
        public Integer total_refer;
        @SerializedName("total_refer_income")
        @Expose
        public Integer total_refer_income;

        @SerializedName("total_add_money")
        @Expose
        public Integer total_add_money;

        @SerializedName("total_withdraw")
        @Expose
        public Integer total_withdraw;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(Integer totalBalance) {
            this.totalBalance = totalBalance;
        }

        public Integer getDepositBalance() {
            return depositBalance;
        }

        public void setDepositBalance(Integer depositBalance) {
            this.depositBalance = depositBalance;
        }

        public Integer getWinningBalance() {
            return winningBalance;
        }

        public void setWinningBalance(Integer winningBalance) {
            this.winningBalance = winningBalance;
        }

        public Integer getTotalMatchPlay() {
            return totalMatchPlay;
        }

        public void setTotalMatchPlay(Integer totalMatchPlay) {
            this.totalMatchPlay = totalMatchPlay;
        }

        public Integer getMatchWin() {
            return matchWin;
        }

        public void setMatchWin(Integer matchWin) {
            this.matchWin = matchWin;
        }

        public Integer getTotal_refer() {
            return total_refer;
        }

        public void setTotal_refer(Integer total_refer) {
            this.total_refer = total_refer;
        }

        public Integer getTotal_refer_income() {
            return total_refer_income;
        }

        public void setTotal_refer_income(Integer total_refer_income) {
            this.total_refer_income = total_refer_income;
        }

        public Integer getTotal_add_money() {
            return total_add_money;
        }

        public void setTotal_add_money(Integer total_add_money) {
            this.total_add_money = total_add_money;
        }

        public Integer getTotal_withdraw() {
            return total_withdraw;
        }

        public void setTotal_withdraw(Integer total_withdraw) {
            this.total_withdraw = total_withdraw;
        }
    }
}
