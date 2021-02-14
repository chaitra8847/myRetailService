package com.myretail;

import com.myretail.impl.ProductApiServiceImpl;
import com.myretail.model.Price;
import com.myretail.model.Product;
import com.myretail.util.DbConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Map;

@SpringBootApplication
public class MyRetailApplication implements CommandLineRunner {
    final static Logger logger = LogManager.getLogger(ProductApiServiceImpl.class);
    public  DbConnection dbf;

    public static void main(String[] args) {
        SpringApplication.run(MyRetailApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        dbf = DbConnection.getInstance();
        //Initialize DB and add a product
        logger.info("MyRetailApplication Started");
        dbf.connectToAerospike();
        Product product = new Product("1234","prod1",new Price(2.99,"INR"));
        Map<String, Object> stringObjectMap = Product.getValues(product);
        dbf.putProduct(product.getProductId(),stringObjectMap);

    }

}
