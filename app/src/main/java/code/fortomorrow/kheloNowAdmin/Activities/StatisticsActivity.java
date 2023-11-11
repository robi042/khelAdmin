package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity extends AppCompatActivity {
    private APIService apiService;
    private String api_token, secret_id;
    private TextView total_user, total_new_user, total_pass_change_user, old_active_user, new_active_user,
            total_firefire_join, total_ludo_join, today_addmoney, today_withdraw, today_profit, monthly_addmoney, monthly_withdraw, monthly_profit;
    Dialog loader;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loader = new Dialog(StatisticsActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        loader.show();

        apiService.get_total_user(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    total_user.setText(response.body().getM());
                    loader.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });
        apiService.get_new_user(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    total_new_user.setText(response.body().getM());
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });
        apiService.get_pass_change_req(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    total_pass_change_user.setText(response.body().getM());
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_new_active_user(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    new_active_user.setText(response.body().getM());
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_old_active_user(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    old_active_user.setText(response.body().getM());
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_today_firefire_join(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    total_firefire_join.setText(response.body().getM());
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_today_ludo_join(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){
                    total_ludo_join.setText(response.body().getM());
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_today_profit(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){

                    String[] items = response.body().getM().split("-");
                    today_addmoney.setText(items[0]);
                    today_withdraw.setText(items[1]);
                    today_profit.setText(items[2]);
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });

        apiService.get_monthly_profit(secret_id, api_token).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if(response.body().getE() == 0){

                    String[] items = response.body().getM().split("-");
                    monthly_addmoney.setText(items[0]);
                    monthly_withdraw.setText(items[1]);
                    monthly_profit.setText(items[2]);
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {

            }
        });
    }
    private void init_view() {
        EasySharedPref.init(StatisticsActivity.this);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);
        total_user = findViewById(R.id.total_user);
        total_new_user = findViewById(R.id.total_new_user);
        total_pass_change_user = findViewById(R.id.total_pass_change_user);
        backButton = findViewById(R.id.backButton);
        old_active_user = findViewById(R.id.old_active_user);
        new_active_user = findViewById(R.id.new_active_user);
        total_firefire_join = findViewById(R.id.total_firefire_join);
        total_ludo_join = findViewById(R.id.total_ludo_join);
        today_addmoney = findViewById(R.id.today_addmoney);
        today_withdraw = findViewById(R.id.today_withdraw);
        today_profit = findViewById(R.id.today_profit);
        monthly_addmoney = findViewById(R.id.monthly_addmoney);
        monthly_withdraw = findViewById(R.id.monthly_withdraw);
        monthly_profit = findViewById(R.id.monthly_profit);
    }
}