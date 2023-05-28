package com.zerobase.moy.controller;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.DiaryService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

  private final DiaryService diaryService;

  @PostMapping

  public ResponseEntity<?> postDiary(@AuthenticationPrincipal User user,
      @RequestBody @Validated DiaryForm form) {
    var result = diaryService.postDiary(user, DiaryForm.toDiaryRequestDto(form));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .build(result.getId());

    return ResponseEntity.created(location).body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_CREATED)
            .data(DiaryResultDto.of(result))
            .build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getDiary(@AuthenticationPrincipal User user, @PathVariable Long id) {
    var result = diaryService.getDiary(user, id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_CREATED)
            .data(DiaryResultDto.of(result))
            .build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> putDiary(@AuthenticationPrincipal User user, @PathVariable Long id,
      @Validated @RequestBody DiaryForm form) {

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(id);
    var result = diaryService.putDiary(user, id, DiaryForm.toDiaryRequestDto(form));
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_CREATED)
            .data(DiaryResultDto.of(result))
            .build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDiary(@AuthenticationPrincipal User user, @PathVariable Long id) {
    diaryService.deleteDiary(user, id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_DELETED)
            .build());
  }

  @GetMapping("/public")
  public ResponseEntity<?> testGetPublicDiaries(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "create_date") String sortType) {
    Sort sort = Sort.by(Direction.DESC, sortType);

    PageRequest pageRequest = PageRequest.of(page, size, sort);
    var result = diaryService.getPublicDiaries(pageRequest);

    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result).build()
    );
  }

  @GetMapping("/search")
  public ResponseEntity<?> testSearch(@RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "create_date") String sortType) {
    Sort sort = Sort.by(Direction.DESC, sortType);
    PageRequest pageRequest = PageRequest.of(page, size, sort);
    var result = diaryService.searchDiaries(query, pageRequest);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build()
    );
  }
}
