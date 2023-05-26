package com.zerobase.moy.service;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryRequestDto;
import com.zerobase.moy.data.model.diarydocument.DiaryDocumentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryService {

  Diary postDiary(User user, DiaryRequestDto form);

  Diary putDiary(User user, Long id, DiaryRequestDto dto);

  void deleteDiary(User user, Long id);

  Diary getDiary(User user, Long id);

  Page<DiaryDocumentDto> getPublicDiaries(Pageable pageable);

  Page<DiaryDocumentDto> searchDiaries(String query, Pageable pageable);
}
