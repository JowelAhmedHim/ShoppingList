package com.example.shoppinglist.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.R;
import com.example.shoppinglist.service.localDb.entity.Items;
import com.example.shoppinglist.view.adapter.ItemListAdapter;
import com.example.shoppinglist.viewModel.ItemViewModel;

import java.util.List;

public class ItemActivity extends AppCompatActivity implements ItemListAdapter.HandleItemClick {

    private int category_id;
    private EditText itemEt;
    private Button saveBtn;
    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private ItemViewModel itemViewModel;
    private TextView emptyState;
    private Items itemToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        category_id = getIntent().getIntExtra("uid",0);
        String categoryName = getIntent().getStringExtra("categoryName");

        getSupportActionBar().setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        itemEt = findViewById(R.id.itemEditText);
        saveBtn = findViewById(R.id.saveItem);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemEt.getText().toString();

                if (TextUtils.isEmpty(itemName)){
                    Toast.makeText(getApplicationContext(), "Enter a Item", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (itemToUpdate == null){
                    saveItem(itemName);
                }else {
                    updateItem(itemName);
                }

            }
        });

        initViewModel();
        initRecyclerview();
        itemViewModel.getAllItemList(category_id);

        
        
    }


    private void initViewModel(){
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getItemListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> itemsList) {
                if (itemsList == null){
                    recyclerView.setVisibility(View.GONE);



                }else{

                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setItemList(itemsList);

                }
            }
        });
    }

    private void initRecyclerview(){
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ItemListAdapter(this,this);
        recyclerView.setAdapter(adapter);

    }

    private void saveItem(String itemName) {

        Items item = new Items();
        item.itemName = itemName;
        item.categoryId = category_id;
        itemViewModel.insertItem(item);
        itemEt.setText("");


    }

    @Override
    public void itemClick(Items items) {
        if (items.completed){
            items.completed = false;
        }else{
            items.completed = true;
        }

        itemViewModel.updateItem(items);

    }

    @Override
    public void removeItem(Items items) {
        itemViewModel.deleteItem(items);
    }

    @Override
    public void editItem(Items items) {
        this.itemToUpdate = items;
        itemEt.setText(items.itemName);

    }


    private void updateItem(String itemName) {

        itemToUpdate.itemName = itemName;
        itemViewModel.updateItem(itemToUpdate);
        itemEt.setText("");
        itemToUpdate = null;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case  R.id.home:
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}