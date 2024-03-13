package ru.uvuv643.business_logic.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.Nested;
import ru.uvuv643.business_logic.annotations.MustBeSeeded;
import ru.uvuv643.business_logic.enums.RoleEnum;
import ru.uvuv643.business_logic.models.general.AbstractSeeder;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@Entity
@Table(name = "roles")
@MustBeSeeded
public class Role extends AbstractSeeder {

    public final static Map<RoleEnum, Long> roles = Map.ofEntries(
            entry(RoleEnum.ADMIN, 1L),
            entry(RoleEnum.USER, 2L)
    );

    @Override
    @Transient
    @JsonIgnore
    public List<AbstractSeeder> getDefaultObjects() {
        return roles.keySet().stream().map((key) -> new Role(roles.get(key), key.getCode())).collect(Collectors.toList());
    }

    public Role() {}

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    @Id
    private Long id;

    @Column
    private String role;

//    @ManyToMany(cascade = CascadeType.ALL )
//    @JoinTable(name = "roles_users",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private List<User> users = new ArrayList<>();
//
//    public List<User> getUsers() {
//        return users;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
