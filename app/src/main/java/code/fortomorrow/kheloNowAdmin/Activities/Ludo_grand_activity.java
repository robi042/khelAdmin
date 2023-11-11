package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Calendar;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.AddNewMatch.AddNewMatchResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ludo_grand_activity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout matchListButton, addLudoRulesButton, howToJoinButton;
    LinearLayout addMatchButton, onGoingMatchListButton, resultMatchListButton;
    LinearLayout autoAddMatchButton;
    private String ludoDate, ludoTime;
    Calendar cal;
    Dialog loader;
    APIService apiService;
    String secret_id, api_token;
    String gameTypeID = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_grand);

        init_view();

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
                startActivity(intent);
            }
        });

        addLudoRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Ludo_rules_add_activity.class));
                Intent intent = new Intent(getApplicationContext(), Rules_add_activity.class);
                intent.putExtra("play_type", gameTypeID);
                startActivity(intent);
            }
        });

        howToJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), How_to_join_ludo_activity.class));

            }
        });

        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_ludo_match_function();
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

        autoAddMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto_match_add();
            }
        });
    }

    private void add_ludo_match_function() {
        Dialog addMatchDialog = new Dialog(Ludo_grand_activity.this);
        addMatchDialog.setContentView(R.layout.ludo_add_match_alert);
        addMatchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMatchDialog.setCancelable(false);
        addMatchDialog.show();

        Window window = addMatchDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);


        ImageView closeButton = addMatchDialog.findViewById(R.id.closeButtonID);
        Button ludoDateButton = addMatchDialog.findViewById(R.id.ludoDateButtonID);
        Button ludoTimeButton = addMatchDialog.findViewById(R.id.ludoTimeButtonID);
        EditText ludoTitleText = addMatchDialog.findViewById(R.id.ludoTitleTextID);
        EditText ludoEntryFeeText = addMatchDialog.findViewById(R.id.ludoEntryFeeTextID);
        EditText ludoTotalPlayerText = addMatchDialog.findViewById(R.id.ludoTotalPlayerTextID);
        EditText ludoPrizeText = addMatchDialog.findViewById(R.id.ludoPrizeTextID);
        EditText ludoTypeText = addMatchDialog.findViewById(R.id.ludoTypeTextID);
        EditText ludoGameTypeText = addMatchDialog.findViewById(R.id.ludoGameTypeTextID);
        Button ludoSubmitButton = addMatchDialog.findViewById(R.id.ludoSubmitButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatchDialog.dismiss();
            }
        });

        ludoDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(Ludo_grand_activity.this, (view1, year, month, dayOfMonth) -> {

                    ludoDate = String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year);
                    //Toast.makeText(getApplicationContext(), ludoDate, Toast.LENGTH_SHORT).show();

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });

        ludoTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(Ludo_grand_activity.this, (view1, hourOfDay, minute) -> {

                    boolean isPM = (hourOfDay >= 12);

                    int hour = hourOfDay % 12;
                    if (hour == 0)
                        hour = 12;

                    ludoTime = String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");

                    //Toast.makeText(getApplicationContext(), ludoTime, Toast.LENGTH_SHORT).show();

                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(Ludo_grand_activity.this));
                tpd.show();
            }
        });

        ludoSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ludoTitle = ludoTitleText.getText().toString().trim();
                String ludoEntryFee = ludoEntryFeeText.getText().toString().trim();
                String ludoTotalPlayer = ludoTotalPlayerText.getText().toString().trim();
                String ludoPrize = ludoPrizeText.getText().toString().trim();
                String ludoType = ludoTypeText.getText().toString().trim();
                String ludoGameType = ludoGameTypeText.getText().toString().trim();

                if (TextUtils.isEmpty(ludoDate) || TextUtils.isEmpty(ludoTime) || TextUtils.isEmpty(ludoTitle) || TextUtils.isEmpty(ludoEntryFee) || TextUtils.isEmpty(ludoTotalPlayer) || TextUtils.isEmpty(ludoPrize) || TextUtils.isEmpty(ludoType) || TextUtils.isEmpty(ludoGameType)) {
                    if (TextUtils.isEmpty(ludoDate)) {
                        Toasty.error(getApplicationContext(), "Set Date", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoTime)) {
                        Toasty.error(getApplicationContext(), "Set Time", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoTitle)) {
                        Toasty.error(getApplicationContext(), "Empty Title", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoEntryFee)) {
                        Toasty.error(getApplicationContext(), "Empty Entry Fee", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoTotalPlayer)) {
                        Toasty.error(getApplicationContext(), "Empty Total Player", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoPrize)) {
                        Toasty.error(getApplicationContext(), "Empty Prize", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoType)) {
                        Toasty.error(getApplicationContext(), "Empty Type", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoGameType)) {
                        Toasty.error(getApplicationContext(), "Empty Game Type", Toasty.LENGTH_SHORT).show();

                    }
                } else {
                    //Toasty.success(getApplicationContext(), "All Done", Toasty.LENGTH_SHORT).show();
                    //code
                    //Log.d("dataxxx", ludoDate+" "+ludoTitle+" "+ludoType);
                    loader.show();

                    apiService.getLudoAddMatchResponse(secret_id, api_token, ludoDate, ludoTime, ludoTitle, ludoEntryFee, ludoTotalPlayer, ludoPrize, ludoGameType, "4").enqueue(new Callback<AddNewMatchResponse>() {
                        @Override
                        public void onResponse(Call<AddNewMatchResponse> call, Response<AddNewMatchResponse> response) {
                            loader.dismiss();
                            //dialog1.dismiss();

                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.success(getApplicationContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddNewMatchResponse> call, Throwable t) {
                            loader.dismiss();

                            Toasty.success(getApplicationContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void auto_match_add() {
        Dialog addAutoMatchDialog = new Dialog(Ludo_grand_activity.this);
        addAutoMatchDialog.setContentView(R.layout.ludo_auto_match_add_alert);
        addAutoMatchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addAutoMatchDialog.setCancelable(false);
        addAutoMatchDialog.show();

        Window window = addAutoMatchDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);


        ImageView closeButton = addAutoMatchDialog.findViewById(R.id.closeButtonID);
        EditText ludoTitleText = addAutoMatchDialog.findViewById(R.id.ludoTitleTextID);
        EditText ludoEntryFeeText = addAutoMatchDialog.findViewById(R.id.ludoEntryFeeTextID);
        EditText ludoTotalPlayerText = addAutoMatchDialog.findViewById(R.id.ludoTotalPlayerTextID);
        EditText ludoPrizeText = addAutoMatchDialog.findViewById(R.id.ludoPrizeTextID);
        EditText ludoTypeText = addAutoMatchDialog.findViewById(R.id.ludoTypeTextID);
        EditText ludoGameTypeText = addAutoMatchDialog.findViewById(R.id.ludoGameTypeTextID);
        EditText titleNumberText = addAutoMatchDialog.findViewById(R.id.titleNumberTextID);
        EditText totalMatchText = addAutoMatchDialog.findViewById(R.id.totalMatchTextID);
        EditText sameTimeMatchText = addAutoMatchDialog.findViewById(R.id.sameTimeMatchTextID);
        EditText gapTimeText = addAutoMatchDialog.findViewById(R.id.gapTimeTextID);
        Button ludoDateButton = addAutoMatchDialog.findViewById(R.id.ludoDateButtonID);
        Button ludoTimeButton = addAutoMatchDialog.findViewById(R.id.ludoTimeButtonID);
        Button ludoSubmitButton = addAutoMatchDialog.findViewById(R.id.ludoSubmitButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAutoMatchDialog.dismiss();
            }
        });

        ludoDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(Ludo_grand_activity.this, (view1, year, month, dayOfMonth) -> {

                    ludoDate = String.format("%d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + String.format("%02d", year);
                    //Toast.makeText(getApplicationContext(), ludoDate, Toast.LENGTH_SHORT).show();

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });

        ludoTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(Ludo_grand_activity.this, (view1, hourOfDay, minute) -> {

                    boolean isPM = (hourOfDay >= 12);

                    int hour = hourOfDay % 12;
                    if (hour == 0)
                        hour = 12;

                    ludoTime = String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");

                    //Toast.makeText(getApplicationContext(), ludoTime, Toast.LENGTH_SHORT).show();

                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(Ludo_grand_activity.this));
                tpd.show();
            }
        });

        ludoSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ludoTitle = ludoTitleText.getText().toString().trim();
                String titleNumber = titleNumberText.getText().toString().trim();
                String totalMatch = totalMatchText.getText().toString().trim();
                String sameTimeMatch = sameTimeMatchText.getText().toString().trim();
                String gapTime = gapTimeText.getText().toString().trim();
                String ludoEntryFee = ludoEntryFeeText.getText().toString().trim();
                String ludoTotalPlayer = ludoTotalPlayerText.getText().toString().trim();
                String ludoPrize = ludoPrizeText.getText().toString().trim();
                String ludoType = ludoTypeText.getText().toString().trim();
                String ludoGameType = ludoGameTypeText.getText().toString().trim();

                if (TextUtils.isEmpty(ludoDate) || TextUtils.isEmpty(ludoTime) || TextUtils.isEmpty(ludoTitle) ||
                        TextUtils.isEmpty(titleNumber) || TextUtils.isEmpty(totalMatch) || TextUtils.isEmpty(sameTimeMatch) ||
                        TextUtils.isEmpty(gapTime) || TextUtils.isEmpty(ludoEntryFee) || TextUtils.isEmpty(ludoTotalPlayer) ||
                        TextUtils.isEmpty(ludoPrize) || TextUtils.isEmpty(ludoType) || TextUtils.isEmpty(ludoGameType)) {
                    if (TextUtils.isEmpty(ludoDate)) {
                        Toasty.error(getApplicationContext(), "Set Date", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoTime)) {
                        Toasty.error(getApplicationContext(), "Set Time", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoTitle)) {
                        Toasty.error(getApplicationContext(), "Empty Title", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoEntryFee)) {
                        Toasty.error(getApplicationContext(), "Empty Entry Fee", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoTotalPlayer)) {
                        Toasty.error(getApplicationContext(), "Empty Total Player", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoPrize)) {
                        Toasty.error(getApplicationContext(), "Empty Prize", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoType)) {
                        Toasty.error(getApplicationContext(), "Empty Type", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(ludoGameType)) {
                        Toasty.error(getApplicationContext(), "Empty Game Type", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(titleNumber)) {
                        Toasty.error(getApplicationContext(), "Empty Title Number", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(totalMatch)) {
                        Toasty.error(getApplicationContext(), "Empty Total Match", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(sameTimeMatch)) {
                        Toasty.error(getApplicationContext(), "Empty Same Time Match", Toasty.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(gapTime)) {
                        Toasty.error(getApplicationContext(), "Empty Gap Time", Toasty.LENGTH_SHORT).show();

                    }
                } else {

                    //Log.d("dataxx", ludoDate + " " + ludoTime + " " + ludoTitle + " " + titleNumber + totalMatch + " " + sameTimeMatch + " " + gapTime + " ");

                    loader.show();

                    apiService.auto_add_new_ludo_match(secret_id, api_token, ludoDate, ludoTime, ludoTitle + " ", ludoEntryFee, ludoTotalPlayer, ludoPrize, ludoGameType, gameTypeID, titleNumber, totalMatch, sameTimeMatch, gapTime).enqueue(new Callback<AddNewMatchResponse>() {
                        @Override
                        public void onResponse(Call<AddNewMatchResponse> call, Response<AddNewMatchResponse> response) {
                            loader.dismiss();
                            //dialog1.dismiss();

                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.success(getApplicationContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddNewMatchResponse> call, Throwable t) {
                            loader.dismiss();

                            Toasty.success(getApplicationContext(), "Something went wrong", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        loader = new Dialog(Ludo_grand_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        cal = Calendar.getInstance();

        backButton = findViewById(R.id.backButton);
        matchListButton = findViewById(R.id.matchListButtonID);
        addLudoRulesButton = findViewById(R.id.addLudoRulesButtonID);
        howToJoinButton = findViewById(R.id.howToJoinButtonID);
        addMatchButton = findViewById(R.id.addMatchButtonID);
        onGoingMatchListButton = findViewById(R.id.onGoingMatchListButtonID);
        resultMatchListButton = findViewById(R.id.resultMatchListButtonID);
        autoAddMatchButton = findViewById(R.id.autoAddMatchButtonID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}