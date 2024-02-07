package com.LogisticsCompany.model;

import com.LogisticsCompany.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/**
 * Represents user credentials in the system.
 */
@Entity
@Data
@NoArgsConstructor
@Builder
public class Credentials extends IdGenerator implements UserDetails {
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Long connectedId;

    public Credentials(String username, String password, String email, AccountType accountType, Long connectedId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
        this.connectedId = connectedId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(accountType.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
