package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.SubAdminWithdrawHistory.Withdraw_done_list_history_adapter;
import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sub_admin_details_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView backButton;
    Dialog loader;
    APIService apiService;
    String api_token, secret_id, adminID, type;
    RecyclerView informationView;
    TextView amountText;

    private List<Sub_admin_withdraw_history_response.WithdrawDone> withdrawDoneList;
    private Withdraw_done_list_history_adapter adapter;

    EditText searchEditText;
    LinearLayout searchLayoutButton, calenderButton;

    String date, dateCurrent;
    Calendar myCalendar;
    SimpleDateFormat sdf;
    String myFormat = "yyyy-MM-dd";

    String method;
    String[] methods = {"Bkash", "Nagad"};
    Spinner methodSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_details);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        date = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //Toast.makeText(getApplicationContext(), adminID, Toast.LENGTH_SHORT).show();

        set_search_text(date);

        //get_data(date, method);

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDate = searchEditText.getText().toString().trim();

                if (TextUtils.isEmpty(currentDate)) {
                    Toasty.error(getApplicationContext(), "no date found", Toasty.LENGTH_SHORT).show();
                } else {
                    get_data(currentDate, method);
                }
            }
        });

        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_picker();
            }
        });


        methodSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, methods);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        methodSpinner.setAdapter(aa);
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
                        get_data(dateCurrent, method);
                    } else {
                        Toasty.error(getApplicationContext(), "ERROR: Cross current date", Toasty.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }


            }

        };

        new DatePickerDialog(Sub_admin_details_activity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void set_search_text(String date) {
        searchEditText.setText(date);
    }

    private void get_data(String date, String method) {

        //Toast.makeText(getApplicationContext(), adminID + " " + type, Toast.LENGTH_SHORT).show();
        if (this.type.equals("withdraw")) {
            loader.show();
            apiService.get_sub_admin_withdraw_daily_history(secret_id, api_token, adminID, date, method).enqueue(new Callback<Sub_admin_withdraw_history_response>() {
                @Override
                public void onResponse(Call<Sub_admin_withdraw_history_response> call, Response<Sub_admin_withdraw_history_response> response) {
                    loader.dismiss();
                    if (response.body().e == 0) {
                        amountText.setText("৳" + String.valueOf(response.body().m));
                        withdrawDoneList = new ArrayList<>();
                        withdrawDoneList = response.body().withdrawDoneList;
                        adapter = new Withdraw_done_list_history_adapter(withdrawDoneList);
                        informationView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<Sub_admin_withdraw_history_response> call, Throwable t) {
                    loader.dismiss();
                }
            });
        } else if (this.type.equals("add money")) {

            if(method.equals("Bkash")){
                method = "Bkash ";
            }
            loader.show();
            apiService.get_add_amount_history(secret_id, api_token, adminID, date, method).enqueue(new Callback<Sub_admin_withdraw_history_response>() {
                @Override
                public void onResponse(Call<Sub_admin_withdraw_history_response> call, Response<Sub_admin_withdraw_history_response> response) {
                    loader.dismiss();
                    if (response.body().e == 0) {
                        amountText.setText("৳ " + String.valueOf(response.body().m));
                        withdrawDoneList = new ArrayList<>();
                        withdrawDoneList = response.body().withdrawDoneList;
                        adapter = new Withdraw_done_list_history_adapter(withdrawDoneList);
                        informationView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<Sub_admin_withdraw_history_response> call, Throwable t) {
                    loader.dismiss();
                }
            });
        }
    }

    private void init_view() {
        adminID = getIntent().getStringExtra("admin_id");
        type = getIntent().getStringExtra("type");

        loader = new Dialog(Sub_admin_details_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        Log.d("tokenxx", secret_id + " " + api_token);

        backButton = findViewById(R.id.backButton);
        informationView = findViewById(R.id.informationViewID);
        informationView.setHasFixedSize(true);
        informationView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        amountText = findViewById(R.id.amountTextID);

        searchEditText = findViewById(R.id.searchEditTextId);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        calenderButton = findViewById(R.id.calenderButtonID);

        methodSpinner = findViewById(R.id.methodSpinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        method = adapterView.getItemAtPosition(i).toString();
        get_data(searchEditText.getText().toString().trim(), method);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}