package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.User.Get_user_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_activity extends AppCompatActivity {

    ImageView backButton, editButton;
    EditText searchEditText;
    LinearLayout searchLayoutButton;
    LinearLayout profileLayout;
    String secret_id, api_token;
    APIService apiService;
    TextView userNameText, nameText, phoneText, totalBalanceText, depositBalanceText, winningBalanceText, totalMatchPlayText, matchWinText, statusText, referAmountID, referID;

    String searchText, userID;
    LinearLayout statusChangeButton;
    TextView buttonStatusText;
    Dialog loader;
    Button withdraw_amount, add_amount;
    FloatingActionButton sendMessageButton;
    private int add, withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog optionDialog = new Dialog(Search_activity.this);
                optionDialog.setContentView(R.layout.user_information_edit_alert);
                optionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                optionDialog.show();

                Window window = optionDialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
                //wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(wlp);

                ImageView closeButton = optionDialog.findViewById(R.id.closeButtonID);
                EditText totalBalanceEditText = optionDialog.findViewById(R.id.totalBalanceEditTextID);
                EditText depositBalanceEditText = optionDialog.findViewById(R.id.depositBalanceEditTextID);
                EditText winningBalanceEditText = optionDialog.findViewById(R.id.winningBalanceEditTextID);
                AppCompatButton submitButton = optionDialog.findViewById(R.id.submitButtonID);

                totalBalanceEditText.setText(totalBalanceText.getText().toString().trim());
                depositBalanceEditText.setText(depositBalanceText.getText().toString().trim());
                winningBalanceEditText.setText(winningBalanceText.getText().toString().trim());

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionDialog.dismiss();
                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String total = totalBalanceEditText.getText().toString().trim();
                        String winning = winningBalanceEditText.getText().toString().trim();
                        String deposit = depositBalanceEditText.getText().toString().trim();

                        if (TextUtils.isEmpty(total) || TextUtils.isEmpty(winning) || TextUtils.isEmpty(deposit)) {
                            Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                        } else {
                            loader.show();
                            apiService.updateUserBalance(secret_id, api_token, userID, total, deposit, winning).enqueue(new Callback<SorkariResponse>() {
                                @Override
                                public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                    loader.dismiss();
                                    if (response.body().getE() == 0) {
                                        Toasty.success(getApplicationContext(), "Updated", Toasty.LENGTH_SHORT).show();
                                        optionDialog.dismiss();
                                        get_user();
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
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    profileLayout.setVisibility(View.GONE);
                }
            }
        });

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEditText.getText().toString();

                if (TextUtils.isEmpty(searchText)) {
                    Toasty.error(getApplicationContext(), "Search box empty", Toasty.LENGTH_SHORT).show();
                    profileLayout.setVisibility(View.GONE);
                } else {
                    //Toasty.success(getApplicationContext(), searchText, Toasty.LENGTH_SHORT).show();
                    //profileLayout.setVisibility(View.VISIBLE);

                    get_user();
                }
            }
        });

        //Log.d("tokenxxx", api_token);

        statusChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
                loader.show();
                apiService.blockUnBlockUser(secret_id, api_token, userID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0) {
                            Toasty.success(getApplicationContext(), "Successful", Toasty.LENGTH_SHORT).show();
                            get_user();
                        } else {
                            Toasty.error(getApplicationContext(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
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

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message();
            }
        });

        add_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add > 0) {
                    Intent intent = new Intent(getApplicationContext(), SearchAddWithdrawHistory.class);
                    intent.putExtra("user_id", userID);
                    intent.putExtra("type", "Add Money");
                    startActivity(intent);
                }
                else{
                    Toasty.error(getApplicationContext(), "No Add Money History!", Toasty.LENGTH_SHORT).show();
                }
            }
        });



        withdraw_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(withdraw > 0) {
                    Intent intent = new Intent(getApplicationContext(), SearchAddWithdrawHistory.class);
                    intent.putExtra("user_id", userID);
                    intent.putExtra("type", "Withdraw Money");
                    startActivity(intent);
                }
                else{
                    Toasty.error(getApplicationContext(), "No Withdraw History!", Toasty.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void send_message() {
        Dialog messageDialog = new Dialog(Search_activity.this);
        messageDialog.setContentView(R.layout.send_message_alert);
        messageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        messageDialog.show();

        Window window = messageDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        //wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        ImageView closeButton = messageDialog.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog.dismiss();
            }
        });
        EditText messageText = messageDialog.findViewById(R.id.messageEditTextID);
        AppCompatButton sendButton = messageDialog.findViewById(R.id.sendMessageButtonID);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString().trim();

                if (TextUtils.isEmpty(message)) {
                    Toasty.error(getApplicationContext(), "empty message", Toasty.LENGTH_SHORT).show();
                } else {
                    loader.show();
                    apiService.sendMessageToUser(secret_id, api_token, message, userID).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {
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

    private void init_view() {
        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        loader = new Dialog(Search_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        backButton = findViewById(R.id.backButton);
        searchEditText = findViewById(R.id.searchEditTextId);
        editButton = findViewById(R.id.editButtonID);

        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        profileLayout = findViewById(R.id.profileLayoutID);
        statusChangeButton = findViewById(R.id.statusChangeButtonID);

        userNameText = findViewById(R.id.userNameTextID);
        nameText = findViewById(R.id.nameTextID);
        phoneText = findViewById(R.id.phoneTextID);
        totalBalanceText = findViewById(R.id.totalBalanceTextID);
        depositBalanceText = findViewById(R.id.depositBalanceTextID);
        winningBalanceText = findViewById(R.id.winningBalanceTextID);
        totalMatchPlayText = findViewById(R.id.totalMatchPlayTextID);
        matchWinText = findViewById(R.id.matchWinTextID);
        statusText = findViewById(R.id.statusTextID);
        buttonStatusText = findViewById(R.id.buttonStatusTextID);
        sendMessageButton = findViewById(R.id.sendMessageButtonID);
        referAmountID = findViewById(R.id.referAmountID);
        referID = findViewById(R.id.referID);
        add_amount = findViewById(R.id.add_amount);
        withdraw_amount = findViewById(R.id.withdraw_amount);
    }

    private void get_user() {
        apiService.getUserdata(secret_id, api_token, searchText).enqueue(new Callback<Get_user_response>() {
            @Override
            public void onResponse(Call<Get_user_response> call, Response<Get_user_response> response) {
                if (response.body().getE() == 0) {
                    //Toasty.success(getApplicationContext(), "User found", Toasty.LENGTH_SHORT).show();
                    profileLayout.setVisibility(View.VISIBLE);

                    userID = String.valueOf(response.body().getM().getUserId());
                    //Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();

                    userNameText.setText(response.body().getM().getUserName());
                    nameText.setText(response.body().getM().getName());
                    phoneText.setText(response.body().getM().getPhone());
                    totalBalanceText.setText(String.valueOf(response.body().getM().getTotalBalance()));
                    depositBalanceText.setText(String.valueOf(response.body().getM().getDepositBalance()));
                    winningBalanceText.setText(String.valueOf(response.body().getM().getWinningBalance()));
                    totalMatchPlayText.setText(String.valueOf(response.body().getM().getTotalMatchPlay()));
                    matchWinText.setText(String.valueOf(response.body().getM().getMatchWin()));
                    referID.setText(String.valueOf(response.body().getM().getTotal_refer()));
                    referAmountID.setText(String.valueOf(response.body().getM().getTotal_refer_income()));
                    add_amount.setText("Total Add Money\n"+String.valueOf(response.body().getM().getTotal_add_money()));
                    withdraw_amount.setText("Total Withdraw\n"+String.valueOf(response.body().getM().getTotal_withdraw()));
                    add = response.body().getM().getTotal_add_money();
                    withdraw = response.body().getM().getTotal_withdraw();

                    if (response.body().getM().getStatus().equals("active")) {
                        statusText.setText(response.body().getM().getStatus());
                        statusText.setTextColor(getResources().getColor(R.color.green));

                        buttonStatusText.setText("Inactive");

                    } else if (response.body().getM().getStatus().equals("inactive")) {
                        statusText.setText(response.body().getM().getStatus());
                        statusText.setTextColor(getResources().getColor(R.color.red));

                        buttonStatusText.setText("Active");
                    }

                } else {
                    Toasty.error(getApplicationContext(), "User not found", Toasty.LENGTH_SHORT).show();
                    profileLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Get_user_response> call, Throwable t) {
                Toasty.error(getApplicationContext(), "User not found", Toasty.LENGTH_SHORT).show();
                profileLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}