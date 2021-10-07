package com.example.shoppinglist.service.localDb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shoppinglist.service.localDb.entity.Category;
import com.example.shoppinglist.service.localDb.entity.Items;

import java.util.List;


@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM Category")
    List<Category> getAllCategoryList();



    @Insert
    void insertCategory(Category...categories);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM Items where categoryId = :cartId")
    List<Items>getAllItemList(int cartId);

    @Insert
    void insertItems(Items items);

    @Update
    void updateItems(Items items);

    @Delete
    void deleteItems(Items items);
}
