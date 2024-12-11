package com.ayushsabharwal.ayuvyaayurveda.ui.productlist;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayushsabharwal.ayuvyaayurveda.R;
import com.ayushsabharwal.ayuvyaayurveda.ui.data.model.ProductModel;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductModel> products;
    private OnAddToCartListener onAddToCartListener;
    private OnProductClickListener onProductClickListener;

    public interface OnAddToCartListener {
        void onAddToCart(ProductModel product);
    }

    public interface OnProductClickListener {
        void onProductClick(ProductModel product);
    }

    public ProductAdapter(List<ProductModel> products, OnAddToCartListener listener, OnProductClickListener listener2) {
        this.products = products;
        this.onAddToCartListener = listener;
        this.onProductClickListener = listener2;
    }

    public void updateProducts(List<ProductModel> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = products.get(position);
        Picasso.get().load(product.getImageUrl()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_background).into(holder.productImageView);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        if (product.getIsInCart()) {
            holder.addToCartButton.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
        } else {
            holder.addToCartButton.setVisibility(View.VISIBLE);
            holder.linearLayout.setVisibility(View.GONE);
        }

        holder.addToCartButton.setOnClickListener(v -> {
            product.setIsInCart(true);
            product.setQuantity(1);
            notifyItemChanged(position);
            if (onAddToCartListener != null) {
                onAddToCartListener.onAddToCart(product);
            }
        });
        holder.plusButton.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() + 1;
            product.setQuantity(newQuantity);
            holder.productQuantity.setText(String.valueOf(newQuantity));
            updateQuantityInFirebase(product);
        });
        holder.minusButton.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() - 1;
            if (newQuantity > 0) {
                product.setQuantity(newQuantity);
                holder.productQuantity.setText(String.valueOf(newQuantity));
                updateQuantityInFirebase(product);
            } else {
                product.setIsInCart(false);
                product.setQuantity(0);
                notifyItemChanged(position);
                if (onAddToCartListener != null) {
                    onAddToCartListener.onAddToCart(product);
                }
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (onProductClickListener != null) {
                onProductClickListener.onProductClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    private void updateQuantityInFirebase(ProductModel product) {
        FirebaseDatabase.getInstance().getReference("products").child(product.getId()).child("quantity").setValue(product.getQuantity());
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImageView;
        TextView nameTextView, priceTextView, minusButton, productQuantity, plusButton;
        Button addToCartButton;

        LinearLayout linearLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            minusButton = itemView.findViewById(R.id.minusButton);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            plusButton = itemView.findViewById(R.id.plusButton);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
