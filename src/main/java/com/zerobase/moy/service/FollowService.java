package com.zerobase.moy.service;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryDto;
import java.util.List;

public interface FollowService {

  void addFollower(User user, Long id);

  void deleteFollower(User user, Long id);

  Integer getCountFollowings(User user);

  Integer getCountFollowers(User user);

  List<DiaryDto> getFeeds(User user);

}
