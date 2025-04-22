package edu.miu.cs.cs489.surgeries.repository;

import edu.miu.cs.cs489.surgeries.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
