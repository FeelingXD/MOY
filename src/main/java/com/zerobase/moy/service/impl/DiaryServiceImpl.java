package com.zerobase.moy.service.impl;

import com.zerobase.moy.data.domain.DiaryDocument;
import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryRequestDto;
import com.zerobase.moy.data.model.diarydocument.DiaryDocumentDto;
import com.zerobase.moy.repository.elastic.DiaryDocumentRepository;
import com.zerobase.moy.repository.jpa.DiaryRepository;
import com.zerobase.moy.response.exception.CustomException;
import com.zerobase.moy.response.exception.ErrorCode;
import com.zerobase.moy.service.DiaryService;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;

  private final DiaryDocumentRepository diaryDocumentRepository;

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

  @Override
  public Page<DiaryDocumentDto> getPublicDiaries(Pageable pageable) {
    return new PageImpl<>(diaryDocumentRepository.findAllByDeletedIsFalseAndIsPublicIsTrue(pageable)
        .stream()
        .map(DiaryDocumentDto::of)
        .collect(Collectors.toList()));
  }

  @Override
  public Page<DiaryDocumentDto> searchDiaries(String query, Pageable pageable) {
    return new PageImpl<>(diaryDocumentRepository.searchByFields(query, pageable)
        .stream()
        .map(DiaryDocumentDto::of)
        .collect(Collectors.toList()));
  }

}
