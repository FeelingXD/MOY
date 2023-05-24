package com.zerobase.moy.repository.jpa.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.moy.data.entity.QDiary;
import com.zerobase.moy.data.entity.QReport;
import com.zerobase.moy.data.entity.QUser;
import com.zerobase.moy.data.entity.Report;
import com.zerobase.moy.repository.jpa.ReportRepositoryCustom;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  QReport qReport = QReport.report;
  QDiary qDiary = QDiary.diary;
  QUser qUser = QUser.user;

  @Override
  public Optional<Report> findByIdAndDiary_User_Id(Long id, Long user_id) {

    var result = queryFactory
        .select(qReport)
        .from(qReport)
        .join(qReport.diary, qDiary)
        .join(qDiary.user, qUser)
        .where(qReport.id.eq(id)
            .and(qUser.id.eq(user_id))
            .and(qDiary.deleted.isFalse())
        ).fetch();

    return result.stream().findFirst();
  }

  @Override
  public Page<Report> getMyReports(Long userId, Pageable pageable) {

    var result = queryFactory.select(qReport)
        .from(qReport)
        .join(qReport.diary,qDiary).fetchJoin()
        .join(qDiary.user,qUser).fetchJoin()
        .where(qUser.id.eq(userId))
        .fetch();

    return new PageImpl<>(result);
  }
}
