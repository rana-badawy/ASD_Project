package edu.miu.cs.cs489.surgeries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false)
    @NotBlank(message = "Role Name is required")
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
