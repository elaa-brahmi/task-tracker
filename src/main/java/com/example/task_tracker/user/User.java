package com.example.task_tracker.user;

import com.example.task_tracker.model.Task;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Data
@Entity
@Table(name="users")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User implements UserDetails {
    public User(String firstName, String email,String password,UserRole role ) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.role = role;

    }
    public User(String username, UserRole role, String password, boolean locked, String email, boolean enabled, List<Task> tasks) {
        this.firstName = username;
        this.role = role;
        this.password = password;
        this.locked = locked;
        this.email = email;
        this.enabled = enabled;
        this.tasks = tasks;
    }
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize=1
    )

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"

    )
    @Column(name="user_id")
    private Integer idUser;

    @Column(name="firstName")
    private String firstName;
    @Column(name="UserRole")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    private boolean locked;
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    //@JsonManagedReference: Indicates that this is the forward part of the relationship (e.g., User -> Task).
    private List<Task> tasks;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }
// dunno yomken yrajaa username
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

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
