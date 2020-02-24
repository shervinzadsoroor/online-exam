package com.shervin.maktabfinalproject.crudrepositories.rolerepository;

import com.shervin.maktabfinalproject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
