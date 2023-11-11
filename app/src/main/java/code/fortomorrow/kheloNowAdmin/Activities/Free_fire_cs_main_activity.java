package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.AddNewMatch.AddNewMatchResponse;
import code.fortomorrow.kheloNowAdmin.Model.GameType.M;
import code.fortomorrow.kheloNowAdmin.Model.PlayType.Play_Type;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Free_fire_cs_main_activity extends AppCompatActivity {

    LinearLayout matchListButton, addRulesButton, howToJoinButton;
    LinearLayout addMatchButton, onGoingMatchListButton, resultMatchListButton;
    LinearLayout autoAddMatchButton;
    ImageView backButton;

    APIService apiService;
    String api_token, secret_id;
    Dialog loader;

    Calendar cal;
    private List<M> gameTypeResponses = new ArrayList<>();
    private List<String> gameType = new ArrayList<>();
    private List<code.fortomorrow.kheloNowAdmin.Model.PlayType.M> getPlayType = new ArrayList<>();
    List<String> playType = new ArrayList<>();
    String gameTypeID;
    String playTypeID;
    static String date, time;
    TextView nameText;
    Boolean oneState = false, twoState = false, threeState = false, fourState = false, fiveState = false, sixState = false, sevenState = false, eightState = false, nineState = false, tenState = false;
    String prizeOne, prizeTwo, prizeThree, prizeFour, prizeFive, prizeSix, prizeSeven, prizeEight, prizeNine, prizeTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_cs_main);

        init_view();

        if (gameTypeID.equals("2")) {
            nameText.setText("Free Fire (CS Regular)");
        } else if (gameTypeID.equals("5")) {
            nameText.setText("Free Fire (CS Grand)");
        }

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        loader = new Dialog(Free_fire_cs_main_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        matchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllCreatedMatches.class);
                intent.putExtra("play_type", gameTypeID);
                //intent.putExtra("game_type", playTypeID);
                startActivity(intent);
            }
        });

        addRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Rules_add_activity.class);
                intent.putExtra("play_type", gameTypeID);
                startActivity(intent);
            }
        });

        howToJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Available soon", Toast.LENGTH_SHORT).show();
            }
        });

        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_match();
            }
        });

        autoAddMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto_add_match();
            }
        });

        onGoingMatchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OnGoingActivity.class);
                intent.putExtra("play_type", gameTypeID);
                startActivity(intent);
            }
        });

        resultMatchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("play_type", gameTypeID);
                startActivity(intent);
            }
        });
    }

    private void auto_add_match() {
        Dialog addMatchDialog = new Dialog(Free_fire_cs_main_activity.this);
        addMatchDialog.setContentView(R.layout.free_fire_add_auto_match_alert);
        addMatchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMatchDialog.setCancelable(false);
        addMatchDialog.show();

        Window window = addMatchDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = addMatchDialog.findViewById(R.id.closeButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatchDialog.dismiss();
            }
        });

        Button saveButton = addMatchDialog.findViewById(R.id.mBtn);
        Button mDate = addMatchDialog.findViewById(R.id.mDate);
        Button mTime = addMatchDialog.findViewById(R.id.mTime);

        Button choose = addMatchDialog.findViewById(R.id.choose);

        EditText mTitle = addMatchDialog.findViewById(R.id.mTitle);
        EditText titleNumberText = addMatchDialog.findViewById(R.id.titleNumberTextID);
        EditText totalMatchText = addMatchDialog.findViewById(R.id.totalMatchTextID);
        EditText sameTimeMatchText = addMatchDialog.findViewById(R.id.sameTimeMatchTextID);
        EditText gapTimeText = addMatchDialog.findViewById(R.id.gapTimeTextID);
        EditText mEntryFee = addMatchDialog.findViewById(R.id.mEntryFee);
        EditText mTotalPlayers = addMatchDialog.findViewById(R.id.mTotalPlayer);
        EditText mTotalPrize = addMatchDialog.findViewById(R.id.mTotalPrize);
        EditText mPerKill = addMatchDialog.findViewById(R.id.mPerKill);
        EditText mMap = addMatchDialog.findViewById(R.id.mMap);
        EditText mVersion = addMatchDialog.findViewById(R.id.mVersion);
        Spinner typeSpinners = addMatchDialog.findViewById(R.id.typeSpinners);
        //typeSpinners.setVisibility(View.GONE);

        apiService.getPlayType(secret_id, api_token).enqueue(new Callback<Play_Type>() {
            @Override
            public void onResponse(Call<Play_Type> call, Response<Play_Type> response) {
                if (response.isSuccessful() && response.body().getE() == 0 && response.body().getM().size() != 0) {
                    getPlayType = response.body().getM();
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getM().size(); i++) {
                            playType.add(response.body().getM().get(i).getPlayingType());
                        }
                        //Toast.makeText(getApplicationContext(), String.valueOf("Playtype" +playType.size()), Toast.LENGTH_SHORT).show()

                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, playType);
                        typeSpinners.setAdapter(dataAdapter2);

                    }
                }
            }

            @Override
            public void onFailure(Call<Play_Type> call, Throwable t) {

            }
        });


        EditText mPrizeOne = addMatchDialog.findViewById(R.id.mPrizeOne);
        EditText mPrizeTwo = addMatchDialog.findViewById(R.id.mPrizeTwo);
        EditText mPrizeThree = addMatchDialog.findViewById(R.id.mPrizeThree);
        EditText mPrizeFour = addMatchDialog.findViewById(R.id.mPrizeFour);
        EditText mPrizeFive = addMatchDialog.findViewById(R.id.mPrizeFive);
        EditText mPrizeSix = addMatchDialog.findViewById(R.id.mPrizeSix);
        EditText mPrizeSeven = addMatchDialog.findViewById(R.id.mPrizeSeven);
        EditText mPrizeEight = addMatchDialog.findViewById(R.id.mPrizeEight);
        EditText mPrizeNine = addMatchDialog.findViewById(R.id.mPrizeNine);
        EditText mPrizeTen = addMatchDialog.findViewById(R.id.mPrizeTen);


        typeSpinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeofGame = typeSpinners.getSelectedItem().toString();
                //Log.d("typeofSpinner", typeofGame);
                for (int i = 0; i < getPlayType.size(); i++) {
                    if (getPlayType.get(i).getPlayingType().contains(typeofGame)) {
                        playTypeID = String.valueOf(getPlayType.get(i).getPlayTypeId());
                        break;
                    }
                }

                //Toast.makeText(Free_fire_cs_main_activity.this, playTypeID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                loader.show();

                if (checkAllFileds()) {

                    prizeOne = mPrizeOne.getText().toString().trim();
                    prizeTwo = mPrizeTwo.getText().toString().trim();
                    prizeThree = mPrizeThree.getText().toString().trim();
                    prizeFour = mPrizeFour.getText().toString().trim();
                    prizeFive = mPrizeFive.getText().toString().trim();
                    prizeSix = mPrizeSix.getText().toString().trim();
                    prizeSeven = mPrizeSeven.getText().toString().trim();
                    prizeEight = mPrizeEight.getText().toString().trim();
                    prizeNine = mPrizeNine.getText().toString().trim();
                    prizeTen = mPrizeTen.getText().toString().trim();

                    if (!TextUtils.isEmpty(prizeOne)) {
                        oneState = true;
                    }

                    if (!TextUtils.isEmpty(prizeTwo)) {
                        twoState = true;
                    }
                    if (!TextUtils.isEmpty(prizeThree)) {
                        threeState = true;
                    }
                    if (!TextUtils.isEmpty(prizeFour)) {
                        fourState = true;
                    }
                    if (!TextUtils.isEmpty(prizeFive)) {
                        fiveState = true;
                    }
                    if (!TextUtils.isEmpty(prizeSix)) {
                        sixState = true;
                    }
                    if (!TextUtils.isEmpty(prizeSeven)) {
                        sevenState = true;
                    }
                    if (!TextUtils.isEmpty(prizeEight)) {
                        eightState = true;
                    }
                    if (!TextUtils.isEmpty(prizeNine)) {
                        nineState = true;
                    }
                    if (!TextUtils.isEmpty(prizeTen)) {
                        tenState = true;
                    }


                    apiService.add_automated_cs_match(secret_id, api_token, date, time, mEntryFee.getText().toString(),
                            mMap.getText().toString(), mPerKill.getText().toString(), mTitle.getText().toString()+" ",
                            titleNumberText.getText().toString(), totalMatchText.getText().toString(), sameTimeMatchText.getText().toString(),
                            gapTimeText.getText().toString(), mTotalPlayers.getText().toString(), mTotalPrize.getText().toString(),
                            oneState.toString(), prizeOne, twoState.toString(), prizeTwo, threeState.toString(), prizeThree, fourState.toString(),
                            prizeFour, fiveState.toString(), prizeFive, sixState.toString(), prizeSix, sevenState.toString(),
                            prizeSeven, eightState.toString(), prizeEight, nineState.toString(), prizeNine,
                            tenState.toString(), prizeTen, gameTypeID, playTypeID).enqueue(new Callback<AddNewMatchResponse>() {
                        @Override
                        public void onResponse(Call<AddNewMatchResponse> call, Response<AddNewMatchResponse> response) {
                            if (response.body().getE() == 0) {
                                loader.dismiss();
                                Toasty.success(getApplicationContext(), "Match Added Successfully").show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddNewMatchResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toasty.error(getApplicationContext(), "Please all the fills", Toasty.LENGTH_SHORT).show();
                }


            }

            private boolean checkAllFileds() {
                if (TextUtils.isEmpty(date) || TextUtils.isEmpty(time) || TextUtils.isEmpty(mEntryFee.getText().toString()) ||

                        TextUtils.isEmpty(mMap.getText().toString()) || TextUtils.isEmpty(mPerKill.getText().toString()) ||
                        TextUtils.isEmpty(mTitle.getText().toString()) || TextUtils.isEmpty(mTotalPlayers.getText().toString())
                ) {

                    return false;
                } else {
                    return true;
                }
            }


        });


        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog tpd = new TimePickerDialog(Free_fire_cs_main_activity.this, (view1, hourOfDay, minute) -> {

                    boolean isPM = (hourOfDay >= 12);

                    int hour = hourOfDay % 12;
                    if (hour == 0)
                        hour = 12;

                    time = String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");

                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(Free_fire_cs_main_activity.this));
                tpd.show();

            }
        });


        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog dpd = new DatePickerDialog(Free_fire_cs_main_activity.this, (view1, year, month, dayOfMonth) -> {

                    date = String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year);

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();

            }
        });
    }

    private void add_new_match() {
        Dialog addMatchDialog = new Dialog(Free_fire_cs_main_activity.this);
        addMatchDialog.setContentView(R.layout.free_fire_add_match_alert);
        addMatchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMatchDialog.setCancelable(false);
        addMatchDialog.show();

        Window window = addMatchDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = addMatchDialog.findViewById(R.id.closeButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatchDialog.dismiss();
            }
        });

        Button saveButton = addMatchDialog.findViewById(R.id.mBtn);
        Button mDate = addMatchDialog.findViewById(R.id.mDate);
        Button mTime = addMatchDialog.findViewById(R.id.mTime);

        Button choose = addMatchDialog.findViewById(R.id.choose);

        EditText mTitle = addMatchDialog.findViewById(R.id.mTitle);
        EditText mEntryFee = addMatchDialog.findViewById(R.id.mEntryFee);
        EditText mTotalPlayers = addMatchDialog.findViewById(R.id.mTotalPlayer);
        EditText mTotalPrize = addMatchDialog.findViewById(R.id.mTotalPrize);
        EditText mPerKill = addMatchDialog.findViewById(R.id.mPerKill);
        EditText mMap = addMatchDialog.findViewById(R.id.mMap);
        EditText mVersion = addMatchDialog.findViewById(R.id.mVersion);
        Spinner typeSpinners = addMatchDialog.findViewById(R.id.typeSpinners);
        //typeSpinners.setVisibility(View.GONE);

        apiService.getPlayType(secret_id, api_token).enqueue(new Callback<Play_Type>() {
            @Override
            public void onResponse(Call<Play_Type> call, Response<Play_Type> response) {
                if (response.isSuccessful() && response.body().getE() == 0 && response.body().getM().size() != 0) {
                    getPlayType = response.body().getM();
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().getM().size(); i++) {
                            playType.add(response.body().getM().get(i).getPlayingType());
                        }
                        //Toast.makeText(getApplicationContext(), String.valueOf("Playtype" +playType.size()), Toast.LENGTH_SHORT).show()

                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, playType);
                        typeSpinners.setAdapter(dataAdapter2);

                    }
                }
            }

            @Override
            public void onFailure(Call<Play_Type> call, Throwable t) {

            }
        });


        EditText mPrizeOne = addMatchDialog.findViewById(R.id.mPrizeOne);
        EditText mPrizeTwo = addMatchDialog.findViewById(R.id.mPrizeTwo);
        EditText mPrizeThree = addMatchDialog.findViewById(R.id.mPrizeThree);
        EditText mPrizeFour = addMatchDialog.findViewById(R.id.mPrizeFour);
        EditText mPrizeFive = addMatchDialog.findViewById(R.id.mPrizeFive);
        EditText mPrizeSix = addMatchDialog.findViewById(R.id.mPrizeSix);
        EditText mPrizeSeven = addMatchDialog.findViewById(R.id.mPrizeSeven);
        EditText mPrizeEight = addMatchDialog.findViewById(R.id.mPrizeEight);
        EditText mPrizeNine = addMatchDialog.findViewById(R.id.mPrizeNine);
        EditText mPrizeTen = addMatchDialog.findViewById(R.id.mPrizeTen);


        typeSpinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typeofGame = typeSpinners.getSelectedItem().toString();
                //Log.d("typeofSpinner", typeofGame);
                for (int i = 0; i < getPlayType.size(); i++) {
                    if (getPlayType.get(i).getPlayingType().contains(typeofGame)) {
                        playTypeID = String.valueOf(getPlayType.get(i).getPlayTypeId());
                        break;
                    }
                }

                //Toast.makeText(Free_fire_cs_main_activity.this, playTypeID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loader.show();

                if (checkAllFileds()) {

                    prizeOne = mPrizeOne.getText().toString().trim();
                    prizeTwo = mPrizeTwo.getText().toString().trim();
                    prizeThree = mPrizeThree.getText().toString().trim();
                    prizeFour = mPrizeFour.getText().toString().trim();
                    prizeFive = mPrizeFive.getText().toString().trim();
                    prizeSix = mPrizeSix.getText().toString().trim();
                    prizeSeven = mPrizeSeven.getText().toString().trim();
                    prizeEight = mPrizeEight.getText().toString().trim();
                    prizeNine = mPrizeNine.getText().toString().trim();
                    prizeTen = mPrizeTen.getText().toString().trim();

                    if (!TextUtils.isEmpty(prizeOne)) {
                        oneState = true;
                    }

                    if (!TextUtils.isEmpty(prizeTwo)) {
                        twoState = true;
                    }
                    if (!TextUtils.isEmpty(prizeThree)) {
                        threeState = true;
                    }
                    if (!TextUtils.isEmpty(prizeFour)) {
                        fourState = true;
                    }
                    if (!TextUtils.isEmpty(prizeFive)) {
                        fiveState = true;
                    }
                    if (!TextUtils.isEmpty(prizeSix)) {
                        sixState = true;
                    }
                    if (!TextUtils.isEmpty(prizeSeven)) {
                        sevenState = true;
                    }
                    if (!TextUtils.isEmpty(prizeEight)) {
                        eightState = true;
                    }
                    if (!TextUtils.isEmpty(prizeNine)) {
                        nineState = true;
                    }
                    if (!TextUtils.isEmpty(prizeTen)) {
                        tenState = true;
                    }

                    apiService.getAddMatchResponse(secret_id, api_token, date + " at " + time, mEntryFee.getText().toString(), mMap.getText().toString(),
                            mPerKill.getText().toString(), mTitle.getText().toString(), mTotalPlayers.getText().toString(), mTotalPrize.getText().toString(),
                            oneState.toString(), prizeOne, twoState.toString(), prizeTwo, threeState.toString(), prizeThree, fourState.toString(), prizeFour,
                            fiveState.toString(), prizeFive, sixState.toString(), prizeSix, sevenState.toString(), prizeSeven, eightState.toString(),
                            prizeEight, nineState.toString(), prizeNine, tenState.toString(), prizeTen, gameTypeID, playTypeID).enqueue(new Callback<AddNewMatchResponse>() {
                        @Override
                        public void onResponse(Call<AddNewMatchResponse> call, Response<AddNewMatchResponse> response) {
                            if (response.body().getE() == 0) {
                                loader.dismiss();
                                Toasty.success(getApplicationContext(), "Match Added Successfully").show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddNewMatchResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toasty.error(getApplicationContext(), "Please all the fills", Toasty.LENGTH_SHORT).show();
                }


            }

            private boolean checkAllFileds() {
                if (TextUtils.isEmpty(date) || TextUtils.isEmpty(time) || TextUtils.isEmpty(mEntryFee.getText().toString()) ||

                        TextUtils.isEmpty(mMap.getText().toString()) || TextUtils.isEmpty(mPerKill.getText().toString()) ||
                        TextUtils.isEmpty(mTitle.getText().toString()) || TextUtils.isEmpty(mTotalPlayers.getText().toString())
                ) {

                    return false;
                } else {
                    return true;
                }
            }


        });


        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog tpd = new TimePickerDialog(Free_fire_cs_main_activity.this, (view1, hourOfDay, minute) -> {

                    boolean isPM = (hourOfDay >= 12);

                    int hour = hourOfDay % 12;
                    if (hour == 0)
                        hour = 12;

                    time = String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");

                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(Free_fire_cs_main_activity.this));
                tpd.show();

            }
        });


        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog dpd = new DatePickerDialog(Free_fire_cs_main_activity.this, (view1, year, month, dayOfMonth) -> {

                    date = String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year);

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();

            }
        });

    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        gameTypeID = getIntent().getStringExtra("game_type");
        //playTypeID = getIntent().getStringExtra("play_type");
       // Toast.makeText(this, gameTypeID+" "+playTypeID, Toast.LENGTH_SHORT).show();

        cal = Calendar.getInstance();
        backButton = findViewById(R.id.backButton);
        matchListButton = findViewById(R.id.matchListButtonID);
        addRulesButton = findViewById(R.id.addRulesButtonID);
        howToJoinButton = findViewById(R.id.howToJoinButtonID);
        addMatchButton = findViewById(R.id.addMatchButtonID);
        onGoingMatchListButton = findViewById(R.id.onGoingMatchListButtonID);
        resultMatchListButton = findViewById(R.id.resultMatchListButtonID);
        autoAddMatchButton = findViewById(R.id.autoAddMatchButtonID);

        nameText = findViewById(R.id.nameTextID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}