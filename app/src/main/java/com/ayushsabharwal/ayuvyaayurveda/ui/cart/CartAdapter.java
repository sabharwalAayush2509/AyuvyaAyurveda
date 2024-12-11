package com.ayushsabharwal.ayuvyaayurveda.ui.cart;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayushsabharwal.ayuvyaayurveda.R;
import com.ayushsabharwal.ayuvyaayurveda.data.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<ProductModel> cartItems;
    private final OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged(ProductModel product, int newQuantity);
    }

    public CartAdapter(List<ProductModel> cartItems, OnQuantityChangeListener listener) {
        this.cartItems = cartItems;
        this.quantityChangeListener = listener;
    }

    public void updateCartItems(List<ProductModel> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductModel product = cartItems.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("₹ %.2f", product.getPrice()));
        holder.productQuantity.setText("Quantity: " + product.getQuantity());
        Picasso.get().load(product.getImageUrl()).placeholder(R.drawable.ic_downloading).error(R.drawable.ic_downloading).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImageView);
            productName = itemView.findViewById(R.id.nameTextView);
            productPrice = itemView.findViewById(R.id.priceTextView);
            productQuantity = itemView.findViewById(R.id.quantityTextView);
        }
    }
}
