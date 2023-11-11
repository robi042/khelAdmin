package code.fortomorrow.kheloNowAdmin.Model.GameType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

    @SerializedName("game_id")
    @Expose
    private Integer gameId;
    @SerializedName("game_name")
    @Expose
    private String gameName;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

}