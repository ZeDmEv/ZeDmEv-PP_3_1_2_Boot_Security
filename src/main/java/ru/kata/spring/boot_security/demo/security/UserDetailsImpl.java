package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Collection;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    // Нужен для авторизации, возвращает список прав пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //Возвращает пароль юзера
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    //Возвращает имя юзера
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    //Проверяет активен ли аккаунт/не просрочен ли
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Проверяет заблокирован ли аккаунт
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Проверяет просрочен ли пароль
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Проверяет включен ли аккаунт
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser(){
        return this.user;
    }
    public Set<Role> getRoles(){
        return this.user.getRoles();
    }
}
