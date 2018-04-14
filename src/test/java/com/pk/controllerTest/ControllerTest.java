package com.pk.controllerTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTest extends TestCase {
  @Override
  @Before
  public void setUp() throws Exception {
    // sunyongxia1
    token =
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwcmluY2lwYWwiOnsidXNlcklkIjoiY2MzMjQ2NzM2MjM3NGI5ZmFjZjNiOTU2MmMzZDY5NzQiLCJ1c2VyTmFtZSI6IuWtmeawuOS-oCIsIm9yZ0lkIjoiZmE2OTdhMDc5NDIxNDg4ZjkwMmQ4NTRkMDgzMjE4NDUifSwiZXhwaXJlcyI6MTQ5NDQ5MzkzNDAxNiwiaWF0IjoxNDkzODg5MTM0fQ.Eq-7svZoGmyrmccC7vvJXL7zeqLTW2NrB66S1JoNbZPxu1fIr5m7JTR3w2GbZDEjRZChmwcxTN93Lzx4pxKsFBQoHYyLpiOw6jVAuSRv6xsowzMN86zgwvmRptEv2mq-sKomR9W0adAY_KzZ1DqhmjZX9RCbGCaKFeF7viNATuAg8Gr7PK0jBqk7quRgueOtxqZPcgIpqXi4oW85-HrcVGzgc6qqtBGEKKIAI8H5w8k7QA53z7CwgAuI83hVQeocIkld5PYfU1MltY6W2lYUWhE2BZtwdLoTUXlAZgrrbv617iYo1gJWn0kPbEbjrI_A0IpCFlniaXzinJdyrM_9Lg";
  }

  private String token;

  @Override
  @After
  public void tearDown() throws Exception {}

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate rest;
  @Parameter(0)
  private String checkType;

  @Test
  @Ignore
  public void getTest() {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    // headers.add("Authorization", "Bearer " + token);
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    body.set("id", "3173b282-98c1-49a6-b486-6c599489c738");
    System.out.println("port：" + port);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = this.rest.exchange("http://localhost:" + port + "/test/login",
        HttpMethod.POST, request, String.class);
    System.out.println(response.getBody());
    System.out.println(response);
  }

  @Test
  @Ignore
  public void checkWhetherCanRegister() {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    // headers.add("Authorization", "Bearer " + token);
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    body.set("phoneNumber", "15608216920");
    System.out.println("port：" + port);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response =
        this.rest.exchange("http://localhost:" + port + "/home/checkWhetherCanRegister",
            HttpMethod.POST, request, String.class);
    System.out.println(response.getBody());
    System.out.println(response);
  }

  @Test
  @Ignore
  public void getCommentToAuthorSelf() {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    // headers.add("Authorization", "Bearer " + token);
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    body.set("token", "53afb449-e6a2-49dd-91a1-b24aaa0b49cc");
    body.set("blog_id", "25bab038-71b2-41af-855e-41c13bb73e30");
    body.set("nowPage", "0");
    body.set("pageSize", "10");
    System.out.println("port：" + port);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response =
        this.rest.exchange("http://localhost:" + port + "/comment/get-comment-author-self",
            HttpMethod.POST, request, String.class);
    System.out.println(response.getBody());
    System.out.println(response);
  }

  @Test
  @Ignore
  public void findUser() {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    // headers.add("Authorization", "Bearer " + token);
    MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
    body.set("token", "c14fbd33-2321-4b9a-9dd9-78c926c67434");
    body.set("searchStr", "哈哈");
    System.out.println("port：" + port);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response = this.rest.exchange(
        "http://localhost:" + port + "/map_data/find_user", HttpMethod.POST, request, String.class);
    System.out.println(response.getBody());
    System.out.println(response);
    
   
  }
  @Test
  public void checkWhetherCanRegisterEmail(){
  	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
      // headers.add("Authorization", "Bearer " + token);
      MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
      body.set("email", "1457431899qq.com");
      System.out.println("port：" + port);
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
      ResponseEntity<String> response = this.rest.exchange(
          "http://localhost:" + port + "/home/check-whether-can-register-email", HttpMethod.POST, request, String.class);
      System.out.println(response.getBody());
      System.out.println(response);
  }
}
