package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Promoter.Promoter_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Promoter.Promoter_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Promoter_activity extends AppCompatActivity implements Promoter_adapter.OnItemClickListener {

    Dialog loader;
    ImageView backButton;
    APIService apiService;
    String secret_id, api_token;
    FloatingActionButton addPromoterButton;
    RecyclerView promoterListView;
    private Promoter_adapter adapter;
    private List<Promoter_response.M> promoterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addPromoterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Promoter_add_activity.class));
            }
        });

        get_promoter_list();

    }

    private void get_promoter_list() {

        //Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
        apiService.get_promoter_list(secret_id, api_token).enqueue(new Callback<Promoter_response>() {
            @Override
            public void onResponse(Call<Promoter_response> call, Response<Promoter_response> response) {

                if (response.body().e == 0) {
                    promoterList = new ArrayList<>();
                    promoterList = response.body().m;
                    adapter = new Promoter_adapter(promoterList);
                    adapter.setOnClickListener(Promoter_activity.this::OnItemClick);
                    promoterListView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Promoter_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {
        loader = new Dialog(Promoter_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        //Log.d("tokenxx", api_token+" "+secret_id);

        backButton = findViewById(R.id.backButton);
        addPromoterButton = findViewById(R.id.addPromoterButtonID);

        promoterListView = findViewById(R.id.promoterListViewID);
        promoterListView.setHasFixedSize(true);
        promoterListView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void OnItemClick(int position) {
        Promoter_response.M response = promoterList.get(position);

        String promoterID = String.valueOf(response.promoter_id);

        //Toast.makeText(this, promoterID, Toast.LENGTH_SHORT).show();
        Dialog updateAlert = new Dialog(Promoter_activity.this);
        updateAlert.setContentView(R.layout.promoter_info_update_alert);
        updateAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateAlert.setCancelable(false);
        updateAlert.show();

        Window window = updateAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        ImageView closeButton = updateAlert.findViewById(R.id.closeButtonID);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAlert.dismiss();
            }
        });

        EditText userNameEditText = updateAlert.findViewById(R.id.userNameEditTextID);
        EditText nameEditText = updateAlert.findViewById(R.id.nameEditTextID);
        EditText phoneEditText = updateAlert.findViewById(R.id.phoneEditTextID);
        EditText passwordEditText = updateAlert.findViewById(R.id.passwordEditTextID);
        AppCompatButton updateButton = updateAlert.findViewById(R.id.updateButtonID);

        userNameEditText.setText(response.userName);
        nameEditText.setText(response.name);
        phoneEditText.setText(response.phone);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                    Toasty.error(getApplicationContext(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    Boolean hasPassword;

                    if (TextUtils.isEmpty(password)) {
                        hasPassword = false;
                    } else {
                        hasPassword = true;
                    }

                    //Log.d("dataxx", name + " " + String.valueOf(hasBoolean));
                    loader.show();
                    apiService.update_promoter_info(secret_id, api_token, promoterID, userName, name, phone, String.valueOf(hasPassword), password).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getApplicationContext(), response.body().getM(), Toasty.LENGTH_LONG).show();
                                updateAlert.dismiss();
                                get_promoter_list();
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
            }
        });

    }
}