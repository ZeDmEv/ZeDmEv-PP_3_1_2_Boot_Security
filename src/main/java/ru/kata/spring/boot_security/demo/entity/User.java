package ru.kata.spring.boot_security.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Заполните имя")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Column(name = "password")
    private String password;

    @Column(name = "job")
    private String job;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    public User() {
    }
    public User(String name, String job, String password) {
        this.username = name;
        this.job = job;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getRolesToString() {
        StringBuilder tmp = new StringBuilder();
        for (Role r : roles) {
            tmp
                    .append(r.getName())
                    .append(", ");
        }
        if (tmp.length() > 0){
            tmp.delete(tmp.length() - 2, tmp.length());
        }
        return tmp.toString();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", job='" + job + '\'' +
                ", roles=" + roles +
                '}';
    }
}

