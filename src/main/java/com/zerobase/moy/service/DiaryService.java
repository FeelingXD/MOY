package com.zerobase.moy.service;

import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;

public interface DiaryService {
  DiaryResultDto postDiary(DiaryForm form);

  DiaryResultDto patchDiary(Long id, DiaryForm form) ;

  void deleteDiary(Long id);

  DiaryResultDto getDiary(Long id);
}
