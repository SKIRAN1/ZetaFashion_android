package com.example.zetafashion_android.Model;

import java.util.Comparator;

public class MenProducts {
    String ProductName,ProductPrice,ProductCategory;
    String ProductImageUrl;

    public MenProducts() {
    }

    public MenProducts(String productName, String productPrice, String productCategory, String productImageUrl) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductCategory = productCategory;
        ProductImageUrl = productImageUrl;
    }

    public static Comparator<MenProducts> ProductPriceHLComparator = new Comparator<MenProducts>() {
        @Override
        public int compare(MenProducts p1, MenProducts p2) {
            return p1.getProductName().compareTo(p2.getProductName());
        }
    };

    public static Comparator<MenProducts> ProductPriceLHComparator = new Comparator<MenProducts>() {
        @Override
        public int compare(MenProducts p1, MenProducts p2) {
            return p2.getProductName().compareTo(p1.getProductName());
        }
    };

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }
}
