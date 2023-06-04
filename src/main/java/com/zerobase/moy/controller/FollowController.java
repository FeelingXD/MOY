package com.zerobase.moy.controller;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"Follow Controller 팔로우 API"})

@RestController
@RequiredArgsConstructor
public class FollowController {

  private final FollowService followService;

  @ApiOperation(value = "팔로우 추가" , notes = "id 에 해당하는 유저를 내 팔로우로 추가합니다.")
  @PostMapping("follow/{id}")
  public ResponseEntity<?> addFollower(@AuthenticationPrincipal User user, @Parameter(description = "유저 id") @PathVariable Long id) {
    followService.addFollower(user, id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .build()
    );
  }

  @ApiOperation(value = "팔로우 삭제" , notes = "id 에해당하는 유저를 내 팔로우에서 제외합니다.")
  @DeleteMapping("follow/{id}")
  public ResponseEntity<?> deleteFollower(@AuthenticationPrincipal User user,
      @Parameter(description = "유저 id") @PathVariable Long id) {
    followService.deleteFollower(user, id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .build()
    );
  }

  @ApiOperation(value = "팔로워수 보기" , notes = "내가 추가한 팔로워수 보기")
  @GetMapping("/followers")
  public ResponseEntity<?> getFollowers(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(followService.getCountFollowers(user))
        .build());
  }

  @ApiOperation(value = "팔로윙수 보기" , notes = "나를 추가한 팔로윙수 보기")
  @GetMapping("/followings")
  public ResponseEntity<?> getFollowings(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(followService.getCountFollowings(user))
        .build());
  }

  @ApiOperation(value = "피드가져 오기" , notes = "팔로우 한 유저의 게시글(다이어리)를 가져옵니다.")
  @GetMapping("/feeds")
  public ResponseEntity<?> getFeeds(@AuthenticationPrincipal User user) {
    var result = followService.getFeeds(user);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(result)
        .build());
  }

}
