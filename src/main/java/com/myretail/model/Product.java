package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class Product {

    @NotNull
    private String productId;

    @JsonProperty
    @NotNull
    private String name;

    @JsonProperty
    @NotNull
    private Price currentPrice;

    public Product(){}

    public Product(String productId, String name, Price currentPrice) {
        this.productId = productId;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public static Map<String, Object> getValues(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map =
                mapper.convertValue(product, new TypeReference<Map<String, Object>>() {});
        return map;
    }

    public static Product getProductPoJo( Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        Product product  =
                mapper.convertValue(map,Product.class);
        return product;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }

    @JsonGetter("productId")
    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Price currentPrice) {
        this.currentPrice = currentPrice;
    }

}
