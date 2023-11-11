package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.ShowParticipantAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ShowParticipant.M;
import code.fortomorrow.kheloNowAdmin.Model.ShowParticipant.ShowparticipantResponse;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowParticipantActivity extends AppCompatActivity implements ShowParticipantAdapter.OnUserNameCopyClickListener, ShowParticipantAdapter.OnItemClickListener {
    private RecyclerView participantListRV;
    private APIService apiService;
    private String api_token, secret_id;
    private String matchId;
    private List<M> showParticipant = new ArrayList<>();

    ShowParticipantAdapter showParticipantAdapter;
    ImageView backButton, reloadButton;
    AppCompatButton resultButton;
    Dialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_match_list);

        initall();

        getAllParticipants();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllParticipants();
            }
        });

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.show();
                apiService.update_done_result(secret_id, api_token, matchId).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();

                        if (response.body().getE() == 0) {
                            Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_LONG).show();

                        } else {
                            Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        loader.dismiss();
                        Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void initall() {

        loader = new Dialog(ShowParticipantActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        matchId = getIntent().getStringExtra("matchId");

        Log.d("tokenxx", api_token + " " + secret_id + " " + matchId);

        reloadButton = findViewById(R.id.reloadButtonID);
        backButton = findViewById(R.id.backButton);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        participantListRV = findViewById(R.id.participantList);
        participantListRV.setHasFixedSize(true);
        participantListRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        participantListRV.setItemViewCacheSize(150);

        resultButton = findViewById(R.id.resultButtonID);
    }

    private void getAllParticipants() {
        apiService.getAllParticipants(secret_id, api_token, matchId).enqueue(new Callback<ShowparticipantResponse>() {
            @Override
            public void onResponse(Call<ShowparticipantResponse> call, Response<ShowparticipantResponse> response) {
                //Log.d("showlist", new Gson().toJson(response.body().getM()));
                if (response.body().getE() == 0 && response.body().getM() != null) {
                    //showParticipant.addAll(response.body().getM());
                    showParticipant = response.body().getM();
                    showParticipantAdapter = new ShowParticipantAdapter(ShowParticipantActivity.this, showParticipant, getApplicationContext());
                    showParticipantAdapter.setOnClickListener(ShowParticipantActivity.this::OnUserNameCopyClick, ShowParticipantActivity.this::OnItemClick);
                    //showParticipantAdapter.notifyDataSetChanged();
                    participantListRV.setAdapter(showParticipantAdapter);

                }
            }

            @Override
            public void onFailure(Call<ShowparticipantResponse> call, Throwable t) {

            }
        });

    }

    public void updatePlayer(Integer userId, String firstPlayer, String secondPlayer, String thirdPlayer, String fourthPlayer, String fifthPlayer, String sixthPlayer, String name, int position) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShowParticipantActivity.this);
        View mView = ShowParticipantActivity.this.getLayoutInflater().inflate(R.layout.custom_result_list_item_layput, null);

        mBuilder.setView(mView);
        final AlertDialog dialog1 = mBuilder.create();
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();


        TextView mUserName = dialog1.findViewById(R.id.mUserNamee);
        EditText mKill = mView.findViewById(R.id.mKill);
        EditText mRank = mView.findViewById(R.id.mRank);
        EditText mWinning = mView.findViewById(R.id.mWinning);
        EditText mRefund = mView.findViewById(R.id.mRefund);

        TextView player1 = mView.findViewById(R.id.player1);
        TextView player2 = mView.findViewById(R.id.player2);
        TextView player3 = mView.findViewById(R.id.player3);
        TextView player4 = mView.findViewById(R.id.player4);
        TextView player5 = mView.findViewById(R.id.player5);
        TextView player6 = mView.findViewById(R.id.player6);

        Button mBtn = mView.findViewById(R.id.mBtn);
        Button RefundBTN = mView.findViewById(R.id.RefundBTN);


        mUserName.setText(name);

        if (!TextUtils.isEmpty(firstPlayer)) {
            player1.setVisibility(View.VISIBLE);
            player1.setText("1st Player: " + firstPlayer);
        }

        if (!TextUtils.isEmpty(secondPlayer)) {
            player2.setVisibility(View.VISIBLE);
            player2.setText("2nd Player: " + secondPlayer);
        }

        if (!TextUtils.isEmpty(thirdPlayer)) {
            player3.setVisibility(View.VISIBLE);
            player3.setText("3rd Player: " + thirdPlayer);
        }

        if (!TextUtils.isEmpty(fourthPlayer)) {
            player4.setVisibility(View.VISIBLE);
            player4.setText("4th Player: " + fourthPlayer);
        }

        if (!TextUtils.isEmpty(fifthPlayer)) {
            player5.setVisibility(View.VISIBLE);
            player5.setText("5th Player: " + fifthPlayer);
        }

        if (!TextUtils.isEmpty(sixthPlayer)) {
            player6.setVisibility(View.VISIBLE);
            player6.setText("6th Player: " + sixthPlayer);
        }


        RefundBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getKill = mKill.getText().toString();
                String getRank = mRank.getText().toString();
                String getWinning = mWinning.getText().toString();
                String getRefundAmount = mRefund.getText().toString();
                if (mRefund.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), "Refund Amount is empty", Toasty.LENGTH_SHORT).show();
                } else {
                    RefundBTN.setEnabled(false);
                    mBtn.setEnabled(false);
                    ProgressDialog pd = new ProgressDialog(ShowParticipantActivity.this);
                    pd.setMessage("Updating..");
                    pd.show();
                    apiService.updateRefund(secret_id, api_token, matchId, String.valueOf(userId), mRefund.getText().toString()).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            if (response.body().getE() == 0) {
                                pd.dismiss();
                                dialog1.dismiss();
                                //getAllparticipants();
                                showParticipant.remove(position);
                                showParticipantAdapter.notifyDataSetChanged();
                                Toasty.success(getApplicationContext(), "Refund done", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), "Error: ", Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RefundBTN.setEnabled(false);
                mBtn.setEnabled(false);
                ProgressDialog pd = new ProgressDialog(ShowParticipantActivity.this);
                pd.setMessage("Updating..");
                pd.show();


                String getKill = mKill.getText().toString();
                String getRank = mRank.getText().toString();
                String getWinning = mWinning.getText().toString();
                String getRefundAmount = mRefund.getText().toString();
                if (getKill.isEmpty() || getRank.isEmpty() || getWinning.isEmpty() || !getRefundAmount.isEmpty()) {
                    pd.dismiss();
                    Toasty.error(getApplicationContext(), "আপডেটের সকল ফিল্ড দেওয়া হয়নি! ", Toasty.LENGTH_SHORT).show();
                } else {
                    apiService.updateResultsParticipant(secret_id, api_token, matchId, String.valueOf(userId), getKill, getWinning, getRank).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            if (response.body().getE() == 0) {
                                dialog1.dismiss();
                                pd.dismiss();
                                showParticipant.remove(position);
                                showParticipantAdapter.notifyDataSetChanged();
                                Toasty.success(getApplicationContext(), "Update done", Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), "Error: ", Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {

                        }
                    });

                }


            }
        });


    }


    @Override
    public void OnUserNameCopyClick(int position) {
        M response = showParticipant.get(position);
        String userName = response.getUserName();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("room_code", userName);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), " Username copied", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void OnItemClick(int position) {
        M response = showParticipant.get(position);

        Integer userId = response.getUserId();
        String firstPlayer = response.getFirstPlayer();
        String secondPlayer = response.getSecondPlayer();
        String thirdPlayer = response.getThird_player();
        String fourthPlayer = response.getFourth_player();
        String fifthPlayer = response.getFifthPlayer();
        String sixthPlayer = response.getSixthPlayer();

        String name = response.getUserName();

        //showParticipant.remove(position);
        //showParticipantAdapter.notifyDataSetChanged();

        updatePlayer(userId, firstPlayer, secondPlayer, thirdPlayer, fourthPlayer, fifthPlayer, sixthPlayer, name, position);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}