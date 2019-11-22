package com.api.transaction.mapper.controller.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.api.transaction.mapper.app.TransactionMapperApplication;
import com.api.transaction.mapper.domain.TransactionMapperResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionMapperApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class TransactionMapperControllerNegativeFlowTest {
	
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
                        new UsernamePasswordCredentials("admin", "wrongadmin"));
 
        HttpClient client = HttpClientBuilder
                                .create()
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .build();
        return client;
    }
    
    @Test(expected = HttpClientErrorException.class)
    public void testUnauthorizedException() throws URISyntaxException
    {
    	final String baseUrl = "http://localhost:"+randomLocalServerPort+"/transaction-mapper/transactions/alltransactions/";
        URI uri = new URI(baseUrl);
        restTemplate.getForEntity(uri, TransactionMapperResult.class);
    }

}
