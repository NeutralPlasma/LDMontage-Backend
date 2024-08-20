package eu.virtusdevelops.ldmontage.domain.work;

import eu.virtusdevelops.ldmontage.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BreakAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fieldName;
    private String oldValue;
    private String newValue;

    @ManyToOne
    private User modifiedBy;

    @ManyToOne
    private Break workBreak;



    // datetime stuff
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
    }
}
