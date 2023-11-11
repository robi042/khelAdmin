package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin.Sub_admin_information_adapter;
import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_information_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sub_admin_information_activity extends AppCompatActivity {

    ImageView backButton;

    APIService apiService;
    String api_token, secret_id;
    Dialog loader;

    LinearLayout noDataLayout, searchLayoutButton;
    HorizontalScrollView horizontalScrollView;
    RecyclerView informationView;
    EditText searchEditText;
    private List<Sub_admin_information_response.M> dataList = new ArrayList<>();
    private Sub_admin_information_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_information);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Log.d("tokenxx", api_token);

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
                    horizontalScrollView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                    dataList.clear();
                }
            }
        });

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim();

                if (TextUtils.isEmpty(searchText)) {
                    Toasty.error(getApplicationContext(), "search box empty", Toasty.LENGTH_SHORT).show();
                } else {
                    get_data(searchText);
                }
            }
        });
    }

    private void get_data(String searchText) {
        apiService.sub_admin_withdraw_history(secret_id, api_token, searchText).enqueue(new Callback<Sub_admin_information_response>() {
            @Override
            public void onResponse(Call<Sub_admin_information_response> call, Response<Sub_admin_information_response> response) {
                if (response.body().e == 0 && response.body().m.size() != 0) {
                    horizontalScrollView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(), String.valueOf(response.body().m.size()), Toast.LENGTH_SHORT).show();
                    dataList = response.body().m;
                    adapter = new Sub_admin_information_adapter(dataList);
                    informationView.setAdapter(adapter);
                } else {
                    horizontalScrollView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Sub_admin_information_response> call, Throwable t) {
                horizontalScrollView.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        loader = new Dialog(Sub_admin_information_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        backButton = findViewById(R.id.backButton);
        noDataLayout = findViewById(R.id.noDataLayoutID);
        horizontalScrollView = findViewById(R.id.horizontalScrollViewID);

        informationView = findViewById(R.id.informationViewID);
        informationView.setHasFixedSize(true);
        informationView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        searchEditText = findViewById(R.id.searchEditTextId);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);

    }
}