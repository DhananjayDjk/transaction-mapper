package com.api.transaction.mapper.controller.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.api.transaction.mapper.app.TransactionMapperApplication;
import com.api.transaction.mapper.domain.TransactionMapperResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionMapperApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class TransactionMapperControllerPositiveFlowTest {
	
	private static final Logger logger = LogManager.getLogger(TransactionMapperControllerPositiveFlowTest.class);
	
	@LocalServerPort
    int randomLocalServerPort;
	
	public RestTemplate restTemplate;
    
    @Before
    public void setUp() 
    {
        restTemplate = new RestTemplate(getClientHttpRequestFactory());
    }
     
    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() 
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                          = new HttpComponentsClientHttpRequestFactory();
         
        clientHttpRequestFactory.setHttpClient(httpClient());      
        return clientHttpRequestFactory;
    }
    
    private HttpClient httpClient() 
    {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, 
                        new UsernamePasswordCredentials("admin", "admin"));
 
        HttpClient client = HttpClientBuilder
                                .create()
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .build();
        return client;
    }
    
    @Test
    public void testGetAllTransactions() throws URISyntaxException
    {
    	final String baseUrl = "http://localhost:"+randomLocalServerPort+"/transaction-mapper/transactions/alltransactions/";
        URI uri = new URI(baseUrl);
        ResponseEntity<TransactionMapperResult> result = restTemplate.getForEntity(uri, TransactionMapperResult.class);  
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(50, result.getBody().getTransformedTransactions().size());
        logger.info("result status code..."+result.getStatusCodeValue());
        logger.info("all transactions list size is..."+result.getBody().getTransformedTransactions().size());
    }
    
    @Test
    public void testGetTransactionsByType() throws URISyntaxException
    {
    	final String baseUrl = "http://localhost:"+randomLocalServerPort+"/transaction-mapper/transactions/type/SANDBOX_TAN/";
        URI uri = new URI(baseUrl);
        ResponseEntity<TransactionMapperResult> result = restTemplate.getForEntity(uri, TransactionMapperResult.class);         
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(2, result.getBody().getTransformedTransactions().size());
        logger.info("result status code..."+result.getStatusCodeValue());
        logger.info("all transactions list size for transaction type SANDBOX_TAN is..."+result.getBody().getTransformedTransactions().size());
    }
     
    @Test
    public void testGetTransactionAmountByType() throws URISyntaxException
    {
    	final String baseUrl = "http://localhost:"+randomLocalServerPort+"/transaction-mapper/transactions/amount/SANDBOX_TAN/";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);         
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("10.00", result.getBody());
        logger.info("result status code..."+result.getStatusCodeValue());
        logger.info("total transaction amount for transaction type SANDBOX_TAN is..."+result.getBody());
    }

}
