package com.ayushsabharwal.ayuvyaayurveda.ui.productlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayushsabharwal.ayuvyaayurveda.R;
import com.ayushsabharwal.ayuvyaayurveda.ui.cart.CartActivity;
import com.ayushsabharwal.ayuvyaayurveda.ui.cart.CartViewModel;
import com.ayushsabharwal.ayuvyaayurveda.ui.productdetails.ProductDetails;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    private TextView cartBadge;
    private CartViewModel cartViewModel;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ImageView cart = findViewById(R.id.cart);
        cartBadge = findViewById(R.id.cartBadge);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartQuantity().observe(this, this::updateCartBadge);

        cart.setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(new ArrayList<>(), product -> {
            FirebaseDatabase.getInstance().getReference("products").child(product.getId()).setValue(product);
        }, product -> {
            Intent intent = new Intent(ProductListActivity.this, ProductDetails.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        recyclerView.setAdapter(productAdapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductList().observe(this, productList -> {
            if (productList != null) {
                productAdapter.updateProducts(productList);
            }
        });
    }

    private void updateCartBadge(int totalQuantity) {
        if (totalQuantity > 0) {
            cartBadge.setText(String.valueOf(totalQuantity));
            cartBadge.setVisibility(View.VISIBLE);
        } else {
            cartBadge.setVisibility(View.GONE);
        }
    }
}