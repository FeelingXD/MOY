package com.zerobase.moy.controller;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;
import com.zerobase.moy.service.DiaryService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  public ResponseEntity<?> postDiary(@AuthenticationPrincipal User user,
      @RequestBody DiaryForm form) {
    var result = diaryService.postDiary(user, DiaryForm.toDiaryRequestDto(form));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .build(result.getId());

    return ResponseEntity.created(location).body(DiaryResultDto.of(result));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getDiary(@AuthenticationPrincipal User user, @PathVariable Long id) {
    var result = diaryService.getDiary(user, id);
    return ResponseEntity.ok().body(DiaryResultDto.of(result));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> patchDiary(@AuthenticationPrincipal User user, @PathVariable Long id,
      @RequestBody DiaryForm form) {

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(id);
    var result = diaryService.patchDiary(user, id, form);
    return ResponseEntity.created(location).body(DiaryResultDto.of(result));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDiary(@AuthenticationPrincipal User user, @PathVariable Long id) {
    diaryService.deleteDiary(user, id);
    return ResponseEntity.accepted().body("삭제 되었습니다.");
  }
}