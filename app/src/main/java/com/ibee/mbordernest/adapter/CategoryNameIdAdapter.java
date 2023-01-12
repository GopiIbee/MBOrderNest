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
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CategoryNameIdAdapter extends RecyclerView.Adapter<CategoryNameIdAdapter.ViewHolder> {


    List<SubCategoriesNameIdModelClass> subCategoriesNameIdModelClass;
    SubCategoryNameIdAdapter subCategoryNameIdAdapter;

    private final List<CategoriesNameIdModelClass> pressModels;
    Context context;
    int row_index = 0;
    int OrderId;

    JSONArray subCategories = new JSONArray();
//    private RecyclerView sub_categories;

    public CategoryNameIdAdapter(Context context, List<CategoriesNameIdModelClass> pressModels, JSONArray subCategories) {
        this.context = context;
        this.pressModels = pressModels;
        this.subCategories = subCategories;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.home_dinign_list_items3, parent, false);

        return new ViewHolder(view);
    }


    public void getOrderId() {
        OrderId = ((OrderAndBill) context).updatedOrderId();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final CategoriesNameIdModelClass menuModel = pressModels.get(position);


        holder.table_name.setText(menuModel.getTable_name());

        updateSubCategory(menuModel.getId(), holder.sub_categories);

        if (position == row_index) {

            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.icon_color_blue5));
            getOrderId();
            if (holder.sub_categories.getVisibility() == View.GONE) {
                holder.sub_categories.setVisibility(View.VISIBLE);

            } else {
                holder.sub_categories.setVisibility(View.GONE);
            }


        } else {
            holder.sub_categories.setVisibility(View.GONE);

            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.icon_color_blue5));


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

    private void updateSubCategory(int id, RecyclerView recyclerView) {
        subCategoriesNameIdModelClass = new ArrayList<>();
        SubCategoriesNameIdModelClass navigationMenuModel = null;
        for (int i = 0; i < subCategories.length(); i++) {

            try {
                if (subCategories.getJSONObject(i).getInt("categoryId") == id) {
                    navigationMenuModel = new SubCategoriesNameIdModelClass(subCategories.getJSONObject(i).getString("subCategoryName"),
                            subCategories.getJSONObject(i).getInt("subCategoryId"));
                    subCategoriesNameIdModelClass.add(navigationMenuModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        subCategoryNameIdAdapter = new SubCategoryNameIdAdapter(context, subCategoriesNameIdModelClass);
        recyclerView.setAdapter(subCategoryNameIdAdapter);
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
        RecyclerView sub_categories;


        public ViewHolder(View itemView) {
            super(itemView);


            table_name = (TextView) itemView.findViewById(R.id.list1);
            sub_categories = (RecyclerView) itemView.findViewById(R.id.sub_categories);
            sub_categories.setVisibility(View.GONE);
        }
    }


}



