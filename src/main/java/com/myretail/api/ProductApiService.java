package com.myretail.api;

import com.myretail.model.Product;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public abstract class ProductApiService {

    public abstract ResponseEntity deleteProduct(String productId) throws NotFoundException;

    public abstract ResponseEntity getProduct(String productId) throws NotFoundException;

    public abstract ResponseEntity getProducts( ) throws NotFoundException;

    public abstract ResponseEntity putProduct(Product productProfile, String productId) throws NotFoundException;

}
