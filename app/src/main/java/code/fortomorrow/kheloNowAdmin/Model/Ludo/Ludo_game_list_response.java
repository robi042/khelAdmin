package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ludo_game_list_response {

    @SerializedName("match_id")
    @Expose
    public Integer matchId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("entry_fee")
    @Expose
    public String entryFee;
    @SerializedName("total_player")
    @Expose
    public String totalPlayer;
    @SerializedName("total_prize")
    @Expose
    public String totalPrize;
    @SerializedName("host_app")
    @Expose
    public String hostApp;

    @SerializedName("hasRoomcode")
    @Expose
    public Boolean hasRoomcode;
    @SerializedName("room_code")
    @Expose
    public String roomCode;

    @SerializedName("hasImage")
    @Expose
    public  Boolean hasImage;

    @SerializedName("image_link")
    @Expose
    public String imageLink;

    @SerializedName("note")
    @Expose
    public String note;

    @SerializedName("total_image_upload")
    @Expose
    public Integer total_image_upload;

    public Integer getTotal_image_upload() {
        return total_image_upload;
    }

    public void setTotal_image_upload(Integer total_image_upload) {
        this.total_image_upload = total_image_upload;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getHasRoomcode() {
        return hasRoomcode;
    }

    public void setHasRoomcode(Boolean hasRoomcode) {
        this.hasRoomcode = hasRoomcode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
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

    public String getHostApp() {
        return hostApp;
    }

    public void setHostApp(String hostApp) {
        this.hostApp = hostApp;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }
}
