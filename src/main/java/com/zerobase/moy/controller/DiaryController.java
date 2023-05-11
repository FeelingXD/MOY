package com.zerobase.moy.controller;

import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;
import com.zerobase.moy.service.DiaryService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

  private final DiaryService diaryService;

  @PostMapping
  public ResponseEntity<?> postDiary(@RequestBody DiaryForm form) {
    var diary=diaryService.postDiary(form);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .build(diary.getId());
    return ResponseEntity.created(location).body(DiaryResultDto.of(diary));
  }
  @GetMapping("/{id}")
  public ResponseEntity<?> getDiary(@PathVariable Long id){
    return ResponseEntity.ok().body(diaryService.getDiary(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> patchDiary(@PathVariable Long id,@RequestBody DiaryForm form) {

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(id);

    return ResponseEntity.created(location).body(diaryService.patchDiary(id,form));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDiary(@PathVariable Long id){
    diaryService.deleteDiary(id);
    return ResponseEntity.accepted().body("삭제 되었습니다.");
  }
}
