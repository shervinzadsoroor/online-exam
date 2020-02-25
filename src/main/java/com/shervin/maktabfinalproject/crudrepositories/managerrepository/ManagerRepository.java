package com.shervin.maktabfinalproject.crudrepositories.managerrepository;

import com.shervin.maktabfinalproject.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
