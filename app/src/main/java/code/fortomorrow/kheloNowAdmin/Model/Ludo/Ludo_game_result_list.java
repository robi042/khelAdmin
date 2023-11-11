package code.fortomorrow.kheloNowAdmin.Model.Ludo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ludo_game_result_list {

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
        @SerializedName("image_link")
        @Expose
        public String imageLink;
        @SerializedName("hasRoomcode")
        @Expose
        public Boolean hasRoomcode;
        @SerializedName("room_code")
        @Expose
        public String roomCode;

        @SerializedName("result_done")
        @Expose
        public Boolean resultDone;

        @SerializedName("image_uploaded_by")
        @Expose
        public String image_uploaded_by;

        @SerializedName("note")
        @Expose
        public String note;

        @SerializedName("room_update_time")
        @Expose
        public String room_update_time;

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getImage_uploaded_by() {
            return image_uploaded_by;
        }

        public void setImage_uploaded_by(String image_uploaded_by) {
            this.image_uploaded_by = image_uploaded_by;
        }

        public Boolean getResultDone() {
            return resultDone;
        }

        public void setResultDone(Boolean resultDone) {
            this.resultDone = resultDone;
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

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
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
    }

}
