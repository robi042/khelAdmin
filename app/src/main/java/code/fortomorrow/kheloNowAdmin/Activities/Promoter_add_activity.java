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

public class Promoter_add_activity extends AppCompatActivity {

    ImageView backButton;
    Dialog loader;
    APIService apiService;
    String api_token, secret_id;
    AppCompatButton addButton;
    EditText nameEditText, userNameEditText, phoneEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promoter);

        init_view();

        //Log.d("tokenxx", secret_id+" "+api_token);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String userName = userNameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    //Toasty.success(getApplicationContext(), "xx", Toasty.LENGTH_SHORT).show();

                    add_promoter_func(name, userName, phone, password);

                }
            }
        });
    }

    private void add_promoter_func(String name, String userName, String phone, String password) {
        loader.show();
        apiService.add_promoter(secret_id, api_token, name, userName, phone, password).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();

                } else {
                    Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                loader.dismiss();
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void init_view() {
        loader = new Dialog(Promoter_add_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        addButton = findViewById(R.id.addButtonID);

        nameEditText = findViewById(R.id.nameEditTextID);
        userNameEditText = findViewById(R.id.userNameEditTextID);
        phoneEditText = findViewById(R.id.phoneEditTextID);
        passwordEditText = findViewById(R.id.passwordEditTextID);
    }
}