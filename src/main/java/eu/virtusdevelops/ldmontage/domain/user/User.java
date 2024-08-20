package eu.virtusdevelops.ldmontage.domain.user;

import eu.virtusdevelops.ldmontage.domain.permission.Permission;
import eu.virtusdevelops.ldmontage.domain.permission.Role;
import eu.virtusdevelops.ldmontage.domain.work.WorkTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date birthDate;



    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean verified;


    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean locked;


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }




    // permissions
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permissions",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    private List<Permission> permissions;


    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // return authority list from all the role permissions
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
            }
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        for (Permission permission : permissions) {
            if (authorities.contains(new SimpleGrantedAuthority(permission.getPermission()))) {
                continue;
            }
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        return authorities;

    }

    // work times

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkTime> workTimers = new HashSet<>();


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
