package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.AppRoleDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.repositories.AppRoleRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppRoleServiceImpl implements AppRoleService{
    private AppRoleRepo roleRepo;


    @Override
    public AppRoleDTO createOrUpdateAppRole(AppRole role) {
        log.info("creating role...");
        AppRole appRole = roleRepo.save(role);
        return AppRoleDTO.mapToDTO(appRole);
    }

    @Override
    public AppRole getRole(Long roleId)  {
        log.info("getting role by id :: " +roleId +"...");
        Optional<AppRole> optionalAppRole = roleRepo.findById(roleId);
        if (optionalAppRole.isPresent()){
            return optionalAppRole.get();
        }else {
            throw new EntityNotFoundException("Role not found for id :: "+roleId);
        }

    }

    @Override
    public AppRoleDTO getRoleDTO(Long roleId)  {
        log.info("getting role by id :: " +roleId +"...");
        return AppRoleDTO.mapToDTO(getRole(roleId));

    }

    @Override
    public AppRole findByName(String name) {
        log.info("getting role by name :: " +name +"...");
        Optional<AppRole> optionalAppRole = roleRepo.findByName(name);
        if (optionalAppRole.isPresent()){
            return optionalAppRole.get();
        }else {
            throw new EntityNotFoundException("Role not found for name :: "+name);
        }
    }

    @Override
    public AppRoleDTO findByNameDTO(String name) {
        log.info("getting role by name :: " +name +"...");
        return AppRoleDTO.mapToDTO(findByName(name));
    }

    @Override
    public PageDTO<AppRoleDTO> listRole(int page, int size) {
        log.info("list role ...");
        Page<AppRole> roles = roleRepo.findAllByOrderByNameAsc(PageRequest.of(page, size));
       return PageDTO.mapToAppRolePageDTO(roles);

    }

    @Override
    public void deleteRole(Long roleId) {
        log.info("deleting role by id :: " +roleId +"...");
        if (roleRepo.existsById(roleId)) {
            roleRepo.delete(getRole(roleId));
        }else {
            throw new EntityNotFoundException("Role not found for id :: " +roleId);
        }


    }

}
