package code.fortomorrow.kheloNowAdmin.Adapter.FreeFire;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.AllCreatedMatches;
import code.fortomorrow.kheloNowAdmin.Model.Ongoing.M;
import code.fortomorrow.kheloNowAdmin.R;

public class Free_fire_game_adapter extends RecyclerView.Adapter<Free_fire_game_adapter.ViewHolder> {
    private Context context;
    private List<M> ongoingList;
    private AllCreatedMatches allCreatedMatches;

    public Free_fire_game_adapter(AllCreatedMatches allCreatedMatches, Context context, List<M> ongoingList) {
        this.context = context;
        this.ongoingList = ongoingList;
        this.allCreatedMatches = allCreatedMatches;
    }

    @NonNull
    @Override
    public Free_fire_game_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_fire_daily_match_list_layer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Free_fire_game_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mTitle.setText(ongoingList.get(position).getTitle());
        holder.mTime.setText(ongoingList.get(position).getMatchTime());
        holder.mTotalPrize.setText(ongoingList.get(position).getTotalPrize());
        holder.mPerKill.setText(ongoingList.get(position).getPer_kill_rate());
        holder.mEntryFee.setText(ongoingList.get(position).getEntryFee());
        holder.mType.setText(ongoingList.get(position).getGameType());
        holder.mVersion.setText(String.valueOf(ongoingList.get(position).getVersion()));
        holder.mMap.setText(String.valueOf(ongoingList.get(position).getMap()));
        holder.roomIDText.setText(ongoingList.get(position).getRoom_id());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allCreatedMatches.moveToOnoging(ongoingList.get(position).getMatchId(), ongoingList.get(position).getMatchTime(),
                        ongoingList.get(position).getEntryFee(), ongoingList.get(position).getMap(), ongoingList.get(position).getPer_kill_rate(),
                        ongoingList.get(position).getTitle(), ongoingList.get(position).getTotalPrize(), ongoingList.get(position).getFirstPrize(),
                        ongoingList.get(position).getSecondPrize(), ongoingList.get(position).getThirdPrize(), ongoingList.get(position).getTotalPlayer()
                );
            }
        });

        holder.selectMatchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    allCreatedMatches.add_match_in_the_list(String.valueOf(ongoingList.get(position).getMatchId()));
                } else {
                    allCreatedMatches.remove_match_from_the_list(String.valueOf(ongoingList.get(position).getMatchId()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ongoingList.size();
    }

    private OnNotifyItemClickListener onNotifyClickListener;
    private OnSelectMatchClickListener onSelectMatchClickListener;

    public interface OnSelectMatchClickListener {
        void OnFreeFireSelectItemClick(int position);
    }


    public interface OnNotifyItemClickListener {
        void OnNotifyItemClick(int position);
    }


    public void setOnClickListener(OnNotifyItemClickListener onNotifyClickListener) {
        this.onNotifyClickListener = onNotifyClickListener;
        //this.onSelectMatchClickListener = onSelectMatchClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mTime, mTotalPrize, mType, mPerKill, mVersion, mEntryFee, mMap, mSpotLeft, mTotalPlayer, roomIDText;
        private LinearLayout mainLayout;
        ImageView notifyPeopleButton;
        LinearLayout noteLayout;
        CheckBox selectMatchCheckBox;

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
            mainLayout = itemView.findViewById(R.id.mainLayoutID);
            roomIDText = itemView.findViewById(R.id.roomIDTextID);
            notifyPeopleButton = itemView.findViewById(R.id.notifyPeopleButtonID);
            selectMatchCheckBox = itemView.findViewById(R.id.selectMatchCheckBoxID);
            noteLayout = itemView.findViewById(R.id.noteLayoutID);
            noteLayout.setVisibility(View.GONE);

            notifyPeopleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNotifyClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onNotifyClickListener.OnNotifyItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
