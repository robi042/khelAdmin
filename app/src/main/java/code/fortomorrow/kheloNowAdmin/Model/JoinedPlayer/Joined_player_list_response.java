package code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Joined_player_list_response {
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
        @SerializedName("hasFirstPlayer")
        @Expose
        public Boolean hasFirstPlayer;
        @SerializedName("first_player")
        @Expose
        public String firstPlayer;
        @SerializedName("hasSecondPlayer")
        @Expose
        public Boolean hasSecondPlayer;
        @SerializedName("second_player")
        @Expose
        public String secondPlayer;
        @SerializedName("hasThirdPlayer")
        @Expose
        public Boolean hasThirdPlayer;
        @SerializedName("third_player")
        @Expose
        public String thirdPlayer;
        @SerializedName("hasForthPlayer")
        @Expose
        public Boolean hasForthPlayer;
        @SerializedName("forth_player")
        @Expose
        public String forthPlayer;
        @SerializedName("hasFifthPlayer")
        @Expose
        public Boolean hasFifthPlayer;
        @SerializedName("fifth_player")
        @Expose
        public String fifthPlayer;
        @SerializedName("hasSixthPlayer")
        @Expose
        public Boolean hasSixthPlayer;
        @SerializedName("sixth_player")
        @Expose
        public String sixthPlayer;

        //player_name
        @SerializedName("player_name")
        @Expose
        public String player_name;

        @SerializedName("isReady")
        @Expose
        public Boolean isReady;

        public Boolean getReady() {
            return isReady;
        }

        public void setReady(Boolean ready) {
            isReady = ready;
        }

        public String getPlayer_name() {
            return player_name;
        }

        public void setPlayer_name(String player_name) {
            this.player_name = player_name;
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

        public Boolean getHasFirstPlayer() {
            return hasFirstPlayer;
        }

        public void setHasFirstPlayer(Boolean hasFirstPlayer) {
            this.hasFirstPlayer = hasFirstPlayer;
        }

        public String getFirstPlayer() {
            return firstPlayer;
        }

        public void setFirstPlayer(String firstPlayer) {
            this.firstPlayer = firstPlayer;
        }

        public Boolean getHasSecondPlayer() {
            return hasSecondPlayer;
        }

        public void setHasSecondPlayer(Boolean hasSecondPlayer) {
            this.hasSecondPlayer = hasSecondPlayer;
        }

        public String getSecondPlayer() {
            return secondPlayer;
        }

        public void setSecondPlayer(String secondPlayer) {
            this.secondPlayer = secondPlayer;
        }

        public Boolean getHasThirdPlayer() {
            return hasThirdPlayer;
        }

        public void setHasThirdPlayer(Boolean hasThirdPlayer) {
            this.hasThirdPlayer = hasThirdPlayer;
        }

        public String getThirdPlayer() {
            return thirdPlayer;
        }

        public void setThirdPlayer(String thirdPlayer) {
            this.thirdPlayer = thirdPlayer;
        }

        public Boolean getHasForthPlayer() {
            return hasForthPlayer;
        }

        public void setHasForthPlayer(Boolean hasForthPlayer) {
            this.hasForthPlayer = hasForthPlayer;
        }

        public String getForthPlayer() {
            return forthPlayer;
        }

        public void setForthPlayer(String forthPlayer) {
            this.forthPlayer = forthPlayer;
        }

        public Boolean getHasFifthPlayer() {
            return hasFifthPlayer;
        }

        public void setHasFifthPlayer(Boolean hasFifthPlayer) {
            this.hasFifthPlayer = hasFifthPlayer;
        }

        public String getFifthPlayer() {
            return fifthPlayer;
        }

        public void setFifthPlayer(String fifthPlayer) {
            this.fifthPlayer = fifthPlayer;
        }

        public Boolean getHasSixthPlayer() {
            return hasSixthPlayer;
        }

        public void setHasSixthPlayer(Boolean hasSixthPlayer) {
            this.hasSixthPlayer = hasSixthPlayer;
        }

        public String getSixthPlayer() {
            return sixthPlayer;
        }

        public void setSixthPlayer(String sixthPlayer) {
            this.sixthPlayer = sixthPlayer;
        }
    }
}
