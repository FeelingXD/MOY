package com.zerobase.moy.controller;

import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Api(tags = {"Diary Controller 게시물(다이어리) API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

  private final DiaryService diaryService;

  @PostMapping
  @ApiOperation(value = "다이어리 작성" , notes = "다이어리를 작성합니다.")
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
  @ApiOperation(value = "다이어리 가져오기" , notes = "id 에 해당하는 다이어리를 가져옵니다.")

  public ResponseEntity<?> getDiary(@AuthenticationPrincipal User user,@Parameter(description = "Diary id") @PathVariable Long id) {
    var result = diaryService.getDiary(user, id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_CREATED)
            .data(DiaryResultDto.of(result))
            .build());
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "다이어리 수정" , notes = "id 에 해당하는 다이어리를 수정합니다.")

  public ResponseEntity<?> putDiary(@AuthenticationPrincipal User user,@Parameter(description = "Diary id") @PathVariable Long id,
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
  @ApiOperation(value = "다이어리 삭제" , notes = "id 에 해당하는 다이어리를 삭제합니다.")

  public ResponseEntity<?> deleteDiary(@AuthenticationPrincipal User user,@Parameter(description = "Diary id") @PathVariable Long id) {
    diaryService.deleteDiary(user, id);
    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_DELETED)
            .build());
  }

  @GetMapping("/public")
  @ApiOperation(value = "공개 다이어리 가져오기" , notes = "공개목록 다이어리를 가져옵니다.")

  public ResponseEntity<?> testGetPublicDiaries(
      @RequestParam(defaultValue = "0") @ApiParam("시작  페이지") int page,
      @RequestParam(defaultValue = "10") @ApiParam("불러올 최대 갯수") int size,
      @RequestParam(defaultValue = "create_date") @ApiParam("칼럼이름 default:작성일자")String sortType) {
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
  @ApiOperation(value = "다이어리 검색" , notes = "단어로 검색합니다.")

  public ResponseEntity<?> testSearch(@Parameter(description = "검색어") @RequestParam String query,
      @RequestParam(defaultValue = "0") @ApiParam("시작  페이지") int page,
      @RequestParam(defaultValue = "10") @ApiParam("불러올 최대 갯수") int size,
      @RequestParam(defaultValue = "create_date") @ApiParam("칼럼이름 default:작성일자")String sortType) {
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
