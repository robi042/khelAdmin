package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.MainActivity;
import code.fortomorrow.kheloNowAdmin.Model.LoginResponse.LoginResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private APIService apiService;
    private EditText email, password;
    private ImageView showPassoword;
    private RelativeLayout forword_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySharedPref.init(getApplicationContext());
        if (!EasySharedPref.read("secret_id", "").isEmpty()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        init();
        checkTime();
        apiService = AppConfig.getRetrofit().create(APIService.class);
        forword_btn.setOnClickListener(v -> checkvalues());


    }

    private void checkTime() {

    }

    private void checkvalues() {
        if (TextUtils.isEmpty(email.getText().toString())) {
            Toasty.error(getApplicationContext(), "Empty username", Toasty.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toasty.error(getApplicationContext(), "Empty password", Toasty.LENGTH_SHORT).show();

        } else {
            loginReqSentToServer();
        }

    }

    private void loginReqSentToServer() {
        apiService.getLoginResponse(email.getText().toString(), password.getText().toString(), "kgit8@fu").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    //Log.d("Login", new Gson().toJson(response.body()));
                    EasySharedPref.write("secret_id", String.valueOf(response.body().getM().getSecretId()));
                    EasySharedPref.write("api_token", String.valueOf(response.body().getM().getApiToken()));

                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //Log.d("errorxx", "hi");
                    Toasty.error(getApplicationContext(), "Error username or Password", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error username or Password", Toasty.LENGTH_SHORT).show();
                Log.d("errorxx", t.getMessage());

            }
        });
    }

    private void init() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forword_btn = findViewById(R.id.forword_btn);
    }
}