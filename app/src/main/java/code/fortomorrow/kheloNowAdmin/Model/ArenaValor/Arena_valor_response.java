package code.fortomorrow.kheloNowAdmin.Model.ArenaValor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Arena_valor_response {
    @SerializedName("e")
    @Expose
    public Integer e;
    @SerializedName("m")
    @Expose
    public List<M> m = null;

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
        @SerializedName("version")
        @Expose
        public String version;
        @SerializedName("hasRoomcode")
        @Expose
        public Boolean hasRoomCode;
        @SerializedName("room_code")
        @Expose
        public String roomCode;
        @SerializedName("admin_note")
        @Expose
        public String admin_note;

    }
}
