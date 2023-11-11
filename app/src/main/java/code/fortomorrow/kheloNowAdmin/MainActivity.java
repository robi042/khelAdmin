package code.fortomorrow.kheloNowAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.Accept_money_history_activity;
import code.fortomorrow.kheloNowAdmin.Activities.AddMoneyActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Add_money_list_activity;
import code.fortomorrow.kheloNowAdmin.Activities.ArenaValor_item_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Balance_history_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Promoter_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Admin_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Before_all_created_matches_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Buy_sell_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Create_admin_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Daily_scrims_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Free_fire_item_activity;
import code.fortomorrow.kheloNowAdmin.Activities.How_to_join_activity;
import code.fortomorrow.kheloNowAdmin.Activities.LoginActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Ludo_item_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Notice_activity;
import code.fortomorrow.kheloNowAdmin.Activities.NumberAddActivity;
import code.fortomorrow.kheloNowAdmin.Activities.OnGoingActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Pop_up_add_activity;
import code.fortomorrow.kheloNowAdmin.Activities.ResultActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Search_activity;
import code.fortomorrow.kheloNowAdmin.Activities.SendNotificationActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Slider_add_activity;
import code.fortomorrow.kheloNowAdmin.Activities.StatisticsActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Sub_admin_activity;
import code.fortomorrow.kheloNowAdmin.Activities.Sub_admin_information_activity;
import code.fortomorrow.kheloNowAdmin.Activities.SupportActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Tournament_acitivity;
import code.fortomorrow.kheloNowAdmin.Activities.User_above_activity;
import code.fortomorrow.kheloNowAdmin.Activities.WithdrawRequestActivity;
import code.fortomorrow.kheloNowAdmin.Activities.Withdraw_money_list_activity;
import code.fortomorrow.kheloNowAdmin.KheloLudo.View.Activities.Khelo_ludo_activity;
import code.fortomorrow.kheloNowAdmin.Model.Check_admin_status_response;
import code.fortomorrow.kheloNowAdmin.Model.GameType.GameTypeResponse;
import code.fortomorrow.kheloNowAdmin.Model.GameType.M;
import code.fortomorrow.kheloNowAdmin.Model.PlayType.Play_Type;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String api_token;
    String secret_id;
    static String date, time;
    private String ludoDate, ludoTime;
    APIService apiService;
    String gameTypeID = "1";
    String playTypeID = "1";
    private List<M> gameTypeResponses = new ArrayList<>();
    private List<String> gameType = new ArrayList<>();
    private List<code.fortomorrow.kheloNowAdmin.Model.PlayType.M> getPlayType = new ArrayList<>();
    List<String> playType = new ArrayList<>();
    private CardView number, add, withdraw;
    private CardView mSendNotofication, mSupport;
    LinearLayout othersLayout, ludoLayout, searchViewButton;
    LinearLayout howToJoinLudoButton, subAdminControlButton, createSubAdminButton;
    LinearLayout sliderLayoutButton, popUpButton, noticeButton, khelostatasticID;
    CardView createSubAdminCard, adminControlButton, buySellButton, addPromoterButton, balanceCard, userAbove500;
    ImageView powerButton;
    LinearLayout ludoButton, dailyScrimsButton, subAdminInfoButton, arenaValorButton;
    LinearLayout addMoneyListButton, withdrawListButton, howToButton, freeFireButton;
    LinearLayout tournamentButton, balanceButton, kheloLudoButton, acceptHistoryButton;

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();

        get_admin_status();

        get_match_type();

        get_play_type();

        balanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Balance_history_activity.class));
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NumberAddActivity.class));
            }
        });

        kheloLudoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Khelo_ludo_activity.class));
            }
        });

        mSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SupportActivity.class));
            }
        });

        mSendNotofication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SendNotificationActivity.class));
            }
        });


        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notice_activity.class);
                startActivity(intent);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddMoneyActivity.class);
                startActivity(intent);

            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WithdrawRequestActivity.class));

            }
        });


        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_out_func();
            }
        });

        searchViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Search_activity.class));
            }
        });

        subAdminControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sub_admin_activity.class));
            }
        });

        createSubAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Create_admin_activity.class));
            }
        });

        sliderLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Slider_add_activity.class));
            }
        });

        popUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Pop_up_add_activity.class));
            }
        });

        ludoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ludo_item_activity.class));
            }
        });

        addMoneyListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Add_money_list_activity.class));
            }
        });

        withdrawListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Withdraw_money_list_activity.class));
            }
        });

        howToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), How_to_join_activity.class));
            }
        });

        dailyScrimsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Daily_scrims_activity.class));
            }
        });

        adminControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Admin_activity.class));
            }
        });

        freeFireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Free_fire_item_activity.class));
            }
        });

        buySellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Buy_sell_activity.class));
            }
        });

        subAdminInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sub_admin_information_activity.class));
            }
        });

        addPromoterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Promoter_activity.class));

            }
        });

        arenaValorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ArenaValor_item_activity.class));

            }
        });

        tournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Tournament_acitivity.class));
            }
        });

        userAbove500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), User_above_activity.class));
            }
        });

        acceptHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Accept_money_history_activity.class));
            }
        });

        khelostatasticID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StatisticsActivity.class);
                startActivity(i);
            }
        });

        if(!checkPermissionForReadExtertalStorage()){
            try {
                requestPermissionForReadExtertalStorage();
            }catch (Exception e){

            }
        }else {

        }
    }


    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = this.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void log_out_func() {
        EasySharedPref.write("api_token", "");
        EasySharedPref.write("secret_id", "");
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void get_play_type() {
        apiService.getPlayType(secret_id, api_token).enqueue(new Callback<Play_Type>() {
            @Override
            public void onResponse(Call<Play_Type> call, Response<Play_Type> response) {
                if (response.isSuccessful() && response.body().getE() == 0 && response.body().getM().size() != 0) {
                    getPlayType = response.body().getM();
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getM().size(); i++) {
                            playType.add(response.body().getM().get(i).getPlayingType());
                        }
                        //Log.d("playType", new Gson().toJson(playType));

                    }
                }
            }

            @Override
            public void onFailure(Call<Play_Type> call, Throwable t) {

            }
        });
    }

    private void get_match_type() {
        apiService.getmatchType(secret_id, api_token).enqueue(new Callback<GameTypeResponse>() {
            @Override
            public void onResponse(Call<GameTypeResponse> call, Response<GameTypeResponse> response) {
                if (response.isSuccessful() && response.body().getE() == 0 && response.body().getM().size() != 0) {
                    gameTypeResponses = response.body().getM();
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getM().size(); i++) {
                            gameType.add(response.body().getM().get(i).getGameName());
                        }
                        //Log.d("gamesTypes", new Gson().toJson(gameType));

                    }
                }
            }

            @Override
            public void onFailure(Call<GameTypeResponse> call, Throwable t) {

            }
        });
    }

    private void get_admin_status() {
        apiService.checkAdminStatus(secret_id, api_token).enqueue(new Callback<Check_admin_status_response>() {
            @Override
            public void onResponse(Call<Check_admin_status_response> call, Response<Check_admin_status_response> response) {
                if (response.body().getE() == 0) {
                    Boolean status = response.body().getM().getAddAdmin();

                    if (status) {
                        createSubAdminCard.setVisibility(View.VISIBLE);
                        adminControlButton.setVisibility(View.VISIBLE);
                        addPromoterButton.setVisibility(View.VISIBLE);
                        balanceCard.setVisibility(View.VISIBLE);
                        userAbove500.setVisibility(View.VISIBLE);
                        acceptHistoryButton.setVisibility(View.VISIBLE);
                    } else {
                        createSubAdminCard.setVisibility(View.GONE);
                        adminControlButton.setVisibility(View.GONE);
                        addPromoterButton.setVisibility(View.GONE);
                        balanceCard.setVisibility(View.GONE);
                        userAbove500.setVisibility(View.GONE);
                        acceptHistoryButton.setVisibility(View.GONE);
                    }
                } else {
                    createSubAdminCard.setVisibility(View.GONE);
                    adminControlButton.setVisibility(View.GONE);
                    addPromoterButton.setVisibility(View.GONE);
                    balanceCard.setVisibility(View.GONE);
                    userAbove500.setVisibility(View.GONE);
                    acceptHistoryButton.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Session expired", Toast.LENGTH_SHORT).show();
                    log_out_func();
                }
            }

            @Override
            public void onFailure(Call<Check_admin_status_response> call, Throwable t) {
                //createSubAdminCard.setVisibility(View.GONE);
                //adminControlButton.setVisibility(View.GONE);


                //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //finish();
            }
        });
    }

    private void openDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_match_dialog, null);

        TextView mAddNew = mView.findViewById(R.id.mAddNew);
        TextView mItemView = mView.findViewById(R.id.mItemView);

        TextView mOnGoing = mView.findViewById(R.id.mOnGoing);
        TextView mResult = mView.findViewById(R.id.mResult);


        //mAddNew.setText("Add New Match");


        mBuilder.setView(mView);
        final AlertDialog dialog1 = mBuilder.create();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();


        mOnGoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), OnGoingActivity.class);
                startActivity(i);

            }
        });


        mResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(i);


            }
        });


        mAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog1.dismiss();


                //addnewMatch();


            }
        });

        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Before_all_created_matches_activity.class);
                startActivity(i);


            }
        });




    }

    private void initview() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx",secret_id+" "+ api_token);

        number = findViewById(R.id.number);
        add = findViewById(R.id.add);
        withdraw = findViewById(R.id.withdraw);
        subAdminControlButton = findViewById(R.id.subAdminControlButtonID);
        createSubAdminButton = findViewById(R.id.createSubAdminButtonID);
        createSubAdminCard = findViewById(R.id.createSubAdminCardID);
        sliderLayoutButton = findViewById(R.id.sliderLayoutButtonID);
        mSendNotofication = findViewById(R.id.mSendNotofication);
        mSupport = findViewById(R.id.mSupport);
        powerButton = findViewById(R.id.powerButtonID);
        searchViewButton = findViewById(R.id.searchViewButtonID);
        popUpButton = findViewById(R.id.popUpButtonID);
        noticeButton = findViewById(R.id.noticeButtonID);
        ludoButton = findViewById(R.id.ludoButtonID);
        freeFireButton = findViewById(R.id.freeFireButtonID);

        addMoneyListButton = findViewById(R.id.addMoneyListButtonID);
        withdrawListButton = findViewById(R.id.withdrawListButtonID);
        howToButton = findViewById(R.id.howToButtonID);
        dailyScrimsButton = findViewById(R.id.dailyScrimsButtonID);
        adminControlButton = findViewById(R.id.adminControlButtonID);
        buySellButton = findViewById(R.id.buySellButtonID);
        subAdminInfoButton = findViewById(R.id.subAdminInfoButtonID);
        addPromoterButton = findViewById(R.id.addPromoterButtonID);
        arenaValorButton = findViewById(R.id.arenaValorButtonID);
        tournamentButton = findViewById(R.id.tournamentButtonID);
        balanceButton = findViewById(R.id.balanceButtonID);
        khelostatasticID = findViewById(R.id.khelostatasticID);

        balanceCard = findViewById(R.id.balanceCardID);
        kheloLudoButton = findViewById(R.id.kheloLudoButtonID);
        userAbove500 = findViewById(R.id.userAbove500);

        acceptHistoryButton = findViewById(R.id.acceptHistoryButton);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}