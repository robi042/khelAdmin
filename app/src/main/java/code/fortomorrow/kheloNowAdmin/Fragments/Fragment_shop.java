package code.fortomorrow.kheloNowAdmin.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Adapter.BuySell.Buy_sell_adapter;
import code.fortomorrow.kheloNowAdmin.Model.BuySell.Buy_sell_response;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_shop extends Fragment implements Buy_sell_adapter.OnDeleteItemClickListener, Buy_sell_adapter.OnItemClickListener{
    RecyclerView itemRecyclerView;
    private Buy_sell_adapter buy_sell_adapter;

    private List<Buy_sell_response.M> buySellItemList = new ArrayList<>();
    Dialog loader;
    APIService apiService;
    String secret_id, api_token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        init_view(view);

        show_item();

        return view;
    }

    private void init_view(View view) {
        itemRecyclerView = view.findViewById(R.id.itemRecyclerViewID);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        EasySharedPref.init(getActivity());

        apiService = AppConfig.getRetrofit().create(APIService.class);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
    }

    private void show_item() {
        apiService.getBuySellItem(secret_id, api_token, "shop").enqueue(new Callback<Buy_sell_response>() {
            @Override
            public void onResponse(Call<Buy_sell_response> call, Response<Buy_sell_response> response) {
                if (response.body().getE() == 0 && response.body().getM().size() != 0) {
                    itemRecyclerView.setVisibility(View.VISIBLE);

                    buySellItemList = response.body().getM();
                    buy_sell_adapter = new Buy_sell_adapter(buySellItemList);
                    buy_sell_adapter.setOnClickListener(Fragment_shop.this::OnItemClick, Fragment_shop.this::OnDeleteItemClick);
                    itemRecyclerView.setAdapter(buy_sell_adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Buy_sell_response> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnDeleteItemClick(int position) {

        Buy_sell_response.M response = buySellItemList.get(position);

        String productID = String.valueOf(response.productId);

        //Toast.makeText(getActivity(), "d"+" "+productID, Toast.LENGTH_SHORT).show();

        Dialog confirmDialog = new Dialog(getActivity());
        confirmDialog.setContentView(R.layout.confirmation_alert);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.setCancelable(false);
        confirmDialog.show();

        Window window = confirmDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        TextView yesButton = confirmDialog.findViewById(R.id.yesButtonID);
        TextView noButton = confirmDialog.findViewById(R.id.noButtonID);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.show();
                apiService.removeProduct(secret_id, api_token, productID).enqueue(new Callback<SorkariResponse>() {
                    @Override
                    public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                        loader.dismiss();
                        if (response.body().getE() == 0) {
                            Toasty.success(getActivity(), "product removed", Toasty.LENGTH_SHORT).show();
                            confirmDialog.dismiss();
                            buySellItemList.remove(position);
                            buy_sell_adapter.notifyDataSetChanged();
                        } else {
                            Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SorkariResponse> call, Throwable t) {
                        loader.dismiss();
                        Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });


    }

    @Override
    public void OnItemClick(int position) {
        Buy_sell_response.M response = buySellItemList.get(position);

        String productID = String.valueOf(response.productId);

        Dialog updateDiscountAlert = new Dialog(getActivity());
        updateDiscountAlert.setContentView(R.layout.buy_sell_update_discount_alert);
        updateDiscountAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateDiscountAlert.setCancelable(false);
        updateDiscountAlert.show();

        Window window = updateDiscountAlert.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        EditText discountEditText = updateDiscountAlert.findViewById(R.id.discountEditTextID);
        AppCompatButton updateButton = updateDiscountAlert.findViewById(R.id.updateButtonID);
        ImageView closeButton = updateDiscountAlert.findViewById(R.id.closeButtonID);

        try {
            if (response.getHasDiscount()) {
                discountEditText.setText(String.valueOf(response.discount));
            }
        } catch (Exception e) {
            discountEditText.setText("0");
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDiscountAlert.dismiss();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discount = discountEditText.getText().toString().trim();

                if (TextUtils.isEmpty(discount)) {
                    Toasty.error(getActivity(), "empty field", Toasty.LENGTH_SHORT).show();
                } else {
                    //Toasty.success(getActivity(), discount, Toasty.LENGTH_SHORT).show();
                    loader.show();
                    apiService.updatePercentAmount(secret_id, api_token, productID, discount).enqueue(new Callback<SorkariResponse>() {
                        @Override
                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                            loader.dismiss();
                            if (response.body().getE() == 0) {
                                Toasty.success(getActivity(), "discount updated", Toasty.LENGTH_SHORT).show();
                                updateDiscountAlert.dismiss();

                                show_item();
                            } else {
                                Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                            loader.dismiss();
                            Toasty.error(getActivity(), getString(R.string.something_wrong), Toasty.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}