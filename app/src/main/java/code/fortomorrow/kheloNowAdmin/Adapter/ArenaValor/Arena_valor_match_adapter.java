package code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.AllCreatedMatches;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.LudoMatchAdapter;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_participant_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_match_adapter extends RecyclerView.Adapter<Arena_valor_match_adapter.ViewHolder> {
    private List<Arena_valor_response.M> arenaValorMatchList;
    private AllCreatedMatches allCreatedMatches;

    public Arena_valor_match_adapter(List<Arena_valor_response.M> arenaValorMatchList, AllCreatedMatches allCreatedMatches) {
        this.arenaValorMatchList = arenaValorMatchList;
        this.allCreatedMatches = allCreatedMatches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arena_valor_match_card, parent, false);
        return new Arena_valor_match_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Arena_valor_response.M response = arenaValorMatchList.get(position);
        holder.gameTitleText.setText(response.title);
        holder.dateTimeText.setText(response.date + " " + response.time);
        holder.totalPlayerText.setText(response.totalPlayer);
        holder.prizeText.setText(response.totalPrize);
        holder.versionText.setText(response.version);
        holder.feeText.setText(response.entryFee);
        holder.roomCodeText.setText(response.roomCode);

        holder.selectMatchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    allCreatedMatches.add_match_in_the_list(String.valueOf(response.matchId));
                } else {
                    allCreatedMatches.remove_match_from_the_list(String.valueOf(response.matchId));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arenaValorMatchList.size();
    }

    private OnArenaValorItemClickListener onArenaValorItemClickListener;
    private OnArenaValorItemDeleteListener onArenaValorDeleteListener;

    public interface OnArenaValorItemClickListener {
        void OnArenaValorItemClick(int position);
    }

    public interface OnArenaValorItemDeleteListener {
        void OnArenaValorDeleteClick(int position);
    }

    public void setOnClickListener(Arena_valor_match_adapter.OnArenaValorItemClickListener onArenaValorItemClickListener,Arena_valor_match_adapter.OnArenaValorItemDeleteListener onArenaValorDeleteListener) {
        this.onArenaValorItemClickListener = onArenaValorItemClickListener;
        this.onArenaValorDeleteListener = onArenaValorDeleteListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, feeText, versionText, gameTypeText, totalPlayerText, roomCodeText;
        LinearLayout deleteButton;
        CheckBox selectMatchCheckBox;
        CardView mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            feeText = itemView.findViewById(R.id.feeTextID);
            versionText = itemView.findViewById(R.id.versionTextID);
            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            roomCodeText = itemView.findViewById(R.id.roomCodeTextID);
            selectMatchCheckBox = itemView.findViewById(R.id.selectMatchCheckBoxID);
            deleteButton = itemView.findViewById(R.id.deleteButtonID);
            mainLayout = itemView.findViewById(R.id.mainLayoutID);

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onArenaValorItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorItemClickListener.OnArenaValorItemClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onArenaValorDeleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onArenaValorDeleteListener.OnArenaValorDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
