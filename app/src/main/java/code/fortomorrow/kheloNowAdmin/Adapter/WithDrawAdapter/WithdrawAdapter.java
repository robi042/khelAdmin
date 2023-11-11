package code.fortomorrow.kheloNowAdmin.Adapter.WithDrawAdapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.List;

import code.fortomorrow.kheloNowAdmin.Activities.WithdrawRequestActivity;
import code.fortomorrow.kheloNowAdmin.Model.WithDraw.M;
import code.fortomorrow.kheloNowAdmin.R;


public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.ViewHolder> {

    private WithdrawRequestActivity activity;

    private Context context;
    private List<M> list;


    public WithdrawAdapter(WithdrawRequestActivity activity, Context context, List<M> list) {
        this.activity = activity;
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.withdraw_req_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        viewHolder.userNameText.setText(list.get(position).getUserName());
        viewHolder.setDate(list.get(position).getRequestedTime());
        viewHolder.setAmount(String.valueOf(list.get(position).getAmount()));
        viewHolder.setNumber(String.valueOf(list.get(position).getPhoneNumber()));
        viewHolder.setWithdraw(String.valueOf(list.get(position).getPaymentMethod()));
        viewHolder.source.setText("Withdraw");

        viewHolder.userNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.copyWithDraw(list.get(position).getUserName());
            }
        });

        viewHolder.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.copyWithDraw(String.valueOf(list.get(position).getPhoneNumber()));
            }
        });


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.deleteReq(list.get(position).getWithdrawMoneyId());

            }
        });


        viewHolder.sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                activity.sendWithDrawReq(list.get(position).getWithdrawMoneyId());


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView date, time, amount, delete, add_balance, source, number, withdraw, sent, userNameText;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            source = view.findViewById(R.id.source);
            date = view.findViewById(R.id.date);
            delete = view.findViewById(R.id.delete);
            sent = view.findViewById(R.id.sent);
            number = view.findViewById(R.id.number);
//            time = view.findViewById(R.id.time);
            withdraw = view.findViewById(R.id.withdraw);
            amount = view.findViewById(R.id.amount);
            userNameText = view.findViewById(R.id.userNameTextID);
        }

        public void setWithdraw(String id) {
            TextView tt = (TextView) view.findViewById(R.id.withdraw);
            tt.setText(id);

        }

        public void setDate(String date) {
            TextView tt = (TextView) view.findViewById(R.id.date);
            tt.setText(date);

        }


        public void setTime(String time) {
            TextView tt = (TextView) view.findViewById(R.id.time);
            tt.setText(time);

        }


        public void setNumber(String status) {
            TextView tt = (TextView) view.findViewById(R.id.number);
            tt.setText(status);

        }


        public void setAmount(String amount) {
            TextView tt = (TextView) view.findViewById(R.id.amount);
            tt.setText("৳ " + amount);

        }


    }


}