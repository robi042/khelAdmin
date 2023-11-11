package code.fortomorrow.kheloNowAdmin.Adapter.SubAdmin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.SubAdmin.Sub_admin_response;
import code.fortomorrow.kheloNowAdmin.R;

public class Sub_admin_adapter extends RecyclerView.Adapter<Sub_admin_adapter.ViewHolder> {
    private List<Sub_admin_response.M> subAdminList;

    public Sub_admin_adapter(List<Sub_admin_response.M> subAdminList) {
        this.subAdminList = subAdminList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_admin_card, parent, false);
        return new Sub_admin_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Sub_admin_response.M response = subAdminList.get(position);
        holder.userNameText.setText(response.getUserName());
        holder.statusText.setText(response.getStatus());
        if (response.getStatus().equals("active")) {
            holder.controlButton.setText("Inactive");
            holder.statusText.setTextColor(Color.BLUE);
        } else if (response.getStatus().equals("inactive")) {
            holder.statusText.setTextColor(Color.RED);
            holder.controlButton.setText("Active");
        }
    }

    @Override
    public int getItemCount() {
        return subAdminList.size();
    }

    private OnItemControlClickListener onControlClickListener;
    private OnItemClickListener onClickListener;


    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public interface OnItemControlClickListener {
        void OnItemControlClick(int position);
    }


    public void setOnClickListener(Sub_admin_adapter.OnItemControlClickListener onControlClickListener, Sub_admin_adapter.OnItemClickListener onItemClickListener) {
        this.onControlClickListener = onControlClickListener;
        this.onClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, statusText;
        AppCompatButton controlButton;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.userNameTextID);
            statusText = itemView.findViewById(R.id.statusTextID);
            controlButton = itemView.findViewById(R.id.controlButtonID);

            mainLayout = itemView.findViewById(R.id.mainLayoutID);

            mainLayout.setOnClickListener(new View.OnClickListener() {
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

            controlButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onControlClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onControlClickListener.OnItemControlClick(position);
                        }
                    }
                }
            });
        }
    }
}
