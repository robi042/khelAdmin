package code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_result_adapter extends RecyclerView.Adapter<Arena_valor_result_adapter.ViewHolder> {
    private List<Arena_valor_response.M> arenaValorResultList;

    public Arena_valor_result_adapter(List<Arena_valor_response.M> arenaValorResultList) {
        this.arenaValorResultList = arenaValorResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arena_valor_result_card, parent, false);
        return new Arena_valor_result_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Arena_valor_response.M response = arenaValorResultList.get(position);
        holder.gameTitleText.setText(response.title);
        holder.dateTimeText.setText(response.date + " " + response.time);
        holder.totalPlayerText.setText(response.totalPlayer);
        holder.prizeText.setText(response.totalPrize);
        holder.versionText.setText(response.version);
        holder.feeText.setText(response.entryFee);


        if (TextUtils.isEmpty(response.admin_note)) {
            holder.noteLayout.setVisibility(View.GONE);
        } else {
            holder.noteLayout.setVisibility(View.VISIBLE);
            holder.noteText.setText(response.admin_note);
        }

        try {
            if (response.hasRoomCode) {
                holder.roomCodeText.setText(response.roomCode);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return arenaValorResultList.size();
    }

    private OnArenaValorItemClickListener onArenaValorItemClickListener;


    public interface OnArenaValorItemClickListener {
        void OnArenaValorItemClick(int position);
    }

    public void setOnClickListener(Arena_valor_result_adapter.OnArenaValorItemClickListener onArenaValorItemClickListener) {
        this.onArenaValorItemClickListener = onArenaValorItemClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, feeText, versionText, totalPlayerText, roomCodeText, noteText;
        LinearLayout noteLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            feeText = itemView.findViewById(R.id.feeTextID);
            versionText = itemView.findViewById(R.id.versionTextID);
            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            roomCodeText = itemView.findViewById(R.id.roomCodeTextID);
            noteText = itemView.findViewById(R.id.noteTextId);
            noteLayout = itemView.findViewById(R.id.noteLayoutID);

            itemView.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
