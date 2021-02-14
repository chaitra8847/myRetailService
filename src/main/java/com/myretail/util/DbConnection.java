package com.myretail.util;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.myretail.api.ProductApiService;
import com.myretail.impl.ProductApiServiceImpl;
import com.myretail.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbConnection {

    final static Logger logger = LogManager.getLogger(ProductApiServiceImpl.class);

    private static DbConnection dbConnection = null;

    public static DbConnection getInstance()
    {

        if (dbConnection == null)
            dbConnection = new DbConnection();
        return dbConnection;
    }

    AerospikeClient client ;
    WritePolicy policy;
    String namespace = "test";
    String setName = "myretailSet";


    public boolean connectToAerospike() {
        try {
            client = new AerospikeClient("host.docker.internal", 3000);
            policy = new WritePolicy(client.writePolicyDefault);
            logger.info("DbConnection" + client.toString());
            return true;
        } catch (Exception aIn)
        {
            aIn.printStackTrace();
            return false;
        }
    }

    public boolean putProduct(String key, Map<String, Object> aInValues)
    {
        try{
            Bin[] lBins = getBins(aInValues);

            client.put(policy, new Key(namespace, setName, key),lBins);
        }catch(Exception aInE)
        {
            aInE.printStackTrace();
            logger.error("Exception in Reading data from Aerospike:: "  + aInE.getCause());
            return false;
        }
        return true;

    }

    public boolean deleteProduct(String key)
    {
        try{
            Key dBkey = new Key(namespace, setName, key);
            client.delete(policy, dBkey);
        }catch(Exception aInE)
        {
            aInE.printStackTrace();
            logger.error("Exception in Reading data from Aerospike:: "  + aInE.getCause());
            return false;
        }
        return true;

    }

    public Product getProduct(String key)
    {
        try{
            logger.info("getProduct key" + key);
            Key dBkey = new Key(namespace, setName, key);
            Record record = null;
            record = client.get(policy, dBkey);

            Product product = Product.getProductPoJo(record.bins);
            logger.info("getProduct product" + product);

            return product;

        }catch(Exception aInE)
        {
            aInE.printStackTrace();
            logger.error("Exception in Reading data from Aerospike:: "  + aInE.getCause());
            return null;
        }
    }

    public List<Product> getAllProduct()
    {
        RecordSet rs = null;
        try{
            Statement stmt = new Statement();
            stmt.setNamespace(namespace);
            stmt.setSetName(setName);
            List<Product> productList = new ArrayList<>();
             rs = client.query(null, stmt);

                while (rs.next()) {
                    Key key = rs.getKey();
                    Record record = rs.getRecord();
                    Product product = Product.getProductPoJo(record.bins);
                    productList.add(product);
                }
            return productList;
        }catch(Exception aInE)
        {
            aInE.printStackTrace();
            logger.error("Exception in Reading data from Aerospike:: "  + aInE.getCause());
            return null;
        }
        finally {
             rs.close();
        }
    }

    public Bin[] getBins(Map<String, Object> aInValues)
    {
        try
        {
            List<Bin> lBins = new ArrayList<>();
            for (Map.Entry<String, Object> lEntry : aInValues.entrySet())
            {
                Object lValue = lEntry.getValue();
                if (lValue == null)
                {
                    //No value, don't add a bin
                    continue;
                }
                Bin lBin;
                if (lValue instanceof List)
                {
                    lBin = new Bin(lEntry.getKey(), (List<?>) lValue);
                }
                else if (lValue instanceof Map)
                {
                    lBin = new Bin(lEntry.getKey(), (Map<?, ?>) lValue);
                }
                else
                {
                    lBin = new Bin(lEntry.getKey(), lValue);
                }
                lBins.add(lBin);
            }
            // Convert list to an array and return it
            return lBins.toArray(new Bin[lBins.size()]);
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
