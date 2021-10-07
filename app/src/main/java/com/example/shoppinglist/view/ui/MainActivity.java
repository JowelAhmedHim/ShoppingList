package com.example.shoppinglist.view.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.service.localDb.entity.Category;
import com.example.shoppinglist.view.adapter.CategoryListAdapter;
import com.example.shoppinglist.viewModel.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryListAdapter.HandleCategoryClick {

    private FloatingActionButton fab;
    private MainActivityViewModel viewModel;

    private TextView emptyState;
    private RecyclerView recyclerView;
    private CategoryListAdapter adapter;

    private Category categoryForEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Shopping List");


        emptyState = findViewById(R.id.emptyState);
        recyclerView = findViewById(R.id.recyclerview);
        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCategoryDialog(false);
            }
        });
        initViewModel();
        initRecyclerView();
        viewModel.getAllCategoryList();

    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        adapter = new CategoryListAdapter(this,this);
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getCategoryListObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories == null){
                    emptyState.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                }else{
                    emptyState.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setCategoryList(categories);
                }

            }
        });


    }

    private void showAddCategoryDialog(boolean isForEdit) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        View dialogView =  getLayoutInflater().inflate(R.layout.add_category_layout,null);
        EditText categoryEt = dialogView.findViewById(R.id.categoryEt);
        Button createBtn = dialogView.findViewById(R.id.createBtn);
        Button cancelBtn = dialogView.findViewById(R.id.cancelBtn);
        if (isForEdit){
            createBtn.setText("Update");
            categoryEt.setText(categoryForEdit.categoryName);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryName = categoryEt.getText().toString();
                if (TextUtils.isEmpty(categoryName))
                {
                    Toast.makeText(getApplicationContext(), "Enter Category Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isForEdit){
                    categoryForEdit.categoryName = categoryName;
                    viewModel.updateCategory(categoryForEdit);

                }else {
                    viewModel.insertCategory(categoryName);
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(dialogView);
        alertDialog.show();


    }

    @Override
    public void itemClick(Category category) {

        Intent intent = new Intent(this,ItemActivity.class);
        intent.putExtra("uid",category.uid);
        intent.putExtra("categoryName",category.categoryName);
        startActivity(intent);

    }

    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);
    }

    @Override
    public void editItem(Category category) {
        this.categoryForEdit = category;
        showAddCategoryDialog(true);
    }
}