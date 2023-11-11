package code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_information_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Sub_admin_information_adapter extends RecyclerView.Adapter<Sub_admin_information_adapter.ViewHolder> {
    private List<Sub_admin_information_response.M> dataList;

    public Sub_admin_information_adapter(List<Sub_admin_information_response.M> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_admin_information_card, parent, false);
        return new Sub_admin_information_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sub_admin_information_response.M response = dataList.get(position);
        holder.amountText.setText(String.valueOf(response.acceptAmount));
        holder.requestedByText.setText(response.requestedBy);
        holder.requestedTimeText.setText(response.requestedTime);
        holder.acceptByText.setText(response.acceptedBy);
        holder.acceptTimeText.setText(response.acceptedTime);
        holder.transactionCodeText.setText(response.transactionCode);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amountText, requestedByText, requestedTimeText, acceptByText, acceptTimeText, transactionCodeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestedByText = itemView.findViewById(R.id.requestedByTextID);
            amountText = itemView.findViewById(R.id.amountTextID);
            requestedTimeText = itemView.findViewById(R.id.requestedTimeTextID);
            acceptByText = itemView.findViewById(R.id.acceptByTextID);
            acceptTimeText = itemView.findViewById(R.id.acceptTimeTextID);
            transactionCodeText = itemView.findViewById(R.id.transactionCodeTextID);
        }
    }
}
