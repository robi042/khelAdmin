package code.fortomorrow.kheloNowAdmin.Adapter.UerrAddWithdrawHistory;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.AddWithdrawHistory.Add_withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;

public class AdapterClassUserAddWithdrawHistory extends RecyclerView.Adapter<AdapterClassUserAddWithdrawHistory.HistoryHolder> {
    private List<Add_withdraw_history_response.M> response;
    Context context;

    public AdapterClassUserAddWithdrawHistory(List<Add_withdraw_history_response.M> response, Context context) {
        this.response = response;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_all_account_activity, parent, false);
        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        try{
            holder.method.setText(response.get(position).getPaymentMethod().toString());
            //Toasty.success(context, response.get(1).getPaymentMethod()+"", Toasty.LENGTH_SHORT).show();
        }catch (NullPointerException e){

        }
        try{
            holder.amount.setText(response.get(position).getAmount().toString());
        }catch (NullPointerException e){

        }
        try{
            holder.status.setText(response.get(position).getStatus().toString());
        }catch (NullPointerException e){

        }
        try{
            holder.phone.setText(response.get(position).getPhone().toString());
        }catch (NullPointerException e){

        }

    }

    @Override
    public int getItemCount() {
        return response == null ? 0 : response.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView method, amount, status, phone;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            method = itemView.findViewById(R.id.method);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
            phone = itemView.findViewById(R.id.phone);
        }
    }
}
