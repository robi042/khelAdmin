package code.fortomorrow.kheloNowAdmin.Adapter.UserAbove;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.ShowParticipantAdapter;
import code.fortomorrow.kheloNowAdmin.Model.UserAbove.User_above_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;

public class User_above_adapter extends RecyclerView.Adapter<User_above_adapter.ViewHolder> {

    List<User_above_response.Data> dataList;

    public User_above_adapter(List<User_above_response.Data> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public User_above_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_above_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_above_adapter.ViewHolder holder, int position) {

        User_above_response.Data response = dataList.get(position);
        holder.userIDText.setText(String.valueOf(response.userId));
        holder.userNameText.setText(String.valueOf(response.userName));
        holder.balanceText.setText(String.valueOf(response.balance));
        holder.depositBalanceText.setText(String.valueOf(response.depositBalance));
        holder.winBalanceText.setText(String.valueOf(response.winningBalance));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userIDText, userNameText, balanceText, depositBalanceText, winBalanceText;

        ImageView viewButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userIDText = itemView.findViewById(R.id.userIDText);
            userNameText = itemView.findViewById(R.id.userNameText);
            balanceText = itemView.findViewById(R.id.balanceText);
            depositBalanceText = itemView.findViewById(R.id.depositBalanceText);
            winBalanceText = itemView.findViewById(R.id.winBalanceText);

            viewButton = itemView.findViewById(R.id.viewButton);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.OnItemClick(position);
                        }
                    }
                }
            });

            userNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("username", dataList.get(position).userName);
                    clipboard.setPrimaryClip(clip);

                    Toasty.success(itemView.getContext(), "username copied",Toasty.LENGTH_SHORT).show();
                }
            });
        }
    }
}
