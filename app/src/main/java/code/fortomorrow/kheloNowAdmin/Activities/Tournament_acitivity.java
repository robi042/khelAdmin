package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Tournament.Tournament_adapter;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.Tournament.Tournament_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tournament_acitivity extends AppCompatActivity {

    ImageView backButton;
    FloatingActionButton addTournamentButton;
    Calendar myCalendar;
    String date;
    String dateFormat = "dd-MM-yyyy";
    SimpleDateFormat sdf;
    String dateCurrent;

    EditText startDateText, endDateText;
    Boolean hasPrizeOne = false, hasPrizeTwo = false, hasPrizeThree = false, hasPrizeFour = false, hasPrizeFive = false, hasPrizeSix = false, hasPrizeSeven = false, hasPrizeEight = false, hasPrizeNine = false, hasPrizeTen = false, hasPrizeEleven = false, hasPrizeTwelve = false;

    RecyclerView tournamentView;
    private Tournament_adapter tournamentAdapter;
    APIService apiService;
    String secret_id, api_token;
    Dialog loader;

    private List<Tournament_list_response.M> tournamentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_acitivity);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_tournament_func();
            }
        });

        get_data();
    }

    private void get_data() {


        apiService.get_tournament_matches(secret_id, api_token).enqueue(new Callback<Tournament_list_response>() {
            @Override
            public void onResponse(Call<Tournament_list_response> call, Response<Tournament_list_response> response) {
                if(response.body().e == 0){
                    tournamentList = new ArrayList<>();
                    tournamentList = response.body().m;
                    tournamentAdapter = new Tournament_adapter(tournamentList);
                    tournamentView.setAdapter(tournamentAdapter);
                }else {

                }
            }

            @Override
            public void onFailure(Call<Tournament_list_response> call, Throwable t) {

            }
        });
    }

    private void add_tournament_func() {
        Dialog addTournament = new Dialog(Tournament_acitivity.this);
        addTournament.setContentView(R.layout.add_tournament_alert);
        addTournament.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addTournament.setCancelable(false);
        addTournament.show();

        Window window = addTournament.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = addTournament.findViewById(R.id.closeButtonID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTournament.dismiss();
            }
        });

        startDateText = addTournament.findViewById(R.id.startDateTextID);
        endDateText = addTournament.findViewById(R.id.endDateTextID);

        startDateText.setText(current_date());
        endDateText.setText(current_date());

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_start_date();

                //startDateText.setText(pick_date());
            }
        });

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick_end_date();
            }
        });

        EditText titleEditText = addTournament.findViewById(R.id.titleEditTextID);
        EditText teamEditText = addTournament.findViewById(R.id.teamEditTextID);
        EditText prizeEditText = addTournament.findViewById(R.id.prizeEditTextID);
        EditText prizeOneEditText = addTournament.findViewById(R.id.prizeOneEditTextID);
        EditText prizeTwoEditText = addTournament.findViewById(R.id.prizeTwoEditTextID);
        EditText prizeThreeEditText = addTournament.findViewById(R.id.prizeThreeEditTextID);
        EditText prizeFourEditText = addTournament.findViewById(R.id.prizeFourEditTextID);
        EditText prizeFiveEditText = addTournament.findViewById(R.id.prizeFiveEditTextID);
        EditText prizeSixEditText = addTournament.findViewById(R.id.prizeSixEditTextID);
        EditText prizeSevenEditText = addTournament.findViewById(R.id.prizeSevenEditTextID);
        EditText prizeEightEditText = addTournament.findViewById(R.id.prizeEightEditTextID);
        EditText prizeNineEditText = addTournament.findViewById(R.id.prizeNineEditTextID);
        EditText prizeTenEditText = addTournament.findViewById(R.id.prizeTenEditTextID);
        EditText prizeElevenEditText = addTournament.findViewById(R.id.prizeElevenEditTextID);
        EditText prizeTwelveEditText = addTournament.findViewById(R.id.prizeTwelveEditTextID);
        AppCompatButton submitButton = addTournament.findViewById(R.id.submitButtonID);
        EditText entryFeeEditText = addTournament.findViewById(R.id.entryFeeEditTextID);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDate = startDateText.getText().toString().trim();
                String endDate = endDateText.getText().toString().trim();
                String title = titleEditText.getText().toString().trim();
                String totalTeam = teamEditText.getText().toString().trim();
                String prize = prizeEditText.getText().toString().trim();
                String entryFee = entryFeeEditText.getText().toString().trim();
                String prizeOne = prizeOneEditText.getText().toString().trim();
                String prizeTwo = prizeTwoEditText.getText().toString().trim();
                String prizeThree = prizeThreeEditText.getText().toString().trim();
                String prizeFour = prizeFourEditText.getText().toString().trim();
                String prizeFive = prizeFiveEditText.getText().toString().trim();
                String prizeSix = prizeSixEditText.getText().toString().trim();
                String prizeSeven = prizeSevenEditText.getText().toString().trim();
                String prizeEight = prizeEightEditText.getText().toString().trim();
                String prizeNine = prizeNineEditText.getText().toString().trim();
                String prizeTen = prizeTenEditText.getText().toString().trim();
                String prizeEleven = prizeElevenEditText.getText().toString().trim();
                String prizeTwelve = prizeTwelveEditText.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(totalTeam) || TextUtils.isEmpty(prize) || TextUtils.isEmpty(entryFee)) {
                    if (TextUtils.isEmpty(title)) {
                        Toasty.error(getApplicationContext(), "title empty", Toasty.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(totalTeam)) {
                        Toasty.error(getApplicationContext(), "team empty", Toasty.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(prize)) {
                        Toasty.error(getApplicationContext(), "prize empty", Toasty.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(entryFee)) {
                        Toasty.error(getApplicationContext(), "entry fee empty", Toasty.LENGTH_SHORT).show();
                    }
                } else {
                    if (TextUtils.isEmpty(prizeOne)) {
                        hasPrizeOne = false;
                    } else {
                        hasPrizeOne = true;
                    }

                    if (TextUtils.isEmpty(prizeTwo)) {
                        hasPrizeTwo = false;
                    } else {
                        hasPrizeTwo = true;
                    }

                    if (TextUtils.isEmpty(prizeThree)) {
                        hasPrizeThree = false;
                    } else {
                        hasPrizeThree = true;
                    }

                    if (TextUtils.isEmpty(prizeFour)) {
                        hasPrizeFour = false;
                    } else {
                        hasPrizeFour = true;
                    }

                    if (TextUtils.isEmpty(prizeFive)) {
                        hasPrizeFive = false;
                    } else {
                        hasPrizeFive = true;
                    }

                    if (TextUtils.isEmpty(prizeSix)) {
                        hasPrizeSix = false;
                    } else {
                        hasPrizeSix = true;
                    }

                    if (TextUtils.isEmpty(prizeSeven)) {
                        hasPrizeSeven = false;
                    } else {
                        hasPrizeSeven = true;
                    }

                    if (TextUtils.isEmpty(prizeEight)) {
                        hasPrizeEight = false;
                    } else {
                        hasPrizeEight = true;
                    }

                    if (TextUtils.isEmpty(prizeNine)) {
                        hasPrizeNine = false;
                    } else {
                        hasPrizeNine = true;
                    }

                    if (TextUtils.isEmpty(prizeTen)) {
                        hasPrizeTen = false;
                    } else {
                        hasPrizeTen = true;
                    }

                    if (TextUtils.isEmpty(prizeEleven)) {
                        hasPrizeEleven = false;
                    } else {
                        hasPrizeEleven = true;
                    }

                    if (TextUtils.isEmpty(prizeTwelve)) {
                        hasPrizeTwelve = false;
                    } else {
                        hasPrizeTwelve = true;
                    }

                    //Log.d("infoxxx", title + " " + totalTeam + " " + prize + " " + entryFee + "" + prizeOne + " " + prizeTwo + " " + prizeThree + " " + prizeFour + " " + prizeFive + " " + prizeSix + " " + prizeSeven + " " + prizeEight + " " + prizeNine + " " + prizeTen + " " + prizeEleven + " " + prizeTwelve + " " + hasPrizeOne.toString() + " " + hasPrizeTwo.toString() + " " + hasPrizeThree.toString() + " " + hasPrizeFour.toString() + " " + hasPrizeFive.toString() + " " + hasPrizeSix.toString() + " " + hasPrizeSeven.toString() + " " + hasPrizeEight.toString() + " " + hasPrizeNine.toString() + " " + hasPrizeTen.toString() + " " + hasPrizeEleven.toString() + " " + hasPrizeTwelve.toString());

                    loader.show();
                    apiService.add_tournament_match(secret_id, api_token, title, prize, startDate, endDate, entryFee, totalTeam,
                            hasPrizeOne.toString(), prizeOne, hasPrizeTwo.toString(), prizeTwo, hasPrizeThree.toString(), prizeThree,
                            hasPrizeFour.toString(), prizeFour, hasPrizeFive.toString(), prizeFive, hasPrizeSix.toString(), prizeSix,
                            hasPrizeSeven.toString(), prizeSeven, hasPrizeEight.toString(), prizeEight, hasPrizeNine.toString(),
                            prizeNine, hasPrizeTen.toString(), prizeTen, hasPrizeEleven.toString(), prizeEleven,
                            hasPrizeTwelve.toString(), prizeTwelve).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                            //Log.d("responsexx", String.valueOf(response.body().getE()));
                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();

                            } else {
                                Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void pick_start_date() {

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(dateFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());

                startDateText.setText(dateCurrent);
            }

        };

        new DatePickerDialog(Tournament_acitivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private void pick_end_date() {

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(dateFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());

                endDateText.setText(dateCurrent);
            }

        };

        new DatePickerDialog(Tournament_acitivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private String current_date() {

        date = new SimpleDateFormat(dateFormat, Locale.getDefault()).format(new Date());
        return date;
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("dataxxx", api_token);
        backButton = findViewById(R.id.backButton);
        addTournamentButton = findViewById(R.id.addTournamentButtonID);

        tournamentView = findViewById(R.id.tournamentViewID);
        tournamentView.setHasFixedSize(true);
        tournamentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loader = new Dialog(Tournament_acitivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}