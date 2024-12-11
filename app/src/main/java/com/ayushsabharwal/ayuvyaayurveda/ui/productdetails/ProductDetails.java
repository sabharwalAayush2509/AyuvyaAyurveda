package com.ayushsabharwal.ayuvyaayurveda.ui.productdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ayushsabharwal.ayuvyaayurveda.R;
import com.ayushsabharwal.ayuvyaayurveda.ui.cart.CartActivity;
import com.ayushsabharwal.ayuvyaayurveda.ui.cart.CartViewModel;
import com.ayushsabharwal.ayuvyaayurveda.ui.data.model.ProductModel;
import com.squareup.picasso.Picasso;

public class ProductDetails extends AppCompatActivity {
    private TextView cartBadge;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ImageView cart = findViewById(R.id.cart);
        cartBadge = findViewById(R.id.cartBadge);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartQuantity().observe(this, this::updateCartBadge);

        cart.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetails.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productDescription = findViewById(R.id.productDescription);

        ProductModel product = (ProductModel) getIntent().getSerializableExtra("product");
        if (product != null) {
            Picasso.get().load(product.getImageUrl()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_background).into(productImage);
            productName.setText(product.getName());
            productPrice.setText(String.format("$%.2f", product.getPrice()));
            productDescription.setText(product.getDescription());
        }
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