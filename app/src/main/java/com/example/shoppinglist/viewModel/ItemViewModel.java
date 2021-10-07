package com.example.shoppinglist.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.service.localDb.AppRoomDatabase;
import com.example.shoppinglist.service.localDb.entity.Items;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private MutableLiveData<List<Items>> listOfItem;
    private AppRoomDatabase appRoomDatabase;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        listOfItem = new MutableLiveData<>();
        appRoomDatabase = AppRoomDatabase.getDbInstance(getApplication().getApplicationContext());


    }


    public MutableLiveData<List<Items>> getItemListObserver(){
        return listOfItem;
    }

    public void getAllItemList(int categoryId){
        List<Items> itemsList = appRoomDatabase.shoppingListDao().getAllItemList(categoryId);
        if (itemsList.size()>0){
            listOfItem.postValue(itemsList);
        }else {
            listOfItem.postValue(null);
        }
    }

    public void insertItem(Items items){
        appRoomDatabase.shoppingListDao().insertItems(items);
        getAllItemList(items.categoryId);
    }

    public void updateItem(Items items){
        appRoomDatabase.shoppingListDao().updateItems(items);
        getAllItemList(items.categoryId);
    }

    public void deleteItem(Items items){
        appRoomDatabase.shoppingListDao().deleteItems(items);
        getAllItemList(items.categoryId);
    }
}
