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
import com.ibee.mbordernest.model.SwapTableModelClass;

import java.util.List;

public class SwapTableAdapter extends RecyclerView.Adapter<SwapTableAdapter.ViewHolder> {
    private List<SwapTableModelClass> pressModels;
    Context context;
    private SharedPreferences sharedPreferences;
    String User_key = "";
    String API_TOKEN = "";

    public SwapTableAdapter(Context context, List<SwapTableModelClass> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.table_list, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        User_key = sharedPreferences.getString("StoreKey", "");
        API_TOKEN = sharedPreferences.getString("accesskey", "");

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SwapTableModelClass menuModel = pressModels.get(position);


        holder.table_name.setText(menuModel.getTable_name());

        holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.table_name.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderAndBill) context).SwapTableMethod(menuModel.getId(),menuModel.getPOStableId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_name;


        public ViewHolder(View itemView) {
            super(itemView);
            table_name = (TextView) itemView.findViewById(R.id.list1);
        }
    }

}



