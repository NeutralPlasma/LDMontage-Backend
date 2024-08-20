package eu.virtusdevelops.ldmontage.domain.work;


import eu.virtusdevelops.ldmontage.domain.location.GPSLocation;
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

    @Enumerated(EnumType.ORDINAL)
    private BreakType breakType;
    @OneToMany(mappedBy = "break", cascade = CascadeType.ALL)
    List<BreakAuditLog> auditLog;

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
