package code.fortomorrow.kheloNowAdmin.Adapter.BuySell;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.AddMoney.Add_money_accepted_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.JoinedPlayer.Joined_player_list_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.Ludo.Ludo_match_result_adapter;
import code.fortomorrow.kheloNowAdmin.Model.BuySell.Buy_sell_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Buy_sell_adapter extends RecyclerView.Adapter<Buy_sell_adapter.ViewHolder> {
    private List<Buy_sell_response.M> buySellItemList;

    public Buy_sell_adapter(List<Buy_sell_response.M> buySellItemList) {
        this.buySellItemList = buySellItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.buy_sell_item_card, parent, false);
        return new Buy_sell_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buy_sell_response.M response = buySellItemList.get(position);

        holder.nameText.setText(response.title);
        holder.priceText.setText("Price: "+String.valueOf(response.price));
        holder.descriptionText.setText("Description: " + response.description);


        try {
            if (response.getHasDiscount()) {
                holder.priceText.setText(holder.priceText.getText().toString(), TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) holder.priceText.getText();
                spannable.setSpan(new StrikethroughSpan(), 0, holder.priceText.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                //holder.priceText.setPaintFlags(holder.nameText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.discountLayout.setVisibility(View.VISIBLE);
                holder.discountPriceText.setText(String.valueOf(response.discountedPrice));
                holder.discountPriceText.setTextColor(Color.RED);
            }
        } catch (Exception e) {

        }

        Picasso.get().load(response.image).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return buySellItemList.size();
    }

    private OnItemClickListener onItemClickListener;
    private OnDeleteItemClickListener onDeleteItemClickListener;


    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnDeleteItemClickListener {
        void OnDeleteItemClick(int position);
    }

    public void setOnClickListener(Buy_sell_adapter.OnItemClickListener onItemClickListener, Buy_sell_adapter.OnDeleteItemClickListener onDeleteItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.onDeleteItemClickListener = onDeleteItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage, deleteButton;
        TextView nameText, priceText, discountPriceText, descriptionText;
        LinearLayout discountLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImageID);
            nameText = itemView.findViewById(R.id.nameTextID);
            priceText = itemView.findViewById(R.id.priceTextID);
            discountPriceText = itemView.findViewById(R.id.discountPriceTextID);
            descriptionText = itemView.findViewById(R.id.descriptionTextID);
            discountLayout = itemView.findViewById(R.id.discountLayoutID);
            deleteButton = itemView.findViewById(R.id.deleteButtonID);

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
