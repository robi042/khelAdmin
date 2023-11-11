package code.fortomorrow.kheloNowAdmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin.Sub_admin_adapter;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sub_admin_activity extends AppCompatActivity implements Sub_admin_adapter.OnItemControlClickListener, Sub_admin_adapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    APIService apiService;
    String api_token, secret_id;
    ImageView backButton;
    RecyclerView subAdminView;
    private List<Sub_admin_response.M> subAdminList;
    private List<Sub_admin_response.M> itemList = new ArrayList<>();
    private Sub_admin_adapter adapter;
    Dialog loader;
    TextView historyButton;
    Spinner typeSpinner;
    String[] types = {"Active", "Inactive"};
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_admin);

        init_view();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Sub_admin_history_activity.class));
            }
        });


        typeSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        typeSpinner.setAdapter(aa);


    }

    private void admin_data(String type) {

        //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        loader.show();
        apiService.getSubAdmins(secret_id, api_token, "sub_admin").enqueue(new Callback<Sub_admin_response>() {
            @Override
            public void onResponse(Call<Sub_admin_response> call, Response<Sub_admin_response> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    subAdminList = new ArrayList<>();
                    subAdminList = response.body().getM();

                    itemList.clear();
                    for (int i = 0; i < subAdminList.size(); i++) {
                        if (subAdminList.get(i).status.equals(type.toLowerCase(Locale.ROOT))) {
                            itemList.add(response.body().m.get(i));
                        }
                    }

                    //Toast.makeText(Sub_admin_activity.this, String.valueOf(subAdminList.size())+" "+String.valueOf(itemList.size()), Toast.LENGTH_SHORT).show();

                    adapter = new Sub_admin_adapter(itemList);
                    adapter.setOnClickListener(Sub_admin_activity.this::OnItemControlClick, Sub_admin_activity.this::OnItemClick);
                    subAdminView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Sub_admin_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {

        loader = new Dialog(Sub_admin_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        subAdminView = findViewById(R.id.subAdminViewID);
        subAdminView.setHasFixedSize(true);
        subAdminView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        historyButton = findViewById(R.id.historyButtonID);
        typeSpinner = findViewById(R.id.typeSpinner);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void OnItemControlClick(int position) {
        Sub_admin_response.M response = itemList.get(position);
        String adminID = String.valueOf(response.getSubAdminId());

        //Toast.makeText(getApplicationContext(), adminID, Toast.LENGTH_SHORT).show();

        loader.show();
        apiService.controlSubAdmins(secret_id, api_token, adminID).enqueue(new Callback<SorkariResponse>() {
            @Override
            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                loader.dismiss();
                if (response.body().getE() == 0) {
                    Toasty.success(getApplicationContext(), "Status changed", Toasty.LENGTH_SHORT).show();

                    admin_data(type);
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

    @Override
    public void OnItemClick(int position) {
        Sub_admin_response.M response = itemList.get(position);
        String adminID = String.valueOf(response.getSubAdminId());

        Toast.makeText(this, response.userName, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Admin_option_activity.class);
        intent.putExtra("admin_id", adminID);
        //startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        type = adapterView.getItemAtPosition(pos).toString();

        admin_data(type);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}