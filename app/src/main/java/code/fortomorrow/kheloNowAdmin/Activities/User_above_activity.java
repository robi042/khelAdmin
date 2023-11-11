package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.UserAbove.User_above_adapter;
import code.fortomorrow.kheloNowAdmin.Model.EarnHistory.Earn_history_response;
import code.fortomorrow.kheloNowAdmin.Model.EarnHistory.Refer_income_response;
import code.fortomorrow.kheloNowAdmin.Model.UserAbove.User_above_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_above_activity extends AppCompatActivity implements User_above_adapter.OnItemClickListener {

    ImageView backButton, editButton;
    EditText searchEditText;
    LinearLayout searchLayoutButton;
    LinearLayout profileLayout;
    String searchText;

    String secret_id, api_token;
    APIService apiService;

    RecyclerView itemView;
    List<User_above_response.Data> dataList;
    User_above_adapter userAboveAdapter;

    HorizontalScrollView horizontalScrollView;

    Dialog loader;

    TextView userAmountText;

    int refer = 0, addMoney = 0, withdraw = 0, freePaid = 0, freeWinning = 0, freeRefund = 0, ludoPaid = 0, ludoWinning = 0, ludoRefund = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_above);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    horizontalScrollView.setVisibility(View.GONE);
                }
            }
        });

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEditText.getText().toString();

                if (TextUtils.isEmpty(searchText)) {
                    Toasty.error(getApplicationContext(), "Search box empty", Toasty.LENGTH_SHORT).show();
                    horizontalScrollView.setVisibility(View.GONE);
                } else {
                    //Toasty.success(getApplicationContext(), searchText, Toasty.LENGTH_SHORT).show();
                    //profileLayout.setVisibility(View.VISIBLE);

                    get_users(searchText);
                }
            }
        });
    }

    private void get_users(String searchText) {

        loader.show();
        apiService.get_user_above(secret_id, api_token, searchText).enqueue(new Callback<User_above_response>() {
            @Override
            public void onResponse(Call<User_above_response> call, Response<User_above_response> response) {
                loader.dismiss();
                if (response.body().e == 0) {
                    horizontalScrollView.setVisibility(View.VISIBLE);
                    dataList = new ArrayList<>();
                    dataList = response.body().data;

                    userAmountText.setText(String.valueOf(response.body().m) + " user found");

                    //Toast.makeText(User_above_activity.this, String.valueOf(dataList.size()), Toast.LENGTH_SHORT).show();
                    userAboveAdapter = new User_above_adapter(dataList);
                    userAboveAdapter.setOnClickListener(User_above_activity.this::OnItemClick);
                    itemView.setAdapter(userAboveAdapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<User_above_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {

        EasySharedPref.init(getApplicationContext());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        searchEditText = findViewById(R.id.searchEditTextId);
        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);

        itemView = findViewById(R.id.itemView);
        itemView.setHasFixedSize(true);
        itemView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        horizontalScrollView = findViewById(R.id.horizontalScrollView);

        userAmountText = findViewById(R.id.userAmountText);

        loader = new Dialog(this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }

    @Override
    public void OnItemClick(int position) {
        User_above_response.Data response = dataList.get(position);
        String userID = response.userId.toString();

        loader.show();

        Dialog dialog = new Dialog(User_above_activity.this);
        dialog.setContentView(R.layout.refer_income_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);


        TextView incomeText = dialog.findViewById(R.id.incomeText);
        TextView addMoneyText = dialog.findViewById(R.id.addMoneyText);
        TextView withdrawMoneyText = dialog.findViewById(R.id.withdrawMoneyText);

        //freefire
        TextView paidText = dialog.findViewById(R.id.paidText);
        TextView winningText = dialog.findViewById(R.id.winningAmountText);
        TextView refundText = dialog.findViewById(R.id.refundAmountText);

        //ludo
        TextView ludoPaidText = dialog.findViewById(R.id.ludoPaidText);
        TextView ludoWinningText = dialog.findViewById(R.id.ludoWinningAmountText);
        TextView ludoRefundText = dialog.findViewById(R.id.ludoRefundAmountText);

        apiService.user_income_from_refer(secret_id, api_token, userID).enqueue(new Callback<Refer_income_response>() {
            @Override
            public void onResponse(Call<Refer_income_response> call, Response<Refer_income_response> response) {
                if (response.body().e == 0) {

                    refer = response.body().data.incomeFromRefer;

                    apiService.get_add_withdraw_history(secret_id, api_token, userID).enqueue(new Callback<Earn_history_response>() {
                        @Override
                        public void onResponse(Call<Earn_history_response> call, Response<Earn_history_response> response) {

                            if (response.body().e == 0) {
                                addMoney = response.body().m.totalAddMoney;
                                withdraw = response.body().m.totalWithdrawMoney;
                                apiService.free_fire_earn_history(secret_id, api_token, userID).enqueue(new Callback<Earn_history_response>() {
                                    @Override
                                    public void onResponse(Call<Earn_history_response> call, Response<Earn_history_response> response) {

                                        if (response.body().e == 0) {

                                            freePaid = response.body().m.totalPaidAmount;
                                            freeWinning = response.body().m.totalWinningAmount;
                                            freeRefund = response.body().m.totalRefundAmount;

                                            apiService.ludo_earn_history(secret_id, api_token, userID).enqueue(new Callback<Earn_history_response>() {
                                                @Override
                                                public void onResponse(Call<Earn_history_response> call, Response<Earn_history_response> response) {
                                                    loader.dismiss();
                                                    if (response.body().e == 0) {
                                                        dialog.show();

                                                        withdrawMoneyText.setText("৳ " + String.valueOf(withdraw));
                                                        incomeText.setText("৳ " + String.valueOf(refer));
                                                        addMoneyText.setText("৳ " + String.valueOf(addMoney));

                                                        paidText.setText("৳ " +String.valueOf(freePaid));
                                                        winningText.setText("৳ " +String.valueOf(freeWinning));
                                                        refundText.setText("৳ " +String.valueOf(freeRefund));

                                                        ludoPaidText.setText("৳ " +String.valueOf(response.body().m.totalPaidAmount));
                                                        ludoWinningText.setText("৳ " +String.valueOf(response.body().m.totalWinningAmount));
                                                        ludoRefundText.setText("৳ " +String.valueOf(response.body().m.totalRefundAmount));
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Earn_history_response> call, Throwable t) {

                                                    loader.dismiss();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Earn_history_response> call, Throwable t) {
                                        loader.dismiss();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Earn_history_response> call, Throwable t) {
                            loader.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Refer_income_response> call, Throwable t) {
                loader.dismiss();
            }
        });
    }
}