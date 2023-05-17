package com.zerobase.moy.service.impl;

import com.zerobase.moy.data.entity.Follow;
import com.zerobase.moy.data.entity.Follow.FollowId;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryDto;
import com.zerobase.moy.repository.FollowRepository;
import com.zerobase.moy.repository.UserRepository;
import com.zerobase.moy.response.exception.CustomException;
import com.zerobase.moy.response.exception.ErrorCode;
import com.zerobase.moy.service.FollowService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  @Override
  public void addFollower(User user, Long id) {
    if(Objects.equals(user.getId(), id)){
      throw new CustomException(ErrorCode.CAN_NOT_SELF_FOLLOW);
    }
    FollowId followId=new FollowId(user.getId(),id);

    if(followRepository.findById(followId).isPresent()){
      throw new CustomException(ErrorCode.ALREADY_FOLLOWED);
    }

    User toUser= userRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    Follow follow=Follow.builder()
        .id(followId)
        .fromUser(user)
        .toUser(toUser)
        .build();
    followRepository.save(follow);
  }

  @Override
  public void deleteFollower(User user, Long id) {
    Follow follow=followRepository.findById(new FollowId(user.getId(),id)).orElseThrow(() -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND));
    followRepository.delete(follow);
  }

  @Override
  public Integer getFollowings(User user) {
    return followRepository.countByFromUserId(user.getId());
  }

  @Override
  public Integer getFollowers(User user) {
    return followRepository.countByToUserId(user.getId());

  }

  @Override
  public List<DiaryDto> getFeeds(User user) {
    return followRepository.getFeed(user);
  }
}
