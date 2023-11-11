package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor.Arena_valor_participant_adapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_result_match_info_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArenaValor_participant_activity extends AppCompatActivity implements Arena_valor_participant_adapter.OnItemClickListener, Arena_valor_participant_adapter.OnUserNameCopyClickListener {

    APIService apiService;
    String secret_id, api_token;
    ImageView backButton, reloadButton;
    RecyclerView participantListView;
    String matchID;

    private List<Arena_valor_result_match_info_response.M> participantList;
    private Arena_valor_participant_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena_valor_participant);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_participants();
            }
        });

        get_participants();
    }

    private void get_participants() {
        apiService.arenaValor_get_result_match_info(secret_id, api_token, matchID).enqueue(new Callback<Arena_valor_result_match_info_response>() {
            @Override
            public void onResponse(Call<Arena_valor_result_match_info_response> call, Response<Arena_valor_result_match_info_response> response) {
                participantList = new ArrayList<>();
                participantList = response.body().m;
                adapter = new Arena_valor_participant_adapter(participantList);
                adapter.setOnClickListener(ArenaValor_participant_activity.this::OnUserNameCopyClick, ArenaValor_participant_activity.this::OnItemClick);
                participantListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Arena_valor_result_match_info_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        matchID = getIntent().getStringExtra("match_id");

        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        apiService = AppConfig.getRetrofit().create(APIService.class);

        reloadButton = findViewById(R.id.reloadButtonID);
        backButton = findViewById(R.id.backButton);
        participantListView = findViewById(R.id.participantListViewID);
        participantListView.setHasFixedSize(true);
        participantListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        participantListView.setItemViewCacheSize(150);
    }

    @Override
    public void OnItemClick(int position) {
        Arena_valor_result_match_info_response.M response = participantList.get(position);

        String userID = String.valueOf(response.userId);

        Dialog resultDialog = new Dialog(ArenaValor_participant_activity.this);
        resultDialog.setContentView(R.layout.arena_of_valor_update_result_data);
        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

        Window window = resultDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView mUserName = resultDialog.findViewById(R.id.mUserNamee);
        EditText mRank = resultDialog.findViewById(R.id.mRank);
        EditText mWinning = resultDialog.findViewById(R.id.mWinning);
        EditText mRefund = resultDialog.findViewById(R.id.mRefund);

        TextView player1 = resultDialog.findViewById(R.id.player1);
        TextView player2 = resultDialog.findViewById(R.id.player2);
        TextView player3 = resultDialog.findViewById(R.id.player3);
        TextView player4 = resultDialog.findViewById(R.id.player4);
        TextView player5 = resultDialog.findViewById(R.id.player5);
        TextView exPlayer1 = resultDialog.findViewById(R.id.exPlayer1TextID);
        TextView exPlayer2 = resultDialog.findViewById(R.id.exPlayer2TextID);

        Button updateButton = resultDialog.findViewById(R.id.mBtn);
        Button refundButton = resultDialog.findViewById(R.id.RefundBTN);

        mUserName.setText(response.userName);

        if (!TextUtils.isEmpty(response.firstPlayer)) {
            player1.setVisibility(View.VISIBLE);
            player1.setText("1st Player: " + response.firstPlayer);
        }

        if (!TextUtils.isEmpty(response.secondPlayer)) {
            player2.setVisibility(View.VISIBLE);
            player2.setText("2nd Player: " + response.secondPlayer);
        }

        if (!TextUtils.isEmpty(response.thirdPlayer)) {
            player3.setVisibility(View.VISIBLE);
            player3.setText("3rd Player: " + response.thirdPlayer);
        }

        if (!TextUtils.isEmpty(response.forthPlayer)) {
            player4.setVisibility(View.VISIBLE);
            player4.setText("4th Player: " + response.forthPlayer);
        }

        if (!TextUtils.isEmpty(response.fifthPlayer)) {
            player5.setVisibility(View.VISIBLE);
            player5.setText("5th Player: " + response.fifthPlayer);
        }

        if (!TextUtils.isEmpty(response.extraOne)) {
            exPlayer1.setVisibility(View.VISIBLE);
            exPlayer1.setText("Ex. Player 1: " + response.extraOne);
        }

        if (!TextUtils.isEmpty(response.extraTwo)) {
            exPlayer2.setVisibility(View.VISIBLE);
            exPlayer2.setText("Ex. Player 2: " + response.extraTwo);
        }

        mRank.setText(response.rank.toString());
        mWinning.setText(response.winningMoney.toString());
        mRefund.setText(response.refundAmount.toString());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getRank = mRank.getText().toString();
                String getWinning = mWinning.getText().toString();
                if (getRank.isEmpty() || getWinning.isEmpty()) {
                    Toasty.error(getApplicationContext(), "আপডেটের সকল ফিল্ড দেওয়া হয়নি! ", Toasty.LENGTH_SHORT).show();
                } else {
                    ProgressDialog pd = new ProgressDialog(ArenaValor_participant_activity.this);
                    pd.setMessage("Updating..");
                    pd.setCancelable(false);
                    pd.show();

                    apiService.arenaValor_giving_result(secret_id, api_token, userID, matchID, getWinning, getRank).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            if (response.body().getE() == 0) {
                                resultDialog.dismiss();
                                pd.dismiss();
                                participantList.remove(position);
                                adapter.notifyDataSetChanged();
                                Toasty.success(getApplicationContext(), "Update done", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), "Error: ", Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            pd.dismiss();
                            Toasty.error(getApplicationContext(), "Error: ", Toasty.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        refundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refundAmount = mRefund.getText().toString().trim();
                if (refundAmount.isEmpty()) {
                    Toasty.error(getApplicationContext(), "Refund Amount is empty", Toasty.LENGTH_SHORT).show();
                } else {
                    refundButton.setEnabled(false);
                    updateButton.setEnabled(false);
                    ProgressDialog pd = new ProgressDialog(ArenaValor_participant_activity.this);
                    pd.setMessage("Updating..");
                    pd.setCancelable(false);
                    pd.show();
                    apiService.arenaValor_giving_refund(secret_id, api_token, userID, matchID, refundAmount).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            pd.dismiss();
                            if (response.body().getE() == 0) {

                                resultDialog.dismiss();
                                participantList.remove(position);
                                adapter.notifyDataSetChanged();
                                Toasty.success(getApplicationContext(), "Refund done", Toasty.LENGTH_SHORT).show();
                            } else {

                                Toasty.error(getApplicationContext(), "Error: ", Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            Log.d("errorxx", t.getMessage());
                            pd.dismiss();
                            Toasty.error(getApplicationContext(), "Error: ", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void OnUserNameCopyClick(int position) {
        Arena_valor_result_match_info_response.M response = participantList.get(position);
        String userName = response.userName;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("user_name", userName);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), " Username copied", Toast.LENGTH_SHORT).show();
    }
}