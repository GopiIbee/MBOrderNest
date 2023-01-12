package com.ibee.mbordernest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.HomeScreenActivity;
import com.ibee.mbordernest.activitys.OrderAndBill;
import com.ibee.mbordernest.model.HomeLeftModel;
import com.ibee.mbordernest.model.SwapLeftModel;

import java.util.List;

public class SwapLeftAdapter extends RecyclerView.Adapter<SwapLeftAdapter.ViewHolder> {
    private final List<SwapLeftModel> pressModels;
    Context context;
    int row_index = 0;

    public SwapLeftAdapter(Context context, List<SwapLeftModel> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.home_dinign_list_items, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final SwapLeftModel menuModel = pressModels.get(position);


        holder.table_name.setText(menuModel.getTable_name());
        holder.count.setText(menuModel.getCount());


        if (position == row_index) {
            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.icon_color_blue));
            holder.count.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.count.setBackgroundColor(context.getResources().getColor(R.color.icon_color_blue));


        } else {
            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.count.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.count.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                ((OrderAndBill) context).swapnavigation(menuModel.getId(), menuModel.getTable_name(), "1");
//                if (position != 0) {
                    notifyDataSetChanged();
//                }
//                ((OrderAndBill) context).swapnavigation(menuModel.getId(), menuModel.getTable_name());
            }
        });

    }


    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_name, count;


        public ViewHolder(View itemView) {
            super(itemView);
            table_name = (TextView) itemView.findViewById(R.id.list1);
            count = (TextView) itemView.findViewById(R.id.count);

        }
    }

}



