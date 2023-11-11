package code.fortomorrow.kheloNowAdmin.Model.Ongoing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("match_id")
    @Expose
    private Integer matchId;
    @SerializedName("match_time")
    @Expose
    private String matchTime;
    @SerializedName("entry_fee")
    @Expose
    private String entryFee;
    @SerializedName("map")
    @Expose
    private String map;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("total_player")
    @Expose
    private String totalPlayer;
    @SerializedName("total_prize")
    @Expose
    private String totalPrize;
    @SerializedName("first_prize")
    @Expose
    private String firstPrize;
    @SerializedName("second_prize")
    @Expose
    private String secondPrize;
    @SerializedName("third_prize")
    @Expose
    private String thirdPrize;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("game_type")
    @Expose
    private String gameType;
    @SerializedName("player_type")
    @Expose
    private String playerType;
    @SerializedName("per_kill_rate")
    @Expose
    private String per_kill_rate;

    @SerializedName("room_id")
    @Expose
    private String room_id;

    @SerializedName("result_done")
    @Expose
    private Boolean result_done;

    @SerializedName("room_update_time")
    @Expose
    public String room_update_time;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("ongoing_by")
    @Expose
    public String ongoing_by;

    public Boolean getResult_done() {
        return result_done;
    }

    public void setResult_done(Boolean result_done) {
        this.result_done = result_done;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getPer_kill_rate() {
        return per_kill_rate;
    }

    public void setPer_kill_rate(String per_kill_rate) {
        this.per_kill_rate = per_kill_rate;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(String totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(String secondPrize) {
        this.secondPrize = secondPrize;
    }

    public String getThirdPrize() {
        return thirdPrize;
    }

    public void setThirdPrize(String thirdPrize) {
        this.thirdPrize = thirdPrize;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

}