package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Accept_money_history_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Accept_money_history_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Dialog loader;
    APIService apiService;
    String api_token, secret_id;
    ImageView backButton;

    EditText searchEditText;
    LinearLayout searchLayoutButton, calenderButton;
    String date, dateCurrent;
    Calendar myCalendar;
    SimpleDateFormat sdf;
    String myFormat = "MM-dd-yyyy";

    Spinner itemSpinner;

    String[] itemList = {"Bkash", "Nagad", "Rocket"};

    TextView totalText, bKashText, nagadText, rocketText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_money_history);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        date = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();

        set_search_text(date);

        get_data(date);

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDate = searchEditText.getText().toString().trim();

                if (TextUtils.isEmpty(currentDate)) {
                    Toasty.error(getApplicationContext(), "no date found", Toasty.LENGTH_SHORT).show();
                } else {
                    get_data(date);
                }
            }
        });

        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_picker();
            }
        });


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        itemSpinner.setAdapter(aa);

        itemSpinner.setOnItemSelectedListener(this);
    }

    private void set_search_text(String date) {
        searchEditText.setText(date);
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
                        set_search_text(dateCurrent);

                        get_data(dateCurrent);
                    } else {
                        Toasty.error(getApplicationContext(), "ERROR: Cross current date", Toasty.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }


            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void get_data(String date) {
        //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        apiService.get_add_money_daily_history(secret_id, api_token, date).enqueue(new Callback<Accept_money_history_response>() {
            @Override
            public void onResponse(Call<Accept_money_history_response> call, Response<Accept_money_history_response> response) {
                if (response.body().e == 0) {
                    totalText.setText(String.valueOf(response.body().m.total));
                    bKashText.setText(String.valueOf(response.body().m.bkashAddAmount));
                    nagadText.setText(String.valueOf(response.body().m.nagadAddAmount));
                    rocketText.setText(String.valueOf(response.body().m.rocketAddAmount));
                }
            }

            @Override
            public void onFailure(Call<Accept_money_history_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        loader = new Dialog(this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);

        searchEditText = findViewById(R.id.searchEditTextId);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        calenderButton = findViewById(R.id.calenderButtonID);

        itemSpinner = findViewById(R.id.itemSpinnerID);
        totalText = findViewById(R.id.totalText);
        bKashText = findViewById(R.id.bKashText);
        nagadText = findViewById(R.id.nagadText);
        rocketText = findViewById(R.id.rocketText);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}