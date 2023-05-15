package com.zerobase.moy.service.impl;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryRequestDto;
import com.zerobase.moy.exception.CustomException;
import com.zerobase.moy.exception.ErrorCode;
import com.zerobase.moy.repository.DiaryRepository;
import com.zerobase.moy.service.DiaryService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;

  @Override
  public Diary postDiary(User user, DiaryRequestDto dto) {
    var diary = DiaryRequestDto.toEntity(dto);
    diary.setUser(user);
    return diaryRepository.save(diary);
  }

  @Override
  public Diary patchDiary(User user, Long id, DiaryForm form) {

    var diary = diaryRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND));

    if (!Objects.equals(diary.getUser().getId(), user.getId())) {
      throw new CustomException(ErrorCode.NOT_AUTHORIZED);
    }

    diary.setContent(form.getContent());
    diary.setTitle(form.getTitle());

    return diaryRepository.save(diary);
  }

  @Override
  public void deleteDiary(User user, Long id) {
    var diary = diaryRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND));

    if (!Objects.equals(diary.getUser().getId(), user.getId())) {
      throw new CustomException(ErrorCode.NOT_AUTHORIZED);
    }
    diary.setDeleted(true);
    diaryRepository.save(diary);
  }

  @Override
  public Diary getDiary(User user, Long id) {
    var diary = diaryRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> new CustomException(ErrorCode.DIARY_NOT_FOUND));

    if (Objects.equals(diary.getUser().getId(), user.getId()) || diary.isPublic()) {
      return diary;
    }
    throw new CustomException(ErrorCode.DIARY_IS_NOT_PUBLIC);
  }


}
