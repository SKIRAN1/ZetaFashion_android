package com.example.zetafashion_android.Model;

import android.media.Image;
import android.widget.ImageView;

public class Products {
    String ProductName,ProductPrice,ProductCategory;
    String ProductImageUrl;

    public Products() {
    }

    public Products(String productName, String productPrice, String productCategory, String productImageUrl) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductCategory = productCategory;
        ProductImageUrl = productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductPrice(){
        return ProductPrice;
   }

   public String getProductImageUrl(){return ProductImageUrl;}

    public String getProductCategory(){return ProductCategory;}
}
