package code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.Add_WithdrawMoney.Accepted_rejected_money_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.HostHistory.Sub_admin_host_history_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Sub_admin_host_history_adapter extends RecyclerView.Adapter<Sub_admin_host_history_adapter.ViewHolder> {

    List<Sub_admin_host_history_response.M> itemList;

    public Sub_admin_host_history_adapter(List<Sub_admin_host_history_response.M> itemList) {
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public Sub_admin_host_history_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_admin_history_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sub_admin_host_history_adapter.ViewHolder holder, int position) {

        Sub_admin_host_history_response.M response = itemList.get(position);
        holder.userNameText.setText(response.userName);
        holder.totalText.setText(String.valueOf(response.totalHost));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, totalText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.userNameText);
            totalText = itemView.findViewById(R.id.totalText);
        }
    }
}
