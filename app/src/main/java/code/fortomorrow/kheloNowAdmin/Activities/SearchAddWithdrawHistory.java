package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import code.fortomorrow.kheloNowAdmin.Adapter.UerrAddWithdrawHistory.AdapterClassUserAddWithdrawHistory;
import code.fortomorrow.kheloNowAdmin.Model.AddWithdrawHistory.Add_withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddWithdrawHistory extends AppCompatActivity {
    private String user_id, type, secret_id, api_token;
    APIService apiService;
    private RecyclerView recycleItem;
    private List<Add_withdraw_history_response.M> history_response;
    private RecyclerView.Adapter adapter;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add_withdraw_history);
        recycleItem = findViewById(R.id.recycleItem);
        backButton = findViewById(R.id.backButton);

        recycleItem.setHasFixedSize(true);
        recycleItem.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getString("user_id");
            type = extras.getString("type");
        }

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Toasty.success(getApplicationContext(), user_id + " "+ type, Toasty.LENGTH_SHORT).show();

        apiService.get_all_add_withdraw_history_bu_admin(secret_id,api_token,user_id,type).enqueue(new Callback<Add_withdraw_history_response>() {
            @Override
            public void onResponse(Call<Add_withdraw_history_response> call, Response<Add_withdraw_history_response> response) {
                try {
                    if(response.body().getE() == 0){
                        //Toasty.success(getApplicationContext(), response.body().getM().size()+"", Toasty.LENGTH_SHORT).show();
                        history_response = response.body().getM();
                        adapter = new AdapterClassUserAddWithdrawHistory(history_response, getApplicationContext());
                        recycleItem.setAdapter(adapter);
                    }
                }catch (NullPointerException e){}
            }

            @Override
            public void onFailure(Call<Add_withdraw_history_response> call, Throwable t) {

            }
        });

    }
}