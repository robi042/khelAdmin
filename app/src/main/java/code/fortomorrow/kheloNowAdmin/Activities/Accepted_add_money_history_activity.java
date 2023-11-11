package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.AddMoney.Add_money_history_adapter;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Add_money_history_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Accepted_add_money_history_activity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout searchLayoutButton, noDataLayout;
    EditText searchEditText;
    RecyclerView historyView;
    HorizontalScrollView dataView;

    APIService apiService;
    String api_token, secret_id;
    private List<Add_money_history_response.M> historyList;
    private Add_money_history_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_add_money_history);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    dataView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = searchEditText.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    get_user_history(userName);
                }
            }
        });

    }

    private void get_user_history(String userName) {

        apiService.getAddMoneyUserHistory(secret_id, api_token, userName).enqueue(new Callback<Add_money_history_response>() {
            @Override
            public void onResponse(Call<Add_money_history_response> call, Response<Add_money_history_response> response) {
                if (response.body().getE() == 0) {
                    dataView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    historyList = new ArrayList<>();
                    historyList = response.body().getM();

                    //Toast.makeText(getApplicationContext(), String.valueOf(historyList.size()), Toast.LENGTH_SHORT).show();
                    adapter = new Add_money_history_adapter(historyList);
                    historyView.setAdapter(adapter);
                } else {
                    dataView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Add_money_history_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        noDataLayout = findViewById(R.id.noDataLayoutID);
        searchEditText = findViewById(R.id.searchEditTextId);

        dataView = findViewById(R.id.dataViewID);
        historyView = findViewById(R.id.historyViewID);
        historyView.setHasFixedSize(true);
        historyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}