package code.fortomorrow.kheloNowAdmin.Adapter.JoinedPlayer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_match_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;

public class Joined_player_list_adapter extends RecyclerView.Adapter<Joined_player_list_adapter.ViewHolder> {
    private List<Joined_player_list_response.M> joinedPlayerList;

    public Joined_player_list_adapter(List<Joined_player_list_response.M> joinedPlayerList) {
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_player_list_card, parent, false);
        return new Joined_player_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Joined_player_list_response.M response = joinedPlayerList.get(position);
        holder.userNameText.setText(response.getUserName());
        holder.positionText.setText(String.valueOf(position + 1));

        try {
            if (response.getHasFirstPlayer()) {
                holder.player1Text.setVisibility(View.VISIBLE);
                holder.player1Text.setText("player One: " + response.getFirstPlayer());
            }
        } catch (Exception p1) {

        }

        try {
            if (response.getHasSecondPlayer()) {
                holder.player2Text.setVisibility(View.VISIBLE);
                holder.player2Text.setText("player Two: " + response.getSecondPlayer());
            }
        } catch (Exception p1) {

        }

        try {
            if (response.getHasThirdPlayer()) {
                holder.player3Text.setVisibility(View.VISIBLE);
                holder.player3Text.setText("player Three: " + response.getThirdPlayer());
            }
        } catch (Exception p1) {

        }

        try {
            if (response.getHasForthPlayer()) {
                holder.player4Text.setVisibility(View.VISIBLE);
                holder.player4Text.setText("player Four: " + response.getForthPlayer());
            }
        } catch (Exception p1) {

        }

        try {
            if (response.getHasFifthPlayer()) {
                holder.player5Text.setVisibility(View.VISIBLE);
                holder.player5Text.setText("player Five: " + response.getFifthPlayer());
            }
        } catch (Exception p1) {

        }

        try {
            if (response.getHasSixthPlayer()) {
                holder.player6Text.setVisibility(View.VISIBLE);
                holder.player6Text.setText("player Six: " + response.getSixthPlayer());
            }
        } catch (Exception p1) {

        }

        holder.copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("user_name", joinedPlayerList.get(position).getUserName());
                Toasty.success(holder.itemView.getContext(), joinedPlayerList.get(position).getUserName() + " is Copied", Toasty.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }
        });


    }

    @Override
    public int getItemCount() {
        return joinedPlayerList.size();
    }

    private OnRefundItemClickListener onRefundClickListener;
    private OnDeleteItemClickListener onDeleteItemClickListener;

    public interface OnRefundItemClickListener {
        void OnRefundItemClick(int position);
    }

    public interface OnDeleteItemClickListener {
        void OnDeleteItemClick(int position);
    }

    public void setOnClickListener(Joined_player_list_adapter.OnRefundItemClickListener onRefundClickListener, Joined_player_list_adapter.OnDeleteItemClickListener onDeleteItemClickListener) {
        this.onRefundClickListener = onRefundClickListener;
        this.onDeleteItemClickListener = onDeleteItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userNameText, player1Text, player2Text, player3Text, player4Text, player5Text, player6Text, positionText;
        AppCompatButton deleteButton, refundButton;
        LinearLayout copyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            positionText = itemView.findViewById(R.id.positionTextID);
            userNameText = itemView.findViewById(R.id.userNameTextID);
            player1Text = itemView.findViewById(R.id.player1TextID);
            player2Text = itemView.findViewById(R.id.player2TextID);
            player3Text = itemView.findViewById(R.id.player3TextID);
            player4Text = itemView.findViewById(R.id.player4TextID);
            player5Text = itemView.findViewById(R.id.player5TextID);
            player6Text = itemView.findViewById(R.id.player6TextID);
            deleteButton = itemView.findViewById(R.id.deleteButtonID);
            refundButton = itemView.findViewById(R.id.refundButtonID);
            copyButton = itemView.findViewById(R.id.copyButtonID);



            refundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRefundClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onRefundClickListener.OnRefundItemClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onDeleteItemClickListener.OnDeleteItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
