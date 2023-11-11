package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.Profit.Profit_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Ludo extends Fragment implements AdapterView.OnItemSelectedListener{

    private Boolean isStarted = false;
    private Boolean isVisible = false;

    EditText searchEditText;
    LinearLayout searchLayoutButton, calenderButton;

    Dialog loader;

    String date, dateCurrent;
    Calendar myCalendar;
    SimpleDateFormat sdf;
    String myFormat = "yyyy-MM-dd";

    APIService apiService;
    String api_token, secret_id;
    Spinner itemSpinner;

    String[] itemList = {"Ludo (Regular)", "Ludo (Grand)", "Ludo (Quick)", "Ludo (4 Player)"};

    int gameID;

    TextView totalEarnText, totalWinningText, totalRefundText, netIncomeText;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            viewDidAppear();
        }
    }

    private void viewDidAppear() {
        //isVisible = true;
        //get_matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__ludo, container, false);


        init_view(view);

        isStarted = true;
        if (isVisible && isStarted) {
            viewDidAppear();
        }

        date = new SimpleDateFormat(myFormat, Locale.getDefault()).format(new Date());
        //Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();

        set_search_text(date);

        //get_data(date);

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDate = searchEditText.getText().toString().trim();

                if (TextUtils.isEmpty(currentDate)) {
                    Toasty.error(getActivity(), "no date found", Toasty.LENGTH_SHORT).show();
                } else {
                    get_data();
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
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, itemList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        itemSpinner.setAdapter(aa);

        itemSpinner.setOnItemSelectedListener(this);

        return view;
    }

    private void get_data() {
        //Toast.makeText(getActivity(), "Free fire " + String.valueOf(gameID) + " " + searchEditText.getText().toString().trim(), Toast.LENGTH_SHORT).show();

        loader.show();
        apiService.get_daily_profit_ludo(secret_id, api_token, searchEditText.getText().toString().trim(), String.valueOf(gameID)).enqueue(new Callback<Profit_response>() {
            @Override
            public void onResponse(Call<Profit_response> call, Response<Profit_response> response) {

                loader.dismiss();
                if (response.body().e == 0) {
                    totalEarnText.setText(String.valueOf(response.body().m));
                    totalWinningText.setText(String.valueOf(response.body().totalWinningAmount));
                    totalRefundText.setText(String.valueOf(response.body().totalRefundAmount));
                    netIncomeText.setText(String.valueOf(response.body().netIncome));
                } else {
                    totalEarnText.setText("0");
                    totalWinningText.setText("0");
                    totalRefundText.setText("0");
                    netIncomeText.setText("0");
                }

            }

            @Override
            public void onFailure(Call<Profit_response> call, Throwable t) {
                loader.dismiss();
                totalEarnText.setText("0");
                totalWinningText.setText("0");
                totalRefundText.setText("0");
                netIncomeText.setText("0");
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

                        get_data();
                    } else {
                        Toasty.error(getActivity(), "ERROR: Cross current date", Toasty.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }


            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void init_view(View view) {

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getActivity());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        searchEditText = view.findViewById(R.id.searchEditTextId);
        searchLayoutButton = view.findViewById(R.id.searchLayoutButtonID);
        calenderButton = view.findViewById(R.id.calenderButtonID);

        itemSpinner = view.findViewById(R.id.itemSpinnerID);

        totalEarnText = view.findViewById(R.id.totalEarnTextID);
        totalWinningText = view.findViewById(R.id.totalWinningTextID);
        totalRefundText = view.findViewById(R.id.totalRefundTextID);
        netIncomeText = view.findViewById(R.id.netIncomeTextID);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        if (item.equals("Ludo (Regular)")) {
            gameID = 3;
        }else if (item.equals("Ludo (Grand)")) {
            gameID = 4;
        } else if (item.equals("Ludo (Quick)")) {
            gameID = 7;
        } else if (item.equals("Ludo (4 Player)")) {
            gameID = 8;
        }

        get_data();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}