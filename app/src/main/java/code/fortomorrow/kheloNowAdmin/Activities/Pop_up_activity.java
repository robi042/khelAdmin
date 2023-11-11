package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;

public class Pop_up_activity extends AppCompatActivity {

    String secret_id, api_token;
    APIService apiService;
    ImageView backButton;
    FloatingActionButton addPopUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("apitokenxx", api_token);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addPopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Pop_up_add_activity.class));
            }
        });
    }

    private void init_view() {

        backButton = findViewById(R.id.backButton);
        addPopUpButton = findViewById(R.id.addPopUpButtonID);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}