package com.ayushsabharwal.ayuvyaayurveda.ui.productlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ayushsabharwal.ayuvyaayurveda.data.model.ProductModel;
import com.ayushsabharwal.ayuvyaayurveda.data.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository productRepository;
    private final LiveData<List<ProductModel>> productList;

    public ProductViewModel() {
        productRepository = new ProductRepository();
        productList = productRepository.getProducts();
    }

    public LiveData<List<ProductModel>> getProductList() {
        return productList;
    }
}
