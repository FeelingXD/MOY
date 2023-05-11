package com.zerobase.moy.service;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.model.diary.DiaryForm;

public interface DiaryService {
  Diary postDiary(DiaryForm form);

  Diary patchDiary(Long id, DiaryForm form) ;

  void deleteDiary(Long id);

  Diary getDiary(Long id);
}
