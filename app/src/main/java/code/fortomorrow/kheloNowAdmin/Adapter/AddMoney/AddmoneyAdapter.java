package code.fortomorrow.kheloNowAdmin.Adapter.AddMoney;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.easysharedpref.EasySharedPref;
import code.fortomorrow.kheloNowAdmin.Activities.AddMoneyActivity;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.M;
import code.fortomorrow.kheloNowAdmin.Model.SorkariResponse.SorkariResponse;
import code.fortomorrow.kheloNowAdmin.R;
import code.fortomorrow.kheloNowAdmin.API.APIService;
import code.fortomorrow.kheloNowAdmin.API.AppConfig;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddmoneyAdapter extends RecyclerView.Adapter<AddmoneyAdapter.Viewholder> {
    private List<M> addMoneyList;
    private Context context;
    private AddMoneyActivity addMoneyActivity;
    private APIService apiService;
    private String api_token, secret_id;
    private ProgressDialog progressBar;
    Dialog loader;

    public AddmoneyAdapter(List<M> addMoneyList, Context context, AddMoneyActivity addMoneyActivity) {
        this.addMoneyList = addMoneyList;
        this.context = context;
        apiService = AppConfig.getRetrofit().create(APIService.class);
        this.addMoneyActivity = addMoneyActivity;
        EasySharedPref.init(context);
        api_token = EasySharedPref.read("api_token", "");
        secret_id = EasySharedPref.read("secret_id", "");
        progressBar = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_money_list, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {

        //setFadeAnimation(holder.itemView);

        holder.id.setText(String.valueOf(addMoneyList.get(position).getPhoneNumber()));
        holder.date.setText(String.valueOf(addMoneyList.get(position).getRequestedTime()));
        holder.amount.setText(String.valueOf(addMoneyList.get(position).getAmount()));
        holder.source.setText(String.valueOf(addMoneyList.get(position).getPaymentMethod()));
        holder.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("phone_number", addMoneyList.get(position).getPhoneNumber());
                Toasty.success(context, addMoneyList.get(position).getPhoneNumber() + " is Copied", Toasty.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                addMoneyActivity.deleteAddMoney(addMoneyList.get(position).getAddMoneyId());

                new AlertDialog.Builder(addMoneyActivity).setTitle("Confirm Delete?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        apiService.getRejectedAdmoney(secret_id, api_token, String.valueOf(addMoneyList.get(position).getAddMoneyId())).enqueue(new Callback<SorkariResponse>() {
                                            @Override
                                            public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                                if (response.body().getE() == 0) {
                                                    addMoneyList.remove(holder.getAdapterPosition());
                                                    notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<SorkariResponse> call, Throwable t) {

                                            }
                                        });
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
        });

        holder.add_balance.setOnClickListener(v -> {

            new AlertDialog.Builder(addMoneyActivity).setTitle("Confirm Delete?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    loader.show();
                                    apiService.getAcceptMoney(secret_id, api_token, String.valueOf(addMoneyList.get(position).getAddMoneyId())).enqueue(new Callback<SorkariResponse>() {
                                        @Override
                                        public void onResponse(Call<SorkariResponse> call, Response<SorkariResponse> response) {
                                            loader.dismiss();
                                            if (response.body().getE() == 0) {
                                                Toasty.success(holder.itemView.getContext(), "added", Toasty.LENGTH_SHORT).show();
                                                addMoneyList.remove(holder.getAdapterPosition());
                                                notifyDataSetChanged();
                                            }else {
                                                Toasty.error(holder.itemView.getContext(), response.body().getM(), Toasty.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SorkariResponse> call, Throwable t) {
                                            loader.dismiss();
                                        }
                                    });


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

        });

    }

    @Override
    public int getItemCount() {
        return addMoneyList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView id, date, status, amount, delete, add_balance, source;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            delete = itemView.findViewById(R.id.delete);
            add_balance = itemView.findViewById(R.id.add_balance);
            source = itemView.findViewById(R.id.source);

            loader = new Dialog(addMoneyActivity);
            loader.setContentView(R.layout.loader);
            loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loader.setCancelable(false);

        }
    }


    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }
}
