package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import code.fortomorrow.kheloNowAdmin.R;

public class Admin_option_activity extends AppCompatActivity {

    ImageView backButton;
    String adminID;
    CardView withdrawButton, addMoneyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_option);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Toast.makeText(this, adminID, Toast.LENGTH_SHORT).show();
        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_details_activity("withdraw");
            }
        });

        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_details_activity("add money");
            }
        });
    }

    private void go_to_details_activity(String type) {
        Intent intent = new Intent(getApplicationContext(), Sub_admin_details_activity.class);
        intent.putExtra("admin_id", adminID);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    private void init_view() {
        backButton = findViewById(R.id.backButton);
        adminID = getIntent().getStringExtra("admin_id");
        withdrawButton = findViewById(R.id.withdrawButtonID);
        addMoneyButton = findViewById(R.id.addMoneyButtonID);
    }
}