package code.fortomorrow.kheloNowAdmin.Model.ShowParticipant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("first_player")
    @Expose
    private String firstPlayer;
    @SerializedName("second_player")
    @Expose
    private String secondPlayer;

    @SerializedName("third_player")
    @Expose
    private String third_player;
    @SerializedName("forth_player")
    @Expose
    private String forth_player;

    @SerializedName("fifth_player")
    @Expose
    public String fifthPlayer;
    @SerializedName("sixth_player")
    @Expose
    public String sixthPlayer;

    public String getFifthPlayer() {
        return fifthPlayer;
    }

    public void setFifthPlayer(String fifthPlayer) {
        this.fifthPlayer = fifthPlayer;
    }

    public String getSixthPlayer() {
        return sixthPlayer;
    }

    public void setSixthPlayer(String sixthPlayer) {
        this.sixthPlayer = sixthPlayer;
    }

    public String getThird_player() {
        return third_player;
    }

    public void setThird_player(String third_player) {
        this.third_player = third_player;
    }

    public String getFourth_player() {
        return forth_player;
    }

    public void setFourth_player(String fourth_player) {
        this.forth_player = fourth_player;
    }

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("kill")
    @Expose
    private Integer kill;
    @SerializedName("winning_money")
    @Expose
    private Integer winningMoney;

    @SerializedName("hasResult")
    @Expose
    public Boolean hasResult;

    @SerializedName("refund_amount")
    @Expose
    public Integer refund_amount;

    public Integer getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(Integer refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getForth_player() {
        return forth_player;
    }

    public void setForth_player(String forth_player) {
        this.forth_player = forth_player;
    }

    public Boolean getHasResult() {
        return hasResult;
    }

    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
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

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getKill() {
        return kill;
    }

    public void setKill(Integer kill) {
        this.kill = kill;
    }

    public Integer getWinningMoney() {
        return winningMoney;
    }

    public void setWinningMoney(Integer winningMoney) {
        this.winningMoney = winningMoney;
    }

}