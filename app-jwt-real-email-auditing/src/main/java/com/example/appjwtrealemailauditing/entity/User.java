package com.example.appjwtrealemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id ;


    @Column(nullable = false , length = 50)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email ;


    private String password ;


    @CreationTimestamp
    private Timestamp createdAt ;


    @UpdateTimestamp
    private Timestamp updatedAt ;

    @ManyToMany
    private Set<Role> roles;

    private boolean AccountNonExpired = true;
    private boolean AccountNonLocked = true;
    private boolean CredentialsNonExpired = true;
    private boolean Enabled = false ;

    // ========= User details interface ni => method lari ========

    // ====== Use ro'yxati xuquqlari =====
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.AccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.AccountNonLocked;
    }

    //Account ishonchliligi muddati tugagan yoki tugamaganligini qaytaradi .
    @Override
    public boolean isCredentialsNonExpired() {
        return this.CredentialsNonExpired;
    }

    //Account yoniq yoki aktivligini | aktiv emaligini qaytaradi .
    @Override
    public boolean isEnabled() {
        return this.Enabled;
    }

    private String emailCode;
}
