package com.zerobase.moy.repository.jpa;

import com.zerobase.moy.data.entity.Report;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface ReportRepositoryCustom {
  Optional<Report> findByIdAndDiary_User_Id( Long id,  Long user_id);
}
