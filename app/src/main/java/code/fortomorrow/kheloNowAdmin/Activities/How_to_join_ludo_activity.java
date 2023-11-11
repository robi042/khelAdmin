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

public class How_to_join_ludo_activity extends AppCompatActivity {

    EditText linkText;
    AppCompatButton setButton;

    String api_token, secret_id;
    APIService apiService;
    Dialog loader;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_join_ludo);

        init_view();

        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);


        //Log.d("infoxx", api_token);
        loader = new Dialog(How_to_join_ludo_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = linkText.getText().toString().trim();

                if (TextUtils.isEmpty(link)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    apiService.updateHowToJoinLudoLink(secret_id, api_token, link).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), "Updated", Toasty.LENGTH_SHORT).show();
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
        backButton = findViewById(R.id.backButton);
        linkText = findViewById(R.id.linkTextID);
        setButton = findViewById(R.id.setButtonID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}