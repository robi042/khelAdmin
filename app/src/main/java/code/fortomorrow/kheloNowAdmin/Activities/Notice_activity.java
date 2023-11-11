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

public class Notice_activity extends AppCompatActivity {

    ImageView backButton;
    EditText noticeText;
    AppCompatButton addButton;
    APIService apiService;
    String api_token, secret_id;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);



        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notice = noticeText.getText().toString().trim();

                if (TextUtils.isEmpty(notice)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {

                    loader.show();
                    apiService.updateNotice(secret_id, api_token, notice).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), "Notice updated", Toasty.LENGTH_SHORT).show();
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
    }

    private void init_view() {
        loader = new Dialog(Notice_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        noticeText = findViewById(R.id.noticeTextID);
        addButton = findViewById(R.id.addButtonID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}