package code.fortomorrow.kheloNowAdmin.Adapter.Ludo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_user_statistics_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_user_statistics_adapter extends RecyclerView.Adapter<Ludo_user_statistics_adapter.ViewHolder> {
    private List<Ludo_user_statistics_response.M> statisticsList;

    public Ludo_user_statistics_adapter(List<Ludo_user_statistics_response.M> statisticsList) {
        this.statisticsList = statisticsList;
    }

    @NonNull
    @Override
    public Ludo_user_statistics_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ludo_user_statistics_card, parent, false);
        return new Ludo_user_statistics_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ludo_user_statistics_adapter.ViewHolder holder, int position) {
        Ludo_user_statistics_response.M response = statisticsList.get(position);
        holder.titleText.setText(response.matchTitle);
        holder.entryFeeText.setText("৳" + String.valueOf(response.paid));

        try {
            if (!String.valueOf(response.winningMoney).equals("null")) {
                holder.winningText.setText("৳" + String.valueOf(response.winningMoney));
            } else {
                holder.winningText.setText(String.valueOf("-"));
            }

        } catch (Exception e) {
            holder.winningText.setText(String.valueOf("-"));
        }

        try {
            if (!String.valueOf(response.refundAmount).equals("null")) {
                holder.refundText.setText("৳" + String.valueOf(response.refundAmount));
            } else {
                holder.refundText.setText(String.valueOf("-"));
            }

        } catch (Exception e) {
            holder.refundText.setText(String.valueOf("-"));
        }
    }

    @Override
    public int getItemCount() {
        return statisticsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, entryFeeText, winningText, refundText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleTextID);
            entryFeeText = itemView.findViewById(R.id.entryFeeTextID);
            winningText = itemView.findViewById(R.id.winningTextID);
            refundText = itemView.findViewById(R.id.refundTextID);
        }
    }
}
