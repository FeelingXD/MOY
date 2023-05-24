package com.zerobase.moy.repository.jpa.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.moy.data.entity.QDiary;
import com.zerobase.moy.data.entity.QFollow;
import com.zerobase.moy.data.entity.QUser;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryDto;
import com.zerobase.moy.repository.jpa.FollowRepositoryCustom;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QFollow qFollow = QFollow.follow;
  private final QDiary qDiary = QDiary.diary;

  @Override
  public List<DiaryDto> getFeed(User user) {
    QFollow qFollow = QFollow.follow;
    QDiary qDiary = QDiary.diary;
    QUser qUser = QUser.user;

    List<DiaryDto> feed = queryFactory
        .select(qDiary)
        .from(qDiary)
        .join(qDiary.user, qUser).fetchJoin()
        .join(qFollow)
        .on(QUser.user.id.eq(qFollow.toUser.id))
        .where(qFollow.fromUser.id.eq(user.getId())
            .and(qDiary.deleted.eq(false))
            .and(qDiary.isPublic.eq(true))
            .and(qUser.deleted.eq(false)))
        .orderBy(qDiary.lastModifiedDate.desc())
        .fetch()
        .stream().map(DiaryDto::of)
        .collect(Collectors.toList());

    return feed;
  }
}
