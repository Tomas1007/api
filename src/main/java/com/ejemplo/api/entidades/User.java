package com.ejemplo.api.entidades;

import com.ejemplo.api.token.Token;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


    @Entity
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "users")
    public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name= "nombre")
        private String name;
        @Column(name= "apellido")
        private String lastName;
        private String email;
        @Column(name= "contrasenia")
        private String password;
        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "users_rol",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
        private Set<Rol> roles = new HashSet<>();

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Inmueble> inmuebles = new HashSet<>();

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Token> tokens = new HashSet<>();

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles.stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getNombreRol())).collect(Collectors.toList());
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




