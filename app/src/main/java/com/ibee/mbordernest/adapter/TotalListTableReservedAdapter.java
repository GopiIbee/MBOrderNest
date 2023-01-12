package com.ibee.mbordernest.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.activitys.GuestInfoActivity;
import com.ibee.mbordernest.activitys.HomeScreenActivity;
import com.ibee.mbordernest.activitys.LoginActivity;
import com.ibee.mbordernest.activitys.OrderAndBill;
import com.ibee.mbordernest.model.TotalListTableModelClass;
import com.ibee.mbordernest.network.CheckNetworkConnection;
import com.ibee.mbordernest.parserclass.ServiceHandler;
import com.ibee.mbordernest.utils.Constants;
import com.ibee.mbordernest.utils.ShowToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class TotalListTableReservedAdapter extends RecyclerView.Adapter<TotalListTableReservedAdapter.ViewHolder> {
    private List<TotalListTableModelClass> pressModels;
    Context context;

    static final String[] numbers = new String[]{
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", ".", "0", "%", "<", "C", "OK"};
    AlertDialog dialogValidatePin;
    private SharedPreferences sharedPreferences;
    String User_key = "";
    String API_TOKEN = "";
    String orderTypeId = "",
            orderTypeName = "",
            table_id = "",
            table_name = "", StorderId = "";

    public TotalListTableReservedAdapter(Context context, List<TotalListTableModelClass> pressModels) {
        this.context = context;
        this.pressModels = pressModels;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.table_list_reserved, parent, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        User_key = sharedPreferences.getString("StoreKey", "");
        API_TOKEN = sharedPreferences.getString("accesskey", "");

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TotalListTableModelClass menuModel = pressModels.get(position);


        if (!menuModel.getCustomer().equals("") || !menuModel.getCustomer().isEmpty()) {
            holder.cu_name.setText(menuModel.getCustomer());

        }
        holder.table_name.setText(menuModel.getTable_name());

        if (menuModel.getIsopen().equals("true")) {
            if (menuModel.getSentToBilling()) {
                holder.image_bg.setBackgroundResource(R.drawable.green_table_new);
                holder.amount.setText("\u20B9 " + menuModel.getAmount());

                holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                holder.image_bg.setBackgroundResource(R.drawable.red_table_new);

                holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                holder.amount.setText("\u20B9 " + menuModel.getAmount());

            }

        }
        else if (menuModel.getIsopen().equals("freeze")) {
            holder.image_bg.setBackgroundResource(R.drawable.red_table_new);

            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.amount.setText("\u20B9 " + menuModel.getAmount());

        }
        else {
            holder.image_bg.setBackgroundResource(R.drawable.grey_table_new);

            holder.table_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }
        holder.itemView.setOnClickListener(v -> {
            ((HomeScreenActivity) context).CallToOpenTable(position);

        });

    }


    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView table_name, cu_name, amount;

        ImageView image_bg;


        public ViewHolder(View itemView) {
            super(itemView);
            table_name = (TextView) itemView.findViewById(R.id.list1);
            cu_name = (TextView) itemView.findViewById(R.id.cu_name);
            amount = (TextView) itemView.findViewById(R.id.amount);
            image_bg = (ImageView) itemView.findViewById(R.id.image_bg);

        }
    }

}



