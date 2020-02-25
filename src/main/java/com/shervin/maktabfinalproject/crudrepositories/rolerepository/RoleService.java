package com.shervin.maktabfinalproject.crudrepositories.rolerepository;

import com.shervin.maktabfinalproject.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void saveRole(Role role){
        roleRepository.save(role);
    }

    public Role findRoleById(Long id){
        return roleRepository.findById(id).get();
    }

}
