package com.zerobase.moy.repository.jpa;

import com.zerobase.moy.data.entity.Report;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {

  @Override
  Optional<Report> findById(Long aLong);

  Optional<Report> findByIdAndDiary_User_Id(Long id, Long user_id);

  @Override
  Page<Report> getMyReports(Long userId, Pageable pageable);
}

