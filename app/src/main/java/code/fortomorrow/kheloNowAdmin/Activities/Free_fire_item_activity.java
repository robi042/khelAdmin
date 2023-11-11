package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_item_activity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout freeFireRegularButton, freeFireCSButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_fire_item);

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
                startActivity(new Intent(getApplicationContext(), Free_fire_full_map_item_activity.class));
            }
        });

        freeFireCSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Free_fire_CS_activity.class));
            }
        });


    }

    private void item_view() {
        backButton = findViewById(R.id.backButton);
        freeFireRegularButton = findViewById(R.id.freeFireRegularButtonID);
        freeFireCSButton = findViewById(R.id.freeFireCSButtonID);
    }
}