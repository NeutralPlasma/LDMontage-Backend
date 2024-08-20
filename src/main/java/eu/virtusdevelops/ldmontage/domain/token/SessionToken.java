package eu.virtusdevelops.ldmontage.domain.token;

import eu.virtusdevelops.ldmontage.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SessionToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;
    private boolean validated;
    private Date expiresOn;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



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
