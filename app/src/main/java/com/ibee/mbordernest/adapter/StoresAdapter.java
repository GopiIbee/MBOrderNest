package com.ibee.mbordernest.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.HomeScreenActivity;
import com.ibee.mbordernest.model.StoreChangeModelClass;

import java.util.List;


public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {
    private List<StoreChangeModelClass> pressModels;
    Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor mEditorObj;
    String loginUserId = "";
//    LinearLayout llhome;
    AlertDialog dailog;

//    TextView store_title;

    public StoresAdapter(Context context, List<StoreChangeModelClass> pressModels, AlertDialog dailog) {
        this.context = context;
        this.pressModels = pressModels;
        this.dailog = dailog;
//        this.llhome = llhome;
//        this.store_title = store_title;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.home_dinign_list_items2, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditorObj = sharedPreferences.edit();

        loginUserId = sharedPreferences.getString("User_Id", "");

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StoreChangeModelClass menuModel = pressModels.get(position);


        holder.table_name.setText(menuModel.getStore_name());

        holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditorObj.putString("StoreId", menuModel.getStore_id()).commit();
                mEditorObj.putString("StoreName", menuModel.getStore_name()).commit();
                mEditorObj.putString("StoreKey", menuModel.getStoreKey()).commit();
                mEditorObj.putString("Currency", menuModel.getCurrency()).commit();
                mEditorObj.putString("ShowImages", menuModel.getShowImages()).commit();
                ((HomeScreenActivity) context).GetOrderUser(menuModel.getStoreKey(), loginUserId);

//                store_title.setText(menuModel.getStore_name());




                dailog.dismiss();
//                llhome.setVisibility(View.VISIBLE);

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



