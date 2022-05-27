package com.example.zetafashion_android.Model;

public class CheckoutProducts {
    String ProductName,ProductPrice,ProductCategory,ProductQuantity;
    String ProductImageUrl;

    public CheckoutProducts() {
    }

    public CheckoutProducts(String productName, String productPrice, String productCategory, String productImageUrl, String productQuantity) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductCategory = productCategory;
        ProductImageUrl = productImageUrl;
        ProductQuantity = productQuantity;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
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
