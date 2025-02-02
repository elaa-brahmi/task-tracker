package com.example.task_tracker.user;

import com.example.task_tracker.notification.Notification;
import com.example.task_tracker.task.Task;
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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails , Principal {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountLocked;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy="assignee", cascade=CascadeType.ALL, orphanRemoval=true,fetch=FetchType.LAZY)
    private List<Task> tasks;
    // we can have private List<Role> roles;
    //but in my application a user has only one role which user
    @OneToMany(mappedBy="assignee", cascade=CascadeType.ALL, orphanRemoval=true,fetch=FetchType.LAZY)
    private List<Notification> notifs;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
   @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

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
        return !accountLocked ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public  String getfullName(){
        return firstName + " " + lastName;
    }
}
