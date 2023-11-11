package code.fortomorrow.kheloNowAdmin.Adapter.AddMoney;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import code.fortomorrow.kheloNowAdmin.Model.AddMoneyList.Add_money_accepted_list_response;
import code.fortomorrow.kheloNowAdmin.R;
import es.dmoral.toasty.Toasty;

public class Add_money_accepted_list_adapter extends RecyclerView.Adapter<Add_money_accepted_list_adapter.ViewHolder> {

    private List<Add_money_accepted_list_response.M> dataList;

    public Add_money_accepted_list_adapter(List<Add_money_accepted_list_response.M> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.add_money_accepted_list_card, parent, false);
        return new Add_money_accepted_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Add_money_accepted_list_response.M response = dataList.get(position);
        holder.userNameText.setText(response.getUserName());
        holder.dateTimeText.setText(response.getRequestedTime());
        holder.amountText.setText(String.valueOf(response.getAmount()));
        holder.phoneText.setText(response.getPhoneNumber());
        holder.methodText.setText(response.getPaymentMethod());
        holder.typeText.setText(response.getType());


        if (response.getStatus().equals("rejected")) {
            holder.statusText.setText(response.getStatus());
            holder.statusText.setTextColor(Color.RED);
        } else if (response.getStatus().equals("accepted")) {
            holder.statusText.setText(response.getStatus());
            holder.statusText.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, dateTimeText, amountText, phoneText, methodText, typeText, statusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.userNameTextID);
            dateTimeText = itemView.findViewById(R.id.dateTimeTextID);
            amountText = itemView.findViewById(R.id.amountTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);
            methodText = itemView.findViewById(R.id.methodTextID);
            typeText = itemView.findViewById(R.id.typeTextID);
            statusText = itemView.findViewById(R.id.statusTextID);

            userNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("user_name", userNameText.getText().toString().trim());
                    Toasty.success(itemView.getContext(), userNameText.getText().toString().trim() + " is Copied", Toasty.LENGTH_SHORT).show();
                    clipboard.setPrimaryClip(clip);
                }
            });
        }
    }
}
