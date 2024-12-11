package com.ayushsabharwal.ayuvyaayurveda.ui.cart;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ayushsabharwal.ayuvyaayurveda.data.model.ProductModel;
import com.ayushsabharwal.ayuvyaayurveda.data.repository.ProductRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private final MutableLiveData<List<ProductModel>> cartItems = new MutableLiveData<>();
    private final DatabaseReference productsRef;
    private final ProductRepository productRepository;
    private final MutableLiveData<Integer> cartQuantityLiveData;

    public CartViewModel() {
        productsRef = FirebaseDatabase.getInstance().getReference("products");
        loadCartItems();
        productRepository = new ProductRepository();
        cartQuantityLiveData = new MutableLiveData<>();
        fetchCartQuantity();
    }

    public LiveData<List<ProductModel>> getCartItems() {
        return cartItems;
    }

    private void loadCartItems() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProductModel> items = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    ProductModel product = productSnapshot.getValue(ProductModel.class);
                    if (product != null && product.getIsInCart()) {
                        items.add(product);
                    }
                }
                cartItems.setValue(items);
                Log.d("CartViewModel", "Cart items loaded: " + items.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CartViewModel", "Database error: " + error.getMessage());
            }
        });
    }

    public void updateQuantity(ProductModel product, int quantity) {
        product.setQuantity(quantity);
        productsRef.child(product.getId()).setValue(product);
    }

    public LiveData<Integer> getCartQuantity() {
        return cartQuantityLiveData;
    }

    private void fetchCartQuantity() {
        productRepository.getTotalCartQuantity(cartQuantityLiveData);
    }
}
