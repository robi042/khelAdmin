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
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_user_statistics_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_user_statistics_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ludo_user_statistics_activity extends AppCompatActivity {

    ImageView backButton;
    Dialog loader;
    APIService apiService;
    String secret_id, api_token;
    AppCompatButton searchButton;
    EditText userNameEditText, dateEditText;
    String date, dateCurrent;
    Calendar myCalendar;
    SimpleDateFormat sdf;
    String myFormat = "dd/MM/yyyy";
    RecyclerView statisticsView;

    private List<Ludo_user_statistics_response.M> statisticsList;
    private Ludo_user_statistics_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_user_statistics);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        date = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());

        set_date_text(date);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_picker();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    Toasty.error(getApplicationContext(), "Username empty", Toasty.LENGTH_SHORT).show();
                } else {

                    get_data(userName, date);
                }
            }
        });
    }

    private void get_data(String userName, String date) {
        loader.show();
        apiService.get_user_ludo_statistics(secret_id, api_token, userName, date).enqueue(new Callback<Ludo_user_statistics_response>() {
            @Override
            public void onResponse(Call<Ludo_user_statistics_response> call, Response<Ludo_user_statistics_response> response) {
                loader.dismiss();
                if (response.body().e == 0) {
                    statisticsList = new ArrayList<>();
                    statisticsList = response.body().m;
                    adapter = new Ludo_user_statistics_adapter(statisticsList);
                    statisticsView.setAdapter(adapter);
                    //Toast.makeText(getApplicationContext(), String.valueOf(statisticsList.size()), Toast.LENGTH_SHORT).show();
                } else {
                    Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ludo_user_statistics_response> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    private void date_picker() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateCurrent = sdf.format(myCalendar.getTime());


                //In which you need put here
                Date strDate = null;
                try {
                    strDate = sdf.parse(dateCurrent);
                    if (System.currentTimeMillis() > strDate.getTime()) {
                        set_date_text(dateCurrent);
                    } else {
                        Toasty.error(getApplicationContext(), "ERROR: Cross current date", Toasty.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }


            }

        };

        new DatePickerDialog(Ludo_user_statistics_activity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void set_date_text(String dateCurrent) {
        dateEditText.setText(dateCurrent);
    }

    private void init_view() {
        backButton = findViewById(R.id.backButton);
        searchButton = findViewById(R.id.searchButtonID);
        dateEditText = findViewById(R.id.dateEditTextID);
        userNameEditText = findViewById(R.id.userNameEditTextID);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        loader = new Dialog(Ludo_user_statistics_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        statisticsView = findViewById(R.id.statisticsViewID);
        statisticsView.setHasFixedSize(true);
        statisticsView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}