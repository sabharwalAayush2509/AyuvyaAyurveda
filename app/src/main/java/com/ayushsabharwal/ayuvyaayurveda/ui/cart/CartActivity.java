package com.ayushsabharwal.ayuvyaayurveda.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayushsabharwal.ayuvyaayurveda.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView recyclerView = findViewById(R.id.cartProductRecyclerView);
        ImageView cartIcon = findViewById(R.id.cartIcon);
        TextView emptyCartText = findViewById(R.id.emptyCartText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(new ArrayList<>(), (product, quantity) -> {
            cartViewModel.updateQuantity(product, quantity);
        });
        recyclerView.setAdapter(cartAdapter);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(this, products -> {
            if (products == null || products.isEmpty()) {
                cartIcon.setVisibility(View.VISIBLE);
                emptyCartText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Log.d("CartActivity", "Cart is empty.");
            } else {
                cartIcon.setVisibility(View.GONE);
                emptyCartText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                cartAdapter.notifyDataSetChanged();
                cartAdapter.updateCartItems(products);
                Log.d("CartActivity", "Cart items displayed: " + products.size());
            }
        });
    }
}
