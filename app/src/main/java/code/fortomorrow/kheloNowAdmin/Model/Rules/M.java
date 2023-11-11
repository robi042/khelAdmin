package code.fortomorrow.kheloNowAdmin.Model.Rules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class M {

@SerializedName("rule")
@Expose
private String rule;

public String getRule() {
return rule;
}

public void setRule(String rule) {
this.rule = rule;
}

}