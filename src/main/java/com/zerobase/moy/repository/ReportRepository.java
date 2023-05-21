package com.zerobase.moy.repository;

import com.zerobase.moy.data.entity.Report;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

  @Query("select r from Report r where r.id =:id and r.diary.user.id=:user_id and r.diary.reported is true")
  Optional<Report> findByIdAndDiary_User_Id(@Param("id") Long id, @Param("user_id") Long user_id);
}
