package com.zerobase.moy.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping()
  public ResponseEntity<?> test(HttpServletRequest req){
    System.out.println(req);
    return ResponseEntity.ok().body("ok");
  }


}
