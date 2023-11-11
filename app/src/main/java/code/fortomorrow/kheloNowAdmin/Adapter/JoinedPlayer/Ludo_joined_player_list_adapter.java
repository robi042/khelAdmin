package code.fortomorrow.kheloNowAdmin.Adapter.JoinedPlayer;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.JoinedPlayer.Joined_player_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;

public class Ludo_joined_player_list_adapter extends RecyclerView.Adapter<Ludo_joined_player_list_adapter.ViewHolder> {
    private List<Joined_player_list_response.M> joinedPlayerList;

    public Ludo_joined_player_list_adapter(List<Joined_player_list_response.M> joinedPlayerList) {
        this.joinedPlayerList = joinedPlayerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_player_list_card, parent, false);
        return new Ludo_joined_player_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Joined_player_list_response.M response = joinedPlayerList.get(position);
        holder.userNameText.setText(response.getUserName());
        holder.positionText.setText(String.valueOf(position + 1));

        holder.player1Text.setVisibility(View.VISIBLE);
        holder.player1Text.setText("Player Name: " + response.getPlayer_name());

        holder.copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("user_name", joinedPlayerList.get(position).getUserName());
                Toasty.success(holder.itemView.getContext(), joinedPlayerList.get(position).getUserName() + " is Copied", Toasty.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }
        });

        try {
            if (response.getReady()) {
                holder.readyText.setText("player is ready");
            }
        } catch (Exception e) {
            holder.readyText.setText("player isn't ready");
            holder.readyText.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return joinedPlayerList.size();
    }

    private OnLudoRefundItemClickListener onLudoRefundClickListener;
    private OnLudoDeleteItemClickListener onLudoDeleteItemClickListener;

    public interface OnLudoRefundItemClickListener {
        void OnLudoRefundItemClick(int position);
    }

    public interface OnLudoDeleteItemClickListener {
        void OnLudoDeleteItemClick(int position);
    }

    public void setOnClickListener(Ludo_joined_player_list_adapter.OnLudoRefundItemClickListener onLudoRefundClickListener, Ludo_joined_player_list_adapter.OnLudoDeleteItemClickListener onLudoDeleteItemClickListener) {
        this.onLudoRefundClickListener = onLudoRefundClickListener;
        this.onLudoDeleteItemClickListener = onLudoDeleteItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, player1Text, player2Text, player3Text, player4Text, player5Text, player6Text, positionText;
        TextView readyText;

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
            readyText = itemView.findViewById(R.id.readyTextID);

            refundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLudoRefundClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onLudoRefundClickListener.OnLudoRefundItemClick(position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLudoDeleteItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onLudoDeleteItemClickListener.OnLudoDeleteItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
