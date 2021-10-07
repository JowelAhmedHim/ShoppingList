package com.example.shoppinglist.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.service.localDb.AppRoomDatabase;
import com.example.shoppinglist.service.localDb.entity.Category;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {


    private MutableLiveData<List<Category>> listOfCategory;
    private AppRoomDatabase appRoomDatabase;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        listOfCategory = new MutableLiveData<>();
        appRoomDatabase = AppRoomDatabase.getDbInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>> getCategoryListObserver(){
        return listOfCategory;
    }

    public void getAllCategoryList(){
      List<Category> categoryList =  appRoomDatabase.shoppingListDao().getAllCategoryList();
      if (categoryList.size() > 0){
          listOfCategory.postValue(categoryList);
      }else {
          listOfCategory.postValue(null);
      }
    }

    public void insertCategory(String catName){
        Category category = new Category();
        category.categoryName = catName;
        appRoomDatabase.shoppingListDao().insertCategory(category);
        getAllCategoryList();
    }
    public void updateCategory(Category category){
        appRoomDatabase.shoppingListDao().updateCategory(category);
        getAllCategoryList();
    }

    public void deleteCategory(Category category){
        appRoomDatabase.shoppingListDao().deleteCategory(category);
        getAllCategoryList();
    }
}
