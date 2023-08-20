package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //적용되는 클래스는 테이블로 생성되지 않는다. BaseEntity를 상속 받은 클래스만 db 테이블이 생성이된다.
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    @CreatedDate //엔티티 생성 시간을 처리
    @Column(name = "regdate", updatable = false) //updatable = false을 사용해 컬럼 값이 변경되지 않음
    private LocalDateTime regDate;

    @LastModifiedDate //엔티티 최종 수정 시간을 자동으로 처리
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
