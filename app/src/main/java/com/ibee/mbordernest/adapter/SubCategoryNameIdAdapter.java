package com.ibee.mbordernest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.OrderAndBill;
import com.ibee.mbordernest.model.CategoriesNameIdModelClass;
import com.ibee.mbordernest.model.SubCategoriesNameIdModelClass;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryNameIdAdapter extends RecyclerView.Adapter<SubCategoryNameIdAdapter.ViewHolder> {


    private List<SubCategoriesNameIdModelClass> pressModels;
    Context context;
    int row_index = 0;
    int OrderId;

    public SubCategoryNameIdAdapter(Context context, List<SubCategoriesNameIdModelClass> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.home_dinign_list_new, parent, false);

        return new ViewHolder(view);
    }


    public void getOrderId() {
        OrderId = ((OrderAndBill) context).updatedOrderId();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        SubCategoriesNameIdModelClass menuModel = pressModels.get(position);


        holder.table_name.setText(menuModel.getTable_name());


        if (position == row_index) {

            holder.table_name.setTextColor(context.getResources().getColor(R.color.icon_color_blue4));
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            getOrderId();

            ((OrderAndBill) context).settingnavigation2(menuModel.getId(),"");


        } else {

            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.icon_color_blue4));


        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                {
                    notifyDataSetChanged();
                }

            }
        });

    }


   /* public void filter(final String text) {

        ((OrderAndBill) context).settingnavigation2(0);

        productsNameIdAdapter.filter(text);
    }*/


    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_name;
        LinearLayout ll1;

        public ViewHolder(View itemView) {
            super(itemView);


            table_name = (TextView) itemView.findViewById(R.id.list1);
        }
    }


}



