//package com.example.newstest3.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//public class AppController {
//
//
//
//    public void callCreateUsers(){
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://localhost:7070/createDummyUsers";
//        ResponseEntity<String> response
//                =restTemplate.postForEntity(fooResourceUrl, null, String.class);
//        System.out.print("response" + response.getStatusCode());
//        if(response.getStatusCode()!= HttpStatus.OK) System.out.println("CREATE NOT OK!!");
//    }
//
//    public void callGetUsers(){
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://localhost:7070/getUsers";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl , String.class);
//        System.out.print("response" + response);
//    }
//}
