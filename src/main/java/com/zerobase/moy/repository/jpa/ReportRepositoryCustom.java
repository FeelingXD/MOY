package com.zerobase.moy.repository.jpa;

import com.zerobase.moy.data.entity.Report;
import java.util.Optional;

public interface ReportRepositoryCustom {

  Optional<Report> findByIdAndDiary_User_Id(Long id, Long user_id);
}
