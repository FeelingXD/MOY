package com.zerobase.moy.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.diary.DiaryForm;
import com.zerobase.moy.data.model.diary.DiaryResultDto;
import com.zerobase.moy.repository.DiaryRepository;
import com.zerobase.moy.service.DiaryService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

@WithMockUser
@ExtendWith(MockitoExtension.class)
class DiaryServiceImplTest {

  DiaryService diaryService;

  @Mock
  DiaryRepository diaryRepository;

  User stub_user = User.builder()
      .id(1L)
      .email("Origin")
      .build();

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    diaryService = new DiaryServiceImpl(diaryRepository);

    Authentication auth = mock(Authentication.class);
    lenient().when(auth.getPrincipal()).thenReturn(stub_user);

    SecurityContext securityContext = mock(SecurityContext.class);
    lenient().when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  @WithUserDetails("test")
  void post() {
    User tmp = User.builder().build();
    System.out.println(SecurityContextHolder.getContext().getAuthentication());
    var testForm = DiaryForm.builder()
        .title("목테스트")
        .content("내용")
        .build();
    //given

    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .build();
    when(diaryRepository.save(any())).thenReturn(mockDiary);
    //when
    var testDto = diaryService.postDiary(testForm);
    //then
    assertEquals(testDto.getTitle(), DiaryResultDto.of(mockDiary).getTitle());
  }

  @Test
  void patch_pass() {
    //given
    var mockForm = DiaryForm.builder()
        .title("patch")
        .content("변경")
        .build();

    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .user(stub_user)
        .build();
    //when
    Diary diary = new Diary();
    diary.setId(1L);
    diary.setTitle("Old Title");
    diary.setContent("Old Content");
    diary.setUser(stub_user);

    when(diaryRepository.findById(1L)).thenReturn(Optional.of(diary));
    when(diaryRepository.save(diary)).thenReturn(diary);

    DiaryResultDto result = diaryService.patchDiary(1L, mockForm);
    //then
    verify(diaryRepository).save(diary);
    assertEquals(result.getContent(), mockForm.getContent());
    assertEquals(result.getTitle(), mockForm.getTitle());

  }

  @Test
  void delete_pass() {
    //given
    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .isPublic(true)
        .user(stub_user)
        .build();
    //when
    when(diaryRepository.findById(any())).thenReturn(Optional.of(mockDiary));
    when(diaryRepository.save(mockDiary)).thenReturn(mockDiary);
    //then
    diaryService.deleteDiary(1L);
    assertTrue(mockDiary.isDeleted());
  }
  @Test
  void get_pass() {
      //given
    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .user(stub_user)
        .build();
      //when
    when(diaryRepository.findById(any())).thenReturn(Optional.of(mockDiary));
      //then
    var result=diaryService.getDiary(1L);
    assertEquals(result.getTitle(),mockDiary.getTitle());
  }

  @Test
  void get_Diary_notPublic(){
    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .isPublic(false)
        .user(new User())
        .build();
    when(diaryRepository.findById(any())).thenReturn(Optional.of(mockDiary));

    assertThrows(RuntimeException.class,() -> diaryService.getDiary(1L));
  }

  @Test
  void get_Diary_deleted(){
    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .isPublic(false)
        .user(stub_user)
        .user(new User())
        .build();
    mockDiary.setDeleted(true);

    //when

    //then
    assertThrows(IllegalArgumentException.class,() -> diaryService.getDiary(1L));
  }
  @Test
  void try_access_others_Diary(){
    //given
    var mockDiary = Diary.builder()
        .id(1L)
        .title("목테스트")
        .content("내용")
        .isPublic(false)
        .user(stub_user)
        .user(new User())
        .build();
    var mockForm = DiaryForm.builder()
        .title("patch")
        .content("변경")
        .build();

    //when
    when(diaryRepository.findById(1L)).thenReturn(Optional.ofNullable(mockDiary));

    //
    assertThrows(RuntimeException.class,() -> diaryService.patchDiary(1L,mockForm));
  }



}