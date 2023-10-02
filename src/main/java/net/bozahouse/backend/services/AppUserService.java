package net.bozahouse.backend.services;


import net.bozahouse.backend.dtos.AppUserDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.model.entities.AppUser;

public interface AppUserService {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    AppUser getAppUser(Long userId);

    AppUserDTO getAppUserDTO(Long userId);

    AppUser getAppUserByEmail(String email);

    AppUserDTO getAppUserByEmailDTO(String email);

    AppUserDTO updateAppUser(AppUserDTO dto);

    void deleteAppUser(Long userId);

    PageDTO<AppUserDTO> listAppUser(int page, int size);

    //PageDTO<AppUserDTO> listAppUserWithRole(String roleName, int page, int size);

    AppUserDTO addRoleToUser(String email, String roleName);

    AppUserDTO removeRoleToUser(String email, String roleName);

    PageDTO<AppUserDTO> findAllByUsername(String key, int page, int size);

    PageDTO<AppUserDTO> listAppUserDisabledByUsername(String key, int page, int size);

    void enabledOrDisableAppUser(Long appUserId);
}
