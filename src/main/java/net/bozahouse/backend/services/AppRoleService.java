package net.bozahouse.backend.services;


import net.bozahouse.backend.dtos.AppRoleDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.model.entities.AppRole;

public interface AppRoleService {


    AppRoleDTO createOrUpdateAppRole(AppRole role);

    AppRole getRole(Long roleId);

    AppRoleDTO getRoleDTO(Long roleId);

    AppRole findByName(String name);

    AppRoleDTO findByNameDTO(String name);

    PageDTO<AppRoleDTO> listRole(int page, int size);

    void deleteRole(Long roleId);
}
