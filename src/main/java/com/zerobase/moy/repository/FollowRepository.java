package com.zerobase.moy.repository;

import com.zerobase.moy.data.entity.Follow;
import com.zerobase.moy.data.entity.Follow.FollowId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId>,FollowRepositoryCustom {

  Integer countByFromUserId(Long fromUserId); //팔로윙 수

  Integer countByToUserId(Long toUserId); //팔로워 수
  Optional<Follow> findById(FollowId id);
}
