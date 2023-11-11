package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_full_map_item_activity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout freeFireRegularButton, freeFirePremiumButton, freeFireGrandButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_full_map_item);
        item_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        freeFireRegularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Free_fire_regular_main_activity.class));

                go_to_activity("1");
            }
        });

        freeFirePremiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_activity("9");
            }
        });

        freeFireGrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_activity("10");
            }
        });

    }

    private void go_to_activity(String gameID) {
        Intent intent = new Intent(getApplicationContext(), Free_fire_full_map_main_activity.class);
        intent.putExtra("game_type", gameID);
        startActivity(intent);
    }

    private void item_view() {
        backButton = findViewById(R.id.backButton);
        freeFireRegularButton = findViewById(R.id.freeFireRegularButtonID);
        freeFirePremiumButton = findViewById(R.id.freeFirePremiumButtonID);
        freeFireGrandButton = findViewById(R.id.freeFireGrandButtonID);
    }
}