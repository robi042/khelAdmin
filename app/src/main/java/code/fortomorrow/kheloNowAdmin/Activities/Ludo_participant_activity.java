package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_participant_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_uploaded_images_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_participant_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_uploaded_image_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Ludo_participant_activity extends AppCompatActivity implements Ludo_participant_list_adapter.OnParticipantItemClickListener, Ludo_participant_list_adapter.OnUserNameCopyClickListener, Ludo_uploaded_images_adapter.OnItemClickListener {

    ImageView backButton;
    RecyclerView ludoParticipantListView;
    String matchID, api_token, secret_id, title, dateTime, prize, roomCode, imageLink;
    APIService apiService;
    private List<Ludo_participant_response.M> participantList;
    private Ludo_participant_list_adapter adapter;
    Dialog loader;
    AppCompatButton resultButton;
    TextView titleText, dateTimeText, winningMoneyText, roomCodeText, imageUploaderUserNameText;
    RecyclerView imageRecyclerView;
    FloatingActionButton messageButton;
    LinearLayout noDataLayout;
    private List<Ludo_uploaded_image_response.M> dataList = new ArrayList<>();
    private Ludo_uploaded_images_adapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ludo_participant_activity);

        init_view();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleText.setText(title);
        dateTimeText.setText(dateTime);
        winningMoneyText.setText(prize);
        roomCodeText.setText(roomCode);


        load_participant();

        load_images();

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                apiService.updateLudoResultStatus(secret_id, api_token, matchID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0) {
                            Toasty.success(getApplicationContext(), "Result updated", Toasty.LENGTH_SHORT).show();
                        } else {
                            Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();

                    }
                });
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Ludo_participant_activity.this);
                dialog.setContentView(R.layout.update_ludo_message_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //dialog.setCancelable(false);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = dialog.findViewById(R.id.closeButtonID);
                EditText messageEditText = dialog.findViewById(R.id.messageEditTextID);
                AppCompatButton updateButton = dialog.findViewById(R.id.updateButtonID);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = messageEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(message)) {
                            Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                        } else {
                            update_message(message);
                        }
                    }
                });
            }
        });

    }

    private void load_images() {
        apiService.getUploadedLudoImages(secret_id, api_token, matchID).enqueue(new Callback<Ludo_uploaded_image_response>() {
            @Override
            public void onResponse(Call<Ludo_uploaded_image_response> call, Response<Ludo_uploaded_image_response> response) {

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    noDataLayout.setVisibility(View.GONE);
                    imageRecyclerView.setVisibility(View.VISIBLE);
                    dataList = response.body().getM();
                    imageAdapter = new Ludo_uploaded_images_adapter(dataList);
                    imageAdapter.setOnItemClickListener(Ludo_participant_activity.this::OnItemClick);
                    imageRecyclerView.setAdapter(imageAdapter);
                } else {
                    noDataLayout.setVisibility(View.VISIBLE);
                    imageRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Ludo_uploaded_image_response> call, Throwable t) {
                noDataLayout.setVisibility(View.VISIBLE);
                imageRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void update_message(String message) {
        loader.show();
        apiService.updateLudoMessage(secret_id, api_token, matchID, message).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), "Message Updated", Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.error(getApplicationContext(), response.body().getE(), Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SorkariResponse> call, Throwable t) {
                loader.dismiss();
                Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void init_view() {
        matchID = getIntent().getStringExtra("match_ID");
        title = getIntent().getStringExtra("title");
        dateTime = getIntent().getStringExtra("date_time");
        prize = getIntent().getStringExtra("prize");
        roomCode = getIntent().getStringExtra("room_code");
        imageLink = getIntent().getStringExtra("image_link");

        loader = new Dialog(Ludo_participant_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        titleText = findViewById(R.id.titleTextID);
        dateTimeText = findViewById(R.id.dateTimeTextID);
        winningMoneyText = findViewById(R.id.winningMoneyTextID);
        roomCodeText = findViewById(R.id.roomCodeTextID);

        imageRecyclerView = findViewById(R.id.imageRecyclerViewID);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        resultButton = findViewById(R.id.resultButtonID);
        ludoParticipantListView = findViewById(R.id.ludoParticipantListViewID);
        ludoParticipantListView.setHasFixedSize(true);
        ludoParticipantListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ludoParticipantListView.setItemViewCacheSize(150);

        messageButton = findViewById(R.id.messageButtonID);
        noDataLayout = findViewById(R.id.noDataLayoutID);

    }

    private void load_participant() {

        apiService.getLudoParticipantList(secret_id, api_token, matchID).enqueue(new Callback<Ludo_participant_response>() {
            @Override
            public void onResponse(Call<Ludo_participant_response> call, Response<Ludo_participant_response> response) {

                if (response.body().getE() == 0) {
                    participantList = new ArrayList<>();
                    participantList = response.body().getM();
                    adapter = new Ludo_participant_list_adapter(participantList);
                    adapter.setOnClickListener(Ludo_participant_activity.this::OnParticipantItemClick, Ludo_participant_activity.this::OnUserNameCopyClick);
                    ludoParticipantListView.setAdapter(adapter);

                    //Toast.makeText(getApplicationContext(), String.valueOf(participantList.size()), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Ludo_participant_response> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }



    @Override
    public void OnUserNameCopyClick(int position) {
        Ludo_participant_response.M response = participantList.get(position);

        String userName = response.getUserName();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("room_code", userName);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Username copied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnParticipantItemClick(int position) {
        Ludo_participant_response.M response = participantList.get(position);
        String userID = String.valueOf(response.getUserId());

        Dialog dialog = new Dialog(Ludo_participant_activity.this);
        dialog.setContentView(R.layout.ludo_participant_info_update_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView userNameText = dialog.findViewById(R.id.userNameTextID);
        TextView playerNameText = dialog.findViewById(R.id.playerNameTextID);

        EditText rankEditText = dialog.findViewById(R.id.rankEditTextID);
        EditText winningMoneyEditText = dialog.findViewById(R.id.winningMoneyEditTextID);
        EditText refundEditText = dialog.findViewById(R.id.refundEditTextID);

        userNameText.setText(response.getUserName());
        playerNameText.setText(response.getPlayerName());
        rankEditText.setText(String.valueOf(response.getRank()));
        winningMoneyEditText.setText(String.valueOf(response.getWinningMoney()));

        AppCompatButton refundButton = dialog.findViewById(R.id.refundButtonID);
        AppCompatButton updateButton = dialog.findViewById(R.id.updateButtonID);

        refundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String refundAmount = refundEditText.getText().toString().trim();

                if (TextUtils.isEmpty(refundAmount)) {
                    Toasty.error(getApplicationContext(), "Refund amount empty", Toasty.LENGTH_SHORT).show();
                } else {
                    loader.show();

                    apiService.updateLudoRefund(secret_id, api_token, matchID, refundAmount, userID).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();

                            //Log.d("errrorxxx", String.valueOf(response.body().getE()));
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), "Refund completed", Toasty.LENGTH_SHORT).show();
                                load_participant();
                                dialog.dismiss();
                            } else {
                                Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();
                            //Log.d("errrorxxx", t.getMessage());
                        }
                    });

                }

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String winningMoney = winningMoneyEditText.getText().toString().trim();
                String rank = rankEditText.getText().toString().trim();

                if (TextUtils.isEmpty(winningMoney) || TextUtils.isEmpty(rank)) {
                    if (TextUtils.isEmpty(winningMoney)) {
                        Toasty.error(getApplicationContext(), "Empty Winning Money");
                    } else if (TextUtils.isEmpty(rank)) {
                        Toasty.error(getApplicationContext(), "Empty Rank");
                    }
                } else {
                    loader.show();
                    apiService.updateLudoResult(secret_id, api_token, matchID, winningMoney, rank, userID).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), "updated", Toasty.LENGTH_SHORT).show();
                                dialog.dismiss();
                                load_participant();
                            } else {
                                Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getApplicationContext(), "Something wrong", Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void OnItemClick(int position) {
        Ludo_uploaded_image_response.M response = dataList.get(position);

        Dialog showImageAlert = new Dialog(Ludo_participant_activity.this);
        showImageAlert.setContentView(R.layout.ludo_result_image_view_alert);
        showImageAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showImageAlert.setCancelable(false);
        showImageAlert.show();

        Window window = showImageAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = showImageAlert.findViewById(R.id.closeButtonID);
        ImageView resultImage= showImageAlert.findViewById(R.id.resultImageID);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageAlert.dismiss();
            }
        });

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(resultImage);
        pAttacher.update();

        Picasso.get().load(response.getImageLink()).into(resultImage);
    }
}