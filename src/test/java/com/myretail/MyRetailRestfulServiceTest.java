package com.myretail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.model.Price;
import com.myretail.model.Product;
import com.myretail.util.DbConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@WebMvcTest(MyRetailRestfulServiceTest.class)
@WebAppConfiguration
public class MyRetailRestfulServiceTest {



    private MockMvc mockMvc;
    private static DbConnection dbConnection;


    /**
     * set up before the tests
     */
    @Before
    public void setup()
    {
        dbConnection = mock(DbConnection.class);
        when(dbConnection.connectToAerospike()).thenReturn(true);

    }
    @Test
    public void putProductTest() throws Exception{
        try {
            Product testProduct = new Product("12345","Mock Product 1",new Price(0.99,"USD"));
            ObjectMapper objectMapper=new ObjectMapper();
            String json = objectMapper.writeValueAsString(testProduct);
            when(dbConnection.putProduct("12345",Product.getValues(testProduct))).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders.put("/v1/products/12345",json)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            ;
        } catch (Exception aInE)
        {
            aInE.printStackTrace();
        }

    }

    @Test
    public void getProductTest() throws Exception{
        try {
            Product testProduct = new Product("12345", "Mock Product 1", new Price(0.99, "USD"));

            when(dbConnection.getProduct("12345")).thenReturn(testProduct);

            mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/12345")
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testProduct.getName()))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            ;
        }catch (Exception aInE)
        {
            aInE.printStackTrace();
        }
    }

    @Test
    public void deleteProductTest() throws Exception{

        try {
            when(dbConnection.deleteProduct("12345")).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders.delete("/v1/products/12345")
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            ;
        }catch (Exception aInE)
        {
            aInE.printStackTrace();
        }
    }

}


