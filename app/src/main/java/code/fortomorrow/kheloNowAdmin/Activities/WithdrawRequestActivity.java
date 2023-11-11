package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.WithDrawAdapter.WithdrawAdapter;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.WithDraw.M;
import code.fortomorrow.kheloNowAdmin.Model.WithDraw.WithDrawResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawRequestActivity extends AppCompatActivity {

    private APIService apiService;
    RecyclerView recyclerView;
    TextView historyButton;
    //ProgressBar progressBar;
    private String api_token, secret_id;
    ImageView backButton;

    Dialog loader;

    String type = "Nagad";
    Boolean scrollState = true;

    private BottomNavigationView navigation;
    FloatingActionButton bottomScrollButton;
    List<M> withdrawList;
    List<M> searchList = new ArrayList<>();

    LinearLayout searchLayoutButton;
    EditText searchEditText;
    LinearLayout noDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        init_view();

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.nagaditem); // change to whichever id should be default
        }

        loader = new Dialog(WithdrawRequestActivity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        recyclerView = findViewById(R.id.withDrawRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        get_withdraw_list(type);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bottomScrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scrollState) {
                    scrollState = false;
                    recyclerView.smoothScrollToPosition(withdrawList.size() - 1);
                    bottomScrollButton.setImageResource(R.drawable.arrow_drop_up_24);
                } else {
                    scrollState = true;
                    recyclerView.smoothScrollToPosition(0);
                    bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }


            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Withdraw_history_activity.class));
            }
        });

        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();

                if (TextUtils.isEmpty(searchText)) {
                    Toasty.error(getApplicationContext(), "Empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    searching(searchText);
                }
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
                    get_withdraw_list(type);
                }
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.nagaditem:
                type = "Nagad";
                scrollState = true;
                bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                break;
            case R.id.bKashitem:
                type = "Bkash";
                scrollState = true;
                bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                break;
            case R.id.rocketitem:
                type = "Rocket";
                scrollState = true;
                bottomScrollButton.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        get_withdraw_list(type);
        return true;
    };

    private void get_withdraw_list(String type) {

        apiService.getWithDrawList(secret_id, api_token, type).enqueue(new Callback<WithDrawResponse>() {
            @Override
            public void onResponse(Call<WithDrawResponse> call, Response<WithDrawResponse> response) {

                if (response.body().getE() == 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    withdrawList = new ArrayList<>();
                    withdrawList = response.body().getM();
                    recyclerView.setAdapter(new WithdrawAdapter(WithdrawRequestActivity.this, getApplicationContext(), response.body().getM()));
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WithDrawResponse> call, Throwable t) {
                recyclerView.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init_view() {
        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("dataxx", api_token);

        backButton = findViewById(R.id.backButton);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomScrollButton = findViewById(R.id.bottomScrollButtonID);
        historyButton = findViewById(R.id.historyButtonID);

        searchLayoutButton = findViewById(R.id.searchLayoutButtonID);
        searchEditText = findViewById(R.id.searchEditTextId);
        noDataLayout = findViewById(R.id.noDataLayoutID);

    }

    public void copyWithDraw(String s) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", s);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(WithdrawRequestActivity.this, "Copied", Toast.LENGTH_SHORT).show();

    }

    public void sendWithDrawReq(Integer withdrawMoneyId) {

        new AlertDialog.Builder(this).setTitle("Confirm Accept?")
                .setMessage("Are you sure?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //progressBar.setVisibility(View.VISIBLE);
                                loader.show();
                                apiService.getAcceptedWithdraw(secret_id, api_token, String.valueOf(withdrawMoneyId), "admin").enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        loader.dismiss();
                                        if (response.body().getE() == 0) {
                                            get_withdraw_list(type);
                                            Toasty.success(getApplicationContext(), "Accepted", Toasty.LENGTH_SHORT).show();
                                        } else {
                                            Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                        loader.dismiss();
                                    }
                                });
                                // Perform Action & Dismiss dialog
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                })
                .create()
                .show();


    }

    public void deleteReq(Integer withdrawMoneyId) {
        new AlertDialog.Builder(this).setTitle("Confirm Accept?")
                .setMessage("Are you sure?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //progressBar.setVisibility(View.VISIBLE);
                                loader.show();
                                apiService.rejectedWithDraw(secret_id, api_token, String.valueOf(withdrawMoneyId)).enqueue(new Callback<SorkariResponse>() {
                                    @Override
                                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                        loader.dismiss();
                                        if (response.body().getE() == 0) {
                                            get_withdraw_list(type);
                                            Toasty.success(getApplicationContext(), "Deleted", Toasty.LENGTH_SHORT).show();
                                        } else {
                                            Toasty.error(getApplicationContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                        }

                                    }


                                    @Override
                                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                        loader.dismiss();
                                    }
                                });
                                // Perform Action & Dismiss dialog
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                })
                .create()
                .show();


        //progressBar.setVisibility(View.VISIBLE);
    }

    private void searching(String searchingKey) {


        if (searchingKey != null && !searchingKey.isEmpty()) {
            searchList.clear();
            for (int i = 0; i < withdrawList.size(); i++) {
                if (withdrawList.get(i).getUserName().contains(searchingKey)) {
                    searchList.add(withdrawList.get(i));
                }

            }

            recyclerView.setAdapter(new WithdrawAdapter(WithdrawRequestActivity.this, getApplicationContext(), searchList));
        }
    }


}
