package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.Rules.RulesResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeFireRegularRulesActivity extends AppCompatActivity {
    private APIService apiService;
    private String api_token, secret_id;
    private Button mAdd;
    private EditText mRules;
    private TextView rulesaa;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_rules);

        mAdd = findViewById(R.id.mFreeAdd);
        mRules = findViewById(R.id.mFreeFireeRules);
        rulesaa = findViewById(R.id.rulesaa);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                apiService.updateFreeFireRules(secret_id, api_token, mRules.getText().toString()).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        Toasty.success(getApplicationContext(), "Rules Updated").show();
                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {

                    }
                });
            }
        });
        apiService.getFreeFireRules(secret_id, api_token).enqueue(new Callback<RulesResponse>() {
            @Override
            public void onResponse(Call<RulesResponse> call, Response<RulesResponse> response) {
                if (response.body().getE() == 0) {
                    rulesaa.setText(response.body().getM().getRule());
                }
            }

            @Override
            public void onFailure(Call<RulesResponse> call, Throwable t) {

            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}