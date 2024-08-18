package eu.virtusdevelops.ldmontage.domain.work;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Break {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Date startTime;
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "worktime_id")
    private WorkTime workTime;


    // TODO: start and end locations


    // datetime stuff
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();
    @LastModifiedDate
    private Date updatedAt = new Date();

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }
}
