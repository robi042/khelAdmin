package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.AddMoney.Add_money_accepted_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Add_money_accepted_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Accepted_add_money_activity extends AppCompatActivity {

    String api_token, secret_id;
    APIService apiService;
    LinearLayout searchLayoutButton, noDataLayout;
    ImageView backButton;
    EditText searchEditText;

    RecyclerView acceptedListView;
    String searchText;
    Add_money_accepted_list_adapter adapter;
    List<Add_money_accepted_list_response.M> dataList;
    TextView historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_add_money_acitivity);
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);
        backButton = findViewById(R.id.backButton);
        searchEditText = findViewById(R.id.searchEditTextId);

        acceptedListView = findViewById(R.id.acceptedListViewID);
        acceptedListView.setHasFixedSize(true);
        acceptedListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noDataLayout = findViewById(R.id.noDataLayoutID);

        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEditText.getText().toString().trim();

                if (TextUtils.isEmpty(searchText)) {
                    Toasty.error(getApplicationContext(), "Search box empty", Toasty.LENGTH_SHORT).show();
                    acceptedListView.setVisibility(View.GONE);
                } else {
                    //Toasty.success(getApplicationContext(), searchText, Toasty.LENGTH_SHORT).show();
                    //profileLayout.setVisibility(View.VISIBLE);

                    get_accepted_data();
                }
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
                    acceptedListView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // Log.d("tokenxx", api_token);
        historyButton = findViewById(R.id.historyButtonID);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Accepted_add_money_history_activity.class));
            }
        });
    }

    private void get_accepted_data() {

        apiService.getAddMoneyAcceptedList(secret_id, api_token, searchText).enqueue(new Callback<Add_money_accepted_list_response>() {
            @Override
            public void onResponse(Call<Add_money_accepted_list_response> call, Response<Add_money_accepted_list_response> response) {
                if (response.body().getE() == 0) {
                    acceptedListView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    dataList = new ArrayList<>();
                    dataList = response.body().getM();
                    adapter = new Add_money_accepted_list_adapter(dataList);
                    acceptedListView.setAdapter(adapter);
                } else {
                    acceptedListView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }

                if (response.body().getM().size() == 0) {
                    acceptedListView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Add_money_accepted_list_response> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}