package code.fortomorrow.kheloNowAdmin.Adapter.Ludo;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_result_list;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_match_result_adapter extends RecyclerView.Adapter<Ludo_match_result_adapter.ViewHolder> {
    private List<Ludo_game_result_list.M> gameResultList;

    public Ludo_match_result_adapter(List<Ludo_game_result_list.M> gameResultList) {
        this.gameResultList = gameResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ludo_result_card, parent, false);
        return new Ludo_match_result_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_game_result_list.M response = gameResultList.get(position);
        holder.gameTitleText.setText(response.getTitle());
        holder.dateTimeText.setText(response.getDate() + " " + response.getTime());
        holder.prizeText.setText(response.getTotalPrize());
        holder.feeText.setText(response.getEntryFee());
        holder.joiningTypeText.setText("Single");
        holder.gameTypeText.setText(response.getHostApp());
        holder.totalPlayerText.setText(response.getTotalPlayer());
        Picasso.get().load(response.getImageLink()).fit().into(holder.showImageButton);

        holder.roomCodeTimeText.setText("Room ID Pass added at " + response.room_update_time);

        try {
            if (response.getHasRoomcode()) {
                holder.roomCodeText.setText(response.getRoomCode());
            }
        } catch (Exception e) {

        }

        if (response.getResultDone()) {
            holder.resultDoneLayout.setVisibility(View.VISIBLE);
        } else {
            holder.resultDoneLayout.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(response.getNote())) {
            holder.noteLayout.setVisibility(View.GONE);
        } else {
            holder.noteLayout.setVisibility(View.VISIBLE);
            holder.noteText.setText(response.getNote());
        }

    }

    @Override
    public int getItemCount() {
        return gameResultList.size();
    }

    private OnItemClickListener onClickListener;
    private OnImageViewListener onImageListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnImageViewListener {
        void OnImageClick(int position);
    }

    public void setOnClickListener(Ludo_match_result_adapter.OnItemClickListener onClickListener, Ludo_match_result_adapter.OnImageViewListener onImageListener) {
        this.onClickListener = onClickListener;
        this.onImageListener = onImageListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, feeText, joiningTypeText, gameTypeText, totalPlayerText, roomCodeText;
        ImageView showImageButton;
        LinearLayout resultDoneLayout, noteLayout;
        TextView noteText, roomCodeTimeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            feeText = itemView.findViewById(R.id.feeTextID);
            joiningTypeText = itemView.findViewById(R.id.joiningTypeTextID);
            gameTypeText = itemView.findViewById(R.id.gameTypeTextID);
            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            showImageButton = itemView.findViewById(R.id.showImageButtonID);
            roomCodeText = itemView.findViewById(R.id.roomCodeTextID);
            resultDoneLayout = itemView.findViewById(R.id.resultDoneLayoutID);
            noteLayout = itemView.findViewById(R.id.noteLayoutID);
            noteText = itemView.findViewById(R.id.noteTextId);
            roomCodeTimeText = itemView.findViewById(R.id.roomCodeTimeText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });

            showImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onImageListener.OnImageClick(position);
                        }
                    }
                }
            });
        }
    }
}
