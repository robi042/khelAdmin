package code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.ShowParticipantAdapter;
import code.fortomorrow.kheloNowAdmin.Model.ArenaValor.Arena_valor_result_match_info_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Arena_valor_participant_adapter extends RecyclerView.Adapter<Arena_valor_participant_adapter.ViewHolder> {
    private List<Arena_valor_result_match_info_response.M> participantList;

    public Arena_valor_participant_adapter(List<Arena_valor_result_match_info_response.M> participantList) {
        this.participantList = participantList;
    }

    @NonNull
    @Override
    public Arena_valor_participant_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arena_valor_participant_card, parent, false);
        return new Arena_valor_participant_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Arena_valor_participant_adapter.ViewHolder holder, int position) {

        Arena_valor_result_match_info_response.M response = participantList.get(position);

        holder.mUserId.setText(String.valueOf(response.userName));
        holder.refundAmountText.setText("Refund : " + String.valueOf(response.refundAmount));
        holder.mWinning.setText("Winning : " + String.valueOf(response.winningMoney));
        holder.mRank.setText("Rank : " + String.valueOf(response.rank));

        if (response.hasResult) {
            holder.blueMark.setVisibility(View.VISIBLE);
        } else {
            holder.blueMark.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.firstPlayer)) {
            holder.mPlayerOne.setVisibility(View.VISIBLE);
            holder.mPlayerOne.setText("Player One : " + response.firstPlayer);
        } else {
            holder.mPlayerOne.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.secondPlayer)) {
            holder.mPlayerTwo.setVisibility(View.VISIBLE);
            holder.mPlayerTwo.setText("Player Two : " + response.secondPlayer);
        } else {
            holder.mPlayerTwo.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.thirdPlayer)) {
            holder.mPlayerThree.setVisibility(View.VISIBLE);
            holder.mPlayerThree.setText("Player Three : " + response.thirdPlayer);
        } else {
            holder.mPlayerThree.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.forthPlayer)) {
            holder.mPlayerFour.setVisibility(View.VISIBLE);
            holder.mPlayerFour.setText("Player Four : " + response.forthPlayer);
        } else {
            holder.mPlayerFour.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.fifthPlayer)) {
            holder.mPlayerFive.setVisibility(View.VISIBLE);
            holder.mPlayerFive.setText("Player Five : " + response.fifthPlayer);
        } else {
            holder.mPlayerFive.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.extraOne)) {
            holder.extraPlayer1Text.setVisibility(View.VISIBLE);
            holder.extraPlayer1Text.setText("Ex. Player One : " + response.extraOne);
        } else {
            holder.extraPlayer1Text.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(response.extraTwo)) {
            holder.extraPlayer2Text.setVisibility(View.VISIBLE);
            holder.extraPlayer2Text.setText("Ex. Player One : " + response.extraTwo);
        } else {
            holder.extraPlayer2Text.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    private OnUserNameCopyClickListener onCopyListener;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }


    public interface OnUserNameCopyClickListener {
        void OnUserNameCopyClick(int position);
    }


    public void setOnClickListener(Arena_valor_participant_adapter.OnUserNameCopyClickListener onCopyListener, Arena_valor_participant_adapter.OnItemClickListener onItemClickListener) {
        this.onCopyListener = onCopyListener;
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mLayer;
        private TextView mUserId, mPlayerOne, mPlayerTwo, mPlayerThree, mPlayerFour, mPlayerFive, extraPlayer1Text, extraPlayer2Text, mWinning, mRank, mUpdate;
        private ImageView blueMark;
        LinearLayout copyUserNameButton;
        TextView refundAmountText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserId = itemView.findViewById(R.id.mUserId);
            mPlayerOne = itemView.findViewById(R.id.mPlayerOne);
            mPlayerTwo = itemView.findViewById(R.id.mPlayerTwo);
            mPlayerThree = itemView.findViewById(R.id.mPlayerThree);
            mPlayerFour = itemView.findViewById(R.id.mPlayerFour);
            mPlayerFive = itemView.findViewById(R.id.mPlayerFive);
            extraPlayer1Text = itemView.findViewById(R.id.extraPlayer1TextID);
            extraPlayer2Text = itemView.findViewById(R.id.extraPlayer2TextID);
            mWinning = itemView.findViewById(R.id.mWinning);
            mRank = itemView.findViewById(R.id.mRank);
            mLayer = itemView.findViewById(R.id.mLayer);
            blueMark = itemView.findViewById(R.id.checkbox);

            copyUserNameButton = itemView.findViewById(R.id.copyUserNameButtonID);
            refundAmountText = itemView.findViewById(R.id.refundAmountTextID);

            copyUserNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCopyListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onCopyListener.OnUserNameCopyClick(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
