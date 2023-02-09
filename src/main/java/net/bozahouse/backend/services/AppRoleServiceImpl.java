package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.exception.entitie.RoleNotFoundException;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.repositories.AppRoleRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppRoleServiceImpl implements AppRoleService{
    private AppRoleRepo roleRepo;


    @Override
    public void createRole(AppRole role) {
        log.info("creating role...");
        roleRepo.save(role);
    }

    @Override
    public AppRole getRole(Integer roleId) throws RoleNotFoundException {
        log.info("getting role by id :: " +roleId +"...");
        Optional<AppRole> optionalRole = roleRepo.findById(roleId);
        AppRole role;
        if (optionalRole.isPresent()){
            role = optionalRole.get();
        }else {
            throw new RoleNotFoundException("");
        }
        return role;
    }

    @Override
    public AppRole findByName(String name) {
        log.info("getting role by name :: " +name +"...");
        return roleRepo.findByName(name).get();
    }

    @Override
    public AppRole updateRole(AppRole role) throws RoleNotFoundException {
        log.info("updating role ...");
        AppRole role1 = roleRepo.save(role);
        return role1;
    }

    @Override
    public List<AppRole> listRole(int page, int size) {
        log.info("list role ...");
        List<AppRole> roles = roleRepo.listRole(PageRequest.of(page, size));
        AppRole role = roles.get(0);
        role.setCurrentPage(page);
        role.setPageSize(size);
        role.setTotalPages( (roles.size() / 5) + 1 );

        return roles;
    }

    @Override
    public void deleteRole(Integer roleId) throws RoleNotFoundException {
        log.info("deleting role by id :: " +roleId +"...");
        AppRole role = getRole(roleId);
            roleRepo.deleteById(role.getId());
    }

    @Override
    public void deleteAllRole() {
        log.info("deleting all role...");
        roleRepo.deleteAll();
    }

}
