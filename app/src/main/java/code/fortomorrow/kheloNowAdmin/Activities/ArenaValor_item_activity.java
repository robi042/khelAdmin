package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import code.fortomorrow.kheloNowAdmin.R;

public class ArenaValor_item_activity extends AppCompatActivity {

    LinearLayout grandButton, regularButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_valor_item);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        regularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_next_activity("11");
            }
        });

        grandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_next_activity("12");
            }
        });
    }

    private void go_to_next_activity(String s) {
        Intent intent = new Intent(getApplicationContext(), ArenaValor_activity.class);
        intent.putExtra("game_id", s);
        startActivity(intent);
    }

    private void init_view() {
        grandButton = findViewById(R.id.grandButtonID);
        regularButton = findViewById(R.id.regularButtonID);
        backButton = findViewById(R.id.backButton);
    }
}