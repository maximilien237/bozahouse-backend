package net.bozahouse.backend.model.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @CreatedDate
  @Column(name = "createdAt", nullable = false, updatable = false)
  private Date createdAt;

  @LastModifiedDate
  @Column(name = "updateAt")
  private Date updateAt;


}
