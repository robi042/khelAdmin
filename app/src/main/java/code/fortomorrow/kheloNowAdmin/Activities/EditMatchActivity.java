package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMatchActivity extends AppCompatActivity {
    private String title, map, match_Id, entry_fee, time, winningprize, perkill, firstPrize, secondPrize, thirdPrize, totalPlayer;
    private EditText titleET, mapET, entry_feeET, timeET, winningprizeET, perkillET, firstPrizeET, secondPrizeET, thirdPrizeET, totalPlayerET;
    private ImageView back;
    private APIService apiService;
    private String api_token, secret_id;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match);
        initView();
        title = getIntent().getStringExtra("title");
        titleET.setText(title);
        map = getIntent().getStringExtra("map");
        mapET.setText(map);
        entry_fee = getIntent().getStringExtra("entryfee");
        entry_feeET.setText(entry_fee);
        time = getIntent().getStringExtra("time");
        timeET.setText(time);
        winningprize = getIntent().getStringExtra("winningprize");
        winningprizeET.setText(winningprize);
        perkill = getIntent().getStringExtra("perkill");
        perkillET.setText(perkill);
        match_Id = getIntent().getStringExtra("match_id");
        firstPrize = getIntent().getStringExtra("firstPrize");
        firstPrizeET.setText(firstPrize);
        secondPrize = getIntent().getStringExtra("secondPrize");
        secondPrizeET.setText(secondPrize);
        thirdPrize = getIntent().getStringExtra("thirdPrize");
        thirdPrizeET.setText(thirdPrize);
        totalPlayer = getIntent().getStringExtra("totalPlayer");
        totalPlayerET.setText(totalPlayer);
        back.setOnClickListener(v -> finish());
        Log.d("iddd", match_Id);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.getUpdateMatchRes(secret_id, api_token, timeET.getText().toString(), entry_feeET.getText().toString(), mapET.getText().toString(),
                        perkillET.getText().toString(), titleET.getText().toString(), totalPlayerET.getText().toString(), winningprizeET.getText().toString()
                        , firstPrizeET.getText().toString(), secondPrizeET.getText().toString(), thirdPrizeET.getText().toString(), match_Id
                ).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        if (response.body().getE() == 0) {
                            Toasty.success(getApplicationContext(), "Successfully Edited the match", Toasty.LENGTH_SHORT).show();
                        } else {
                            Log.d("errorLoggg", new Gson().toJson(response.body()));

                        }

                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        Log.d("errorLoggg", t.toString());
                    }
                });
            }
        });
    }

    private void initView() {
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        titleET = findViewById(R.id.titleET);
        mapET = findViewById(R.id.mapET);
        entry_feeET = findViewById(R.id.entryfeeET);
        winningprizeET = findViewById(R.id.winningprizeET);
        perkillET = findViewById(R.id.perkillET);
        firstPrizeET = findViewById(R.id.firstPrizeET);
        secondPrizeET = findViewById(R.id.secondPrizeET);
        thirdPrizeET = findViewById(R.id.thirdPrizeET);
        timeET = findViewById(R.id.timeET);
        totalPlayerET = findViewById(R.id.totalPlayerET);

    }
}