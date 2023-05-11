package com.zerobase.moy.service.impl;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;
import com.zerobase.moy.repository.DiaryRepository;
import com.zerobase.moy.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;
  @Override
  public Diary postDiary(DiaryForm form) {

    var user=  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var diary=DiaryForm.toEntity(form);
    diary.setUser(user);
    Diary savedResult=diaryRepository.save(diary);

    return savedResult;
  }

  @Override
  public DiaryResultDto patchDiary(Long id, DiaryForm form) {

    var diary=diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 포스트가 없습니다."));
    var user=  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(!diary.getUser().equals(user)){
      throw new RuntimeException("권한이 없습니다.");
    }

    diary.setContent(form.getContent());
    diary.setTitle(form.getTitle());

    Diary savedResult=diaryRepository.save(diary);

    return DiaryResultDto.of(savedResult);
  }

  @Override
  public void deleteDiary(Long id) {
    var diary=diaryRepository.findById(id).filter(d->!d.isDeleted()).orElseThrow(()->new IllegalArgumentException("해당하는 포스트가 없습니다."));
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(!diary.getUser().equals(user)){
      throw new RuntimeException("권한이 없습니다.");
    }
    diary.setDeleted(true);
    diaryRepository.save(diary);
  }

  @Override
  public DiaryResultDto getDiary(Long id) {
    var diary=diaryRepository.findById(id).filter(d->!d.isDeleted()).orElseThrow(()->new IllegalArgumentException("해당하는 포스트가 없습니다."));
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(diary.getUser().equals(user)|| diary.isPublic()){
      return DiaryResultDto.of(diary);
    }
    throw new RuntimeException("비공개 포스트입니다.");
  }


}
