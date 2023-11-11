package code.fortomorrow.kheloNowAdmin.Adapter.Slider;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Slider.Slider_list_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Slider_list_adapter extends RecyclerView.Adapter<Slider_list_adapter.ViewHolder> {
    private List<Slider_list_response.M> sliderList;

    public Slider_list_adapter(List<Slider_list_response.M> sliderList) {
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_list_card, parent, false);
        return new Slider_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Slider_list_response.M response = sliderList.get(position);

        holder.titleText.setText(response.getTitle());
        holder.linkText.setText(response.getLink());
        holder.statusText.setText(response.getStatus());

        if (response.getStatus().equals("active")) {
            holder.statusText.setTextColor(Color.BLUE);
            holder.activeInactiveButton.setText("Inactive");
        } else if (response.getStatus().equals("inactive")) {
            holder.statusText.setTextColor(Color.RED);
            holder.activeInactiveButton.setText("Active");
        }

        Picasso.get().load(response.getImageLink()).into(holder.sliderImage);
    }

    @Override
    public int getItemCount() {
        return sliderList.size();
    }

    private OnItemClickListener onClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Slider_list_adapter.OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView sliderImage;
        TextView titleText, linkText, statusText;
        AppCompatButton activeInactiveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sliderImage = itemView.findViewById(R.id.sliderImageID);
            titleText = itemView.findViewById(R.id.titleTextID);
            linkText = itemView.findViewById(R.id.linkTextID);
            statusText = itemView.findViewById(R.id.statusTextID);
            activeInactiveButton = itemView.findViewById(R.id.activeInactiveButtonID);

            activeInactiveButton.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
