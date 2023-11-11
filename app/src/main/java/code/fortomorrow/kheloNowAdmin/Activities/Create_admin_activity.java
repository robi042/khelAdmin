package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_admin_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView backButton;
    Spinner itemSpinner;
    APIService apiService;
    String item, secret_id, api_token;
    AppCompatButton addButton;
    String[] items = {"Admin", "Sub Admin"};
    TextInputEditText userNameEditText, passwordEditText;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        itemSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        itemSpinner.setAdapter(aa);

        loader = new Dialog(Create_admin_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();

                String userName = userNameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {

                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();

                } else {
                    loader.show();

                    apiService.addNewAdmin(secret_id, api_token, item, userName, password).enqueue(new Callback<SorkariResponse>() {
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
    }

    private void init_view() {
        backButton = findViewById(R.id.backButton);
        itemSpinner = findViewById(R.id.itemSpinnerID);
        addButton = findViewById(R.id.addButtonID);
        userNameEditText = findViewById(R.id.userNameEditTextID);
        passwordEditText = findViewById(R.id.passwordEditTextID);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(getApplicationContext(), String.valueOf(parent.getSelectedItemId()), Toast.LENGTH_SHORT).show();
        if (parent.getSelectedItemId() == 0) {
            item = "admin";
        } else if (parent.getSelectedItemId() == 1) {
            item = "sub_admin";
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}