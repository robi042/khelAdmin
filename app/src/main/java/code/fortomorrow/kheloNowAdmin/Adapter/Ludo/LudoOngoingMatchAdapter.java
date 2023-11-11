package code.fortomorrow.kheloNowAdmin.Adapter.Ludo;

import android.content.Context;
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

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.OnGoingActivity;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_game_list_response;
import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_ongoing_response;
import code.fortomorrow.kheloNowAdmin.R;

public class LudoOngoingMatchAdapter extends RecyclerView.Adapter<LudoOngoingMatchAdapter.ViewHolder> {

    OnGoingActivity onGoingActivity;
    Context applicationContext;
    private List<Ludo_ongoing_response.M> ludoMatchList;

    public LudoOngoingMatchAdapter(OnGoingActivity onGoingActivity, Context applicationContext, List<Ludo_ongoing_response.M> ludoMatchList) {
        this.onGoingActivity = onGoingActivity;
        this.applicationContext = applicationContext;
        this.ludoMatchList = ludoMatchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ludo_game_ongoing_card, parent, false);
        return new LudoOngoingMatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_ongoing_response.M response = ludoMatchList.get(position);
        holder.gameTitleText.setText(response.getTitle());
        holder.dateTimeText.setText(response.getDate() + " " + response.getTime());
        holder.prizeText.setText(response.getTotalPrize());
        holder.feeText.setText(response.getEntryFee());
        holder.joiningTypeText.setText("Single");
        holder.totalPlayerText.setText(response.getTotalPlayer());
        holder.gameTypeText.setText(response.getHostApp());
        holder.ongoingByText.setText("Ongoing By " + response.ongoing_by);
        //holder.imageCountText.setText(String.valueOf(response.getTotal_image_upload()));
        if (response.getTotal_image_upload() > 0) {
            holder.showImageButton.setVisibility(View.VISIBLE);
        } else {
            holder.showImageButton.setVisibility(View.GONE);
        }

        /*try {
            if (response.getHasImage()) {
                holder.showImageButton.setVisibility(View.VISIBLE);
                Picasso.get().load(response.getImageLink()).fit().memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.showImageButton);
                //Glide.with().load(downloadUri).into(blogImageView);
            }
        } catch (Exception e) {
            holder.showImageButton.setVisibility(View.GONE);
        }*/

        try {
            if (response.getHasRoomcode()) {
                holder.roomCodeText.setText(response.getRoomCode());
            }
        } catch (Exception e) {

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
        return ludoMatchList.size();
    }

    private OnItemClickListener onClickListener;
    private OnShowImageClickListener onShowImageListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnShowImageClickListener {
        void OnImageClick(int position);
    }

    public void setOnClickListener(LudoOngoingMatchAdapter.OnItemClickListener onClickListener, LudoOngoingMatchAdapter.OnShowImageClickListener onShowImageListener) {
        this.onClickListener = onClickListener;
        this.onShowImageListener = onShowImageListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameTitleText, dateTimeText, prizeText, feeText, joiningTypeText, gameTypeText, totalPlayerText, roomCodeText;
        CardView deleteButton;
        ImageView showImageButton;

        LinearLayout noteLayout;
        TextView noteText, imageCountText, ongoingByText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gameTitleText = itemView.findViewById(R.id.gameTitleTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            prizeText = itemView.findViewById(R.id.prizeTextID);
            feeText = itemView.findViewById(R.id.feeTextID);
            joiningTypeText = itemView.findViewById(R.id.joiningTypeTextID);
            gameTypeText = itemView.findViewById(R.id.gameTypeTextID);
            roomCodeText = itemView.findViewById(R.id.roomCodeTextID);
            totalPlayerText = itemView.findViewById(R.id.totalPlayerTextID);
            showImageButton = itemView.findViewById(R.id.showImageButtonID);
            noteLayout = itemView.findViewById(R.id.noteLayoutID);
            noteText = itemView.findViewById(R.id.noteTextId);
            imageCountText = itemView.findViewById(R.id.imageCountTextID);
            ongoingByText = itemView.findViewById(R.id.ongoingByText);

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
                    if (onShowImageListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onShowImageListener.OnImageClick(position);
                        }
                    }
                }
            });

            //deleteButton.setVisibility(View.GONE);
        }
    }
}
