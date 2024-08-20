package eu.virtusdevelops.ldmontage.domain.work;


import eu.virtusdevelops.ldmontage.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WorkSite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private float latitude;
    private float longitude;



    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "worksite_user",
            joinColumns = {@JoinColumn(name = "worksite_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    Set<User> authorizedWorkers;



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
