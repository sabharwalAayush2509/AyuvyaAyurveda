package com.ayushsabharwal.ayuvyaayurveda.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ayushsabharwal.ayuvyaayurveda.data.model.ProductModel;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final DatabaseReference productReference;

    public ProductRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        productReference = database.getReference("products");
    }

    public LiveData<List<ProductModel>> getProducts() {
        MutableLiveData<List<ProductModel>> productsLiveData = new MutableLiveData<>();
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<ProductModel> productList = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    ProductModel product = productSnapshot.getValue(ProductModel.class);
                    if (product != null) {
                        productList.add(product);
                    } else {
                        Log.e("ProductRepository", "Null product found!");
                    }
                }
                productsLiveData.setValue(productList);
                Log.d("ProductRepository", "Products loaded: " + productList.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                productsLiveData.setValue(null);
                Log.e("ProductRepository", "Database error: " + error.getMessage());
            }
        });
        return productsLiveData;
    }

    public void getTotalCartQuantity(MutableLiveData<Integer> cartQuantityLiveData) {
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalQuantity = 0;
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    ProductModel product = productSnapshot.getValue(ProductModel.class);
                    if (product != null && product.getIsInCart()) {
                        totalQuantity += product.getQuantity();
                    }
                }
                cartQuantityLiveData.postValue(totalQuantity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching cart data", error.toException());
            }
        });
    }
}
