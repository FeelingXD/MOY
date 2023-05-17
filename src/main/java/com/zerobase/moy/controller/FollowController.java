package com.zerobase.moy.controller;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

  private final FollowService followService;
  @PostMapping("follow/{id}")
  public ResponseEntity<?> addFollower(@AuthenticationPrincipal User user,@PathVariable Long id){
    followService.addFollower(user,id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .build()
    );
  }
  @DeleteMapping("follow/{id}")
  public ResponseEntity<?> deleteFollower(@AuthenticationPrincipal User user,@PathVariable Long id){
    followService.deleteFollower(user,id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .build()
    );
  }
  @GetMapping("/followers")
  public ResponseEntity<?> getFollowers(@AuthenticationPrincipal User user){
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(followService.getCountFollowers(user))
        .build());
  }
  @GetMapping("/followings")
  public ResponseEntity<?> getFollowings(@AuthenticationPrincipal User user){
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(followService.getCountFollowings(user))
        .build());
  }
  @GetMapping("/feeds")
  public ResponseEntity<?> getFeeds(@AuthenticationPrincipal User user){
    var result=followService.getFeeds(user);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(result)
        .build());
  }

}
