package uz.usmonov.appjwtrealemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners (AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @CreatedBy
    private UUID createdBy;//kim qoshganligi




    @LastModifiedBy  //=>Auditing qilyapti
    private UUID updatedBy;//kim edit qiganligi

    @CreationTimestamp()
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;


}