package ru.kata.spring.boot_security.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_role")
public class Role {
    @Id
    private Long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(){
    }

    public Role(Long id){
        this.id = id;
    }

    public Role(Long id, String name) {
        this(id);
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
