package code.fortomorrow.kheloNowAdmin.Adapter.SubAdminWithdrawHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Withdraw_done_list_history_adapter extends RecyclerView.Adapter<Withdraw_done_list_history_adapter.ViewHolder> {
    private List<Sub_admin_withdraw_history_response.WithdrawDone> withdrawDoneList;

    public Withdraw_done_list_history_adapter(List<Sub_admin_withdraw_history_response.WithdrawDone> withdrawDoneList) {
        this.withdrawDoneList = withdrawDoneList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_done_list_history_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sub_admin_withdraw_history_response.WithdrawDone response = withdrawDoneList.get(position);
        holder.amountText.setText(String.valueOf(response.amount));
        holder.timeText.setText(response.cashOutTime);
        holder.transactionCodeText.setText(response.transactionCode);
        holder.numberText.setText(response.phone_number);

    }

    @Override
    public int getItemCount() {
        return withdrawDoneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amountText, timeText, transactionCodeText, numberText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amountText = itemView.findViewById(R.id.amountTextID);
            timeText = itemView.findViewById(R.id.timeTextID);
            transactionCodeText = itemView.findViewById(R.id.transactionCodeTextID);
            numberText = itemView.findViewById(R.id.numberTextID);
        }
    }
}
