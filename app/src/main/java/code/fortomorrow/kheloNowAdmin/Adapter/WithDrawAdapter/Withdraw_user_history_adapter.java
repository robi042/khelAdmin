package code.fortomorrow.kheloNowAdmin.Adapter.WithDrawAdapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_match_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.WithDraw.Withdraw_history_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Withdraw_user_history_adapter extends RecyclerView.Adapter<Withdraw_user_history_adapter.ViewHolder> {
    private List<Withdraw_history_response.M> historyList;

    public Withdraw_user_history_adapter(List<Withdraw_history_response.M> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_user_history_card, parent, false);
        return new Withdraw_user_history_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Withdraw_history_response.M response = historyList.get(position);
        holder.requestedTimeText.setText(response.getRequestedTime());
        holder.phoneText.setText(response.getPhoneNo());
        holder.amountText.setText(String.valueOf(response.getAmount()));
        holder.methodText.setText(response.getPaymentMethod());
        holder.statusText.setText(response.getStatus());

        if(response.getStatus().equals("accepted")){
            holder.statusText.setTextColor(Color.GREEN);
        }else if(response.getStatus().equals("rejected")){
            holder.statusText.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView requestedTimeText, phoneText, amountText, methodText, statusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestedTimeText = itemView.findViewById(R.id.requestedTimeTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);
            amountText = itemView.findViewById(R.id.amountTextID);
            methodText = itemView.findViewById(R.id.methodTextID);
            statusText = itemView.findViewById(R.id.statusTextID);
        }
    }
}
