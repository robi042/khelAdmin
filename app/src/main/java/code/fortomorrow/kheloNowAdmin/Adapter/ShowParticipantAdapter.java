package code.fortomorrow.kheloNowAdmin.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.ShowParticipantActivity;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_participant_list_adapter;
import code.fortomorrow.kheloNowAdmin.Model.ShowParticipant.M;
import code.fortomorrow.kheloNowAdmin.R;

public class ShowParticipantAdapter extends RecyclerView.Adapter<ShowParticipantAdapter.Viewholder> {
    private List<M> showParticipant;
    private Context context;
    private ShowParticipantActivity showParticipantActivity;

    public ShowParticipantAdapter(ShowParticipantActivity showParticipantActivity, List<M> m, Context applicationContext) {
        this.showParticipant = m;
        this.context = applicationContext;
        this.showParticipantActivity = showParticipantActivity;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_result_layer, parent, false);
        return new ShowParticipantAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.mUserId.setText("User Name : " + String.valueOf(showParticipant.get(position).getUserName()));

        if (!TextUtils.isEmpty(showParticipant.get(position).getFirstPlayer())) {
            holder.mPlayerOne.setVisibility(View.VISIBLE);
            holder.mPlayerOne.setText("Player One : " + showParticipant.get(position).getFirstPlayer());
        } else {
            holder.mPlayerOne.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(showParticipant.get(position).getSecondPlayer())) {
            holder.mPlayerTwo.setVisibility(View.VISIBLE);
            holder.mPlayerTwo.setText("Player Two : " + showParticipant.get(position).getSecondPlayer());
        } else {
            holder.mPlayerTwo.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(showParticipant.get(position).getThird_player())) {
            holder.mPlayerThree.setVisibility(View.VISIBLE);
            holder.mPlayerThree.setText("Player Three : " + showParticipant.get(position).getThird_player());
        } else {
            holder.mPlayerThree.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(showParticipant.get(position).getFourth_player())) {
            holder.mPlayerFour.setVisibility(View.VISIBLE);
            holder.mPlayerFour.setText("Player Four : " + showParticipant.get(position).getFourth_player());
        } else {
            holder.mPlayerFour.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(showParticipant.get(position).getFifthPlayer())) {
            holder.mPlayerFive.setVisibility(View.VISIBLE);
            holder.mPlayerFive.setText("Player Five : " + showParticipant.get(position).getFifthPlayer());
        } else {
            holder.mPlayerFive.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(showParticipant.get(position).getSixthPlayer())) {
            holder.mPlayerSix.setVisibility(View.VISIBLE);
            holder.mPlayerSix.setText("Player Six : " + showParticipant.get(position).getSixthPlayer());
        } else {
            holder.mPlayerSix.setVisibility(View.GONE);
        }


        holder.mWinning.setText("Winning :" + String.valueOf(showParticipant.get(position).getWinningMoney()));
        holder.mRank.setText("Rank : " + String.valueOf(showParticipant.get(position).getRank()));

        holder.mKillNew.setText("Kill: " + String.valueOf(showParticipant.get(position).getKill()));
        holder.refundAmountText.setText("Refund : " + String.valueOf(showParticipant.get(position).getRefund_amount()));

        if (showParticipant.get(position).getHasResult()) {
            holder.blueMark.setVisibility(View.VISIBLE);
        } else {
            holder.blueMark.setVisibility(View.GONE);
        }

        /*holder.mLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showParticipantActivity.updatePlayer(showParticipant.get(position).getUserId(),
                        showParticipant.get(position).getFirstPlayer(),
                        showParticipant.get(position).getSecondPlayer(),
                        showParticipant.get(position).getThird_player(),
                        showParticipant.get(position).getFourth_player(),
                        showParticipant.get(position).getUserName());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return showParticipant.size();
    }

    private OnUserNameCopyClickListener onCopyListener;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }


    public interface OnUserNameCopyClickListener {
        void OnUserNameCopyClick(int position);
    }


    public void setOnClickListener(ShowParticipantAdapter.OnUserNameCopyClickListener onCopyListener, ShowParticipantAdapter.OnItemClickListener onItemClickListener) {
        this.onCopyListener = onCopyListener;
        this.onItemClickListener = onItemClickListener;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private CardView mLayer;
        private TextView mUserId, mPlayerOne, mPlayerTwo, mPlayerThree, mPlayerFour, mPlayerFive, mPlayerSix, mWinning, mRank, mKillNew, mUpdate;
        private ImageView blueMark;
        LinearLayout copyUserNameButton;
        TextView refundAmountText;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            mUserId = itemView.findViewById(R.id.mUserId);
            mPlayerOne = itemView.findViewById(R.id.mPlayerOne);
            mPlayerTwo = itemView.findViewById(R.id.mPlayerTwo);
            mPlayerThree = itemView.findViewById(R.id.mPlayerThree);
            mPlayerFour = itemView.findViewById(R.id.mPlayerFour);
            mPlayerFive = itemView.findViewById(R.id.mPlayerFive);
            mPlayerSix = itemView.findViewById(R.id.mPlayerSix);
            mWinning = itemView.findViewById(R.id.mWinning);
            mRank = itemView.findViewById(R.id.mRank);
            mKillNew = itemView.findViewById(R.id.mKillNew);
            mLayer = itemView.findViewById(R.id.mLayer);
            mUpdate = itemView.findViewById(R.id.mUdateStatus);
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
