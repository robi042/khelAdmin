package code.fortomorrow.kheloNowAdmin.KheloLudo.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Slider.Slider_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Slider.Slider_list_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slider_view_activity extends AppCompatActivity implements Slider_list_adapter.OnItemClickListener {

    ImageView backButton;
    LinearLayout activeLayoutButton, inactiveLayoutButton, noDataLayout;
    RecyclerView sliderView;

    String status = "active", secret_id, api_token;
    APIService apiService;

    private List<Slider_list_response.M> sliderList;
    private Slider_list_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activeLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeLayoutButton.setBackgroundColor(getResources().getColor(R.color.white));
                inactiveLayoutButton.setBackgroundColor(getResources().getColor(R.color.light_grey));

                status = "active";

                load_data(status);
            }
        });

        inactiveLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeLayoutButton.setBackgroundColor(getResources().getColor(R.color.light_grey));
                inactiveLayoutButton.setBackgroundColor(getResources().getColor(R.color.white));

                status = "inactive";

                load_data(status);
            }
        });

        load_data(status);
    }

    private void load_data(String status) {


        apiService.khelo_ludo_slider_list(secret_id, api_token, status).enqueue(new Callback<Slider_list_response>() {
            @Override
            public void onResponse(Call<Slider_list_response> call, Response<Slider_list_response> response) {
               // Toast.makeText(Slider_view_activity.this, String.valueOf(response.body().m), Toast.LENGTH_SHORT).show();

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {

                    sliderView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    sliderList = new ArrayList<>();
                    sliderList = response.body().getM();

                    //Toast.makeText(getApplicationContext(), String.valueOf(sliderList.size()), Toast.LENGTH_SHORT).show();
                    adapter = new Slider_list_adapter(sliderList);
                    adapter.setOnClickListener(Slider_view_activity.this::OnItemClick);
                    sliderView.setAdapter(adapter);
                } else {
                    sliderView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Slider_list_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx", secret_id+" "+api_token);

        backButton = findViewById(R.id.backButton);
        activeLayoutButton = findViewById(R.id.activeLayoutButtonID);
        inactiveLayoutButton = findViewById(R.id.inactiveLayoutButtonID);
        sliderView = findViewById(R.id.sliderViewID);
        sliderView.setHasFixedSize(true);
        sliderView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noDataLayout = findViewById(R.id.noDataLayoutID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void OnItemClick(int position) {
        Slider_list_response.M response = sliderList.get(position);

        String sliderID = String.valueOf(response.getSliderId());
        //Toast.makeText(this, sliderID, Toast.LENGTH_SHORT).show();

        apiService.khelo_ludo_update_slider(secret_id, api_token, sliderID).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), "Successful", Toasty.LENGTH_SHORT).show();
                    load_data(status);
                } else {
                    Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });
    }
}