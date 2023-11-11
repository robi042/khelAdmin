package code.fortomorrow.kheloNowAdmin.Adapter.Ludo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_participant_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_participant_list_adapter extends RecyclerView.Adapter<Ludo_participant_list_adapter.ViewHolder> {
    private List<Ludo_participant_response.M> participantList;

    public Ludo_participant_list_adapter(List<Ludo_participant_response.M> participantList) {
        this.participantList = participantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_participant_list_card, parent, false);
        return new Ludo_participant_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_participant_response.M response = participantList.get(position);
        holder.userNameText.setText(response.getUserName());
        holder.playerNameText.setText(response.getPlayerName());
        holder.rankText.setText(String.valueOf(response.getRank()));
        holder.winningMoneyText.setText(String.valueOf(response.getWinningMoney()));
        holder.refundMoneyText.setText(String.valueOf(response.getRefund_amount()));

        try {
            if(response.getReady()){
                holder.readyText.setText("already ready");
            }
        }catch (Exception e){
            holder.readyText.setText("isn't ready");
            holder.readyText.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    private OnParticipantItemClickListener onParticipantClickListener;
    private OnUserNameCopyClickListener onCopyListener;


    public interface OnParticipantItemClickListener {
        void OnParticipantItemClick(int position);
    }

    public interface OnUserNameCopyClickListener {
        void OnUserNameCopyClick(int position);
    }


    public void setOnClickListener(Ludo_participant_list_adapter.OnParticipantItemClickListener onParticipantClickListener, Ludo_participant_list_adapter.OnUserNameCopyClickListener onCopyListener) {
        this.onParticipantClickListener = onParticipantClickListener;
        this.onCopyListener = onCopyListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, playerNameText, rankText, winningMoneyText;
        LinearLayout copyUserNameButton;
        ImageView tikSign;
        TextView refundMoneyText, readyText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.userNameTextID);
            playerNameText = itemView.findViewById(R.id.playerNameTextID);
            rankText = itemView.findViewById(R.id.rankTextID);
            winningMoneyText = itemView.findViewById(R.id.winningMoneyTextID);
            copyUserNameButton = itemView.findViewById(R.id.copyUserNameButtonID);
            refundMoneyText = itemView.findViewById(R.id.refundMoneyTextID);
            readyText = itemView.findViewById(R.id.readyTextID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onParticipantClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onParticipantClickListener.OnParticipantItemClick(position);
                        }
                    }
                }
            });

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
        }
    }
}
