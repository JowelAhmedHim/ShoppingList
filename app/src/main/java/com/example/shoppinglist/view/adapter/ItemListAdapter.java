package com.example.shoppinglist.view.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.service.localDb.entity.Items;
import com.google.android.material.transition.Hold;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder>  {

    private Context context;
    private List<Items> itemsList;
    private HandleItemClick itemClick;

    public ItemListAdapter(Context context,HandleItemClick mItemClick) {
        this.context = context;
        this.itemClick = mItemClick;
    }

    public void setItemList(List<Items> mItemsList){
        this.itemsList = mItemsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category_layout,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.categoryName.setText(itemsList.get(position).itemName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClick(itemsList.get(position));
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.editItem(itemsList.get(position));
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.removeItem(itemsList.get(position));
            }
        });

        if (itemsList.get(position).completed){
            holder.categoryName.setPaintFlags(holder.categoryName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.categoryName.setPaintFlags(0);
        }
    }

    @Override
    public int getItemCount() {
        if (itemsList == null){
            return 0;
        }else {
            return itemsList.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        ImageButton deleteBtn;
        ImageButton editBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.categoryTv);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editBtn = itemView.findViewById(R.id.editBtn);

        }


    }

    public interface HandleItemClick{

         void itemClick(Items items);
         void removeItem(Items items);
         void editItem(Items items);

    }
}
