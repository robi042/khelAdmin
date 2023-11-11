package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.JoinedPlayer.Joined_player_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.JoinedPlayer.Ludo_joined_player_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Joined_player_list_activity extends AppCompatActivity implements Joined_player_list_adapter.OnRefundItemClickListener, Joined_player_list_adapter.OnDeleteItemClickListener, Ludo_joined_player_list_adapter.OnLudoRefundItemClickListener, Ludo_joined_player_list_adapter.OnLudoDeleteItemClickListener {

    ImageView backButton;
    RecyclerView playersListView;
    LinearLayout noMatchesLayout;
    APIService apiService;
    String api_token, secret_id, matchID;
    private List<Joined_player_list_response.M> joinedPlayerList;
    private List<Joined_player_list_response.M> joinedLudoPlayerList;
    private Joined_player_list_adapter adapter;
    private Ludo_joined_player_list_adapter ludoAdapter;

    Dialog loader;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_player_list);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (type.equals("ludo")) {
            ludo_joined_player();
        } else {
            join_player();
        }

        //Log.d("tokenxx", api_token);

    }

    private void ludo_joined_player() {
        apiService.getJoinedLudoPlayerList(secret_id, api_token, matchID).enqueue(new Callback<Joined_player_list_response>() {
            @Override
            public void onResponse(Call<Joined_player_list_response> call, Response<Joined_player_list_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    playersListView.setVisibility(View.VISIBLE);
                    noMatchesLayout.setVisibility(View.GONE);
                    joinedLudoPlayerList = new ArrayList<>();
                    joinedLudoPlayerList = response.body().getM();
                    ludoAdapter = new Ludo_joined_player_list_adapter(joinedLudoPlayerList);
                    ludoAdapter.setOnClickListener(Joined_player_list_activity.this::OnLudoRefundItemClick, Joined_player_list_activity.this::OnLudoDeleteItemClick);
                    playersListView.setAdapter(ludoAdapter);
                } else {
                    playersListView.setVisibility(View.GONE);
                    noMatchesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Joined_player_list_response> call, Throwable t) {
                playersListView.setVisibility(View.GONE);
                noMatchesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void join_player() {
        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();

        apiService.getJoinedPlayerList(secret_id, api_token, matchID).enqueue(new Callback<Joined_player_list_response>() {
            @Override
            public void onResponse(Call<Joined_player_list_response> call, Response<Joined_player_list_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    playersListView.setVisibility(View.VISIBLE);
                    noMatchesLayout.setVisibility(View.GONE);
                    joinedPlayerList = new ArrayList<>();
                    joinedPlayerList = response.body().getM();
                    adapter = new Joined_player_list_adapter(joinedPlayerList);
                    adapter.setOnClickListener(Joined_player_list_activity.this::OnRefundItemClick, Joined_player_list_activity.this::OnDeleteItemClick);
                    playersListView.setAdapter(adapter);
                } else {
                    playersListView.setVisibility(View.GONE);
                    noMatchesLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Joined_player_list_response> call, Throwable t) {
                playersListView.setVisibility(View.GONE);
                noMatchesLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init_view() {
        matchID = getIntent().getStringExtra("match_id");
        //Toast.makeText(getApplicationContext(), matchID, Toast.LENGTH_SHORT).show();

        loader = new Dialog(Joined_player_list_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        type = getIntent().getStringExtra("type");

        apiService = AppConfig.getRetrofit().create(APIService.class);
        EasySharedPref.init(getApplicationContext());
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        playersListView = findViewById(R.id.playersListViewID);
        playersListView.setHasFixedSize(true);
        playersListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        noMatchesLayout = findViewById(R.id.noMatchLayoutID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void OnRefundItemClick(int position) {
        Joined_player_list_response.M response = joinedPlayerList.get(position);
        //Toast.makeText(getApplicationContext(), "refund " + response.getUserId(), Toast.LENGTH_SHORT).show();
        String userID = String.valueOf(response.getUserId());

        Dialog dialog = new Dialog(Joined_player_list_activity.this);
        dialog.setContentView(R.layout.refund_amount_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        EditText refundText = dialog.findViewById(R.id.refundEditTextID);
        AppCompatButton refundButton = dialog.findViewById(R.id.refundButtonID);

        refundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refundAmount = refundText.getText().toString().trim();

                if (TextUtils.isEmpty(refundAmount)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    apiService.refundBeforeMatch(secret_id, api_token, matchID, userID, refundAmount).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                joinedPlayerList.remove(position);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.success(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void OnDeleteItemClick(int position) {
        Joined_player_list_response.M response = joinedPlayerList.get(position);
        //Toast.makeText(getApplicationContext(), "delete " + response.getUserId(), Toast.LENGTH_SHORT).show();
        String userID = String.valueOf(response.getUserId());

        Dialog deleteDialog = new Dialog(Joined_player_list_activity.this);
        deleteDialog.setContentView(R.layout.confirmation_alert);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.show();

        Window window = deleteDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = deleteDialog.findViewById(R.id.yesButtonID);
        TextView noButton = deleteDialog.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                apiService.removeUser(secret_id, api_token, matchID, userID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {

                        loader.dismiss();
                        if (response.body().getE() == 0) {
                            joinedPlayerList.remove(position);
                            adapter.notifyDataSetChanged();
                            deleteDialog.dismiss();
                            Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                        } else {
                            Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        loader.dismiss();
                        Toasty.success(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });


    }

    @Override
    public void OnLudoRefundItemClick(int position) {
        Joined_player_list_response.M response = joinedLudoPlayerList.get(position);

        String userID = String.valueOf(response.getUserId());
        Toast.makeText(getApplicationContext(), matchID + " " + response.getUserId(), Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(Joined_player_list_activity.this);
        dialog.setContentView(R.layout.refund_amount_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        EditText refundText = dialog.findViewById(R.id.refundEditTextID);
        AppCompatButton refundButton = dialog.findViewById(R.id.refundButtonID);

        refundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refundAmount = refundText.getText().toString().trim();

                if (TextUtils.isEmpty(refundAmount)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    loader.show();

                    apiService.refundBeforeLudoMatch(secret_id, api_token, matchID, userID, refundAmount).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();

                            if (response.body().getE() == 0) {
                                joinedLudoPlayerList.remove(position);
                                ludoAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            } else {
                                Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void OnLudoDeleteItemClick(int position) {
        Joined_player_list_response.M response = joinedLudoPlayerList.get(position);

        String userID = String.valueOf(response.getUserId());
        Toast.makeText(getApplicationContext(), "ID " + matchID, Toast.LENGTH_SHORT).show();

        Dialog deleteDialog = new Dialog(Joined_player_list_activity.this);
        deleteDialog.setContentView(R.layout.confirmation_alert);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.show();

        Window window = deleteDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = deleteDialog.findViewById(R.id.yesButtonID);
        TextView noButton = deleteDialog.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                apiService.removeLudoUser(secret_id, api_token, matchID, userID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0) {
                            joinedLudoPlayerList.remove(position);
                            ludoAdapter.notifyDataSetChanged();
                            deleteDialog.dismiss();
                            Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                        } else {
                            Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        loader.dismiss();
                        Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

    }
}