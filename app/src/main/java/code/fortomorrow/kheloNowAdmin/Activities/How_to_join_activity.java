package code.fortomorrow.kheloNowAdmin.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class How_to_join_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner typeSpinner;
    APIService apiService;
    String api_token, secret_id;
    EditText linkText;
    AppCompatButton setButton;
    String[] types = {"Sign Up", "Log In", "Add Money", "Refer and earn", "Full Map Regular","Full Map Premium", "Full Map Grand", "CS Regular","CS Grand", "Daily Scrims", "Ludo (Regular)", "Ludo (Grand)", "Ludo (Quick)", "Ludo (4 Player)", "Arena Of Valor(Regular)", "Arena Of Valor(Grand)"};
    String type;
    Dialog loader;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_join);

        init_view();


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        typeSpinner.setAdapter(aa);

        typeSpinner.setOnItemSelectedListener(this);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = linkText.getText().toString().trim();

                if (TextUtils.isEmpty(link)) {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                    loader.show();
                    apiService.addHowToLink(secret_id, api_token, type, link).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init_view() {
        loader = new Dialog(How_to_join_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        typeSpinner = findViewById(R.id.typeSpinnerID);
        linkText = findViewById(R.id.linkTextID);
        setButton = findViewById(R.id.setButtonID);

        backButton = findViewById(R.id.backButton);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = String.valueOf(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}