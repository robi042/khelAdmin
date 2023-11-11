package code.fortomorrow.kheloNowAdmin.Adapter.Tournament;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.AddMoney.Add_money_accepted_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Tournament.Tournament_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Tournament_adapter extends RecyclerView.Adapter<Tournament_adapter.ViewHolder> {
    private List<Tournament_list_response.M> tournamentList;

    public Tournament_adapter(List<Tournament_list_response.M> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tournament_card, parent, false);
        return new Tournament_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tournament_list_response.M response = tournamentList.get(position);
        holder.gameTitleText.setText(response.title);
        holder.dateTimeText.setText("Start: "+response.startDate+"    "+"End: "+response.endDate);
        holder.totalTeamText.setText(response.totalTeam);
        holder.feeText.setText(response.entryFee);
        holder.prizeText.setText(response.total_prize);
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, feeText, totalTeamText, prizeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            feeText = itemView.findViewById(R.id.feeTextID);
            totalTeamText = itemView.findViewById(R.id.totalTeamTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
        }
    }
}
