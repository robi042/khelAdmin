package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NumberAddActivity extends AppCompatActivity {
    private EditText num1, num2;
    private Button add;
    private APIService apiService;
    private String api_token, secret_id;
    private String payment_method = "Bkash";
    ImageView backButton;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_add);

        initAll();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(num1.getText().toString()) && TextUtils.isEmpty(num2.getText().toString())) {
                    Toasty.error(getApplicationContext(), "Please Enter a Number", Toasty.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    apiService.updatePaymentNumber(secret_id, api_token, payment_method, num1.getText().toString(), num2.getText().toString()).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_LONG).show();
                            } else {
                                Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_LONG).show();

                        }
                    });
                }

            }
        });
    }

    private void initAll() {
        loader = new Dialog(NumberAddActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        add = findViewById(R.id.add);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);

        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.bkash:
                if (checked)
                    payment_method = "Bkash";
                break;
            case R.id.nagad:
                if (checked)
                    payment_method = "Nagad";
                break;
            case R.id.rocket:
                if (checked)
                    payment_method = "Rocket";
                break;
        }
    }
}