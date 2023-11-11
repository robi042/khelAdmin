package code.fortomorrow.kheloNowAdmin.Adapter.FreeFire;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.ResultActivity;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.M;
import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_result_adapter extends RecyclerView.Adapter<Free_fire_result_adapter.ViewHolder> {
    private Context context;
    private List<M> ongoingList;
    private ResultActivity allCreatedMatches;

    public Free_fire_result_adapter(ResultActivity allCreatedMatches, Context context, List<M> ongoingList) {
        this.context = context;
        this.ongoingList = ongoingList;
        this.allCreatedMatches = allCreatedMatches;
    }

    @NonNull
    @Override
    public Free_fire_result_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_fire_result_match_list_layer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Free_fire_result_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mTitle.setText(ongoingList.get(position).getTitle());
        holder.mTime.setText(ongoingList.get(position).getMatchTime());
        holder.mTotalPrize.setText(ongoingList.get(position).getTotalPrize());
        holder.mPerKill.setText(ongoingList.get(position).getPer_kill_rate());
        holder.mEntryFee.setText(ongoingList.get(position).getEntryFee());
        holder.mType.setText(ongoingList.get(position).getGameType());
        holder.mVersion.setText(String.valueOf(ongoingList.get(position).getVersion()));
        holder.mMap.setText(String.valueOf(ongoingList.get(position).getMap()));
        holder.roomCodeLayout.setVisibility(View.GONE);

        if (TextUtils.isEmpty(ongoingList.get(position).getNote())) {
            holder.noteLayout.setVisibility(View.GONE);
        } else {
            holder.noteLayout.setVisibility(View.VISIBLE);
            holder.noteText.setText(ongoingList.get(position).getNote());
        }

        if (ongoingList.get(position).getResult_done()) {
            holder.resultDoneLayout.setVisibility(View.VISIBLE);
        } else {
            holder.resultDoneLayout.setVisibility(View.GONE);
        }

        holder.roomCodeTimeText.setText("Room ID Pass added at " + ongoingList.get(position).room_update_time);

        holder.mLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allCreatedMatches.ShowList(ongoingList.get(position).getMatchId(), ongoingList.get(position).getNote());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ongoingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mTime, mTotalPrize, mType, mPerKill, mVersion, mEntryFee, mMap, mSpotLeft, mTotalPlayer;
        private LinearLayout mLayer, roomCodeLayout;
        LinearLayout noteLayout, resultDoneLayout;
        TextView noteText, roomIDText, roomCodeTimeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.mTitle);
            mTime = itemView.findViewById(R.id.mTime);
            mTotalPrize = itemView.findViewById(R.id.mTotalPrize);
            mType = itemView.findViewById(R.id.mType);
            mPerKill = itemView.findViewById(R.id.mPerKill);
            mEntryFee = itemView.findViewById(R.id.mEntryFee);
            mMap = itemView.findViewById(R.id.mMap);
            mTotalPlayer = itemView.findViewById(R.id.mTotalPlayer);
            mSpotLeft = itemView.findViewById(R.id.mSpotLeft);
            mVersion = itemView.findViewById(R.id.mVersion);
            mLayer = itemView.findViewById(R.id.mLayer);
            roomCodeLayout = itemView.findViewById(R.id.roomCodeLayoutID);
            noteLayout = itemView.findViewById(R.id.noteLayoutID);
            noteText = itemView.findViewById(R.id.noteTextId);
            resultDoneLayout = itemView.findViewById(R.id.resultDoneLayoutID);
            roomIDText = itemView.findViewById(R.id.roomIDTextID);
            roomCodeTimeText = itemView.findViewById(R.id.roomCodeTimeText);
        }
    }
}
