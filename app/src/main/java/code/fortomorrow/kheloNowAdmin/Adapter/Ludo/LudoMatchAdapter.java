package code.fortomorrow.kheloNowAdmin.Adapter.Ludo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.AllCreatedMatches;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_ongoing_response;
import code.fortomorrow.kheloNowAdmin.R;

public class LudoMatchAdapter extends RecyclerView.Adapter<LudoMatchAdapter.ViewHolder> {
    private Context context;
    private List<Ludo_ongoing_response.M> ludoMatchList;
    private AllCreatedMatches allCreatedMatches;
    CheckBox selectMatchCheckBox;
    //List<String> checkList = new ArrayList<>();

    private ArrayList<TextView> checkList = new ArrayList<TextView>();
    Boolean isSelectedAll = false;

    public LudoMatchAdapter(AllCreatedMatches allCreatedMatches, Context context, List<Ludo_ongoing_response.M> ongoingList) {
        this.context = context;
        this.ludoMatchList = ongoingList;
        this.allCreatedMatches = allCreatedMatches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ludu_game_main_card, parent, false);
        return new LudoMatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //checkList.add(holder.gameTitleText);


        Ludo_ongoing_response.M response = ludoMatchList.get(position);
        holder.gameTitleText.setText(response.getTitle());
        holder.dateTimeText.setText(response.getDate() + " " + response.getTime());
        holder.prizeText.setText(response.getTotalPrize());
        holder.feeText.setText(response.getEntryFee());
        holder.joiningTypeText.setText("Single");
        holder.totalPlayerText.setText(response.getTotalPlayer());
        holder.gameTypeText.setText(response.getHostApp());
        holder.roomCodeText.setText(response.getRoomCode());

        /*if (isSelectedAll) {
            selectMatchCheckBox.setChecked(true);
            allCreatedMatches.add_ludo_match_in_the_list(String.valueOf(response.matchId));
        } else {
            selectMatchCheckBox.setChecked(false);
            allCreatedMatches.remove_ludo_match_in_the_list(String.valueOf(response.matchId));
        }*/

        selectMatchCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    allCreatedMatches.add_match_in_the_list(String.valueOf(response.matchId));
                } else {
                    allCreatedMatches.remove_match_from_the_list(String.valueOf(response.matchId));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ludoMatchList.size();
    }

    private OnItemClickListener onClickListener;
    private OnItemDeleteListener onDeleteListener;
    private OnNotifyLudoUserListener onNotifyLudoUserListener;
    private OnSelectMatchClickListener onSelectMatchClickListener;

    public interface OnSelectMatchClickListener {
        void OnLudoSelectItemClick(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnItemDeleteListener {
        void OnDeleteClick(int position);
    }

    public interface OnNotifyLudoUserListener {
        void OnNotifyLudoClick(int position);
    }

    public void setOnClickListener(OnItemClickListener onClickListener, OnItemDeleteListener onDeleteListener, OnNotifyLudoUserListener onNotifyLudoUserListener) {
        this.onClickListener = onClickListener;
        this.onDeleteListener = onDeleteListener;
        this.onNotifyLudoUserListener = onNotifyLudoUserListener;
        //this.onSelectMatchClickListener = onSelectMatchClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, feeText, joiningTypeText, gameTypeText, totalPlayerText, roomCodeText;
        CardView deleteButton, mainLayout;
        ImageView notifyPeopleButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            feeText = itemView.findViewById(R.id.feeTextID);
            joiningTypeText = itemView.findViewById(R.id.joiningTypeTextID);
            gameTypeText = itemView.findViewById(R.id.gameTypeTextID);
            deleteButton = itemView.findViewById(R.id.deleteButtonID);
            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            roomCodeText = itemView.findViewById(R.id.roomCodeTextID);
            notifyPeopleButton = itemView.findViewById(R.id.notifyPeopleButtonID);
            mainLayout = itemView.findViewById(R.id.mainLayoutID);
            selectMatchCheckBox = itemView.findViewById(R.id.selectMatchCheckBoxID);


            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });*/

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onDeleteListener.OnDeleteClick(position);
                        }
                    }
                }
            });

            notifyPeopleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(itemView.getContext(), "he", Toast.LENGTH_SHORT).show();
                    if (onNotifyLudoUserListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onNotifyLudoUserListener.OnNotifyLudoClick(position);
                        }
                    }
                }
            });

            /*selectMatchCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSelectMatchClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onSelectMatchClickListener.OnLudoSelectItemClick(position);
                        }
                    }
                }
            });*/
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectAll() {
        /*for (int i = 0; i < checkList.size(); i++) {
//            selectMatchCheckBox = (CheckBox) checkList.get(i);
            selectMatchCheckBox.setChecked(true);

            allCreatedMatches.add_ludo_match_in_the_list(String.valueOf(ludoMatchList.get(i).matchId));
        }*/
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void unselectAll() {
        /*for (int i = 0; i < checkList.size(); i++) {
            selectMatchCheckBox.setChecked(false);
            allCreatedMatches.remove_ludo_match_in_the_list(String.valueOf(ludoMatchList.get(i).matchId));
        }*/
        isSelectedAll = false;
        notifyDataSetChanged();
    }
}
