package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ludo_rules_add_activity extends AppCompatActivity {

    AppCompatButton addButton;
    EditText rulesText;
    private APIService apiService;
    private String api_token, secret_id;
    Dialog loader;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_rules_add);

        init_view();


        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);

        loader = new Dialog(Ludo_rules_add_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newRule = rulesText.getText().toString().trim();

                if (TextUtils.isEmpty(newRule)) {
                    Toasty.error(getApplicationContext(), "Empty rule", Toasty.LENGTH_SHORT).show();
                } else {
                    //Toasty.success(getApplicationContext(), "Ok", Toasty.LENGTH_SHORT).show();

                    //Log.d("infoxxx", secret_id+" "+api_token);

                    loader.show();
                    apiService.updateLudoRules(secret_id, api_token, newRule).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), "Rules added", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init_view() {
        addButton = findViewById(R.id.addButtonID);
        rulesText = findViewById(R.id.rulesTextID);
        backButton = findViewById(R.id.backButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}