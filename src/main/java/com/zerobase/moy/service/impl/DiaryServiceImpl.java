package com.zerobase.moy.service.impl;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.repository.DiaryRepository;
import com.zerobase.moy.service.DiaryService;
import java.util.Objects;
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
    return diaryRepository.save(diary);
  }

  @Override
  public Diary patchDiary(Long id, DiaryForm form) {

    var diary=diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 포스트가 없습니다."));
    var user=  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(!Objects.equals(diary.getUser().getId(), user.getId())){
      throw new RuntimeException("권한이 없습니다.");
    }

    diary.setContent(form.getContent());
    diary.setTitle(form.getTitle());


    return diaryRepository.save(diary);
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
  public Diary getDiary(Long id) {
    var diary=diaryRepository.findById(id).filter(d->!d.isDeleted()).orElseThrow(()->new IllegalArgumentException("해당하는 포스트가 없습니다."));
    var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(Objects.equals(diary.getUser().getId(), user.getId()) || diary.isPublic()){
      return diary;
    }
    throw new RuntimeException("비공개 포스트입니다.");
  }


}
