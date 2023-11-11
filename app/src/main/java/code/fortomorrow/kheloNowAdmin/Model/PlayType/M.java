package code.fortomorrow.kheloNowAdmin.Model.PlayType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("playType_id")
@Expose
private Integer playTypeId;
@SerializedName("playing_type")
@Expose
private String playingType;

public Integer getPlayTypeId() {
return playTypeId;
}

public void setPlayTypeId(Integer playTypeId) {
this.playTypeId = playTypeId;
}

public String getPlayingType() {
return playingType;
}

public void setPlayingType(String playingType) {
this.playingType = playingType;
}

}