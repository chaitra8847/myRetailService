package com.myretail.api;

import com.myretail.impl.ProductApiServiceImpl;
import com.myretail.model.Product;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@RestController
@ResponseBody
@RequestMapping(value = "/v1/products")
public class MyRetailApi {

    private final ProductApiService delegate = ProductApiServiceImpl.getInstance();

    @RequestMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable(value = "productId")  String productID) throws NotFoundException {
        return this.delegate.getProduct(productID);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/All",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProducts() throws NotFoundException {
        return this.delegate.getProducts( );
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity putProduct(@ApiParam( required = true) @RequestBody Product product,@PathVariable(value = "productId")
            String productID) throws NotFoundException {
        return this.delegate.putProduct(product,productID);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteProduct(@PathVariable(value = "productId")  String productID ) throws NotFoundException {
        return this.delegate.deleteProduct(productID);
    }
}
