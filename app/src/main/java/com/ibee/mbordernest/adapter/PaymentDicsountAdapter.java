package com.ibee.mbordernest.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.OrderAndBill;
import com.ibee.mbordernest.model.PaymentDicsountModel;

import java.util.List;

public class PaymentDicsountAdapter extends RecyclerView.Adapter<PaymentDicsountAdapter.ViewHolder> {
    private List<PaymentDicsountModel> pressModels;
    Context context;
    String from;
    private SharedPreferences sharedPreferences;
    String Currency;


    public PaymentDicsountAdapter(Context context, List<PaymentDicsountModel> pressModels, String from) {
        this.context = context;
        this.pressModels = pressModels;
        this.from = from;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.paymet_discount_list_items, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Currency = sharedPreferences.getString("Currency", "");

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PaymentDicsountModel menuModel = pressModels.get(position);
        if (from.equals("discount")) {
            holder.title.setText(menuModel.getNameOrCode());
            holder.discount.setText(menuModel.getDiscountType());
        } else if (from.equals("payment")) {
            holder.title.setText(menuModel.getNameOrCode());
            holder.discount.setText(Currency + " " + menuModel.getAmount());

        } else {
            holder.title.setText(menuModel.getNameOrCode());
            holder.discount.setText(Currency + " " + menuModel.getAmount());

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("discount")) {
                    ((OrderAndBill) context).callDiscountRequest(menuModel.getDiscountType());

                } else if (from.equals("payment")) {
                   /* if (menuModel.getNameOrCode().equals("NC")) {
                        ((OrderAndBill) context).openDialogComment("reason for NC","NC_Order");

                    } else */{
                        ((OrderAndBill) context).callPaymentRequest(menuModel.getId(), menuModel.getNameOrCode());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, discount;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            discount = (TextView) itemView.findViewById(R.id.discount);

        }
    }

}



