package net.bozahouse.backend.services;

import net.bozahouse.backend.exception.entitie.RoleNotFoundException;
import net.bozahouse.backend.model.entities.AppRole;

import java.util.List;

public interface AppRoleService {
    void createRole(AppRole role);

    AppRole getRole(Integer roleId) throws RoleNotFoundException;

    AppRole findByName(String name);

    AppRole updateRole(AppRole role) throws RoleNotFoundException;

    List<AppRole> listRole(int page, int size);

    void deleteRole(Integer roleId) throws RoleNotFoundException;

    void deleteAllRole();
}
