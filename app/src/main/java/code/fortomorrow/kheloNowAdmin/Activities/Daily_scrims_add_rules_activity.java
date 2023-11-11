package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daily_scrims_add_rules_activity extends AppCompatActivity {

    private APIService apiService;
    private String api_token, secret_id;
    private Button mAdd;
    private EditText mRules;
    private TextView rulesaa;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_scrims_add_rules);

        init_all();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rulesText = mRules.getText().toString().trim();

                if (TextUtils.isEmpty(rulesText)) {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {

                    apiService.updateDailyScrimsRules(secret_id, api_token, rulesText).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), "Scrims rules updated", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void init_all() {
        mAdd = findViewById(R.id.mFreeAdd);
        mRules = findViewById(R.id.mFreeFireeRules);
        rulesaa = findViewById(R.id.rulesaa);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        backButton = findViewById(R.id.backButton);
    }
}