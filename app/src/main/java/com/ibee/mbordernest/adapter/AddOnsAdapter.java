package com.ibee.mbordernest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ibee.mbordernest.R;
import com.ibee.mbordernest.model.AddOnsModel;

import java.util.List;


public class AddOnsAdapter extends RecyclerView.Adapter<AddOnsAdapter.ViewHolder> {
    private List<AddOnsModel> pressModels;
    Context context;
    ProductsNameIdAdapter productsNameIdAdapter;//=new ProductsNameIdAdapter(); //PeopleAdapter7 instadded of NameIdAdapter2

    String seleted_items = "";

    public AddOnsAdapter(List<AddOnsModel> pressModels, Context context, Button mDilgActvytBtn) {
        this.context = context;
        this.pressModels = pressModels;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.addons_list_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AddOnsModel menuModel = pressModels.get(position);
        holder.title.setText(menuModel.getName());
        holder.price.setText(menuModel.getPrice());

        if (holder.checkbox.isChecked()) {

            if (seleted_items.isEmpty()) {
                seleted_items = menuModel.getAddOnId();
            } else {
                seleted_items = seleted_items + "," + menuModel.getAddOnId();
            }
        }
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleted_items = "";
                notifyDataSetChanged();
            }
        });


    }

    public String getSelectedItem() {
//        ShowToast.toastMessage(context, seleted_items);

        return seleted_items;
    }

    @Override
    public int getItemCount() {
        return pressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

}



