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

import java.util.Calendar;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArenaValor_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String gameID;
    TextView titleText;
    ImageView backButton;
    LinearLayout matchListButton, addRulesButton, howToJoinButton, addMatchButton, onGoingMatchListButton, resultMatchListButton;
    Dialog loader;
    String secret_id, api_token;
    APIService apiService;
    Calendar cal;
    static String date, time;
    String[] gameTypes = {"3 vs 3", "5 vs 5"};
    String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_valor);

        init_view();

        set_text();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_new_match();
            }
        });

        matchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllCreatedMatches.class);
                intent.putExtra("play_type", gameID);
                startActivity(intent);
            }
        });

        onGoingMatchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OnGoingActivity.class);
                intent.putExtra("play_type", gameID);
                startActivity(intent);
            }
        });

        resultMatchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("play_type", gameID);
                startActivity(intent);
            }
        });

        addRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Rules_add_activity.class));
                Intent intent = new Intent(new Intent(getApplicationContext(), Rules_add_activity.class));
                intent.putExtra("play_type", gameID);
                startActivity(intent);
            }
        });


    }

    private void add_new_match() {
        Dialog addMatchDialog = new Dialog(ArenaValor_activity.this);
        addMatchDialog.setContentView(R.layout.arena_valor_add_match_alert);
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

        EditText mVersion = addMatchDialog.findViewById(R.id.mVersion);
        Spinner typeSpinners = addMatchDialog.findViewById(R.id.typeSpinners);

        typeSpinners.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gameTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        typeSpinners.setAdapter(aa);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                if (checkAllFileds()) {


                    String playTypeID = null;
                    //Log.d("dataxx", gameType + " " + date + " " + time + " " + mTotalPrize.getText().toString().trim() + " " + gameType + " " + gameID);
                    if (gameType.equals("3 vs 3")) {
                        playTypeID = "10";
                    } else if (gameType.equals("5 vs 5")) {
                        playTypeID = "11";
                    }

                    loader.show();

                    apiService.addNewValorMatch(secret_id, api_token, date, time,
                            mTitle.getText().toString().trim(),
                            mEntryFee.getText().toString().trim(),
                            mTotalPlayers.getText().toString().trim(),
                            mTotalPrize.getText().toString().trim(),
                            mVersion.getText().toString().trim(),
                            gameID, playTypeID).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();

                            if (response.body().e == 0) {
                                Toasty.success(getApplicationContext(), "match added", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), response.body().m, Toasty.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                }


            }

            private boolean checkAllFileds() {
                if (TextUtils.isEmpty(date) ||
                        TextUtils.isEmpty(time) ||
                        TextUtils.isEmpty(mEntryFee.getText().toString()) ||
                        TextUtils.isEmpty(mTitle.getText().toString()) ||
                        TextUtils.isEmpty(mTotalPlayers.getText().toString()) ||
                        TextUtils.isEmpty(mTotalPrize.getText().toString().trim()) ||
                        TextUtils.isEmpty(mVersion.getText().toString().trim())
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


                TimePickerDialog tpd = new TimePickerDialog(ArenaValor_activity.this, (view1, hourOfDay, minute) -> {

                    boolean isPM = (hourOfDay >= 12);

                    int hour = hourOfDay % 12;
                    if (hour == 0)
                        hour = 12;

                    time = String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");

                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(ArenaValor_activity.this));
                tpd.show();

            }
        });


        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog dpd = new DatePickerDialog(ArenaValor_activity.this, (view1, year, month, dayOfMonth) -> {

                    date = String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year);

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();

            }
        });

    }

    private void set_text() {
        if (gameID.equals("11")) {
            titleText.setText("Arena of Valor (Regular)");
        } else if (gameID.equals("12")) {
            titleText.setText("Arena of Valor (Grand)");
        }
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx", api_token);

        loader = new Dialog(ArenaValor_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cal = Calendar.getInstance();

        gameID = getIntent().getStringExtra("game_id");
        titleText = findViewById(R.id.titleTextID);

        backButton = findViewById(R.id.backButton);
        matchListButton = findViewById(R.id.matchListButtonID);
        addRulesButton = findViewById(R.id.addRulesButtonID);
        howToJoinButton = findViewById(R.id.howToJoinButtonID);
        addMatchButton = findViewById(R.id.addMatchButtonID);
        onGoingMatchListButton = findViewById(R.id.onGoingMatchListButtonID);
        resultMatchListButton = findViewById(R.id.resultMatchListButtonID);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gameType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}