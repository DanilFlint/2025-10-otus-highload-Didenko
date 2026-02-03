package ru.didenko.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class User implements UserDetails {

    private Long id;

    private String username;

    private String lastname;

    private String dateOfBirth;

    private String city;

    private String gender;

    private String interests;

    private transient final String password;

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public UserDTO toDto() {
        return UserDTO.builder()
                .id(id)
                .username(username)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .city(city)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
