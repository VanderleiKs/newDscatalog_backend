package dscatalog.dto;

import dscatalog.entities.Role;

import java.io.Serializable;

/**
 * A DTO for the {@link dscatalog.entities.Role} entity
 */
public class RoleDTO implements Serializable {
    private Long id;
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

}