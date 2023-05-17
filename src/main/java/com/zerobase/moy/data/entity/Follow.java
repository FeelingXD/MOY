package com.zerobase.moy.data.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

  @EmbeddedId
  private FollowId id;
  @ManyToOne
  @MapsId("fromUserId")
  private User fromUser;
  @ManyToOne
  @MapsId("toUserId")
  private User toUser;


  @Embeddable
  @NoArgsConstructor
  public static class FollowId implements Serializable {

    private Long fromUserId;
    private Long toUserId;

    public FollowId(Long fromUserId, Long toUserId) {
      this.fromUserId = fromUserId;
      this.toUserId = toUserId;
    }
  }
}
