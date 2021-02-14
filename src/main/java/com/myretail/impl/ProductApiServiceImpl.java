package com.myretail.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.myretail.api.ProductApiService;
import com.myretail.model.Product;
import com.myretail.util.DbConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductApiServiceImpl extends ProductApiService {

    private static ProductApiService service = null;

    final static Logger logger = LogManager.getLogger(ProductApiServiceImpl.class);
    DbConnection dbConnection = DbConnection.getInstance();

    public static ProductApiService getInstance()
    {
        if (service == null)
            service = new ProductApiServiceImpl();

        return service;
    }

    @Override
    public ResponseEntity deleteProduct(String productId) throws NotFoundException {

        boolean status = dbConnection.delete(productId);
        if(status)
            return new ResponseEntity(HttpStatus.OK);

        logger.debug(String.format("Error Deleting Record for %s",productId));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(String.format("Error Deleting Record for ID %s",productId));


    }

    @Override
    public ResponseEntity getProduct(String productId) throws NotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Product product = dbConnection.getProduct(productId);
            if(product==null)
            {
                logger.debug(String.format("Error getting Record for ID %s",productId));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                        body(String.format("Error getting Record for ID %s",productId));
            }
            String jsonData = objectMapper.writeValueAsString(product);
            return ResponseEntity.status(HttpStatus.OK).body(jsonData);

        } catch (IOException e) {
            logger.error("Exception :: " , e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(String.format("Exception getting Record for ID %s",productId));
        }
    }

    @Override
    public ResponseEntity getProducts() throws NotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        try {
            List<Product> productList = dbConnection.getAllProduct();
            if(productList==null)
            {
                logger.debug(String.format("Error getting Records"));
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                        body(String.format("Error getting Records"));
            }
            String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productList);

            return ResponseEntity.status(HttpStatus.OK).body(jsonData);

        } catch (IOException e) {
            logger.error("Exception :: " , e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(String.format("Exception getting Record fors"));
        }
    }
    @Override
    public ResponseEntity putProduct(Product product, String productId) throws NotFoundException {

        Map<String, Object> stringObjectMap = Product.getValues(product);
        boolean status = dbConnection.put(productId,stringObjectMap);
        if(status)
            return new ResponseEntity(HttpStatus.CREATED);
        logger.debug(String.format("Error Deleting Record for %s",productId));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body(String.format("Error Deleting Record for ID %s",productId));
    }
}
