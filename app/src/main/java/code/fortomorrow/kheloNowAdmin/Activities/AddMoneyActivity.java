package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.AddMoney.AddmoneyAdapter;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.AddMoneyListResponse;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.M;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyActivity extends AppCompatActivity {
    private APIService apiService;
    //Recyccler View
    String uid, key;
    RecyclerView addMoneyListRV;
    TextView sorry;
    String ref_key;
    List<AddMoneyListResponse> AddMoneyList = new ArrayList<>();
    ProgressBar progressBar;
    private EditText searching;
    private String api_token, secret_id;
    private List<M> medicines = new ArrayList<>();
    private List<M> addMoneyList = new ArrayList<>();

    ImageView backButton;
    TextView acceptedListButton;

    private BottomNavigationView navigation;
    FloatingActionButton bottomScrollButton;
    Boolean scrollState = true;

    String type = "Nagad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        bottomScrollButton = findViewById(R.id.bottomScrollButtonID);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        sorry = findViewById(R.id.sorry);
        searching = findViewById(R.id.searching);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.backButton);
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxxx", api_token);

        apiService = AppConfig.getRetrofit().create(APIService.class);

        addMoneyListRV = findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addMoneyListRV.setLayoutManager(linearLayoutManager);

        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                serching(s.toString());
            }
        });


        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.nagaditem); // change to whichever id should be default
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        acceptedListButton = findViewById(R.id.acceptedListButtonID);
        acceptedListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddMoneyActivity.this, Accepted_add_money_activity.class));
            }
        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bottomScrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scrollState) {
                    scrollState = false;
                    addMoneyListRV.smoothScrollToPosition(addMoneyList.size() - 1);
                    bottomScrollButton.setImageResource(R.drawable.arrow_drop_up_24);
                } else {
                    scrollState = true;
                    addMoneyListRV.smoothScrollToPosition(0);
                    bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }


            }
        });

        load_data(type);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.nagaditem:
                type = "Nagad";
                scrollState = true;
                bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                break;
            case R.id.bKashitem:
                type = "Bkash ";
                scrollState = true;
                bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                break;
            case R.id.rocketitem:
                type = "Rocket";
                scrollState = true;
                bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        load_data(type);
        return true;
    };

    private void load_data(String type) {
        //Toast.makeText(getApplicationContext(), type + "1", Toast.LENGTH_SHORT).show();
        addMoneyList.clear();
        //Toast.makeText(getApplicationContext(), String.valueOf(addMoneyList.size()), Toast.LENGTH_SHORT).show();
        apiService.getAddmoneyReq(secret_id, api_token, type).enqueue(new Callback<AddMoneyListResponse>() {
            @Override
            public void onResponse(Call<AddMoneyListResponse> call, Response<AddMoneyListResponse> response) {
                if (response.body().getE() == 0) {
                    progressBar.setVisibility(View.GONE);
                    addMoneyListRV.setVisibility(View.VISIBLE);
                    addMoneyListRV.setAdapter(new AddmoneyAdapter(response.body().getM(), getApplicationContext(), AddMoneyActivity.this));
                    for (int i = 0; i < response.body().getM().size(); i++) {
                        addMoneyList.add(response.body().getM().get(i));
                    }
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    addMoneyListRV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddMoneyListResponse> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                addMoneyListRV.setVisibility(View.GONE);
            }
        });
    }


    private void serching(String searchingKey) {

        if (searchingKey != null && !searchingKey.isEmpty()) {
            medicines.clear();
            for (int i = 0; i < addMoneyList.size(); i++) {
                if (addMoneyList.get(i).getPhoneNumber().toLowerCase().contains(searchingKey)) {
                    medicines.add(addMoneyList.get(i));
                }

            }

            AddmoneyAdapter ongoingOrderAdapter = new AddmoneyAdapter(medicines, getApplicationContext(), AddMoneyActivity.this);
            addMoneyListRV.setAdapter(ongoingOrderAdapter);
        } else {
            AddmoneyAdapter ongoingOrderAdapter = new AddmoneyAdapter(addMoneyList, getApplicationContext(), AddMoneyActivity.this);
            addMoneyListRV.setAdapter(ongoingOrderAdapter);
        }
    }



}
