package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.Add_WithdrawMoney.Accepted_rejected_money_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneywithdrawMoney.Accept_and_rejected_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_rejected extends Fragment {

    String type;
    RecyclerView listView;
    APIService apiService;
    String api_token, secret_id;
    String status = "rejected";
    LinearLayout noDataLayout;
    private List<Accept_and_rejected_list_response.M> dataList;
    private Accepted_rejected_money_list_adapter adapter;
    Dialog loader;
    HorizontalScrollView dataView;

    public Fragment_rejected(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rejected, container, false);

        init_view(view);


        get_rejected_list();

        return view;
    }

    private void get_rejected_list() {
        //loader.show();
        apiService.getAccept_and_Rejected_list(secret_id, api_token, type, status).enqueue(new Callback<Accept_and_rejected_list_response>() {
            @Override
            public void onResponse(Call<Accept_and_rejected_list_response> call, Response<Accept_and_rejected_list_response> response) {

                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    dataView.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                    dataList = new ArrayList<>();
                    dataList = response.body().getM();
                    adapter = new Accepted_rejected_money_list_adapter(dataList);
                    listView.setAdapter(adapter);

                } else {
                    dataView.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<Accept_and_rejected_list_response> call, Throwable t) {
                dataView.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init_view(View view) {
        EasySharedPref.init(getActivity());
        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");

        listView = view.findViewById(R.id.listViewID);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        noDataLayout = view.findViewById(R.id.noDataLayoutID);
        dataView = view.findViewById(R.id.dataViewID);

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);
    }
}