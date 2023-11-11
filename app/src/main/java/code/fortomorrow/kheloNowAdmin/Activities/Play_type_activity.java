package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import code.fortomorrow.kheloNowAdmin.R;

public class Play_type_activity extends AppCompatActivity {
    TextView titleText;
    String gameID;
    LinearLayout soloButton, duoButton, squadButton, type_6_vs_6_Button, type_1_vs_1_Button;
    LinearLayout solo_duo_Button, solo_squad_Button, duo_squad__Button, solo_duo_squad_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playtype);

        init_view();

        set_title();

        soloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("1", gameID);
            }
        });

        duoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("2", gameID);
            }
        });

        squadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("3", gameID);
            }
        });

        solo_duo_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("4", gameID);
            }
        });

        solo_squad_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("5", gameID);
            }
        });

        duo_squad__Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("6", gameID);
            }
        });

        solo_duo_squad_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("7", gameID);
            }
        });

        type_6_vs_6_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("9", gameID);
            }
        });

        type_1_vs_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_activity("8", gameID);
            }
        });
    }

    private void go_to_activity(String playTypeID, String gameID) {
        Intent intent = new Intent(getApplicationContext(), Free_fire_cs_main_activity.class);
        intent.putExtra("play_type", playTypeID);
        intent.putExtra("game_type", gameID);
        startActivity(intent);
    }

    private void set_title() {
        if (gameID.equals("1")) {
            titleText.setText("Free Fire (Regular)");
        } else if (gameID.equals("2")) {
            titleText.setText("Free Fire (CS Regular)");
        } else if (gameID.equals("3")) {
            titleText.setText("Ludo (Regular)");
        } else if (gameID.equals("4")) {
            titleText.setText("Ludo (Grand)");
        } else if (gameID.equals("5")) {
            titleText.setText("Free Fire (CS Grand)");
        } else if (gameID.equals("6")) {
            titleText.setText("Daily Scrims");
        } else if (gameID.equals("7")) {
            titleText.setText("Ludo (Quick)");
        } else if (gameID.equals("8")) {
            titleText.setText("Ludo (4 Player)");
        } else if (gameID.equals("9")) {
            titleText.setText("Free Fire(Premium)");
        } else if (gameID.equals("10")) {
            titleText.setText("Free Fire(Grand)");
        }
    }

    private void init_view() {
        gameID = getIntent().getStringExtra("game_type");
        //Toast.makeText(this, gameID, Toast.LENGTH_SHORT).show();

        titleText = findViewById(R.id.titleTextID);

        soloButton = findViewById(R.id.soloButtonID);
        duoButton = findViewById(R.id.duoButtonID);
        squadButton = findViewById(R.id.squadButtonID);
        type_6_vs_6_Button = findViewById(R.id.type_6_vs_6_ButtonID);
        type_1_vs_1_Button = findViewById(R.id.type_1_vs_1_ButtonID);
        solo_duo_Button = findViewById(R.id.solo_duo_ButtonID);
        solo_squad_Button = findViewById(R.id.solo_squad_ButtonID);
        duo_squad__Button = findViewById(R.id.duo_squad__ButtonID);
        solo_duo_squad_Button = findViewById(R.id.solo_duo_squad_ButtonID);
    }
}