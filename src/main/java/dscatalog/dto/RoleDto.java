package dscatalog.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link dscatalog.entities.Role} entity
 */
public class RoleDto implements Serializable {
    private final Long id;
    private final String authority;

    public RoleDto(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto entity = (RoleDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.authority, entity.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "authority = " + authority + ")";
    }
}