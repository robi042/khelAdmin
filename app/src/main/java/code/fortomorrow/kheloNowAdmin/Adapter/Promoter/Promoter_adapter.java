package code.fortomorrow.kheloNowAdmin.Adapter.Promoter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Adapter.ArenaValor.Arena_valor_match_adapter;
import code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin.Sub_admin_adapter;
import code.fortomorrow.kheloNowAdmin.Model.Promoter.Promoter_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Promoter_adapter extends RecyclerView.Adapter<Promoter_adapter.ViewHolder> {
    private List<Promoter_response.M> promoterList;

    public Promoter_adapter(List<Promoter_response.M> promoterList) {
        this.promoterList = promoterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.promoter_card, parent, false);
        return new Promoter_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promoter_response.M response = promoterList.get(position);
        holder.userNameText.setText(response.userName);
        holder.nameText.setText("Name: "+response.name);
        holder.phoneText.setText("Phone: "+response.phone);
        holder.statusText.setText("Status: "+response.status);
        holder.UserIDText.setText("User ID: "+String.valueOf(response.promoter_id));
    }

    @Override
    public int getItemCount() {
        return promoterList.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Promoter_adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, statusText, phoneText, nameText,UserIDText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.userNameTextID);
            nameText = itemView.findViewById(R.id.nameTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);
            statusText = itemView.findViewById(R.id.statusTextID);
            UserIDText = itemView.findViewById(R.id.UserIDTextID);
            
            itemView.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
