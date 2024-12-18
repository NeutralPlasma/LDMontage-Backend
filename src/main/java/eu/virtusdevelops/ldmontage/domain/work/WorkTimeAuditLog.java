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
public class WorkTimeAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fieldName;
    private String oldValue;
    private String newValue;

    @ManyToOne
    private User modifiedBy;

    @ManyToOne
    private WorkTime workTime;


    // datetime stuff
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private final Date createdAt = new Date();

}
