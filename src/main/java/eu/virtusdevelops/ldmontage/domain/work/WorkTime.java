package eu.virtusdevelops.ldmontage.domain.work;

import eu.virtusdevelops.ldmontage.domain.location.GPSLocation;
import eu.virtusdevelops.ldmontage.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date startTime;
    private Date endTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="latitude", column=@Column(name="start_latitude")),
            @AttributeOverride(name="longitude", column=@Column(name="start_longitude")),
            @AttributeOverride(name="recordedTime", column=@Column(name="start_location_time"))
    })
    private GPSLocation startLocation;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="latitude", column=@Column(name="end_latitude")),
            @AttributeOverride(name="longitude", column=@Column(name="end_longitude")),
            @AttributeOverride(name="recordedTime", column=@Column(name="end_location_time"))
    })
    private GPSLocation endLocation;


    // all breaks
    @OneToMany(mappedBy = "worktime", cascade = CascadeType.ALL)
    Set<Break> breaks;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "worksite_id")
    private WorkSite workSite;

    @OneToMany(mappedBy = "worktime", cascade = CascadeType.ALL)
    List<WorkTimeAuditLog> auditLog;


    // datetime stuff
    @Builder.Default
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();
    @Builder.Default
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
