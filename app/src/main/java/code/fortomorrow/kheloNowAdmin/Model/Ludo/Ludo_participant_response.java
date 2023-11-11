package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_participant_response {

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

        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("player_name")
        @Expose
        public String playerName;
        @SerializedName("rank")
        @Expose
        public Integer rank;
        @SerializedName("winning_money")
        @Expose
        public Integer winningMoney;

        @SerializedName("refund_amount")
        @Expose
        public Integer refund_amount;

        @SerializedName("isReady")
        @Expose
        public Boolean isReady;

        public Boolean getReady() {
            return isReady;
        }

        public void setReady(Boolean ready) {
            isReady = ready;
        }

        public Integer getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(Integer refund_amount) {
            this.refund_amount = refund_amount;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public Integer getWinningMoney() {
            return winningMoney;
        }

        public void setWinningMoney(Integer winningMoney) {
            this.winningMoney = winningMoney;
        }
    }
}
