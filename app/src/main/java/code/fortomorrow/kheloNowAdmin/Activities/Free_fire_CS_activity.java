package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_CS_activity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout regularButton, grandButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_cs);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        regularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_activity("2");
            }
        });

        grandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_activity("5");
                //Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void go_to_activity(String gameID) {

        Intent intent = new Intent(getApplicationContext(), Free_fire_cs_main_activity.class);
        //Intent intent = new Intent(getApplicationContext(), Play_type_activity.class);
        intent.putExtra("game_type", gameID);
        startActivity(intent);
    }

    private void init_view() {
        backButton = findViewById(R.id.backButton);

        regularButton = findViewById(R.id.regularButtonID);
        grandButton = findViewById(R.id.grandButtonID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}