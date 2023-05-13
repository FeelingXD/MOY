package com.zerobase.moy.service;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryRequestDto;

public interface DiaryService {

  Diary postDiary(User user, DiaryRequestDto form);

  Diary patchDiary(User user, Long id, DiaryForm form);

  void deleteDiary(User user, Long id);

  Diary getDiary(User user, Long id);
}
