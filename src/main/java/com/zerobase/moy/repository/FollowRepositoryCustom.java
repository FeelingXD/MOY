package com.zerobase.moy.repository;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryDto;
import java.util.List;

public interface FollowRepositoryCustom {

  List<DiaryDto> getFeed(User user);
}
