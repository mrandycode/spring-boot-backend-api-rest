package com.yo.minimal.rest.models.entity;

import com.yo.minimal.rest.constants.enums.RoleName;

import javax.persistence.*;

import javax.validation.constraints.NotNull;


@Entity
@Table(name="roles_users")
public class RoleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleName roleName;

    public RoleUser() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
