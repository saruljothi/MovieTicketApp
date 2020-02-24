package com.mobiquity.movieReviewApp.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class OmdbClientTest {

  @Mock
  RestTemplate restTemplate;

  @InjectMocks
  //@Spy
  @Autowired
  OmdbClient omdbClient;

  HttpEntity httpEntity;

  String keyName = "apikey1";
  //String keyValue = "53c922c51";

  @Test
  public void checkResponseFromOmdb()
  {

    ResponseEntity<Object> result=omdbClient.getOmdbResponse(keyName);
    System.out.println(result);
    assertEquals(HttpStatus.OK,result.getStatusCode());
  }

  @Test
  public void checkResponseFromOmdbWithTestRestTemplate()
  {
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    ResponseEntity<Object> result = testRestTemplate.exchange("http://www.omdbapi.com/?t=The Social Network&apikey=53c922c5",
        HttpMethod.GET,httpEntity,Object.class);
    assertEquals( HttpStatus.OK,result.getStatusCode());
  }

  //TEst with mockito
  @Test
  public void checkResponseFromOmdbWithMockito()
  {
    ResponseEntity<String> responseEntity = new ResponseEntity<String>("sampleBodyString", HttpStatus.ACCEPTED);
    Mockito.when(restTemplate.exchange(
        ArgumentMatchers.any(),
        ArgumentMatchers.any(HttpMethod.class),
        ArgumentMatchers.<HttpEntity<?>> any(),
        ArgumentMatchers.<Class<String>>any()))
        .thenReturn(responseEntity);

    ResponseEntity<Object> result = omdbClient.getOmdbResponse(keyName);
    assertEquals("sampleBodyString",result.getBody());
  }

}