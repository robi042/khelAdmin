package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import code.fortomorrow.kheloNowAdmin.R;

public class Before_all_created_matches_activity extends AppCompatActivity {

    LinearLayout ludoLayoutButton, freeFireCSButtonlayout, freeFireRegularButtonlayout;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_all_created_matches);

        ludoLayoutButton = findViewById(R.id.ludoLayoutButtonID);
        freeFireCSButtonlayout = findViewById(R.id.freeFireCSButtonlayoutID);
        freeFireRegularButtonlayout = findViewById(R.id.freeFireRegularButtonlayoutID);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ludoLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), AllCreatedMatches.class));
                Intent intent = new Intent(getApplicationContext(), AllCreatedMatches.class);
                intent.putExtra("play_type", "3");
                startActivity(intent);
            }
        });

        freeFireCSButtonlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllCreatedMatches.class);
                intent.putExtra("play_type", "2");
                startActivity(intent);
            }
        });

        freeFireRegularButtonlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllCreatedMatches.class);
                intent.putExtra("play_type", "1");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}