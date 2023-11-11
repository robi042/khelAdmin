package code.fortomorrow.kheloNowAdmin.Adapter.Add_WithdrawMoney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_match_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.AddMoneywithdrawMoney.Accept_and_rejected_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Accepted_rejected_money_list_adapter extends RecyclerView.Adapter<Accepted_rejected_money_list_adapter.ViewHolder> {
    private List<Accept_and_rejected_list_response.M> dataList;

    public Accepted_rejected_money_list_adapter(List<Accept_and_rejected_list_response.M> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_rejected_money_list_card, parent, false);
        return new Accepted_rejected_money_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accept_and_rejected_list_response.M response = dataList.get(position);
        holder.requestedTimeText.setText(response.getRequestedDate());
        holder.userNameText.setText(response.getUserName());
        holder.phoneText.setText(response.getPhone());
        holder.methodText.setText(response.getPaymentMethod());
        holder.amountText.setText(String.valueOf(response.getAmount()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView requestedTimeText, userNameText, phoneText, amountText, methodText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestedTimeText = itemView.findViewById(R.id.requestedTimeTextID);
            userNameText = itemView.findViewById(R.id.userNameTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);
            amountText = itemView.findViewById(R.id.amountTextID);
            methodText = itemView.findViewById(R.id.methodTextID);
        }
    }
}
