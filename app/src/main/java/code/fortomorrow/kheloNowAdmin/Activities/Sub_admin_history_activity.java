package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin.Sub_admin_host_history_adapter;
import code.fortomorrow.kheloNowAdmin.Model.HostHistory.Sub_admin_host_history_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sub_admin_history_activity extends AppCompatActivity {

    Dialog loader;
    ImageView backButton;
    APIService apiService;
    String api_token, secret_id;
    RecyclerView itemView;

    EditText searchEditText;
    LinearLayout searchLayoutButton, calenderButton;

    Calendar myCalendar;
    SimpleDateFormat sdf;

    String myFormat = "MM-dd-yyyy";

    String dateCurrent, date;

    List<Sub_admin_host_history_response.M> itemList;
    Sub_admin_host_history_adapter history_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin_history);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void get_data(String dateCurrent) {
        loader.show();

        apiService.get_sub_admin_host_history(secret_id, api_token, dateCurrent).enqueue(new Callback<Sub_admin_host_history_response>() {
            @Override
            public void onResponse(Call<Sub_admin_host_history_response> call, Response<Sub_admin_host_history_response> response) {

                loader.dismiss();
                if (response.body().e == 0) {
                    itemList = new ArrayList<>();
                    itemList = response.body().m;
                    history_adapter = new Sub_admin_host_history_adapter(itemList);
                    itemView.setAdapter(history_adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Sub_admin_host_history_response> call, Throwable t) {
                loader.dismiss();
            }
        });
    }

    private void init_view() {

        loader = new Dialog(this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        searchEditText = findViewById(R.id.searchEditTextId);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        calenderButton = findViewById(R.id.calenderButtonID);

        backButton = findViewById(R.id.backButton);
        itemView = findViewById(R.id.itemViewID);
        itemView.setHasFixedSize(true);
        itemView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}