package com.zerobase.moy.repository;

import com.zerobase.moy.data.entity.Diary;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

  Optional<Diary> findByIdAndDeletedIsFalse(Long id);

  Optional<Diary> findByIdAndUserIdAndReportedIsFalseAndDeletedIsFalse(Long id, Long UserId);
}
