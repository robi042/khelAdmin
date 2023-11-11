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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Administrator.Admin_adapter;
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

public class Admin_activity extends AppCompatActivity implements Admin_adapter.OnItemControlClickListener, Admin_adapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    Dialog loader;
    APIService apiService;
    String api_token, secret_id;
    ImageView backButton;
    RecyclerView adminView;
    private List<Sub_admin_response.M> adminList;
    private List<Sub_admin_response.M> itemList = new ArrayList<>();
    private Admin_adapter adapter;

    Spinner typeSpinner;
    String[] types = {"Active", "Inactive"};
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        
        init_view();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        typeSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        typeSpinner.setAdapter(aa);

        //admin_data();
    }

    private void admin_data(String type) {

        apiService.getSubAdmins(secret_id, api_token, "admin").enqueue(new Callback<Sub_admin_response>() {
            @Override
            public void onResponse(Call<Sub_admin_response> call, Response<Sub_admin_response> response) {
                if (response.body().getE() == 0) {
                    adminList = new ArrayList<>();
                    adminList = response.body().getM();

                    itemList.clear();
                    for (int i = 0; i < adminList.size(); i++) {
                        if (adminList.get(i).status.equals(type.toLowerCase(Locale.ROOT))) {
                            itemList.add(response.body().m.get(i));
                        }
                    }

                    //Toast.makeText(Sub_admin_activity.this, String.valueOf(subAdminList.size())+" "+String.valueOf(itemList.size()), Toast.LENGTH_SHORT).show();

                    adapter = new Admin_adapter(itemList);
                    adapter.setOnClickListener(Admin_activity.this::OnItemControlClick, Admin_activity.this::OnItemClick);
                    adminView.setAdapter(adapter);

                    //adapter = new Admin_adapter(adminList);
                    //adapter.setOnClickListener(Admin_activity.this::OnItemControlClick, Admin_activity.this::OnItemClick);
                    //adminView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Sub_admin_response> call, Throwable t) {

            }
        });
    }

    private void init_view() {

        loader = new Dialog(Admin_activity.this);
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getApplicationContext());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        backButton = findViewById(R.id.backButton);
        adminView = findViewById(R.id.adminViewID);
        adminView.setHasFixedSize(true);
        adminView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        typeSpinner = findViewById(R.id.typeSpinner);
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

       // Toast.makeText(this, response.userName, Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(getApplicationContext(), Sub_admin_details_activity.class);
        Intent intent = new Intent(getApplicationContext(), Admin_option_activity.class);
        intent.putExtra("admin_id", adminID);
        startActivity(intent);

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