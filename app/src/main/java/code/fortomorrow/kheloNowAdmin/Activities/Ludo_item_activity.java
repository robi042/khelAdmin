package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_item_activity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout ludoButton, ludoGrandButton, ludoQuickButton, ludoFourPlayerButton, userStatisticsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_item_activity);

        item_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ludoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ludo_regular_activity.class));
            }
        });

        ludoGrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Available soon", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Ludo_grand_activity.class));
            }
        });

        ludoQuickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ludo_quick_activity.class));
            }
        });

        ludoFourPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Ludo_four_player_activity.class));
            }
        });

        userStatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Ludo_user_statistics_activity.class));
            }
        });

    }

    private void item_view() {

        ludoButton = findViewById(R.id.ludoButtonID);
        backButton = findViewById(R.id.backButton);
        ludoGrandButton = findViewById(R.id.ludoGrandButtonID);
        ludoQuickButton = findViewById(R.id.ludoQuickButtonID);
        ludoFourPlayerButton = findViewById(R.id.ludoFourPlayerButtonID);
        userStatisticsButton = findViewById(R.id.userStatisticsButtonID);
    }
}