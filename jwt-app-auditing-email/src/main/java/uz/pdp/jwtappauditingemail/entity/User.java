package uz.pdp.jwtappauditingemail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id ; //id user ning takrorlanmas qismi

    @Column(nullable = false,length = 250)
    private String firstName; //ismi

    @Column(nullable = false)
    private String lastName; //familiyasi

    @Column(nullable = false, unique = true)
    private String email; //emaili => username sifatida ketadi .

    @Column(nullable = false)
    private String password ; //passwordi

    @Column(nullable = false , updatable = false)
    @CreationTimestamp() //avtomat qachon royxatdan otganligini
    private Timestamp createdAt; //qachon ro'yxatdan o'tganligi

    @UpdateTimestamp  //aftomat taxrirlangani yozib qoyadi
    private Timestamp updatedAt;  //qachon taxirilangani

    @ManyToMany
    private Set<Role> roles;

    private boolean accountNonExpired = true; //bu userni amal qilish muddati o'tmagan
    private boolean accountNonLocked = true ; //bu user bloklanmaganligi
    private boolean credentialsNonExpired = true; // bu user
    private boolean enabled = false ; //bu user

    private String emailCode ;


    // ============   BULAR USERDETAILS KLASSINI ABSTRACT METHOD LARI ===========

    //BU USERNING HUQUQLARI
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    //USERNING USERNAME NI QAYTARUVCHI METHOD
    @Override
    public String getUsername() {
        return this.email;
    }

    //BU AKKOUNT NING AMAL QILISH MUDDATINI QAYTARADI
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    //BU AKKOUNT BLOKLANLIGI XOLATINI QAYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    //BU AKKOUNT NING ISHONCHLILIGIK MUDDATI TUGAGAN YOKI TUGAMAGANLIGINI QAYTARADI
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    //BU AKKOUNT AKTIV YOKI OCHIQLIGINI QAYTARADI
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
