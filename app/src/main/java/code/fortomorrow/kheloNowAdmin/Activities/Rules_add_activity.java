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
import android.widget.Toast;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rules_add_activity extends AppCompatActivity {
    AppCompatButton addButton;
    EditText rulesText;
    private APIService apiService;
    private String api_token, secret_id;
    Dialog loader;
    ImageView backButton;
    String gameID;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_add);

        init_view();

        if (gameID.equals("1")) {
            type = "freefire_regular";
        } else if (gameID.equals("2")) {
            type = "cs_regular";
        } else if (gameID.equals("3")) {
            type = "ludo_regular";
        } else if (gameID.equals("4")) {
            type = "ludo_grand";
        } else if (gameID.equals("5")) {
            type = "cs_grand";
        } else if (gameID.equals("6")) {
            type = "tournament";
        } else if (gameID.equals("7")) {
            type = "ludo_quick";
        } else if (gameID.equals("8")) {
            type = "ludo_4player";
        } else if (gameID.equals("9")) {
            type = "freefire_premium";
        } else if (gameID.equals("10")) {
            type = "freefire_grand";
        } else if (gameID.equals("11")) {
            type = "Arena Of Valor(Regular)";
        } else if (gameID.equals("12")) {
            type = "Arena Of Valor(Grand)";
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();

                String rule = rulesText.getText().toString().trim();

                if (TextUtils.isEmpty(rule)) {
                    Toasty.success(getApplicationContext(), "empty field", Toast.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    apiService.updateAllRules(secret_id, api_token, rule, type).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {

                                Toasty.success(getApplicationContext(), type + " rules updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.success(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.success(getApplicationContext(), getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }

    private void init_view() {
        gameID = getIntent().getStringExtra("play_type");

        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);

        loader = new Dialog(Rules_add_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
        addButton = findViewById(R.id.addButtonID);
        rulesText = findViewById(R.id.rulesTextID);
        backButton = findViewById(R.id.backButton);
    }
}