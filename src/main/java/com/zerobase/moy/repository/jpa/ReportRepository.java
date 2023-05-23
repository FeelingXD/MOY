package com.zerobase.moy.repository.jpa;

import com.zerobase.moy.data.entity.Report;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>,ReportRepositoryCustom {

  Optional<Report> findByIdAndDiary_User_Id( Long id,  Long user_id);
}
