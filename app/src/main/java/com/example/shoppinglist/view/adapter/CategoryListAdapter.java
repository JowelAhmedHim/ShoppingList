package com.example.shoppinglist.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.service.localDb.entity.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick categoryClick;

    public CategoryListAdapter(Context context, HandleCategoryClick categoryClick)
    {
        this.context = context;
        this.categoryClick = categoryClick;
    }
    public void setCategoryList(List<Category> categoryList){
        this.categoryList = categoryList;
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

        holder.categoryName.setText(categoryList.get(position).categoryName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.itemClick(categoryList.get(position));
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.editItem(categoryList.get(position));
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.removeItem(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categoryList == null)
        {
            return 0;
        }else {
            return categoryList.size();
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

    public interface HandleCategoryClick{
        void itemClick(Category category);
        void removeItem(Category category);
        void editItem(Category category);
    }
}
