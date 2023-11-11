package code.fortomorrow.kheloNowAdmin.Adapter.Ludo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.Ludo.Ludo_uploaded_image_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Ludo_uploaded_images_adapter extends RecyclerView.Adapter<Ludo_uploaded_images_adapter.ViewHolder>{
    private List<Ludo_uploaded_image_response.M> dataList;

    public Ludo_uploaded_images_adapter(List<Ludo_uploaded_image_response.M> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ludo_uploaded_images_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ludo_uploaded_image_response.M response = dataList.get(position);

        holder.nameText.setText(response.getUploadedBy());
        Picasso.get().load(response.getImageLink()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.nameTextID);
            imageView = itemView.findViewById(R.id.imageViewID);

            imageView.setOnClickListener(new View.OnClickListener() {
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
